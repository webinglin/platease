<%--
  User: webinglin
  Date: 2017/4/19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PLATEASE</title>
</head>
<body>
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

<form action="/ease/logout" method="post">
    <input type="submit" value="退出" />
</form>
</body>
</html>
