function editUser() {
    var row = $('#tt').datagrid('getSelected');
    if (row) {
        $('#dlg').dialog('open').dialog('setTitle', 'Edit User');
        $('#fm').form('load', row);
        url = "/admin/db/appnews/update/" + row.id;
    }
}



function saveUser() {
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

function onload_week() {
    var row = $('#tt').datagrid('getSelected');
    if (row) {
        window.open('/admin/records/weekview/gets/sheet/' + row.patientid, '_self');
    }
}

function onload_month() {
    var row = $('#tt').datagrid('getSelected');
    if (row) {
        window.open('/admin/records/monthview/gets/sheet/' + row.patientid, '_self');
    }
}

function download_all_week() {
    window.open('/admin/db/weekview/gets/sheet');
}

function download_all_month() {
    window.open('/admin/db/monthview/gets/sheet');
}


$(document).ready(function() {
    // 先加载数据再绑定其他事件
    $("#tt").datagrid({
        onClickRow: function(index, row) {
            $("#edit").linkbutton('enable');
            $("#week").linkbutton('enable');
            $("#month").linkbutton('enable');
            $("#onload_week").linkbutton('enable');
            $("#onload_month").linkbutton('enable');
        }
    })
    $("#week").click(function() {
        var row = $('#tt').datagrid('getSelected');
        if (row) {
            $('#week_excel').datagrid({
                url: '/admin/db/weekview/gets/患者ID/' + row.patientid,
                fit: true
            });
        }
        $("#week_window").window('open');
    })
    $("#month").click(function() {
        var row = $('#tt').datagrid('getSelected');
        if (row) {
            $('#month_excel').datagrid({
                url: '/admin/db/monthview/gets/患者ID/' + row.patientid,

            });
        }
        $("#month_window").window('open');
    })
    var amount;


    $("#edit").linkbutton('disable');
    $("#week").linkbutton('disable');
    $("#month").linkbutton('disable');
    $("#onload_week").linkbutton('disable');
    $("#onload_month").linkbutton('disable');


})