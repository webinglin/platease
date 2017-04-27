<%--
  User: webinglin
  Date: 2017/4/19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PLATEASE</title>
</head>

<%@include file="/WEB-INF/pages/common/common.jsp" %>
<link rel="stylesheet" href="${URL}/css/system/home.css"/>

<body>
<div class="ui-layout-center">
    Center


    登录成功... Home Page...
    <p>
        $(window).unload(function () {
        $.ajax({
        type: 'GET',
        async: false,
        url: 'SomeUrl.com?id=123'
        });
        });
    </p>

    <form action="${URL}/logout" method="post">
        <input type="submit" value="退出"/>
    </form>



    <p>
        系统管理：
            用户管理
                CRUD
            单位管理
                CRUD
            角色管理 --授权功能
                CRUD, 角色关联的人员，复制角色， 修改角色拥有的权限
            权限管理
                CRUD


    </p>

</div>
<div class="ui-layout-north">North
    <form method="post" action="${URL}/logout"><button type="submit">退出</button></form>
</div>
<div class="ui-layout-west">West</div>

<script type="text/javascript" src="${URL}/plugins/jquery-layout/jquery.layout-latest.js"></script>
<script type="text/javascript" src="${URL}/js/system/home.js"></script>
</body>
</html>
