function hiden() {
    $("#img1").hide();
}
$(document).ready(function() {
    window.onload = hiden();
    // 加载动画
    $.weui.loading('数据加载中...');
    $.get("/admin/records/gettargetusermonthlyrecordlist", function(data, status) {
        $.each(data.message, function(g, item) {
            if (status) {
                t = new Date(item.createtime);
                var year = t.getFullYear();
                var month = t.getMonth() + 1; //修正month的偏移
                var day = t.getDate();
                $("#content1").append('<a class="weui_btn weui_btn_default" style="margin-bottom:10px;margin-top:0px" id="number' + g + '">' + year + "年" + month + "月" + day + '日</a>');
                $("#number" + g).click(function() {
                    window.location.href = "../html/month_news_check.html?assayid=" + item.assayid
                })

            }

        });
        // 加载完毕之后隐藏加载框
        $.weui.hideLoading()
    });
})