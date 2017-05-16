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
        colNames:['id','用户名', '姓名', '证件号码','单位ID','单位','用户状态',"联系方式","最后登录IP","最后登录时间","备注", "操作"],
        colModel:[
            {name:'id', hidden:true, width:60},
            {name:'userName',index:'USER_NAME', width:120},
            {name:'realName',index:'REAL_NAME', width:120},
            {name:'idcard',index:'IDCARD', width:200},
            {name:'deptId',index:'DEPT_ID', hidden:true, width:60},
            {name:'deptName',index:'DEPT_ID', width:240},
            {name:'status',index:'STATUS', width:120},
            {name:'telphone',index:'TELPHONE', width:200},
            {name:'lastLoginIp', sortable:false, index:'LAST_LOGIN_IP', width:200},
            {name:'lastLoginTime',index:'LAST_LOGIN_TIME', width:200},
            {name:'remark', sortable:false, index:'REMARK', hidden:true, width:80},
            {name:'op', sortable:false, formatter:function (cellvalue, options, rowData) {
                return "<a class='link-see' style='padding-left:10px;' data-id='"+rowData['id']+"'><i class='icon-eye-open'></i> 查看</a>";
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
        height: 550,
        modal: true,
        title: "新增用户",
        buttons: [{
            text: "保存",
            click: function () {
                if (!addFormValidator.check()) {
                    return false;
                }

                var formData = web.form.getValues($("#addUserForm"));
                // 加上选择的角色ID，逗号分割
                var selectedRoleIds = $("#addUserMultiSelect").val();
                if(selectedRoleIds) {
                    formData['roleIds'] = selectedRoleIds.join(",");
                }

                formData['password'] = $.md5(formData['password']);
                $.post(basePath + "/user/addUser", formData, function (data) {
                    if ("200" == data['code']) {
                        alert("添加成功");

                        $("#userTable").trigger("reloadGrid");
                        $("#addUserDialog").dialog("close");

                        // 保留parentDeptId和parentDeptCode不能清除
                        var userDeptId = $("#userDeptId").val();
                        var userDeptCode = $("#userDeptCode").val();
                        web.form.reset($("#addUserForm"));
                        $("#userDeptId").val(userDeptId);
                        $("#userDeptCode").val(userDeptCode);

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
        height: 550,
        modal: true,
        title: "修改用户",
        buttons: [{
            text: "确认修改",
            click: function () {
                if (!updateFormValidator.check()) {
                    return false;
                }

                var formData = web.form.getValues($("#updateUserForm"));
                // 加上选择的角色ID，逗号分割
                var selectedRoleIds = $("#updateUserMultiSelect").val();
                if(selectedRoleIds) {
                    formData['roleIds'] = selectedRoleIds.join(",");
                }

                $.post(basePath + "/user/updateUser", formData, function (data) {
                    if ("200" == data['code']) {
                        alert("修改成功");

                        $("#userTable").trigger("reloadGrid");
                        $("#updateUserDialog").dialog("close");

                        // 保留parentDeptId和parentDeptCode不能清除
                        var userDeptId = $("#userDeptId").val();
                        var userDeptCode = $("#userDeptCode").val();
                        web.form.reset($("#updateUserForm"));
                        $("#userDeptId").val(userDeptId);
                        $("#userDeptCode").val(userDeptCode);

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


    // 加载所有的角色列表
    $.post(basePath+"/role/queryRoles",{"rows":99999}, function (data) {
        if(!data){
            return false;
        }
        var roleList = data['datas'];
        for(var i=0,len=roleList.length; i<len; i++){
            $('#addUserMultiSelect').append($("<option value='"+roleList[i]['id']+"'>"+roleList[i]['roleName']+"</option>"));
            $('#updateUserMultiSelect').append($("<option value='"+roleList[i]['id']+"'>"+roleList[i]['roleName']+"</option>"));
        }

        // 初始化下拉多选框
        $('#addUserMultiSelect,#updateUserMultiSelect').multiselect({
            header: true,
            height: 175,
            minWidth: 202,
            classes: '',
            checkAllText: '选中全部',
            uncheckAllText: '取消全选',
            noneSelectedText: '请选择角色',
            selectedText: '# 选中',
            selectedList: 20,
            show: null,
            hide: null,
            autoOpen: false,
            multiple: true,
            position: {},
            appendTo: "#userAppendToDiv",
            menuWidth:null
        });

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

            // 获取角色 重新设置数组['1','2'] 到multiselect里面 .val([]); 然后刷新界面显示
            // $("#updateUserForm .user-role-multi-select").multiselect("refresh")
            $.post(basePath+"/user/queryUserRoles",{"userId":selectedRowIds[0]}, function (data) {
                if(data==null){
                    return false;
                }
                var roleIds = [];
                for(var i=0,len=data.length; i<len; i++){
                    roleIds.push(data[i]['id']);
                }
                $("#updateUserMultiSelect").val(roleIds);
                $("#updateUserMultiSelect").multiselect("refresh");
            });

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

        $(document).on("click",".link-see",function () {
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