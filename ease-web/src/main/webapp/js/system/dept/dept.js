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
        mtype:"POST",
        colNames: ['id', '单位名称', '单位编码', '单位别名', '备注'],
        colModel: [
            {name: 'id', index: 'ID', hidden: true, width: 60},
            {name: 'deptName', index: 'DEPT_NAME', width: 120},
            {name: 'deptCode', index: 'DEPT_CODE', width: 120},
            {name: 'alias', index: 'ALIAS', width: 120},
            {name: 'remark', index: 'REMARK', sortable:false, width: 80}
        ],
        pager: '#deptPager',
        width: 800,
        height: 490,
        rowNum: 20,
        rowList: [20, 50, 100],
        sortname: 'DEPT_CODE',
        sortorder: "asc",
        viewrecords: true,
        multiselect:true,
        multiboxonly:true,
        jsonReader: {
            root: "datas",
            repeatitems: false
        },
        caption: "单位信息",
        beforeRequest: function () {
            var params = {'searchDeptCode':$("#searchDeptCode").val(), 'searchCont':$("#deptSearchCont").val()};
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
                    $("#searchDeptCode").val(treeNode['deptCode']);
                    $("#deptTable").trigger("reloadGrid");
                }
            }
        };

        $.post(basePath + "/dept/queryDepts", {"rows": 9999 }, function (data) {
            deptTree = $.fn.zTree.init($("#deptTree"), setting, data['datas']);
            deptTree.expandAll(true);
        });
    }



    // 表单验证
    var addFormValidator = $("#addDeptForm").Validform({tiptype: 3});
    var updateFormValidator = $("#updateDeptForm").Validform({tiptype: 3});


    $("#addDeptDialog").dialog({
        autoOpen:false,
        appendTo:"#content",
        width:500,
        height:500,
        modal: true,
        title:"新增单位",
        buttons: [{
            text: "保存",
            click: function () {
                if (!addFormValidator.check()) {
                    return false;
                }

                $.post(basePath+"/dept/addDept", web.form.getValues($("#addDeptForm")), function (data) {
                    if("200"==data['code']) {
                        alert("添加单位成功");

                        // 根节点添加的子节点，重新构造树
                        var selectedTreeDeptId = $("#deptParentId").val();
                        deptTree.addNodes(deptTree.getNodeByParam("id",selectedTreeDeptId), -1, data['data']);

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

    $("#updateDeptDialog").dialog({
        autoOpen: false,
        appendTo: "#content",
        width: 500,
        height: 500,
        modal: true,
        title: "修改单位",
        buttons: [{
            text: "确认修改",
            click: function () {
                if (!updateFormValidator.check()) {
                    return false;
                }

                var formData = web.form.getValues($("#updateDeptForm"));
                $.post(basePath + "/dept/updateDept", formData, function (data) {
                    if ("200" == data['code']) {
                        alert("修改成功");

                        $("#deptTable").trigger("reloadGrid");
                        $("#updateDeptDialog").dialog("close");
                        web.form.reset($("#updateDeptForm"));

                        var zNode = deptTree.getNodeByParam("id",formData['id'] );
                        if(zNode!=null){
                            zNode['deptName'] = formData['deptName'];
                            deptTree.updateNode(zNode);
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
        $("#deptReset").click(function () {
            $("#deptSearchCont").val("");
            $("#deptTable").trigger("reloadGrid");
        });


        // 搜索
        $("#deptSearch").click(function () {
            var searchCont = $("#deptSearchCont").val();
            if('' == searchCont){
                return false;
            }
            $("#deptTable").trigger("reloadGrid");
        });


        // 添加权限
        $("#addDept").click(function () {
            if(''==$("#deptParentId").val()){
                alert("请先从左侧选择上级单位");
                return false;
            }
            addFormValidator.resetForm();
            $("#addDeptDialog").dialog("open");
        });

        // 修改权限
        $("#updateDept").click(function () {
            var selectedRowIds = $("#deptTable").jqGrid('getGridParam', 'selarrrow');
            if (selectedRowIds.length != 1) {
                alert("必须且只能选择一条记录进行修改");
                return;
            }

            var formData = $("#deptTable").jqGrid('getRowData', selectedRowIds[0]);
            web.form.setValues($("#updateDeptForm"), formData);

            $("#updateDeptDialog").dialog("open");
        });

        // 删除权限
        $("#delDept").click(function () {
            var selectedRowIds = $("#deptTable").jqGrid('getGridParam','selarrrow');
            if(selectedRowIds.length==0){
                alert("请从表格中选择要删除的单位");
                return false;
            }
            
            if(confirm("删除之后子单位将会一并删除！确认删除?")){
                $.post(basePath+"/dept/delDept", {"id":selectedRowIds.join(",")}, function (data) {
                    if('200'==data['code']) {
                        $("#deptTable").trigger("reloadGrid");
                        for(var i=0,len=selectedRowIds.length; i<len; i++) {
                            deptTree.removeNode(deptTree.getNodeByParam("id",selectedRowIds[i]));
                        }
                        alert("删除成功");
                    } else if('500'==data['code']) {
                        alert(data['msg']);
                    }
                });
            }
        });
    }


});
