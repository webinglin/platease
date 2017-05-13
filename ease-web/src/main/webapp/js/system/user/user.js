/**
 * Created by webinglin on 2017/5/9.
 */

$(function () {
    var rootId = "00000000000000000000000000000000";
    var userDeptTree ;

    $(".btn").button();

    $("#userTable").jqGrid({
        url: basePath + "/user/queryUsers",
        datatype: "json",
        mtype:"POST",
        colNames:['id','pwd','用户名', '姓名', '证件号码','单位ID','单位','用户状态',"联系方式","最后登录IP","最后登录时间","备注", "操作"],
        colModel:[
            {name:'id', hidden:true, width:60},
            {name:'password',hidden:true, width:60},
            {name:'userName',index:'USER_NAME', width:120},
            {name:'realName',index:'REAL_NAME', width:120},
            {name:'idcard',index:'IDCARD', width:200},
            {name:'deptId',index:'DEPT_ID', hidden:true, width:60},
            {name:'deptName',index:'DEPT_ID', width:240},
            {name:'status',index:'STATUS', width:120},
            {name:'telphone',index:'TELPHONE', width:200},
            {name:'lastLoginIp',index:'LAST_LOGIN_IP', width:140},
            {name:'lastLoginTime',index:'LAST_LOGIN_TIME', width:140},
            {name:'remark',index:'REMARK', hidden:true, width:80},
            {name:'op',formatter:function (cellvalue, options, rowData) {
                return "<a class='a-see' data-id='"+rowData['id']+"'>查看</a>";
            }}
        ],
        pager : '#userPager',
        width:980,
        height:490,
        rowNum : 10,
        rowList : [ 10, 20, 30 ],
        sortname : 'USER_NAME',
        sortorder : "desc",
        viewrecords : true,
        multiselect:true,
        multiboxonly:true,
        jsonReader : {
            root: "datas",
            repeatitems:false
        },
        caption : "用户信息列表",
        beforeRequest: function () {
            var params = {};
            if(''!=$("#userDeptCode").val()){
                params['deptCode'] = $("#userDeptCode").val();
            }
            params['searchCont']=$("#userSearchCont").val();
            $("#userTable").jqGrid('setGridParam', {postData: params});
        }
    });

    initDeptTree();

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
                    $("#userDeptCode").val(treeNode['deptCode']);
                    $("#userDeptId").val(treeNode['id']);
                    $("#userTable").trigger("reloadGrid");
                }
            }
        };

        $.post(basePath + "/dept/queryDepts", {"rows": 9999}, function (data) {
            userDeptTree = $.fn.zTree.init($("#userDeptTree"), setting, data['datas']);
            userDeptTree.expandAll(true);
        });
    }

    // 表单验证
    var addFormValidator = $("#addUserForm").Validform({tiptype: 3});
    var updateFormValidator = $("#updateUserForm").Validform({tiptype: 3});

    $("#addUserDialog").dialog({
        autoOpen: false,
        appendTo: "#content",
        width: 500,
        height: 500,
        modal: true,
        title: "新增用户",
        buttons: [{
            text: "保存",
            click: function () {
                if (!addFormValidator.check()) {
                    return false;
                }

                var formData = web.form.getValues($("#addUserForm"));
                formData['password'] = $.md5(formData['password']);
                $.post(basePath + "/user/addUser", formData, function (data) {
                    if ("200" == data['code']) {
                        alert("添加成功");

                        $("#userTable").trigger("reloadGrid");
                        $("#addUserDialog").dialog("close");
                        web.form.reset($("#addUserForm"));

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

    $("#updateUserDialog").dialog({
        autoOpen: false,
        appendTo: "#content",
        width: 500,
        height: 500,
        modal: true,
        title: "修改用户",
        buttons: [{
            text: "确认修改",
            click: function () {
                if (!updateFormValidator.check()) {
                    return false;
                }

                var formData = web.form.getValues($("#updateUserForm"));
                // 对密码加密
                formData['password'] = $.md5(formData['password']);
                $.post(basePath + "/user/updateUser", formData, function (data) {
                    if ("200" == data['code']) {
                        alert("修改成功");

                        $("#userTable").trigger("reloadGrid");
                        $("#updateUserDialog").dialog("close");
                        web.form.reset($("#updateUserForm"));

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

    $("#viewUserDialog").dialog({
        autoOpen: false,
        appendTo: "#content",
        width: 500,
        height: 600,
        modal: true,
        title: "用户信息"
    });

    // 监听事件
    listenEvents();

    function listenEvents() {
        $("#userReset").click(function () {
            $("#userSearchCont").val("");
            $("#userTable").trigger("reloadGrid");
        });


        // 搜索
        $("#userSearch").click(function () {
            var searchCont = $("#userSearchCont").val();
            if('' == searchCont){
                alert("搜索内容不能为空");
                return false;
            }
            $("#userTable").trigger("reloadGrid");
        });


        // 添加用户
        $("#addUser").click(function () {
            if ('' == $("#userDeptCode").val()) {
                alert("请选择需要添加用户的单位");
                return false;
            }

            addFormValidator.resetForm();
            $("#addUserDialog").dialog("open");
        });

        // 修改用户信息
        $("#updateUser").click(function () {
            var selectedRowIds = $("#userTable").jqGrid('getGridParam', 'selarrrow');
            if (selectedRowIds.length != 1) {
                alert("必须且只能选择一条记录进行修改");
                return;
            }

            // 将表格一行的数据取出来修改
            var formData = $("#userTable").jqGrid('getRowData', selectedRowIds[0]);
            web.form.setValues($("#updateUserForm"), formData);

            $("#updateUserDialog").dialog("open");
        });

        // 删除用户
        $("#delUser").click(function () {
            var selectedRowIds = $("#userTable").jqGrid('getGridParam', 'selarrrow');
            if (selectedRowIds.length == 0) {
                alert("请选择要删除的用户");
                return;
            }

            if (confirm("删除用户后用户-角色映射关系也会一并删除！确认删除?")) {
                $.post(basePath + "/user/delUser", {"id": selectedRowIds.join(",")}, function (data) {
                    if ('200' == data['code']) {
                        $("#userTable").trigger("reloadGrid");
                        alert("删除成功");
                    } else if ('500' == data['code']) {
                        alert(data['msg']);
                    }
                });
            }
        });


        /* ****************************查看链接************************** */

        $(document).on("click",".a-see",function () {
            var userId = $(this).attr("data-id");
            if(''==userId){
                alert("用户ID为空，无法查看数据");
                return false;
            }

            $.post(basePath+"/user/queryUserById",{"id":userId},function (data) {
                web.form.setTexts($("#viewUserForm"), data);
                $("#viewUserDialog").dialog("open");
            });
        });

    }
});