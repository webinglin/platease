/**
 * Created by webinglin on 2017/5/12.
 */


$(function () {
    var rootId = "00000000000000000000000000000000";
    var funcTree;

    // 初始化菜单权限树
    initFuncTree();

    // 监听按钮事件
    listenEvents();

    $(".btn").button();

    $("#funcTable").jqGrid({
        url: basePath + "/func/queryFuncs",
        datatype: "json",
        mtype:"POST",
        colNames: ['id', '权限名称', '权限URL', '权限图标URL', '权限类型', '权限字符描述', '权限顺序', '备注'],
        colModel: [
            {name: 'id', index: 'ID', hidden: true, width: 60},
            {name: 'funcTitle', index: 'FUNC_TITLE', width: 100},
            {name: 'funcUrl', index: 'FUNC_URL', width: 120},
            {name: 'imageUrl', index: 'IMAGE_URL', width: 100},
            {name: 'funcType', index: 'FUNC_TYPE', width: 100},
            {name: 'permission', index: 'PERMISSION', width: 120},
            {name: 'orderStr', index: 'ORDER_STR', width: 100},
            {name: 'remark', index: 'REMARK', sortable:false, width: 80}
        ],
        pager: '#funcPager',
        width: 800,
        height: 490,
        rowNum: 20,
        rowList: [20, 50, 100],
        sortname: 'ORDER_STR',
        sortorder: "asc",
        viewrecords: true,
        multiselect: true,
        multiboxonly: true,
        jsonReader: {
            root: "datas",
            repeatitems: false
        },
        caption: "权限信息",
        beforeRequest: function () {
            var pid = $("#funcParentId").val();
            if ('' == pid) {
                pid = rootId;
            }

            var params = {'parentId': pid, 'searchCont':$("#funcSearchCont").val()};
            $("#funcTable").jqGrid('setGridParam', {postData: params});
        }
    });


    function initFuncTree() {
        var setting = {
            view: {
                dblClickExpand: false,
                showLine: true,
                selectedMulti: false
            },
            data: {
                key: {
                    name: "funcTitle"
                },
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId",
                    rootPId: rootId
                }
            },
            callback: {
                beforeClick: function (treeId, treeNode) {
                    $("#funcParentId").val(treeNode['id']);
                    $("#funcTable").trigger("reloadGrid");
                }
            }
        };


        $.post(basePath + "/func/queryFuncs", {"rows": 9999, "from": "tree", "parentId": rootId}, function (data) {
            funcTree = $.fn.zTree.init($("#funcTree"), setting, data['datas']);
            funcTree.expandAll(true);
        });
    }


    // 表单验证
    var addFormValidator = $("#addFuncForm").Validform({tiptype: 3});
    var updateFormValidator = $("#updateFuncForm").Validform({tiptype: 3});


    $("#addFuncDialog").dialog({
        autoOpen: false,
        appendTo: "#content",
        width: 500,
        height: 500,
        modal: true,
        title: "新增菜单/权限",
        buttons: [{
            text: "保存",
            click: function () {
                if (!addFormValidator.check()) {
                    return false;
                }

                $.post(basePath + "/func/addFunc", web.form.getValues($("#addFuncForm")), function (data) {
                    if ("200" == data['code']) {
                        alert("添加权限成功");

                        // 根节点添加的子节点，重新构造树
                        var selectedTreeFuncId = $("#funcParentId").val();
                        if (rootId == selectedTreeFuncId) {
                            funcTree.addNodes(funcTree.getNodeByParam("id", rootId), -1, data['data']);
                        }

                        $("#funcTable").trigger("reloadGrid");
                        $("#addFuncDialog").dialog("close");

                        web.form.reset($("#addFuncForm"));
                        $("#funcParentId").val(selectedTreeFuncId);
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


    $("#updateFuncDialog").dialog({
        autoOpen: false,
        appendTo: "#content",
        width: 500,
        height: 500,
        modal: true,
        title: "修改菜单/权限",
        buttons: [{
            text: "确认修改",
            click: function () {
                if (!updateFormValidator.check()) {
                    return false;
                }

                var formData = web.form.getValues($("#updateFuncForm"));
                $.post(basePath + "/func/updateFunc", formData, function (data) {
                    if ("200" == data['code']) {
                        alert("权限修改成功");

                        $("#funcTable").trigger("reloadGrid");
                        $("#updateFuncDialog").dialog("close");
                        web.form.reset($("#updateFuncForm"));

                        var zNode = funcTree.getNodeByParam("id",formData['id'] );
                        if(zNode!=null){
                            zNode['funcTitle'] = formData['funcTitle'];
                            funcTree.updateNode(zNode);
                        }
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


    function listenEvents() {
        $("#funcReset").click(function () {
            $("#funcSearchCont").val("");
            // $("#funcParentId").val(rootId);
            $("#funcTable").trigger("reloadGrid");
        });


        // 搜索
        $("#funcSearch").click(function () {
            var searchCont = $("#funcSearchCont").val();
            if('' == searchCont){
                return false;
            }
            $("#funcTable").trigger("reloadGrid");
        });


        // 添加权限
        $("#addFunc").click(function () {
            if ('' == $("#funcParentId").val()) {
                $("#funcParentId").val(rootId);
            }
            addFormValidator.resetForm();
            $("#addFuncDialog").dialog("open");
        });

        // 修改权限
        $("#updateFunc").click(function () {
            var selectedRowIds = $("#funcTable").jqGrid('getGridParam', 'selarrrow');
            if (selectedRowIds.length != 1) {
                alert("必须且只能选择一条记录进行修改");
                return;
            }

            // 将表格一行的数据取出来修改
            var formData = $("#funcTable").jqGrid('getRowData', selectedRowIds[0]);
            web.form.setValues($("#updateFuncForm"), formData);

            $("#updateFuncDialog").dialog("open");
        });

        // 删除权限
        $("#delFunc").click(function () {
            var selectedRowIds = $("#funcTable").jqGrid('getGridParam', 'selarrrow');
            if (selectedRowIds.length == 0) {
                alert("请从表格中选择要删除的权限");
                return;
            }

            if (confirm("删除之后子菜单/权限将会一并删除！确认删除?")) {
                $.post(basePath + "/func/delFunc", {"id": selectedRowIds.join(",")}, function (data) {
                    if ('200' == data['code']) {
                        $("#funcTable").trigger("reloadGrid");
                        if (rootId == $("#funcParentId").val()) {
                            for (var i = 0, len = selectedRowIds.length; i < len; i++) {
                                funcTree.removeNode(funcTree.getNodeByParam("id", selectedRowIds[i]));
                            }
                        }
                        alert("删除成功");
                    } else if ('500' == data['code']) {
                        alert(data['msg']);
                    }
                });
            }
        });
    }


});



