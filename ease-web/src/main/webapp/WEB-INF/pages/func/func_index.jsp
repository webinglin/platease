<%--
  User: webinglin
  Date: 2017/4/19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    request.setAttribute("URL", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());
%>

<html>
<head>
    <title>PLATEASE</title>
    <link rel="stylesheet" href="${URL}/css/system/func/func.css"/>
    <link rel="stylesheet" href="${URL}/plugins/ztree/style/zTreeStyle.css"/>
</head>

<body>
<div class="func-tree">
    <ul id="funcTree" class="ztree"></ul>
</div>
<div class="func-table">
    <form>
        <input type="text" name="searchCont" id="searchCont" placeholder="搜索内容">

        <a class="btn" id="funcSearch">过滤</a>
        <a class="btn" id="funcReset">重置</a>
    </form>

    <div class="btn-group">
        <shiro:hasPermission name="/func/addFunc">
            <button class="btn" id="addFunc">添加</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="/func/delFunc">
            <button class="btn" id="delFunc">删除</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="/func/updateFunc">
            <button class="btn" id="updateFunc">修改</button>
        </shiro:hasPermission>
    </div>

    <input type="hidden" id="funcSelectedRowId">
    <table id="funcTable"></table>
    <div id="funcPager"></div>
</div>


<div id="addFuncDialog" style="display: none;">
    <form id="addFuncForm">
        <%-- 父节点ID --%>
        <input type="hidden" name="parentId" id="parentId">

        <div class="form-line">
            <label>权限名称</label><input type="text" name="funcTitle">
        </div>
        <div class="form-line">
            <label>权限URL</label><input type="text" name="funcUrl">
        </div>
        <div class="form-line"><label>权限图标URL</label><input type="text" name="imageUrl"></div>
        <div class="form-line">
            <label>权限类型</label>
            <select name="funcType">
                <option value="MENU">菜单</option>
                <option value="LINK" selected>链接</option>
                <option value="BUTTON">按钮</option>
            </select>
        </div>
        <div class="form-line"><label>权限字符串</label><input type="text" name="permission"></div>
        <div class="form-line"><label>备注</label><textarea name="remark"></textarea></div>
    </form>
</div>

<script type="text/javascript" src="${URL}/plugins/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${URL}/js/system/func/func.js"></script>
</body>
</html>
