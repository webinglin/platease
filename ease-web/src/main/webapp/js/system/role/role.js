/**
 * Created by webinglin on 2017/5/11.
 */


$(function () {

    $("input.ui-checkbox").checkboxradio();

    $(".btn").button();


    $("#roleTable").jqGrid({
        url: basePath + "/role/queryRoles",
        datatype: "json",
        mtype:"POST",
        colNames: ['id', '角色名称', '备注'],
        colModel: [
            {name: 'id', index: 'ID', hidden: true, width: 60},
            {name: 'roleName', index: 'ROLE_NAME', width: 100},
            {name: 'remark', index: 'REMARK', width: 80}
        ],
        pager: '#rolePager',
        width: 530,
        height: 490,
        rowNum: 15,
        rowList: [15, 50, 100],
        sortname: 'ROLE_NAME',
        sortorder: "desc",
        viewrecords: true,
        multiselect:true,
        multiboxonly:true,
        jsonReader: {
            root: "datas",
            repeatitems: false
        },
        caption: "角色信息",
        onSelectRow: function (rowid, status) {

            // 获取某行的数据, 然后查询该角色拥有的所有菜单
            var rowData = $("#roleTable").jqGrid("getRowData", rowid);
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
        } ,
        beforeRequest: function () {
            var params = {'searchCont':$("#roleSearchCont").val()};
            $("#roleTable").jqGrid('setGridParam', {postData: params});
        }
    });


    // 表单验证
    var addFormValidator = $("#addRoleForm").Validform({tiptype: 3});
    var updateFormValidator = $("#updateRoleForm").Validform({tiptype: 3});

    $("#addRoleDialog").dialog({
        autoOpen: false,
        appendTo: "#content",
        width: 500,
        height: 300,
        modal: true,
        title: "新增角色",
        buttons: [{
            text: "保存",
            click: function () {
                if (!addFormValidator.check()) {
                    return false;
                }

                $.post(basePath + "/role/addRole", web.form.getValues($("#addRoleForm")), function (data) {
                    if ("200" == data['code']) {
                        alert("添加成功");

                        $("#roleTable").trigger("reloadGrid");
                        $("#addRoleDialog").dialog("close");
                        web.form.reset($("#addRoleForm"));
                    } else if ("500" == data['code']) {
                        alert(data['msg']);
                    }
                });
            }
        }, {
            text: "取消", click: function () {
                $(this).dialog("close");
            }
        }
        ]
    });

    $("#updateRoleDialog").dialog({
        autoOpen: false,
        appendTo: "#content",
        width: 500,
        height: 300,
        modal: true,
        title: "修改角色",
        buttons: [{
            text: "确认修改",
            click: function () {
                if (!updateFormValidator.check()) {
                    return false;
                }

                var formData = web.form.getValues($("#updateRoleForm"));
                $.post(basePath + "/role/updateRole", formData, function (data) {
                    if ("200" == data['code']) {
                        alert("修改成功");

                        $("#roleTable").trigger("reloadGrid");
                        $("#updateRoleDialog").dialog("close");
                        web.form.reset($("#updateRoleForm"));

                    } else if ("500" == data['code']) {
                        alert(data['msg']);
                    }
                });
            }
        }, {
            text: "取消", click: function () {
                $(this).dialog("close");
            }
        }
        ]
    });

    // 监听事件
    listenEvents();

    function listenEvents() {

        /* ********************授权相关 --开始-- ***************************************************** */

        // 保存修改
        $("#roleFuncSave").click(function () {
            var selectedRowIds = $("#roleTable").jqGrid('getGridParam','selarrrow');
            if (selectedRowIds.length==0) {
                alert("请选择需要修改权限的角色");
                return;
            } else if(selectedRowIds.length!=1){
                alert("只能对一个角色进行权限的修改");
                return;
            }

            var roleId = selectedRowIds[0];
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

        /* ********************授权相关 --结束-- ***************************************************** */



        /* ********************搜索相关 --开始-- ***************************************************** */

        // 重置
        $("#roleReset").click(function () {
            $("#roleSearchCont").val("");
            $("#roleTable").trigger("reloadGrid");
        });

        // 搜索
        $("#roleSearch").click(function () {
            var searchCont = $("#roleSearchCont").val();
            if('' == searchCont){
                alert("搜索内容不能为空");
                return false;
            }
            $("#roleTable").trigger("reloadGrid");
        });

        // 添加角色
        $("#addRole").click(function () {
            addFormValidator.resetForm();
            $("#addRoleDialog").dialog("open");
        });

        // 修改角色
        $("#updateRole").click(function () {
            var selectedRowIds = $("#roleTable").jqGrid('getGridParam', 'selarrrow');
            if (selectedRowIds.length != 1) {
                alert("必须且只能选择一条记录进行修改");
                return;
            }

            // 将表格一行的数据取出来修改
            var formData = $("#roleTable").jqGrid('getRowData', selectedRowIds[0]);
            web.form.setValues($("#updateRoleForm"), formData);
            $("#updateRoleDialog").dialog("open");
        });

        // 删除角色
        $("#delRole").click(function () {
            var selectedRowIds = $("#roleTable").jqGrid('getGridParam', 'selarrrow');
            if (selectedRowIds.length == 0) {
                alert("请选择要删除的角色");
                return;
            }

            if (confirm("删除角色之后会将 角色-权限, 用户-角色 关联关系也一并删除！确认删除?")) {
                $.post(basePath + "/role/delRole", {"id": selectedRowIds.join(",")}, function (data) {
                    if ('200' == data['code']) {
                        alert("删除成功");
                        $("#roleTable").trigger("reloadGrid");
                    } else if ('500' == data['code']) {
                        alert(data['msg']);
                    }
                });
            }
        });

        /* ********************搜索相关 --结束-- ***************************************************** */

    }


});

