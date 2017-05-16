<%--
  User: webinglin
  Date: 2017/4/19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
    request.setAttribute("URL", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
%>
<html>
<head>
    <title>角色管理</title>
    <link rel="stylesheet" href="${URL}/css/system/role/role.css"/>
</head>
<body>

<div class="role-table">

    <form onsubmit="return false">
        <input type="text" name="searchCont" id="roleSearchCont" placeholder="角色名称">

        <a class="btn" id="roleSearch"><i class="icon-search"></i> 过滤</a>
        <a class="btn" id="roleReset"><i class="icon-spinner"></i> 重置</a>
    </form>

    <div class="btn-group">
        <shiro:hasPermission name="/role/addRole">
            <button class="btn" id="addRole"><i class="icon-plus"></i> 新增</button>
        </shiro:hasPermission>

        <shiro:hasPermission name="/role/updateRole">
            <button class="btn" id="updateRole"><i class="icon-edit"></i> 修改</button>
        </shiro:hasPermission>

        <shiro:hasPermission name="/role/delRole">
            <button class="btn" id="delRole"><i class="icon-trash"></i> 删除</button>
        </shiro:hasPermission>
    </div>


    <table id="roleTable"></table>
    <div id="rolePager"></div>
</div>

<div class="role-funcs">
    <div class="role-funcs-legend">
<c:forEach var="legend" items="${funcLegends}">
    <fieldset>
        <legend>${legend.legend}</legend>

        <c:forEach var="func" items="${legend.funcs}">
            <label for="${func.id}" class="ui-margin-8">${func.funcTitle}</label>
            <input type="checkbox" class="ui-checkbox" id="${func.id}">
        </c:forEach>
    </fieldset>
</c:forEach>
    </div>
    <div class="role-funcs-btn">
        <button class="btn" id="roleFuncChoseAll"><i class="icon-check"></i> 全选</button>
        <button class="btn" id="roleFuncDisChoseAll"><i class="icon-check-empty"></i> 反选</button>

        <button class="btn float-right" id="roleFuncSave"><i class="icon-save"></i> 保存修改</button>
    </div>
</div>


<div id="addRoleDialog" style="display: none;">
    <form id="addRoleForm" >
        <div class="form-line">
            <label>角色名称</label><input type="text" name="roleName" maxlength="125" datatype="*2-125" errormsg="角色名称至少2个字符" >
        </div>
        <div class="form-line"><label>备注</label><textarea name="remark" maxlength="125"></textarea></div>
    </form>
</div>

<div id="updateRoleDialog" style="display: none;">
    <form id="updateRoleForm" >
        <input type="hidden" name="id" >
        <div class="form-line">
            <label>角色名称</label><input type="text" name="roleName" maxlength="125" datatype="*2-125" errormsg="角色名称至少2个字符" >
        </div>
        <div class="form-line"><label>备注</label><textarea name="remark" maxlength="125"></textarea></div>
    </form>
</div>


<script type="text/javascript" src="${URL}/js/system/role/role.js"></script>
</body>
</html>
