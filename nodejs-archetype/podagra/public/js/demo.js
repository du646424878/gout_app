    var socket = io();
    socket.on('push_message', function(data) {
        console.log(data);
        var newmsg = "<p> from " + data.sourceuserid + " receive " + data.message + "</p>";
        $("#messages").append(newmsg);
    });

    $(document).ready(function() {
        $("#loginbtn").click(function() {
            socket.emit("login", {
                userid: $("#userid").val(),
            });
            $("#userid").val("");
            $("#loginbtn").hide();
            $("#sendbtn").show();
            $("#message").show();
        });
        $("#sendbtn").click(function() {
            var newmsg = $("#message").val();
            socket.emit("point_message", {
                targetuserid: $("#userid").val(),
                message: $("#message").val()
            });
            $("#messages").append("<p> send " + newmsg + "</p>");
        });
    });