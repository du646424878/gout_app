$(document).ready(function() {
    $("#button").click(function() {
        var array = {
            diseasecourse: "",
            isjointpain: "",
            painpart: "",
            isjointswelling: "",
            swellingpart: "",
            isdietchange: "",
            isexercise: "",
            esr: "",
            crp: "",
            ua: "",
            ganyousanzhi: "",
            totalcholesterol: "",
            tmdasajzym: "",
            basajzym: "",
            cr: "",
            cbc: "",
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
            gcsdosage: "",
            colcdosage: "",
            allopurinoldosage: "",
            benzbromaronedosage: "",
            nsaiddosage: "",
            febuxostatdosage: ""
        }
        var array1 = {
            isjointpain: "",
            isjointswelling: "",
            isdietchange: "",
            isexercise: "",
            havetophus: "",
            b_modeus: "",
            havehypertension: "",
            havediabetes: "",
            haveheartdisease: "",
            havehlp: "",
            haveotherdisease: ""
        }
        var array2 = {
            diseasecourse: "",
            esr: "",
            crp: "",
            ua: "",
            ganyousanzhi: "",
            totalcholesterol: "",
            tmdasajzym: "",
            basajzym: "",
            cr: "",
            cbc: "",
            gcsdosage: "",
            colcdosage: "",
            allopurinoldosage: "",
            benzbromaronedosage: "",
            nsaiddosage: "",
            febuxostatdosage: ""
        }
        for (var key in array) {
            array[key] = $("#" + key).val();
        }
        for (var key in array1) {
            array[key] = $("input[name=" + key + "]:checked").val();
        }
        for (var key in array) {
            if ($("input[name=" + key + "]").val() == undefined || $("input[name=" + key + "]").val() == "") {
                alert("您有未选择的选项");
                var t = $('#' + key).offset().top;
                $(window).scrollTop(t);
                return false;
            }
        }
        for (var key in array2) {
            if (isNaN(parseFloat(array[key]))) {
                alert("输入不符合规范");
                var t = $('#' + key).offset().top;
                $(window).scrollTop(t);
                return false;
            }
        }

        $.post("/admin/records/addcurrentusermonthlyrecord", {
                jsondata: JSON.stringify(array)
            },
            function(data, status) {
                if (data.success) {
                    $.weui.toast('提交成功', {
                        duration: 1000
                    });
                    window.location.href = "../html/month_news_list.html"
                } else {
                    alert("提交失败")
                }
            }, "json");

    })
})