/**
 * Created by webinglin on 2017/5/12.
 */


$(function () {
    var rootId = "00000000000000000000000000000000";
    var deptTree ;

    // 初始化菜单权限树
    initDeptTree();

    // 监听按钮事件
    listenEvents();

    $(".btn").button();

    $("#deptTable").jqGrid({
        url: basePath + "/dept/queryDepts",
        datatype: "json",
        colNames: ['id', '单位名称', '单位编码', '单位别名', '备注'],
        colModel: [
            {name: 'id', index: 'ID', hidden: true, width: 60},
            {name: 'deptName', index: 'DEPT_NAME', width: 120},
            {name: 'deptCode', index: 'DEPT_CODE', width: 120},
            {name: 'alias', index: 'ALIAS', width: 120},
            {name: 'remark', index: 'REMARK', width: 80}
        ],
        pager: '#deptPager',
        width: 800,
        height: 400,
        rowNum: 20,
        rowList: [20, 50, 100],
        sortname: 'DEPT_NAME',
        sortorder: "asc",
        viewrecords: true,
        jsonReader: {
            root: "datas",
            repeatitems: false
        },
        caption: "单位信息",
        onSelectRow: function (rowid, status) {
            $("#deptSelectedRowId").val(rowid);
        },
        beforeRequest: function () {
            var pid = $("#deptParentId").val();
            if ('' == pid) {
                pid = rootId;
            }

            var params = {'parentId': pid};
            $("#deptTable").jqGrid('setGridParam', {postData: params});
        }
    });


    function initDeptTree() {
        var setting = {
            view: {
                dblClickExpand: false,
                showLine: true,
                selectedMulti: false
            },
            data: {
                key: {
                    name: "deptName"
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

                    $("#deptParentId").val(treeNode['id']);
                    $("#deptTable").trigger("reloadGrid");
                }
            }
        };


        $.post(basePath + "/dept/queryDepts", {"rows": 9999, "from": "tree"}, function (data) {
            deptTree = $.fn.zTree.init($("#deptTree"), setting, data['datas']);
            deptTree.expandAll(true);
        });
    }


    $("#addDeptDialog").dialog({
        autoOpen:false,
        width:500,
        height:500,
        modal: true,
        title:"新增单位",
        buttons: [{
            text: "保存",
            click: function () {
                $.post(basePath+"/dept/addDept", web.form.getValues($("#addDeptForm")), function (data) {
                    if("200"==data['code']) {
                        alert("添加单位成功");

                        // 根节点添加的子节点，重新构造树
                        var selectedTreeDeptId = $("#deptParentId").val();
                        deptTree.addNodes(deptTree.getNodeByParam("id",selectedTreeDeptId), data['data']);

                        $("#deptTable").trigger("reloadGrid");
                        $("#addDeptDialog").dialog("close");

                        web.form.reset($("#addDeptForm"));
                        $("#deptParentId").val(selectedTreeDeptId);
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
        $("#deptReset").click(function () {
            $("#deptSearchCont").val("");
            $("#deptParentId").val(rootId);
            $("#deptTable").trigger("reloadGrid");
        });


        // 搜索
        $("#deptSearch").click(function () {

        });


        // 添加权限
        $("#addDept").click(function () {
            if(''==$("#deptParentId").val()){
                alert("请先从左侧选择顶级菜单再添加权限");
                return false;
            }
            $("#addDeptDialog").dialog("open");
        });

        // 修改权限
        $("#updateDept").click(function () {

        });

        // 删除权限
        $("#delDept").click(function () {
            var deptId = $("#deptSelectedRowId").val();
            if(""==deptId){
                alert("请从表格中选择要删除的单位");
                return false;
            }
            
            if(confirm("删除之后子单位将会一并删除！确认删除?")){
                $.post(basePath+"/dept/delDept", {"id":deptId}, function (data) {
                    if('200'==data['code']) {
                        $("#deptTable").trigger("reloadGrid");
                        deptTree.removeNode(deptTree.getNodeByParam("id",deptId));
                        alert("删除成功");
                    } else if('500'==data['code']) {
                        alert(data['msg']);
                    }
                });
            }
        });
    }


});
