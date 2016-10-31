var express = require('express');
var router = express.Router();
var util = require('../lib/util');
var useradmin = require("./users");
var http = require("http");


router.get('/', function(req, res, next) {
    res.render("index", {
        title: "痛风随访系统",
        content: "文档参看<a href='https://wiki.suntao.science/projectdoc/gout'>这里</a><br>点击这里<a href='/html/login.html'>登录</a>"
    })
});

// 是否有用户
router.post("/hasuser", function(req, res, next) {
    var message = {
        message: false,
        type: "boolean hasuser",
        success: false
    };
    var jsondata = req.body.jsondata;
    if (jsondata && jsondata.username) {
        req.models.user.find({
            username: jsondata.username
        }, function(err, users) {
            if (err) throw err;
            message.success = true;
            if (users.length > 0)
                message.message = true;
            res.json(message);
        });
    } else {
        var err = new Error();
        err.jsonmessage = "param error"
        next(err);

    }


});


// 注册
router.post("/signin", function(req, res, next) {
    var message = {
        message: undefined,
        type: "obejct",
        success: false
    };
    var auser = req.body.jsondata;
    if (auser && auser.username && auser.password) { // 如果有这两个字段
        if (auser.userid) auser.userid = null;
        auser.usertypeid = 10; // 设置用户类别为患者
        auser.password = util.md5(auser.password);
        req.models.user.create([auser], function(err, addeds) {
            if (err) throw err;
            else {
                addeduser = addeds[0];
                addeduser.password = undefined;
                message.type = "obejct";
                message.message = addeduser;
                message.success = true;
                res.json(message);
            }
        });
    } else {
        var err = new Error();
        err.jsonmessage = "param error"
        next(err);
    }
});

router.post('/login', function(req, res, next) {
    if (req.body.jsondata && req.body.jsondata.username && req.body.jsondata.password) {
        var param = {
            username: req.body.jsondata.username,
            password: util.md5(req.body.jsondata.password)
        };
        var message = {
            success: true,
            message: undefined,
            type: "token"
        };
        req.models.user.find(param, function(err, users) {
            if (err) throw err;
            if (users.length > 0) {
                user = users[0];
                user.token = util.newuuid();
                message.message = user.token;
                user.save(function(err) {
                    if (err) throw err;
                    user.password = undefined;
                    req.session.islogin = true;
                    req.session.user = user;
                    res.json(message);
                });

            } else {
                var err = new Error();
                err.jsonmessage = "No Such User Or Password Wrong";
                next(err);

            }
        })
    } else {
        var err = new Error();
        err.jsonmessage = "param error"
        next(err);
    }
});

// 重定向登录界面
router.get("/login", (req, res, next) => {
    res.redirect("/html/login.html");
});


module.exports = router;