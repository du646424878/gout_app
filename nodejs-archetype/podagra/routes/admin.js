var express = require('express');
var router = express.Router();
var async = require("async");
var util = require('../lib/util');
var MemoryCache = require('cache-in-memory').MemoryCache;
var cache = new MemoryCache();

// 这个路径下的api都是直接返回json格式数据，而不是message

// /admin/db/..../gets方法是为了easyui而设计的
// -----------------------------------------------------------------

router.all("/db/:view/gets/:col/:value", (req, res, next) => {
  // 这个 API 是为了个体的每周每月查询而设计，但是也可以查询其他内容
  var page = parseInt(req.body.page) || 1;
  var rows = parseInt(req.body.rows) || 10;
  var view = req.params.view;
  var col = req.params.col;
  var value = parseInt(req.params.value);
  value = isNaN(value) ? req.params.value : value; // 如果不是数字的话转换成原始的字符串
  var offset = (page - 1) * rows;
  var total = 0;
  async.waterfall([
    (cb) => {
      req.db.driver.execQuery("select count(*) as `count` from ?? where ?? = ? ", [view, col, value], (err, data) => {
        if (err) {
          console.log("there is an error \n but i think it's not important \nso i passed it");
          console.log(err);
          next();
        } else {
          total = data[0].count;
          cb(null)
        }
      })
    },
    (cb) => {
      req.db.driver.execQuery("select * from ?? where ?? = ? limit ?,?", [view, col, value, offset, rows], (err, data) => {
        if (err) throw err;
        else
          res.json({
            rows: data,
            total: total
          });
      });
    }
  ]);
});

//单纯返回某个表的所有字段
router.all("/db/:table/get",(req,res,next)=>{
  var view = req.params.table;
  var result={};
  req.db.driver.execQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.columns WHERE TABLE_NAME=?", [view], (err, data) => {
      if (err) throw err;
      else{
          for(var i=0;i<data.length;i++){
              result[data[i].COLUMN_NAME]='';
          }
          res.json(result);
      }
    });
  
})

// 返回表格文件的时候将不分页
router.all("/db/:view/gets/:col/:value/sheet", (req, res, next) => {
  var view = req.params.view;
  var col = req.params.col;
  var value = parseInt(req.params.value);
  value = isNaN(value) ? req.params.value : value;
  req.db.driver.execQuery("select * from ?? where ?? = ? ", [view, col, value], (err, data) => {
    if (err) throw err;
    else {
      util.sendSheet(res, data, view + "-" + col + "-" + value + ".xlsx");
    }
  });
});


router.all("/db/:view/gets", (req, res, next) => {
  // 返回任意一张表的EasyUI 所需字段
  var page = parseInt(req.body.page) || 1;
  var rows = parseInt(req.body.rows) || 10;
  var view = req.params.view;
  var offset = (page - 1) * rows;
  var total = 0;
  var key = `${view}-${page}-${rows}-${offset}`;
  if (cache.contains(key)) {
    res.json(cache.get(key))
  } else
    async.waterfall([
      (cb) => {
        req.db.driver.execQuery("select count(*) as `count` from ??", [view], (err, data) => {
          if (err) {
            console.log("there is an error \n but i think it's not important \nso i passed it");
            console.log(err);
            next();
          } else {
            total = data[0].count;
            cb(null)
          }
        })
      },
      (cb) => {
        req.db.driver.execQuery("select * from ?? limit ?,?", [view, offset, rows], (err, data) => {
          if (err) throw err;
          else {
            result = {
              rows: data,
              total: total
            }
            cache.set(key, result, 10 * 60 * 1000);
            res.json(result);
          }
        });
      }
    ]);

});


router.all("/db/:view/gets/sheet", (req, res, next) => {
  var view = req.params.view;
  req.db.driver.execQuery("select * from ?? ", [view], (err, data) => {
    if (err) throw err;
    else {
      util.sendSheet(res, data, view + ".xlsx");
    }
  });

});


// -----------------------------------------------------------------
// 以下是对直接操作数据库的API做预处理

router.all("/db/*", (req, res, next) => {
  // 如果传过来的还是jsondata，对以前的API做一个适配
  if (req.body.hasOwnProperty("jsondata")) {
    req.body = req.body.jsondata;
  }
  next();
});


router.all("/db/reminder/*", (req, res, next) => {
  // 如果操作者是患者 不允许操作非本人的数据
  var invalidop = false;
  if (req.session.user.usertypeid == 10 && req.body) {
    // 如果传输的是一个list
    if (Array.isArray(req.body)) {
      req.body.forEach(function(element) {
        if (element.userid != req.session.user.userid) {
          invalidop = true;
        }
      }, this);

    }
    // 如果是一个对象
    else if (req.body.userid) {
      if (req.body.userid != req.session.user.userid) {
        invalidop = true;
      }
    }
    // 如果没有参数
    else {
      req.body.userid = req.session.user.userid;
    }
  }
  if (invalidop) {
    res.send("no auth operate others data");
  } else {
    next();
  }
});

// -----------------------------------------------------------------
// 以下API是直接操作数据库的API



router.all("/db/:table/:op/:id", (req, res, next) => {
  var table = req.models[req.params.table];
  var params = req.body || {};
  var op = req.params.op;
  var id = req.params.id;
  if (table) {
    switch (op) {
      case "select":
        table.get(id, (err, data) => {
          if (err) next(err);
          else {
            res.json(data);
          }
        })
        break;
      case "delete":
        table.get(id, (err, data) => {
          if (err) next(err);
          else {
            if (data) {
              data.remove((err) => {
                if (err) next(err);
                else {
                  res.send("success");
                }
              })
            }
          }
        })
        break;
      case "update":
        table.get(id, (err, data) => {
          if (err) next(err);
          else {
            if (data) {
              data.save(params, (err) => {
                if (err) next(err);
                else res.send("success");
              })
            }
          }
        })
        break;
      default:
        next();
    }
  } else {
    res.send("no such table")
  }
});


router.all("/db/:table/:op", (req, res, next) => {
  var table = req.models[req.params.table];
  var params = req.body || {};
  var op = req.params.op;
  if (table) {
    switch (op) {
      case "insert":
        table.create([params], (err, data) => {
          if (err) next(err)
          else
            res.json(data)
        })
        break;
      case "selects":
        table.find(params, (err, data) => {
          if (err) next(err)
          else
            res.json(data);
        });
        break;
      default:
        next();
    }
  } else res.send("no such table")
});


module.exports = router;