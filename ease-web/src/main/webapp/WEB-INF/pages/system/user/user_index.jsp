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
</head>

<body>

<div class="dept-tree">
    <ul id="userDeptTree" class="ztree"></ul>
</div>
<div class="user-table">
    <form>
        <input type="text" name="searchCont" id="userSearchCont" placeholder="搜索内容">

        <a class="btn" id="userSearch">过滤</a>
        <a class="btn" id="userReset">重置</a>
    </form>

    <div class="btn-group">
        <shiro:hasPermission name="/user/addUser">
            <button class="btn">新增</button>
        </shiro:hasPermission>

        <shiro:hasPermission name="/user/editUser">
            <button class="btn">修改</button>
        </shiro:hasPermission>

        <shiro:hasPermission name="/user/delUser">
            <button class="btn">删除</button>
        </shiro:hasPermission>
    </div>

    <input type="hidden" id="deptSelectedRowId">
    <table id="userManagerTable" ></table>
    <div id="userManagerPager"></div>
</div>



<input type="hidden" name="deptCode" id="userDeptCode">



</body>

<script type="text/javascript" src="${URL}/plugins/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${URL}/js/system/user/user.js"></script>
</html>
