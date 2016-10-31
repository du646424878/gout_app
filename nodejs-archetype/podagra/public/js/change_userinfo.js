$(document).ready(function() {
    var patientid;
    $.get("/admin/users/getcurrentpatientdetail", function(message, status) {
        if (message) {
            patientid = message.message["patientid"];
            for (var key in message.message) {
                try {
                    $("#" + key).attr("value", message.message[key]);
                    $("#" + key + message.message[key]).attr("checked", true);
                } catch (error) {

                }

            }
        }
    });
    $.get("/admin/users/currentuser", function(message, status) {
        if (message) {
            for (var key in message.message) {
                if (key == "registerdate") {
                    message.message[key] = message.message[key].substr(0, 10);
                }
                $("#" + key).attr("value", message.message[key]);
            }
        }
    });
    $("#submit").click(function() {
        form = {
            patientid: "",
            realname: "",
            age: "",
            weight: "",
            height: "",
            nation: "",
            nativeplace: "",
            job: "",
            email: "",
            havetophus: "",
            b_modeus: "",
            havehypertension: "",
            havediabetes: "",
            haveheartdisease: "",
            havehlp: "",
            haveotherdisease: "",
            hypertensionmedicine: "",
            diabetesmedicine: "",
            heartdiseasemedicine: "",
            hlpmedicine: "",
            otherdiseasemedicine: "",
        }
        for (var key in form) {
            selector = $("#" + key).length > 0 ? $("#" + key) : $("input[name=" + key + "]:checked");
            if (selector) form[key] = selector.val();
        }

        $.post("/admin/users/updatecurrentpatientdetail", {
                jsondata: JSON.stringify(form)
            },
            function(data, status) {
                if (data.success) {
                    $.weui.toast('修改成功', {
                        duration: 1000
                    });
                    window.location.href = "../html/userinfo.html"
                } else {
                    alert("修改失败");
                }
            }, "json");
    });
})