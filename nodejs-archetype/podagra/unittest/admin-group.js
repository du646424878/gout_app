var tmp; //用来存放临时信息

// 需要注意的是/admin/db路径下的两个API都不需要转换jsondata
module.exports = {
    "/admin/db/:table/insert": (test) => {
        request.post(furl("/admin/db/user/insert"), {
            form: {
                username: "unit-test-username",
                password: "unit-test-password",
                usertypeid: 10
            }
        }, (err, res, body) => {
            test.ifError(err)
            test.notEqual(JSON.parse(body).message, "no auth", "auth fail")
            tmp = JSON.parse(body)[0];
            test.equal(tmp.username, "unit-test-username", "name equal");
            test.done()
        })
    },
    "/admin/db/:table/update/:id": (test) => {
        request.post(furl("/admin/db/user/update/" + tmp.userid), {
            form: {
                username: "unit-test-username-modify"
            }
        }, (err, res, body) => {
            if (!err && res.statusCode == 200) test.ok(true);
            else test.ok(false, "access failed");
            if (!body) test.ok(false, "body not found");
            else test.equal(body, "success", "result should be `success`");
            test.done();
        });
    },
    "/admin/db/:table/select/:id": (test) => {
        request(furl("/admin/db/user/select/" + tmp.userid), (err, res, body) => {
            if (!err && res.statusCode == 200) test.ok(true);
            else test.ok(false, "access failed");
            test.equal(JSON.parse(body).username, "unit-test-username-modify", "username should be `unit-test-username-modify`");
            test.done();
        })

    },
    "/admin/db/:table/delete/:id": (test) => {
        request(furl("/admin/db/user/delete/" + tmp.userid), (err, res, body) => {
            if (!err && res.statusCode == 200) test.ok(true);
            else test.ok(false, "access failed");
            test.equal(body, "success", "result should be `success`");
            test.done();
        })
    },
    "/admin/db/:view/gets/:col/:value": (test) => {
        request(furl("/admin/db/user/gets/username/" + username), (err, res, body) => {
            if (!err && res.statusCode == 200) test.ok(true);
            else test.ok(false, "access failed");
            test.equal(JSON.parse(body).rows[0].token, token, "current token should be equal");
            test.done();
        })
    },
    // -------------------------------------------------------------
    // 以下是为了测试reminder
    "/admin/db/reminder/insert": (test) => {
        post(furl("/admin/db/reminder/insert", patienttoken), {
            form: {
                jsondata: JSON.stringify({
                    userid: 63,
                    hour: 12,
                    min: 0,
                    effective: 0
                })
            }
        }, (err, res, body) => {
            test.ifError(err);
            test.done()
        })
    },
    "/admin/db/reminder/selects": (test) => {
        post(furl("/admin/db/reminder/selects", patienttoken), {}, (err, res, body) => {
            test.ifError(err);
            tmp = body = JSON.parse(body)[0];
            test.done()
        });
    },
    "/admin/db/reminder/update": (test) => {
        post(furl("/admin/db/reminder/update/" + tmp.reminderid, patienttoken), {
            form: {
                jsondata: JSON.stringify({
                    hour: 13,
                })
            }
        }, (err, res, body) => {
            test.ifError(err);
            test.done()
        })
    },
    "/admin/db/reminder/select": (test) => {
        request(furl("/admin/db/reminder/select/" + tmp.reminderid, patienttoken), (err, res, body) => {
            test.ifError(err);
            test.equal(JSON.parse(body).hour, 13, "hour be modifed to 13 but not effective`");
            test.done();
        })

    },

    "/admin/db/reminder/delete": (test) => {
        test.ok(tmp.reminderid, "should have a id");
        request(furl("/admin/db/reminder/delete/" + tmp.reminderid, patienttoken), (err, res, body) => {
            test.ifError(err);
            test.done();
        })
    }
}