<%--
  User: webinglin
  Date: 2017/4/19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>角色管理</title>
    <link rel="stylesheet" href="css/system/role/role.css"/>
</head>
<body>

<div class="role-table">
    <input type="hidden" id="selectedRoleId">
    <table id="roleTable"></table>
    <div id="rolePager"></div>
</div>

<div class="role-funcs">
<c:forEach var="legend" items="${funcLegends}">
    <fieldset>
        <legend>${legend.legend}</legend>

        <c:forEach var="func" items="${legend.funcs}">
            <label for="${func.id}" class="ui-margin-8">${func.funcTitle}</label>
            <input type="checkbox" class="ui-checkbox" id="${func.id}">
        </c:forEach>
    </fieldset>
</c:forEach>

    <div class="role-funcs-btn">
        <button class="btn" id="roleFuncChoseAll">全选</button>
        <button class="btn" id="roleFuncDisChoseAll">反选</button>

        <button class="btn float-right" id="roleFuncSave">保存修改</button>
    </div>
</div>

<script type="text/javascript" src="js/system/role.js"></script>
</body>
</html>
