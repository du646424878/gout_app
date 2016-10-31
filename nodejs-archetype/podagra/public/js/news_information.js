function edit_news() {
    var row = $('#tt').datagrid('getSelected');
    if (row) {
        $('#dlg').dialog('open').dialog('setTitle', 'Edit User');
        $('#fm').form('load', row);
        url = "/admin/db/appnews/update/" + row.id;
    }
}

function change_news() {
    row = $('#tt').datagrid('getSelected');
    $("#dlg-news").remove();
    $("body").append("<div id='dlg-news' ></div>")
    $("#dlg-news").dialog({
        title: '新闻',
        width: "90%",
        height: "90%",
        closed: false,
        href: '/app/news/editor/render/' + row.id,
        cache: false,
        modal: true,
        autoOpen: false,
        loadingMessage: "加载中",
        onClose: () => {
            $(this).dialog("destroy");
        }
    })
}

function new_news() {
    $("#dlg-news").remove();
    $("body").append("<div id='dlg-news'  ></div>")
    $("#dlg-news").dialog({
        title: '新闻',
        width: "90%",
        height: "90%",
        closed: false,
        href: '/app/news/editor/render/new',
        cache: false,
        modal: true,
        autoOpen: false,
        loadingMessage: "加载中",
        onClose: () => {
            $(this).dialog("destroy");
        }
    })
}

function save_news() {
    $('#fm').form('submit', {
        url: url,
        onSubmit: function() {
            return $(this).form('validate');
        },
        success: function(result) {
            // var result = eval('('+result+')');
            if (result != "success") {
                $.messager.show({
                    title: 'Error',
                    msg: "修改失败"
                });
            } else {
                $('#dlg').dialog('close'); // close the dialog
                $('#tt').datagrid('reload'); // reload the user data
            }
        }
    });
}

function deletenew() {
    var row = $('#tt').datagrid('getSelected');
    if (row) {
        $.messager.confirm('Confirm', 'Are you sure you want to destroy this user?', function(r) {
            if (r) {
                $.post('/app/new/delete/' + row.id, function(data, status) {
                    if (status) {
                        $('#tt').datagrid('reload'); // reload the user data
                    } else {
                        alert("删除失败");
                    }
                }, 'json');
            }
        });
    }
}

$(document).ready(function() {



    $("#editnew").linkbutton('disable');
    $("#changenew").linkbutton('disable');
    $("#deletenew").linkbutton('disable');
    $("#tt").datagrid({
        onClickRow: function(index, row) {
            $("#editnew").linkbutton('enable');
            $("#changenew").linkbutton('enable');
            $("#deletenew").linkbutton('enable');
        }
    })
    $("#submit").click(function() {
        $('#myModal').modal('hide');
        var essay_content = ue.getAllHtml();
        var essay_title = $("#title").val();
        $.post("/app/news/add", {
                jsondata: JSON.stringify({
                    "title": essay_title,
                    "content": essay_content
                })
            },
            function(data, status) {
                if (status) {
                    alert("添加成功");
                } else {
                    alert("添加失败");
                }
            }, "json");
    });
    $("body").append("<div id='dlg-news'></div>")
    $("#dlg-news").dialog({
        title: '新闻',
        width: "90%",
        height: "90%",
        closed: true,
        cache: false,
        modal: true,
        autoOpen: false,
        loadingMessage: "加载中",
        onClose: () => {
            $(this).dialog("destroy");
        }
    });
})