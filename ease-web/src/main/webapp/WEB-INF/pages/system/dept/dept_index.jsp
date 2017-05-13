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
    <link rel="stylesheet" href="${URL}/css/system/dept/dept.css"/>
    <link rel="stylesheet" href="${URL}/plugins/ztree/style/zTreeStyle.css"/>
</head>

<body>
<div class="dept-tree">
    <ul id="deptTree" class="ztree"></ul>
</div>
<div class="dept-table">
    <form>
        <input type="text" name="searchCont" id="deptSearchCont" placeholder="搜索内容">

        <a class="btn" id="deptSearch">过滤</a>
        <a class="btn" id="deptReset">重置</a>
    </form>

    <div class="btn-group">
        <shiro:hasPermission name="/dept/addDept">
            <button class="btn" id="addDept">添加</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="/dept/delDept">
            <button class="btn" id="delDept">删除</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="/dept/updateDept">
            <button class="btn" id="updateDept">修改</button>
        </shiro:hasPermission>
    </div>

    <input type="hidden" id="deptSelectedRowId">
    <table id="deptTable"></table>
    <div id="deptPager"></div>
</div>


<div id="addDeptDialog" style="display: none;">
    <form id="addDeptForm">
        <%-- 父节点ID --%>
        <input type="hidden" name="parentId" id="deptParentId">

        <div class="form-line">
            <label>单位名称</label><input type="text" name="deptName">
        </div>
        <div class="form-line">
            <label>单位编码</label><input type="text" name="deptCode">
        </div>
        <div class="form-line">
            <label>单位别名</label><input type="text" name="alias">
        </div>
        <div class="form-line"><label>备注</label><textarea name="remark"></textarea></div>
    </form>
</div>

<script type="text/javascript" src="${URL}/plugins/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${URL}/js/system/dept/dept.js"></script>
</body>
</html>
