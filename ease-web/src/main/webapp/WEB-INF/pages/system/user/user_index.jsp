<%--
  User: webinglin
  Date: 2017/4/19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    request.setAttribute("URL", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
%>
<html>
<head>
    <title>PLATEASE</title>
    <link rel="stylesheet" href="${URL}/css/system/user/user.css"/>
    <link rel="stylesheet" href="${URL}/plugins/ztree/style/zTreeStyle.css"/>
    <link rel="stylesheet" href="${URL}/plugins/jquery-ui-multiselect/jquery.multiselect.css"/>
    <%--<link rel="stylesheet" href="${URL}/plugins/jquery-ui-multiselect/jquery.multiselect.filter.css"/>--%>
</head>

<body>

<div class="dept-tree">
    <ul id="userDeptTree" class="ztree"></ul>
</div>
<div class="user-table">
    <form onsubmit="return false">
        <input type="text" name="searchCont" style="width:240px;" id="userSearchCont" placeholder="用户名/用户姓名/证件号码/手机号码">

        <a class="btn" id="userSearch">过滤</a>
        <a class="btn" id="userReset">重置</a>
    </form>

    <div class="btn-group">
        <shiro:hasPermission name="/user/addUser">
            <button class="btn" id="addUser">新增</button>
        </shiro:hasPermission>

        <shiro:hasPermission name="/user/updateUser">
            <button class="btn" id="updateUser">修改</button>
        </shiro:hasPermission>

        <shiro:hasPermission name="/user/delUser">
            <button class="btn" id="delUser">删除</button>
        </shiro:hasPermission>
    </div>

    <table id="userTable" ></table>
    <div id="userPager"></div>
</div>


<div id="addUserDialog" style="display: none;">
    <form id="addUserForm" >
        <input type="hidden" name="deptCode" id="userDeptCode">
        <input type="hidden" name="deptId" id="userDeptId">

        <div class="form-line">
            <label>用户名</label><input type="text" name="userName" maxlength="125" datatype="*4-125" errormsg="用户名至少4个字符" >
        </div>
        <div class="form-line">
            <label>证件号码</label><input type="text" name="idcard" maxlength="125" datatype="n15-32" errormsg="证件号码至少15位" >
        </div>
        <div class="form-line">
            <label>姓名</label><input type="text" name="realName" maxlength="125" >
        </div>
        <div class="form-line">
            <label>密码</label><input type="password" name="password" maxlength="125" >
        </div>
        <div class="form-line">
            <label>联系方式</label><input type="text" name="telphone" maxlength="125" datatype="m" errormsg="请输入正确手机号码" >
        </div>
        <div class="form-line"><label>备注</label><textarea name="remark" maxlength="125"></textarea></div>

        <div class="form-line-role">
            <label>角色</label>
            <select class='user-role-multi-select' id="addUserMultiSelect" multiple="multiple">
            </select>
        </div>

    </form>
</div>


<div id="updateUserDialog" style="display: none;">
    <form id="updateUserForm" >
        <input type="hidden" name="id">

        <div class="form-line">
            <label>用户名</label><input type="text" name="userName" maxlength="125" datatype="*4-125" errormsg="用户名至少4个字符" >
        </div>
        <div class="form-line">
            <label>证件号码</label><input type="text" name="idcard" maxlength="125" datatype="n15-32" errormsg="证件号码至少15位数字" >
        </div>
        <div class="form-line">
            <label>姓名</label><input type="text" name="realName" maxlength="125" >
        </div>
        <div class="form-line">
            <label>联系方式</label><input type="text" name="telphone" maxlength="125" datatype="m" errormsg="请输入正确手机号码" >
        </div>
        <div class="form-line"><label>备注</label><textarea name="remark" maxlength="125"></textarea></div>

        <div class="form-line-role">
            <label>角色</label>
            <select class='user-role-multi-select' id='updateUserMultiSelect' multiple="multiple">
            </select>
        </div>
    </form>
</div>

<div id="viewUserDialog" style="display: none;">
    <form id="viewUserForm" >
        <div class="form-line">
            <label>用户名</label><span class="view-span" name="userName"></span>
        </div>
        <div class="form-line">
            <label>证件号码</label><span class="view-span" name="idcard" ></span>
        </div>
        <div class="form-line">
            <label>姓名</label><span class="view-span" name="realName" ></span>
        </div>
        <div class="form-line">
            <label>联系方式</label><span class="view-span" name="telphone" ></span>
        </div>
        <div class="form-line">
            <label>单位名称</label><span class="view-span" name="deptName" ></span>
        </div>
        <div class="form-line">
            <label>最后登录IP</label><span class="view-span" name="lastLoginIp" ></span>
        </div>
        <div class="form-line">
            <label>最后登录时间</label><span class="view-span"  name="lastLoginTimeStr" ></span>
        </div>
        <div class="form-line">
            <label>创建者</label><span class="view-span" name="creatorName" ></span>
        </div>
        <div class="form-line">
            <label>创建时间</label><span class="view-span" name="createTimeStr" ></span>
        </div>
        <div class="form-line form-line-multi">
            <label>备注</label><span class="view-span-multi" name="remark"></span>
        </div>
        <div class="form-line form-line-multi">
            <label>拥有的角色</label><span class="view-span-multi" name="roleNames"></span>
        </div>
    </form>
</div>

<div id="userAppendToDiv" style="display: none;"></div>

</body>

<script type="text/javascript" src="${URL}/js/common/jquery.md5.js"></script>
<script type="text/javascript" src="${URL}/plugins/ztree/jquery.ztree.all.min.js"></script>
<%--<script type="text/javascript" src="${URL}/plugins/jquery-ui-multiselect/jquery.multiselect.filter.js"></script>--%>
<script type="text/javascript" src="${URL}/plugins/jquery-ui-multiselect/jquery.multiselect.min.js"></script>
<script type="text/javascript" src="${URL}/js/system/user/user.js"></script>
</html>
