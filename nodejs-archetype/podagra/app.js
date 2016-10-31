var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');

var bodyParser = require('body-parser');

var favicon = require('serve-favicon');

var mysql = require("mysql");
var orm = require('orm');
var helmet = require('helmet');

//Lib
var util = require("./lib/util");
var ormsetup = require("./lib/db")
var authsetup = require("./lib/auth");
var upload = require("./lib/upload");

//Route
var index = require('./routes/index');
var users = require('./routes/users');
var admin = require('./routes/admin');
var records = require('./routes/records');
var mapp = require("./routes/mobileapp");

var app = express();

// 模版引擎设置 ejs/jade可选 ,类似于jsp,在后端渲染页面再传到浏览器
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// 只允许本地回路
app.set('trust proxy', 'loopback')



// 以下注释是在说网站图标的事情
app.use(favicon(path.join(__dirname, 'public', 'icon.png')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: false
}));

//安全配置 抵御常见Web攻击
app.use(helmet());



// 配置ORM2框架
ormsetup(app);
// 配置权限管理
authsetup(app);
// 配置上传
upload(app);



// 将jsondata中的字符串转化成对象
app.use(function(req, res, next) {
  if (req.body.jsondata) {
    req.body.jsondata = JSON.parse(req.body.jsondata);
  }
  next();
});




// 配置API的路由
app.use('/', index);
app.use("/", mapp);
app.use('/admin', admin);
app.use('/admin/users', users);
app.use('/admin/records', records);



// 静态文件
app.use(express.static(path.join(__dirname, 'public')));






// 最后如果依旧没有找到URL的处理器 抛出404异常
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

app.use(function(err, req, res, next) {
  if (err.jsonmessage) {
    var message = {
      message: err.jsonmessage,
      type: "err message",
      success: false
    };
    res.json(message);
  } else {
    next(err);
  }
});


// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    if (err.status != 404) console.log(err.stack);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});


module.exports = app;