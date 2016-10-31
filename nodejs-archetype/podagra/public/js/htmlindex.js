function addTab(title, url) {
    if ($('#estabs').tabs('exists', title)) {
        $('#estabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
        $('#estabs').tabs('add', {
            title: title,
            content: content,
            closable: true
        });
    }
}

function logout() {
    $.messager.confirm('注销', '确定要注销么', (r => {
        if (r) {
            $.get("/admin/users/logout", (data, status) => {
                if (data && data.success) {
                    window.location.href = "/login";
                }
            })
        }
    }))
}

function change_password() {
    oldpassword = $("input[name='oldpassword']").val();
    password = $("input[name='password']").val();
    valid = $("input[name='password_valid']").val();
    if (valid == password) {
        $.post("/admin/users/updatecurrentuserpassword", {
            jsondata: JSON.stringify({
                oldpassword: oldpassword,
                password: password
            })
        }, (data, status) => {
            if (data && data.success) {
                $.messager.alert('密码', '密码修改成功，请重新登录!');
                $("#pass_form").form("clear"); //清空数据
                $.get("/admin/users/logout", (data, status) => {
                    if (data && data.success) {
                        window.location.href = "/login";
                    }
                });
            } else {
                $.messager.alert('密码', '旧密码不正确!');
            }
        })
    } else {
        $.messager.alert('注意', '两次输入的密码不一致!');
    }
}

$(document).ready(() => {

    $.get("/admin/users/currentuser", function(data, status) {
        if (data.success && (data.message.usertypeid == 5 || data.message.usertypeid == 1)) {

        } else {
            window.location.href = "/login";
        }
    });

})