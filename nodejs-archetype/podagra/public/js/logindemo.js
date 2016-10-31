$(document).ready(function() {
    $("#submit").click(function() {
        $.post("../login", {
            jsondata:JSON.stringify({
                "username": $("#login-email").val(),
                "password": $("#login-password").val()
            })
            },
            function(data, status) {
               if(data.success){
                    window.location.href="../html/basic_information.html";
                }else{
                    alert("账号密码错误，请重新输入")
                }
                
               
            },"json");

    });
    $("#currentuser").click(function() {
        $.get("/admin/users/currentuser", function(data, status) {
            if (status=='false'){
                alert("Data: " + JSON.stringify(data) + "\nStatus: " + status);
            }
            
        });

    });
    $("#regist").click(function() {
        $.post("../signin", {
            jsondata:JSON.stringify({
                username: $("#register-email").val(),
                password: $("#register-password").val()
            })
            },
            function(data, status) {
               if(data.success){
                    alert("注册成功");
                }else{
                    alert("账号密码错误，请重新输入");
                }
                
               
            },"json");

    });
});