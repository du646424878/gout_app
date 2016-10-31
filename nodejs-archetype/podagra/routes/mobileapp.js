var express = require('express');
var router = express.Router();
var util = require('../lib/util');
var useradmin = require("./users");
var http = require("http");
var async = require("async");
var request = require("request-promise");
var qs = require("querystring");

updateinfo = () => {
    return request("http://api.fir.im/apps/latest/5785fabd748aac5ab3000061?token=d2696fa7e783395875230e9fc4057a64");
}

router.all("/app/current/version", (req, res, next) => {
    updateinfo().then((result) => {
        result = JSON.parse(result)
        res.json({
            success: true,
            type: "current version",
            message: result.version
        });
    })
});

router.all("/app/current/updateurl", (req, res, next) => {
    res.json({
        success: true,
        type: "URL",
        message: "http://gout.suntao.science/app/current/download"
    });
});

router.all("/app/current/download", (req, res, next) => {
    updateinfo().then((result) => {
        result = JSON.parse(result)
        res.redirect(result.installUrl);
    });
});

router.all("/app/news/gets/:page", (req, res, next) => {
    var page = parseInt(req.params.page);
    var offset = (page - 1) * 10

    // 逆序列表
    req.db.driver.execQuery("SELECT appnews.id, appnews.title, appnews.intro, appnews.createdate,appnews.img FROM appnews order by createdate desc limit ?,10", [offset], (err, data) => {
        if (err) next(err);
        else res.json({
            success: true,
            type: "object list",
            message: data
        });
    })
});

router.all("/app/news/get/:newid", (req, res, next) => {
    req.models.appnews.get(parseInt(req.params.newid), (err, data) => {
        if (err) next(err);
        else res.json({
            success: true,
            type: "object",
            message: data
        });
    });
});

router.post("/app/new/add", (req, res, next) => {
    req.models.appnews.create([req.body.jsondata], (err, data) => {
        res.json({
            success: true,
            type: "object list",
            message: data
        });
    })
});

router.post("/app/news/add", (req, res, next) => {
    req.models.appnews.create([req.body.jsondata], (err, data) => {
        res.json({
            success: true,
            type: "object list",
            message: data
        });
    })
});

router.get("/app/news/render/:newid", (req, res, next) => {
    req.models.appnews.get(parseInt(req.params.newid), (err, data) => {
        if (err) next(err);
        else res.render("article", {
            article: data
        });
    });
});

router.get("/app/news/editor/render/:newid", (req, res, next) => {
    if (isNaN(parseInt(req.params.newid))) {
        res.render("editor", {
            article: undefined,
        });
    } else
        req.models.appnews.get(parseInt(req.params.newid), (err, data) => {
            if (err) next(err);
            else res.render("editor", {
                article: data,
            });
        });
});

router.all("/app/new/delete/:newid", (req, res, next) => {
    req.models.appnews.get(parseInt(req.params.newid), (err, data) => {
        if (err) next(err);
        else {
            if (data) {
                data.remove((err) => {
                    if (err) next(err);
                    else res.json({
                        success: true
                    })
                })
            } else {
                res.json({
                    success: false,
                    type: "err message",
                    message: "no such record"
                });
            }
        }
    });
});

router.all("/app/weather/:location", (req, res, next) => {
    var location = req.params.location;
    var ak = req.params.ak || "MPDgj92wUYvRmyaUdQs1XwCf";
    var apiparam = {
        location: location,
        ak: ak,
        output: "json"
    }
    var url = "http://api.map.baidu.com/telematics/v3/weather?" + qs.stringify(apiparam);
    request({
        url: url,
        headers: {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0"
        }
    }).then((result) => {
        res.json(JSON.parse(result).results[0].weather_data[0]);
    }).catch(function(err) {
        console.log(err.message)
        next();
    });
});



module.exports = router;