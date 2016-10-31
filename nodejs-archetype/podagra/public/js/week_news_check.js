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
        habitid: "",
        staplefood: "",
        staplefoodamount: "",
        taste: "",
        dietarypreference: "",
        drinktype: "",
        fieldset: "",
        seafoodpd: "",
        beefpd: "",
        fishpd: "",
        porkpd: "",
        poultrypd: "",
        visceralpd: "",
        vegetablepd: "",
        beanpd: "",
        eggpd: "",
        nutpd: "",
        fruitpd: "",
        saltpd: "",
        beerpd: "",
        milkpd: "",
        liquorpd: "",
        wirepd: "",
        teatype: "",
        teapd: ""
    }

    // 访问该页面时，在url末尾添加?picid=8   

    var habitid1 = getUrlParam('habitid');
    // 填充
    $.get("/admin/db/weekhabit/select/" + habitid1, function(data, status) {
        for (key in data) {
            $("#" + key + data[key]).attr("checked", true);
        }
        // 加载完毕之后隐藏加载框
        $.weui.hideLoading()
    });

    $("#button").click(function() {
        for (var key in array) {
            array[key] = $("input[name=" + key + "]:checked").val();
        }
        array.habitid = habitid1;
        $.post("/admin/records/updateuserweekrecord", {
                jsondata: JSON.stringify(array)
            },
            function(data, status) {
                if (data.success) {
                    $.weui.toast('修改成功', {
                        duration: 1000
                    });
                    window.location.href = "../html/week_news_list.html"
                } else {
                    alert("修改失败")
                }
            }, "json");
    })
})