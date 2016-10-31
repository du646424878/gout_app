function getUrlParam(name) {
    //构造一个含有目标参数的正则表达式对象  
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //匹配目标参数  
    var r = window.location.search.substr(1).match(reg);
    //返回参数值  
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}
$(document).ready(function() {
    // 加载动画
    $.weui.loading('数据加载中...');
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
        painpart: "",
        swellingpart: "",
        esr: "",
        crp: "",
        ua: "",
        ganyousanzhi: "",
        totalcholesterol: "",
        tmdasajzym: "",
        basajzym: "",
        cr: "",
        cbc: "",
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
    var assayid = getUrlParam('assayid');
    $.get("/admin/records/month/get/" + assayid, function(data, status) {
        if (status) {
            for (key in array1) {
                $("#" + key + data.message[key]).attr("checked", true);
            }
            for (key in array2) {
                $("#" + key).attr("value", data.message[key]);
            }
            // 加载完毕之后隐藏加载框
            $.weui.hideLoading()
        } else {
            alert("未授权");
        }
    });
    $("#button").click(function() {
        for (key in array1) {
            array[key] = $("input[name=" + key + "]:checked").val();
        }
        for (key in array2) {
            array[key] = $("#" + key).val();
        }
        array.assayid = assayid;
        $.post("/admin/records/updateusermonthlyrecord", {
                jsondata: JSON.stringify(array)
            },
            function(data, status) {
                if (data.success) {
                    $.weui.toast('修改成功', {
                        duration: 1000
                    });
                    window.location.href = "../html/month_news_list.html"
                } else {
                    alert("修改失败")
                }
            }, "json");
    })
})
    