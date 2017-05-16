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
<link rel="stylesheet" href="${URL}/css/system/manage.css"/>
<link rel="stylesheet" href="${URL}/plugins/jqgrid/ui.jqgrid.css"/>
<link rel="stylesheet" href="${URL}/plugins/validate/validform.css"/>

<body>

<nav class="main-menu">
    <div class="settings"><i class="icon-cogs"></i> 系统管理</div>
    <ul class="nav nav-list">
        <li>
            <a class="link plat-intro" data-url="${URL}/system/intro">
                <i class="icon-home"></i> 平台介绍
            </a>
        </li>
        <shiro:hasPermission name="/user/index">
        <li>
            <a class="link" data-url="${URL}/user/index">
                <i class="icon-user"></i> 用户管理
            </a>
        </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="/role/index">
        <li>
            <a class="link" data-url="${URL}/role/index">
                <i class="icon-qrcode"></i> 角色管理
            </a>
        </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="/func/index">
        <li>
            <a class="link" data-url="${URL}/func/index">
                <i class="icon-magic"></i> 权限管理
            </a>
        </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="/dept/index">
        <li>
            <a class="link" data-url="${URL}/dept/index">
                <i class="icon-sitemap"></i> 单位管理
            </a>
        </li>
        </shiro:hasPermission>
    </ul>

    <ul class="logout">
        <li>
            <a href="http://webinglin.github.io" target="_blank">
                <i class="icon-link"></i> WEBINGLIN
            </a>
        </li>
        <li>
            <a href="${URL}/logout" target="_self">
                <i class="icon-signout"></i> 退出系统
            </a>
        </li>
    </ul>
</nav>

<div class="content" id="content">
</div>

<script type="text/javascript" src="${URL}/plugins/jqgrid/grid.locale-cn.js"></script>
<script type="text/javascript" src="${URL}/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${URL}/plugins/validate/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${URL}/js/system/manage.js"></script>
</body>
</html>
