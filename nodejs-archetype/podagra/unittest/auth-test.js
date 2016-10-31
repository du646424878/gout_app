module.exports = {
    "no-auth-test": (test) => {
        //使用患者的token触发这个API,API应响应失败
        post(furl("/admin/db/appnews/insert", patienttoken), {
            form: {
                title: "no auth test",
                intro: "it should not success"
            }
        }, (err, res, body) => {
            if (err) {
                console.log(err);
            }
            test.equal(JSON.parse(body).success, false, "can not be success");
            test.done();
        });
    },
    "hasuser": (test) => {
        post(furl("/hasuser", null), {
            form: {
                jsondata: JSON.stringify({
                    username: "suntao"
                })
            }
        }, (err, res, body) => {
            if (err) {
                console.log(err);
            }
            test.equal(JSON.parse(body).success, true, "should be true");
            test.done();
        });
    }
}