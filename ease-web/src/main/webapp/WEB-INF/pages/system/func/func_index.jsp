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
    <form onsubmit="return false">
        <input type="text" name="searchCont" id="funcSearchCont" placeholder="权限名称/权限字符串">

        <a class="btn" id="funcSearch"><i class="icon-search"></i> 过滤</a>
        <a class="btn" id="funcReset"><i class="icon-spinner"></i> 重置</a>
    </form>

    <div class="btn-group">
        <shiro:hasPermission name="/func/addFunc">
            <button class="btn" id="addFunc"><i class="icon-plus"></i> 添加</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="/func/updateFunc">
            <button class="btn" id="updateFunc"><i class="icon-edit"></i> 修改</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="/func/delFunc">
            <button class="btn" id="delFunc"><i class="icon-trash"></i> 删除</button>
        </shiro:hasPermission>
    </div>

    <table id="funcTable"></table>
    <div id="funcPager"></div>
</div>

<div id="addFuncDialog" style="display: none;">
    <form id="addFuncForm" >
        <%-- 父节点ID --%>
        <input type="hidden" name="parentId" id="funcParentId">

        <div class="form-line">
            <label>权限名称</label><input type="text" name="funcTitle" maxlength="125" datatype="s" nullmsg="权限名称不能为空" >
        </div>
        <div class="form-line">
            <label>权限URL</label><input type="text" name="funcUrl" maxlength="125" datatype="*4-125" errormsg="URL至少4个字符" >
        </div>
        <div class="form-line">
            <label>权限图标URL</label><input type="text" name="imageUrl" maxlength="125" >
        </div>
        <div class="form-line">
            <label>权限类型</label>
            <select name="funcType">
                <option value="DESKTOP-MENU">桌面菜单</option>
                <option value="MENU">菜单</option>
                <option value="LINK" selected>链接</option>
                <option value="BUTTON">按钮</option>
            </select>
        </div>
        <div class="form-line"><label>权限字符串</label><input type="text" name="permission" maxlength="50"></div>
        <div class="form-line"><label>备注</label><textarea name="remark" maxlength="125"></textarea></div>
    </form>
</div>

<div id="updateFuncDialog" style="display: none;">
    <form id="updateFuncForm">
        <input type="hidden"  name="id">
        <div class="form-line">
            <label>权限名称</label><input type="text" name="funcTitle" maxlength="125" datatype="s" nullmsg="权限名称不能为空" >
        </div>
        <div class="form-line">
            <label>权限URL</label><input type="text" name="funcUrl" maxlength="125" datatype="*4-125" errormsg="URL至少4个字符" >
        </div>
        <div class="form-line">
            <label>权限图标URL</label><input type="text" name="imageUrl" maxlength="125" >
        </div>
        <div class="form-line">
            <label>权限类型</label>
            <select name="funcType">
                <option value="DESKTOP-MENU">桌面菜单</option>
                <option value="MENU">菜单</option>
                <option value="LINK" selected>链接</option>
                <option value="BUTTON">按钮</option>
            </select>
        </div>
        <div class="form-line"><label>权限字符串</label><input type="text" name="permission" maxlength="50"></div>
        <div class="form-line"><label>备注</label><textarea name="remark" maxlength="125"></textarea></div>
    </form>
</div>


<script type="text/javascript" src="${URL}/plugins/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${URL}/js/system/func/func.js"></script>
</body>
</html>
