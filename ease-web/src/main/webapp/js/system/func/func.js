/**
 * Created by webinglin on 2017/5/12.
 */


$(function () {
    var rootId = "00000000000000000000000000000000";
    var funcTree ;

    // 初始化菜单权限树
    initFuncTree();

    // 监听按钮事件
    listenEvents();

    $(".btn").button();

    $("#funcTable").jqGrid({
        url: basePath + "/func/queryFuncs",
        datatype: "json",
        colNames: ['id', '权限名称', '权限URL', '权限图标URL', '权限类型', '权限字符描述', '权限顺序', '备注'],
        colModel: [
            {name: 'id', index: 'ID', hidden: true, width: 60},
            {name: 'funcTitle', index: 'FUNC_TITLE', width: 100},
            {name: 'funcUrl', index: 'FUNC_URL', width: 120},
            {name: 'imageUrl', index: 'IMAGE_URL', width: 100},
            {name: 'funcType', index: 'FUNC_TYPE', width: 100},
            {name: 'permission', index: 'PERMISSION', width: 120},
            {name: 'orderStr', index: 'ORDER_STR', width: 100},
            {name: 'remark', index: 'REMARK', width: 80}
        ],
        pager: '#funcPager',
        width: 800,
        height: 400,
        rowNum: 20,
        rowList: [20, 50, 100],
        sortname: 'ORDER_STR',
        sortorder: "asc",
        viewrecords: true,
        jsonReader: {
            root: "datas",
            repeatitems: false
        },
        caption: "权限信息",
        onSelectRow: function (rowid, status) {
            $("#funcSelectedRowId").val(rowid);
        },
        beforeRequest: function () {
            var pid = $("#parentId").val();
            if ('' == pid) {
                pid = rootId;
            }

            var params = {'parentId': pid};
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
                    $("#parentId").val(treeNode['id']);
                    $("#funcTable").trigger("reloadGrid");
                }
            }
        };


        $.post(basePath + "/func/queryFuncs", {"rows": 9999, "from": "tree", "parentId": rootId}, function (data) {
            funcTree = $.fn.zTree.init($("#funcTree"), setting, data['datas']);
            funcTree.expandAll(true);
        });
    }


    $("#addFuncDialog").dialog({
        autoOpen:false,
        width:500,
        height:500,
        modal: true,
        title:"新增菜单/权限",
        buttons: [{
            text: "保存",
            click: function () {
                $.post(basePath+"/func/addFunc", web.form.getValues($("#addFuncForm")), function (data) {
                    if("200"==data['code']) {
                        alert("添加权限成功");

                        // 根节点添加的子节点，重新构造树
                        var selectedTreeFuncId = $("#parentId").val();
                        if(rootId == selectedTreeFuncId) {
                            funcTree.addNodes(funcTree.getNodeByParam("id",rootId), data['data']);
                        }

                        $("#funcTable").trigger("reloadGrid");
                        $("#addFuncDialog").dialog("close");

                        web.form.reset($("#addFuncForm"));
                        $("#parentId").val(selectedTreeFuncId);
                    } else if("500" == data['code'] ){
                        alert(data['msg']);
                    }
                });
            }
        }, {
            text: "取消",
            click: function () {
                $(this).dialog("close");
            }
        }
        ]
    });


    function listenEvents() {
        $("#funcReset").click(function () {
            $("#searchCont").val("");
            $("#parentId").val(rootId);
            $("#funcTable").trigger("reloadGrid");
        });


        // 搜索
        $("#funcSearch").click(function () {

        });


        // 添加权限
        $("#addFunc").click(function () {
            if(''==$("#parentId").val()){
                alert("请先从左侧选择顶级菜单再添加权限");
                return false;
            }
            $("#addFuncDialog").dialog("open");
        });

        // 修改权限
        $("#updateFunc").click(function () {

        });

        // 删除权限
        $("#delFunc").click(function () {
            var funcId = $("#funcSelectedRowId").val();
            if(""==funcId){
                alert("请从表格中选择要删除的权限");
                return false;
            }

            $.post(basePath+"/func/delFunc", {"id":funcId}, function (data) {
                if('200'==data['code']) {
                    $("#funcTable").trigger("reloadGrid");
                    if(rootId== $("#parentId").val()) {
                        funcTree.removeNode(funcTree.getNodeByParam("id",funcId));
                    }
                    alert("删除成功");
                } else if('500'==data['code']) {
                    alert(data['msg']);
                }
            });
        });
    }


});



