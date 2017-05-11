/**
 * Created by webinglin on 2017/5/9.
 */

$(function () {

    // $("#userTabs").tabs();

    $("#userManagerTable").jqGrid({
        url: basePath + "/user/queryUsers",
        datatype: "json",
        colNames:['id','用户名', '姓名', '证件号码','单位ID','单位','用户状态',"联系方式","最后登录IP","最后登录时间","备注"],
        colModel:[
            {name:'id',index:'ID', hidden:true, width:60},
            {name:'userName',index:'USER_NAME', width:100},
            {name:'realName',index:'REAL_NAME', width:120},
            {name:'idcard',index:'IDCARD', width:150},
            {name:'deptId',index:'DEPT_ID', hidden:true, width:60},
            {name:'deptName',index:'DEPT_ID', width:150},
            {name:'status',index:'STATUS', width:80},
            {name:'telphone',index:'TELPHONE', width:100},
            {name:'lastLoginIp',index:'LAST_LOGIN_IP', width:120},
            {name:'lastLoginTime',index:'LAST_LOGIN_TIME', width:140},
            {name:'remark',index:'REMARK', width:80}
            // ,
            // {name:'op',formatter:function (cellvalue, options, rowObject) {
            //     return "<a>查看</a> | <a>编辑</a> | <a>删除</a>";
            // }}
        ],
        pager : '#userManagerPager',
        width:1000,
        height:400,
        rowNum : 10,
        rowList : [ 10, 20, 30 ],
        sortname : 'USER_NAME',
        sortorder : "desc",
        viewrecords : true,
        jsonReader : {
            root: "datas",
            repeatitems:false
        },
        caption : "用户信息列表"
    });

});