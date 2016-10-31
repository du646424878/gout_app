$(document).ready(function() {
    // 加载动画
    $.weui.loading('数据加载中...');
    $.get("/admin/users/getcurrentpatientdetail", function(message, status) {
        if (message) {
            for (var key in message.message) {
                $("#" + key).html(message.message[key]);
                // 加载完毕之后隐藏加载框
                $.weui.hideLoading()
            }
        }
    });
    $.get("/admin/users/currentuser", function(message, status) {
        if (message) {
            for (var key in message.message) {
                if (key == "registerdate") {
                    t = new Date(message.message[key]);
                    var year = t.getFullYear();
                    var month = t.getMonth() + 1; //修正month的偏移
                    var day = t.getDate();
                    message.message[key] = "" + year + "-" + month + "-" + day
                }
                $("#" + key).html(message.message[key]);
            }
        }
    });
});