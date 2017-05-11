/**
 * Created by webinglin on 2017/5/11.
 */


$(function () {

    $("input.ui-checkbox").checkboxradio();

    $(".btn").button();


    $("#roleTable").jqGrid({
        url: basePath + "/role/queryRoles",
        datatype: "json",
        colNames: ['id', '角色名称', '备注'],
        colModel: [
            {name: 'id', index: 'ID', hidden: true, width: 60},
            {name: 'roleName', index: 'ROLE_NAME', width: 100},
            {name: 'remark', index: 'REMARK', width: 80}
        ],
        pager: '#rolePager',
        width: 530,
        height: 400,
        rowNum: 20,
        rowList: [20, 50, 100],
        sortname: 'ROLE_NAME',
        sortorder: "desc",
        viewrecords: true,
        jsonReader: {
            root: "datas",
            repeatitems: false
        },
        caption: "角色信息",
        onSelectRow: function (rowid, status) {

            // 获取某行的数据, 然后查询该角色拥有的所有菜单
            var rowData = $("#roleTable").jqGrid("getRowData", rowid);
            $("#selectedRoleId").val(rowData['id']);
            $.post(basePath + "/role/queryRoleFuncs", {roleId: rowData['id']}, function (data) {
                // data为funcId列表
                // 选中所有的funcId
                $("input.ui-checkbox").prop("checked", false);

                for (var i = 0, len = data.length; i < len; i++) {
                    $("#" + data[i]).prop("checked", true);
                    // $("#"+data[i]).checkboxradio( "refresh" );
                }

                $("input.ui-checkbox").checkboxradio("refresh");
            });

        }
    });


    // 授权事件
    listenRoleFuncs();

});


function listenRoleFuncs() {
    // 保存修改
    $("#roleFuncSave").click(function () {
        var roleId = $("#selectedRoleId").val();
        if ('' == roleId) {
            alert("请选择需要修改权限的角色");
            return;
        }

        var checkedFuncIds = [];
        $("input.ui-checkbox").each(function (e) {
            var funcId = $(this).attr("id");
            if($("#" + funcId).prop("checked")){
                checkedFuncIds.push(funcId);
            }
        });
        $.post(basePath+"/role/updateRoleFuncs", {roleId:roleId, funcIds:checkedFuncIds.join(",")}, function (data) {
            if("200"==data.code){
                alert("角色修改权限成功");
            } else {
                alert(data.msg);
            }
        });
    });

    // 全选
    $("#roleFuncChoseAll").click(function () {
        $("input.ui-checkbox").prop("checked", true);
        $("input.ui-checkbox").checkboxradio("refresh");
    });

    // 反选
    $("#roleFuncDisChoseAll").click(function () {
        $("input.ui-checkbox").prop("checked", false);
        $("input.ui-checkbox").checkboxradio("refresh");
    });
}