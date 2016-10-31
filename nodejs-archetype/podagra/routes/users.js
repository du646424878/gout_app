var express = require('express');
var router = express.Router();
var util = require('../lib/util');
var fs = require("fs");
var xlsx = require("node-xlsx");

// 当前用户
router.all('/currentuser', function(req, res, next) {
  var message = {
    message: req.session.user,
    type: "obejct",
    success: true
  };
  res.json(message);
});


// 更新指定患者详情
router.all("/updatepatientdetail", function(req, res, next) {
  if (req.body.jsondata && req.body.jsondata.patientid) {
    req.models.patientdetail.find({
      patientid: req.body.jsondata.patientid
    }, function(err, details) {
      if (err) throw err;
      if (details.length > 0) {
        details[0].save(req.body.jsondata, function(err) {
          if (err) throw err;
          res.json({
            success: true
          });
        });
      } else {
        err = new Error();
        err.jsonmessage = "no such patient"
        next(err);
      }
    })
  } else {
    err = new Error();
    err.jsonmessage = "param error"
    next(err);
  }
});


// 获得一个用户实体
router.all('/getuser', function(req, res, next) {
  var message = {
    message: undefined,
    type: "obejct",
    success: false
  };
  if (req.body.jsondata && req.body.jsondata.username) {
    req.models.user.find({
      username: req.body.jsondata.username
    }, function(err, users) {
      if (err) throw err;
      if (users.length > 0) {
        message.success = true;
        message.message = users[0];
        message.message.password = undefined;
        res.json(message);
      } else {
        err = new Error();
        err.jsonmessage = "no such user";
        next(err);
      }
    });
  } else { //如果没有参数
    err = new Error();
    err.jsonmessage = "param error";
    next(err);
  }
});

router.all('/getpatientdetail', function(req, res, next) {
  var message = {
    message: undefined,
    type: "obejct",
    success: false
  };
  if (req.body.jsondata && req.body.jsondata.patientid) {
    req.models.patientdetail.find({
      patientid: req.body.jsondata.patientid
    }, function(err, details) {
      if (err) throw err;
      if (details.length > 0) {
        message.success = true;
        message.message = details[0];
        message.message.password = undefined;
        res.json(message);
      } else {
        err = new Error();
        err.jsonmessage = "no such patient";
        next(err);
      }
    });
  } else { //如果没有参数
    err = new Error();
    err.jsonmessage = "param error";
    next(err);
  }
});


router.all('/getcurrentpatientdetail', function(req, res, next) {
  var message = {
    message: undefined,
    type: "obejct",
    success: false
  };
  req.models.patientdetail.find({
    patientid: req.session.user.userid
  }, function(err, details) {
    if (err) throw err;
    if (details.length > 0) {
      message.success = true;
      message.message = details[0];
      message.message.password = undefined;
      res.json(message);
    } else {
      err = new Error();
      err.jsonmessage = "no such patient";
      next(err);
    }
  });

});

// 更新当前患者详情
router.all("/updatecurrentpatientdetail", function(req, res, next) {
  if (req.body.jsondata) {
    jsondata = req.body.jsondata;
    req.models.patientdetail.find({
      patientid: req.session.user.userid
    }, function(err, details) {
      if (err) throw err;
      if (details.length > 0) {
        jsondata.patientid = req.session.user.userid;
        if (jsondata.id) delete(jsondata.id)
        details[0].save(jsondata, function(err) {
          if (err) throw err;
          res.json({
            success: true
          });
        });
      } else {
        err = new Error();
        err.jsonmessage = "no such patient"
        next(err);
      }
    });
  } else {
    err = new Error();
    err.jsonmessage = "param error"
    next(err);

  }
});

router.all("/updatepassword", function(req, res, next) {
  next();
});

router.all("/updatecurrentuserpassword", function(req, res, next) {
  var jsondata = req.body.jsondata;
  if (jsondata && jsondata.password && jsondata.oldpassword) {
    req.models.user.find({
      userid: req.session.user.userid,
      password: util.md5(jsondata.oldpassword)
    }, function(err, users) {
      if (err) throw err;
      if (users.length > 0) {
        user = users[0];
        user.password = util.md5(req.body.jsondata.password);
        user.token = util.newuuid();
        user.save(function(err) {
          if (err) throw err;
          res.json({
            success: true,
            type: "string",
            message: "token has be reset"
          });
        });
      } else {
        err = new Error();
        err.jsonmessage = "no such user or old password wrong"
        next(err);
      }
    })
  } else {
    err = new Error();
    err.jsonmessage = "param error";
    next(err);
  }
});



// 按页获取患者列表
router.all('/getpatientsdetailbypage', function(req, res, next) {
  startindex = 0;
  endindex = 10;
  ordercol = 'userid';
  keyword = '%%'
  var sql = "select * from patientengview where usertypeid =10 order by ? limit ?,?";
  var sqlparam = [];
  if (req.body.jsondata) {
    if (req.body.jsondata.startindex) startindex = req.body.jsondata.startindex;
    if (req.body.jsondata.endindex) endindex = req.body.jsondata.endindex;
    if (req.body.jsondata.ordercol) ordercol = req.body.jsondata.ordercol;
    if (req.body.jsondata.keyword) {
      keyword = '%' + req.body.jsondata.keyword + '%';
      sql = "select * from patientengview where usertypeid = 10 and realname like ? order by ? limit ?,?";
      sqlparam = [keyword, ordercol, startindex, endindex - startindex];
    } else {
      sqlparam = [ordercol, startindex, endindex - startindex]
    }
  }
  req.db.driver.execQuery(sql, sqlparam, function(err, data) {
    if (err) throw err;
    res.json({
      message: data,
      type: "object list",
      success: true
    });
  });
});

router.all("/patientengview/gets", (req, res, next) => {
  var page = parseInt(req.body.page) || 1;
  var rows = parseInt(req.body.rows) || 10;
  var offset = (page - 1) * rows;
  var total = 50;
  req.db.driver.execQuery("select * from patientengview where usertypeid = 10  limit ?,?", [offset, rows], function(err, data) {
    if (err) throw err;
    else
      res.json({
        rows: data,
        total: total
      });
  });


});

router.all("/patientengview/gets/:order/:start/:end", (req, res, next) => {
  var ordercol = req.params.order;
  var start = parseInt(req.params.start)
  var end = parseInt(req.params.end)
  req.db.driver.execQuery("select * from patientengview where usertypeid = 10  order by ? limit ?,?", [ordercol, start, end - start], function(err, data) {
    if (err) throw err;
    else
      res.json({
        message: data,
        type: "object list",
        success: true
      });
  });
});

router.all("/patientengview/gets/:keyword/:order/:start/:end", (req, res, next) => {
  var keyword = "%" + req.params.keyword + "%";
  var ordercol = req.params.order;
  var start = parseInt(req.params.start)
  var end = parseInt(req.params.end)
  req.db.driver.execQuery("select * from patientengview where usertypeid = 10 and realname like ? order by ? limit ?,?", [keyword, ordercol, start, end - start], function(err, data) {
    if (err) throw err;
    else
      res.json({
        message: data,
        type: "object list",
        success: true
      });
  });
});


//获取patientview的表格文件
router.all('/patientview/gets/sheet', function(req, res, next) {
  req.db.driver.execQuery("select * from patientview ", [], function(err, data) {
    if (err) throw err;
    util.sendSheet(res, data, "patientview.xlsx");
  });
});


// 所有用户
router.all('/allusers', function(req, res, next) {
  req.models.user.find({}).each(function(user) {
    user.password = undefined;
  }).get(function(users) {
    res.json({
      message: users,
      type: "obejctlist",
      success: true
    });
  });
});

// 所有患者
router.all("/allpatients", function(req, res, next) {
  req.models.user.find({}).each(function(user) {
    user.password = undefined;
  }).filter(function(user) {
    return user.usertypeid == 10;
  }).get(function(users) {
    res.json({
      message: users,
      type: "obejctlist",
      success: true
    });
  });
});

// 患者总数
router.all("/allpatientscount", function(req, res, next) {
  var keyword = '%%'
  var sql = "select * from patientengview where usertypeid =10";
  var sqlparam = []
  if (req.body.jsondata && req.body.jsondata.keyword) {
    keyword = "%" + req.body.jsondata.keyword + "%";
    sql = "select * from patientengview where usertypeid =10  and realname like ? ";
    sqlparam = [keyword];
  }
  req.db.driver.execQuery(sql, sqlparam, function(err, data) {
    if (err) throw err;
    res.json({
      message: data.length,
      type: "integer",
      success: true
    });
  });


});

// 注销
router.all("/logout", function(req, res, next) {
  var message = {
    success: false,
    message: "not ",
    type: "err message"
  };
  req.models.user.find({
    username: req.session.user.username
  }, function(err, users) {
    if (err) throw err;
    user = users[0];
    user.token = util.newuuid();
    user.save(function(err) {
      if (err) throw err;
      message.message = "user " + req.session.user.username + " logout";
      message.type = "string";
      message.success = true;
      // 清除session信息
      req.session.user = undefined;
      req.session.islogin = false;
      res.json(message);
    });
  })

});






module.exports = router;