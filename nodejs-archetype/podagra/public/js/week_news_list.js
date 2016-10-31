function hiden() {
    $("#img1").hide();
}
$(document).ready(function() {
    window.onload = hiden();
    // 加载动画
    $.weui.loading('数据加载中...');
    $.post("/admin/records/gettargetuserweekrecordlist", {
        jsondata: JSON.stringify({
            'start': 0,
            'end': 5
        })
    }, function(data, status) {
        $.each(data.message, function(g, item) {
            if (status) {
                t = new Date(item.createtime);
                var year = t.getFullYear();
                var month = t.getMonth() + 1; //修正month的偏移
                var day = t.getDate();
                $("#content1").append('<a2 class="weui_btn weui_btn_default" style="margin-bottom:10px;margin-top:0px" id="number' + g + '">' + year + "年" + month + "月" + day + '日</a2>');
                $("#number" + g).click(function() {
                    window.location.href = "../html/week_news_check.html?habitid=" + item.habitid
                })
            }
        });
        // 加载完毕之后隐藏加载框
        $.weui.hideLoading()
    });
})