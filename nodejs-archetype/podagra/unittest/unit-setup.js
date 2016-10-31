// modules setup , will be used in other unit cases
global.request = require("request");
global.wf = require("async/waterfall")
global.util = require("util")

// server config , point out the address of server and the auth info
global.serverDomain = "localhost"; // test server domain
global.serverPort = 2999 // test service port
global.token = "73abbcf9657844e5b3e36b46fab01e6c";
global.username = "admin"
global.password = "666666799"

global.admintoken = "";
global.doctoken = "";
global.patienttoken = "";

var setup = new Object();

// 设置颜色
var  colors  =  require('colors');      
colors.setTheme({      
    silly:   'rainbow',
    input:   'grey',
    verbose:   'cyan',
    prompt:   'red',
    info:   'green',
    data:   'blue',
    help:   'cyan',
    warn:   'yellow',
    debug:   'magenta',
    error:   'red'  
});    

// 捕获未处理异常
process.on("uncaughtException", function(err) {
    //打印出错误
    console.log(err);
    //打印出错误的调用栈方便调试
    console.log(err.stack);
});


global.furl = function furl(api, t) {
    t = t != null ? t : token;
    // format api url
    return util.format("http://%s:%d%s?token=%s", serverDomain, serverPort, api, t);
}


global.get = request;

global.post = request.post;


setup.furlTest = (test) => {
    test.equal(furl("/app/news"), "http://" + serverDomain + ":" + serverPort + "/app/news?token=" + token)
    test.done();
}



module.exports = {

    "/login": (test) => {
        wf([(cb) => {
            request.post(furl("/login", ""), {
                form: {
                    jsondata: JSON.stringify({
                        username: username,
                        password: password
                    })
                }
            }, (err, res, body) => {
                test.ifError(err);
                msg = JSON.parse(body);
                if (!msg.success) {
                    test.ok(false, "msg not success")
                    console.log(msg);
                } else {
                    test.ok(msg.success);
                    token = msg.message;
                    admintoken = token;
                }
                cb(null);
            })
        }, (cb) => {
            request.post(furl("/login", ""), {
                form: {
                    jsondata: JSON.stringify({
                        username: "duzhekai9",
                        password: "123456"
                    })
                }
            }, (err, res, body) => {
                msg = JSON.parse(body);
                test.ifError(err);
                patienttoken = msg.message;
                cb(null);
            });
        }, (cb) => {
            request.post(furl("/login", ""), {
                form: {
                    jsondata: JSON.stringify({
                        username: "zhouqiao",
                        password: "666666799"
                    })
                }
            }, (err, res, body) => {
                msg = JSON.parse(body);
                test.ifError(err);
                doctoken = msg.message;
                cb(null);
            });

        }, (cb) => {
            console.log(admintoken.info)
            console.log(patienttoken.info);
            console.log(doctoken.info)
            test.done();
        }])
    },
    "checktoken": (test) => {
        request(furl("/admin/users/currentuser"), (err, res, body) => {
            test.ifError(err);
            test.equal(res.statusCode, 200, "statu code should be 2000")
            if (JSON.parse(body).success) {
                test.ok(true)
                test.done();
            } else {
                console.warn("\nTOKEN WAS UN AUTH".error)
            }
        });
    }
}