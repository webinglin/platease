<%--
  User: webinglin
  Date: 2017/4/19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>PLATEASE</title>
    <meta charset="utf-8"/>
</head>

<%@include file="/WEB-INF/pages/common/common.jsp" %>
<link rel="stylesheet" href="${URL}/css/system/home/home.css"/>
<link rel="stylesheet" href="${URL}/plugins/jqgrid/ui.jqgrid.css"/>

<body>

<nav class="main-menu">
    <div class="logo">LOGO</div>

    <div class="settings">系统管理</div>
    <ul>
        <li>
            <a class="link" data-url="${URL}/welcome">
                <i class="fa fa-lg icon-home"></i>
                <span class="nav-text">HOME</span>
            </a>
        </li>

        <shiro:hasPermission name="/user/index">
        <li>
            <a class="link" data-url="${URL}/user/index">
                <i class="fa fa-lg icon-user"></i>
                <span class="nav-text">用户管理</span>
            </a>
        </li>
        </shiro:hasPermission>

        <shiro:hasPermission name="/role/index">
        <li>
            <a class="link" data-url="${URL}/role/index">
                <i class="fa fa-lg icon-role"></i>
                <span class="nav-text">角色管理</span>
            </a>
        </li>
        </shiro:hasPermission>

        <shiro:hasPermission name="/func/index">
        <li>
            <a class="link" data-url="${URL}/func/index">
                <i class="fa fa-lg icon-func"></i>
                <span class="nav-text">权限管理</span>
            </a>
        </li>
        </shiro:hasPermission>

        <shiro:hasPermission name="/dept/index">
        <li>
            <a class="link" data-url="${URL}/dept/index">
                <i class="fa fa-lg icon-dept"></i>
                <span class="nav-text">单位管理</span>
            </a>
        </li>
        </shiro:hasPermission>
    </ul>

    <ul class="logout">
        <li>
            <a href="http://webinglin.github.io" target="_blank">
                <i class="fa fa-lg icon-link"></i>
                <span class="nav-text">WEBINGLIN</span>
            </a>
        </li>
        <li>
            <a href="${URL}/logout" target="_self">
                <i class="fa fa-lg icon-exit"></i>
                <span class="nav-text">退出系统</span>
            </a>
        </li>
    </ul>
</nav>

<div class="content" id="content">
       WELCOME TO PLATEASE
</div>

<script type="text/javascript" src="${URL}/plugins/jqgrid/grid.locale-cn.js"></script>
<script type="text/javascript" src="${URL}/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${URL}/js/system/home.js"></script>
</body>
</html>
