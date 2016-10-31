var alltests = new Object();
var tmp = new Object();

module.exports = {
    "/app/current/version": function(test) {
        request(furl("/app/current/version"), (err, res, body) => {
            test.expect(2);
            if (!err && res.statusCode == 200) test.ok(true);
            if (JSON.parse(body).success == true) test.ok(true);
            test.done();
        });
    },
    "/app/current/updateurl": (test) => {
        request(furl("/app/current/updateurl"), (err, res, body) => {
            if (!err && res.statusCode == 200) test.ok(true);
            if (body == '{"success":true,"type":"URL","message":"http://gout.suntao.science/app/current/download"}') test.ok(true)
            else test.ok(false, "message format wrong")
            test.done();
        })
    },
    "/app/current/download": (test) => {
        request(furl("/app/current/download"), (err, res, body) => {
            test.ifError(err);
            test.done();
        })
    },
    "/app/news/gets/:page": (test) => {
        request(furl("/app/news/gets/1"), (err, res, body) => {
            test.expect(2);
            if (!err && res.statusCode == 200) test.ok(true);
            message = JSON.parse(body);
            if (message.success && message.message.length > 0) test.ok(true);
            else test.ok(false, "message have wrong")
            test.done();
        });
    },
    "/app/news/get/:newid": (test) => {
        request(furl("/app/news/get/1"), (err, res, body) => {
            test.expect(2);
            if (!err && res.statusCode == 200) test.ok(true);
            message = JSON.parse(body);
            if (message.success && message.message.id == 1) test.ok(true);
            else test.ok(false, "message wrong and info is: " + message);
            test.done();
        })
    },
    "/app/news/add": (test) => {
        request.post(furl("/app/news/add"), {
            form: {
                jsondata: JSON.stringify({
                    title: "/add/news/add/ - test",
                    intro: "this is unit test data should be delete"
                })
            }
        }, (err, res, body) => {
            if (!err && res.statusCode == 200) test.ok(true);
            message = JSON.parse(body);
            tmp.inserted = message.message[0];
            if (message.success) test.ok(true);
            else test.ok(false, "message wrong and info is: " + body);
            test.equal(tmp.inserted.title, "/add/news/add/ - test", "title should be `/add/news/add/ - test` but now is" + tmp.inserted.title);
            test.done();
        })
    },
    "/app/new/delete/:newid": (test) => {
        // 删除测试数据
        request.get(furl("/app/new/delete/" + tmp.inserted.id), (err, res, body) => {
            test.ifError(err);
            test.equal(res.statusCode, 200);
            test.ok(JSON.parse(body).success, "message.success should be ture")
            test.done();
        });
    }


}