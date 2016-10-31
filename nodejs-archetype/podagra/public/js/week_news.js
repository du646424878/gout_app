$(document).ready(function() {
    $("#button").click(function() {
        var array = {
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
        var b = 0;
        $("fieldset").each(function() {
            var $a = $(this).find("input");
            if ($("input[name=" + $a.attr("name") + "]:checked").val() == undefined) {
                var t = $('#' + $a.attr("name") + '1').offset().top;
                $(window).scrollTop(t);
                alert("您有未选择的选项");
                b = 1;
                return false;
            }
        })
        for (var key in array) {
            array[key] = $("input[name=" + key + "]:checked").val();
        }
        if (b != 1) {
            $.post("/admin/records/addcurrentuserweekrecord", {
                    jsondata: JSON.stringify(array)
                },
                function(data, status) {
                    if (data.success) {
                        $.weui.toast('提交成功', {
                            duration: 1000
                        });
                        window.location.href = "/html/week_news_list.html"
                    } else {
                        alert("修改失败")
                    }


                }, "json");
        }
    })
})