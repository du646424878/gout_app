var express = require('express');
var router = express.Router();
var util = require("../lib/util");

// 添加每周习惯记录(个人)
router.all("/addcurrentuserweekrecord", function(req, res, next) {
    var aweekrecord = req.body.jsondata;
    if (aweekrecord) {
        aweekrecord.createtime = new Date();
        aweekrecord.modifytime = new Date();
        aweekrecord.userid = req.session.user.userid;
        req.models.weekhabit.create([aweekrecord], function(err, added) {
            if (err) throw err;
            res.json({
                message: added,
                success: true,
                type: "added model"
            });
        });
    } else {
        var err = new Error();
        err.jsonmessage = "param error";
        next(err);
    }
});

//根据ID获取每周记录
router.all("/getweekrecord", function(req, res, next) {
    if (req.body.jsondata && req.body.jsondata.habitid) {
        req.models.weekhabit.find({
            habitid: req.body.jsondata.habitid
        }, function(err, habits) {
            if (err) throw err;
            if (habits[0]) {
                res.json({
                    message: habits[0],
                    success: true,
                    type: "object"
                });
            } else {
                next();
            }
        });
    } else {
        var err = new Error();
        err.jsonmessage = "param error";
        next(err);
    }

});

router.post("/getmonthrecord", (req, res, next) => {
    var assayid = parseInt(req.body.jsondata.assayid);
    req.models.monthyassay.get(assayid, (err, record) => {
        res.json({
            message: record,
            success: true,
            type: "object"
        });
    });
});

//更新一条每周记录
router.all("/updateuserweekrecord", function(req, res, next) {
    var aweekrecord = req.body.jsondata;
    if (aweekrecord && aweekrecord.habitid) {
        aweekrecord.modifytime = new Date();
        req.models.weekhabit.find({
            habitid: aweekrecord.habitid
        }, function(err, habits) {
            if (err) throw err;
            if (habits.length > 0) {
                habits[0].save(aweekrecord, function(err) {
                    if (err) throw err;
                    res.json({
                        success: true
                    });
                });
            } else {
                err = new Error();
                err.jsonmessage = "no such weekhabit";
                next(err);
            }
        });
    } else {
        var err = new Error();
        err.jsonmessage = "param error";
        next(err);
    }
});

router.all("/getcurrentuserrecordlist/:table", function(req, res, next) {
    if (req.body.jsondata && req.body.jsondata.top) top = req.body.jsondata.top;
    else top = 5;
    req.models[req.params.table.toString()].find({
        userid: req.session.user.userid
    }, top, ["createtime", "Z"], function(err, list) {
        if (err) throw err;
        res.json({
            message: list,
            success: true,
            type: "object list"
        });
    });
});

router.all("/gettargetuserweekrecordlist", function(req, res, next) {
    if (req.body.jsondata) jsondata = req.body.jsondata;
    else jsondata = undefined;
    if (jsondata && jsondata.ordelcol) ordelcol = jsondata.ordelcol;
    else ordelcol = "createtime";
    if (jsondata && jsondata.order) {
        if (jsondata.order == "desc") order = "Z";
        else if (jsondata.order == "asc") order = "A";
        else order = "Z";
    } else order = "Z";
    if (jsondata && jsondata.start && jsondata.start >= 0) start = jsondata.start;
    else start = 0;
    if (jsondata && jsondata.end && jsondata.end > start) end = jsondata.end;
    else end = start + 10;
    if (jsondata && jsondata.userid) userid = jsondata.userid;
    else userid = req.session.user.userid;
    req.models.weekhabit.find({
        userid: userid
    }, {
        offset: start
    }, end - start, [ordelcol, order], function(err, records) {
        if (err) throw err;
        res.json({
            message: records,
            success: true,
            type: "object list"
        });
    });
});

router.all("/addcurrentusermonthlyrecord", function(req, res, next) {
    var arecord = req.body.jsondata;
    if (arecord) {
        arecord.createtime = new Date();
        arecord.modifytime = new Date();
        arecord.userid = req.session.user.userid;

        req.models.monthyassay.create([arecord], function(err, added) {
            if (err) throw err;
            res.json({
                message: added,
                success: true,
                type: "added model"
            });
            // 如果添加每月记录后，记录条数小于两条，则认为是第一次就诊
            // 这些并不需要依次完成，只要最后数据持久化到数据库即可
            req.models.monthyassay.count({ userid: req.session.user.userid }, (err, count) => {
                if (count < 2) {
                    req.models.patientdetail.find({ patientid: req.session.user.userid }, (err, users) => {
                        if (err) throw err;
                        if (users.length > 0) {
                            user = users[0];
                            user.firstvisitdate = new Date();
                            user.save();
                        }
                    })
                }
            });
        });
    } else {
        var err = new Error();
        err.jsonmessage = "param error";
        next(err);
    }
});

router.all("/updateusermonthlyrecord", function(req, res, next) {
    var amonthlyrecord = req.body.jsondata;
    if (amonthlyrecord && amonthlyrecord.assayid) {
        amonthlyrecord.modifytime = new Date();
        req.models.monthyassay.find({
            assayid: amonthlyrecord.assayid
        }, function(err, records) {
            if (err) throw err;
            if (records.length > 0) {
                records[0].save(amonthlyrecord, function(err) {
                    if (err) throw err;
                    res.json({
                        success: true
                    });
                });
            } else {
                err = new Error();
                err.jsonmessage = "no such weekhabit";
                next(err);
            }
        });
    } else {
        var err = new Error();
        err.jsonmessage = "param error";
        next(err);
    }
});

router.all("/gettargetusermonthlyrecordlist", function(req, res, next) {
    if (req.body.jsondata) jsondata = req.body.jsondata;
    else jsondata = undefined;
    if (jsondata && jsondata.ordelcol) ordelcol = jsondata.ordelcol;
    else ordelcol = "createtime";
    if (jsondata && jsondata.order) {
        if (jsondata.order == "desc") order = "Z";
        else if (jsondata.order == "asc") order = "A";
        else order = "Z";
    } else order = "Z";
    if (jsondata && jsondata.start && jsondata.start >= 0) start = jsondata.start;
    else start = 0;
    if (jsondata && jsondata.end && jsondata.end > start) end = jsondata.end;
    else end = start + 5;
    if (jsondata && jsondata.userid) userid = jsondata.userid;
    else userid = req.session.user.userid;
    req.models.monthyassay.find({
        userid: userid
    }, {
        offset: start
    }, end - start, [ordelcol, order], function(err, records) {
        if (err) throw err;
        res.json({
            message: records,
            success: true,
            type: "object list"
        });
    });
});


router.all("/monthview/gets", (req, res, next) => {
    if (req.body.jsondata) jsondata = req.body.jsondata;
    else jsondata = undefined;
    if (jsondata && jsondata.start && jsondata.start >= 0) start = jsondata.start;
    else start = 0;
    if (jsondata && jsondata.end && jsondata.end > start) end = jsondata.end;
    else end = start + 10;
    if (jsondata && jsondata.userid) userid = jsondata.userid;
    else userid = req.session.user.userid;
    req.db.driver.execQuery("select * from monthview where `患者ID` = ? order by `创建时间`  LIMIT ?,?", [userid, start, end], (err, records) => {
        if (err) throw err;
        res.json({
            message: records,
            success: true,
            type: "object list"
        });
    })
});

router.all("/monthview/gets/:patientid/:start/:end", (req, res, next) => {
    var userid = parseInt(req.params.patientid);
    var start = parseInt(req.params.start);
    var end = parseInt(req.params.end);
    req.db.driver.execQuery("select * from monthview where `患者ID` = ? order by `创建时间` desc LIMIT ?,? ", [userid, start, end - start], (err, records) => {
        if (err) throw err;
        res.json({
            message: records,
            success: true,
            type: "object list"
        });
    });
});


router.all("/monthview/gets/sheet/:patientid", (req, res, next) => {
    var patientid = req.params.patientid;
    req.db.driver.execQuery("select * from monthview where `患者ID` = ? ", [patientid], (err, records) => {
        if (err) throw err;
        util.sendSheet(res, records, "每月信息-id-" + patientid + ".xlsx");
    });
})


router.all("/week/gets/sheet", (req, res, next) => {
    if (req.body.jsondata) jsondata = req.body.jsondata;
    else jsondata = undefined;
    if (jsondata && jsondata.ordelcol) ordelcol = jsondata.ordelcol;
    else ordelcol = "createtime";
    if (jsondata && jsondata.order) {
        if (jsondata.order == "desc") order = "Z";
        else if (jsondata.order == "asc") order = "A";
        else order = "Z";
    } else order = "Z";
    if (jsondata && jsondata.userid) userid = jsondata.userid;
    else userid = req.session.user.userid;
    req.models.weekhabit.find({
        userid: userid
    }, [ordelcol, order], function(err, records) {
        if (err) throw err;
        util.sendSheet(res, records, "weekhabits.xlsx");
    });
});


router.all("/month/gets/sheet", function(req, res, next) {
    if (req.body.jsondata) jsondata = req.body.jsondata;
    else jsondata = undefined;
    if (jsondata && jsondata.ordelcol) ordelcol = jsondata.ordelcol;
    else ordelcol = "createtime";
    if (jsondata && jsondata.order) {
        if (jsondata.order == "desc") order = "Z";
        else if (jsondata.order == "asc") order = "A";
        else order = "Z";
    } else order = "Z";
    if (jsondata && jsondata.userid) userid = jsondata.userid;
    else userid = req.session.user.userid;
    req.models.monthyassay.find({
        userid: userid
    }, [ordelcol, order], function(err, records) {
        if (err) throw err;
        util.sendSheet(res, records, "monthrecord.xlsx");
    });
});

router.all("/month/get/:assayid", (req, res, next) => {
    var assayid = parseInt(req.params.assayid);
    req.models.monthyassay.get(assayid, (err, record) => {
        res.json({
            message: record,
            success: true,
            type: "object"
        });
    });
});

router.all("/weekview/gets", (req, res, next) => {
    if (req.body.jsondata) jsondata = req.body.jsondata;
    else jsondata = undefined;
    if (jsondata && jsondata.start && jsondata.start >= 0) start = jsondata.start;
    else start = 0;
    if (jsondata && jsondata.end && jsondata.end > start) end = jsondata.end;
    else end = start + 10;
    if (jsondata && jsondata.userid) userid = jsondata.userid;
    else userid = req.session.user.userid;
    req.db.driver.execQuery("select * from weekview where `患者ID` = ? order by `创建时间` desc LIMIT ?,? ", [userid, start, end - start], (err, records) => {
        if (err) throw err;
        res.json({
            message: records,
            success: true,
            type: "object list"
        });
    })

});

router.all("/weekview/gets/:patientid/:start/:end", (req, res, next) => {
    var userid = parseInt(req.params.patientid);
    var start = parseInt(req.params.start);
    var end = parseInt(req.params.end);
    req.db.driver.execQuery("select * from weekview where `患者ID` = ? order by `创建时间` desc LIMIT ?,? ", [userid, start, end - start], (err, records) => {
        if (err) throw err;
        res.json({
            message: records,
            success: true,
            type: "object list"
        });
    });
});

router.all("/weekview/gets/sheet/:patientid", (req, res, next) => {
    var patientid = req.params.patientid;
    req.db.driver.execQuery("select * from weekview where `患者ID` = ? ", [patientid], (err, records) => {
        if (err) throw err;
        util.sendSheet(res, records, "每周信息-id-" + patientid + ".xlsx");
    });
});


module.exports = router;