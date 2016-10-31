var alltests = new Object();

// config
var setup = require("./unit-setup");

// get test cases
var apptest = require("./app-group");
var admintest = require("./admin-group");
var userstest = require("./users-group");
var authtest = require("./auth-test");

// test cases
alltests.setuptest = setup;
alltests.apptest = apptest;
alltests.admintest = admintest;
alltests.userstest = userstest;
alltests.authtest=authtest;

module.exports = alltests;