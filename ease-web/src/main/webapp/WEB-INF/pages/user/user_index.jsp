<%--
  User: webinglin
  Date: 2017/4/19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>--%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>PLATEASE</title>
    <link rel="stylesheet" href="css/system/user/userMgr.css"/>
</head>

<body>

<div class="user-tree">
xx

</div>
<div class="user-mgr-table">
    <div class="search-condition">

        <shiro:hasPermission name="/user/index">
            <button>查看</button>
        </shiro:hasPermission>

        <shiro:hasPermission name="/user/addUser">
            <button>新增</button>
        </shiro:hasPermission>

        <shiro:hasPermission name="/user/editUser">
            <button>修改</button>
        </shiro:hasPermission>

        <shiro:hasPermission name="/user/delUser">
            <button>删除</button>
        </shiro:hasPermission>
    </div>

    <table id="userManagerTable" ></table>
    <div id="userManagerPager"></div>
</div>
</body>

<script type="text/javascript" src="js/system/userMgr.js"></script>
</html>
