<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html id="$LOGIN_PAGE">
<head>
    <title>PLATEASE</title>
</head>
<%@include file="/WEB-INF/pages/common/common.jsp" %>
<link rel="stylesheet" href="${URL}/css/login/login.css" />
<body>

<div class="login-dialog">
    <div class="modal-header">
        <h1 class="text-center">PLATEASE</h1>
    </div>
    <div class="modal-body">
        <form action="${URL}/login" method="post">
            <div class="form-group">
                <input type="text"  name="username" class="form-control input-lg" placeholder="用户名"/>
            </div>

            <div class="form-group">
                <input type="password"  name="password" class="form-control input-lg" placeholder="密码"/>
            </div>

            <div class="form-group">
                <input type="submit" class="btn btn-block btn-lg btn-primary" value="登录"/>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${URL}/js/common/jquery.md5.js"></script>
<script type="text/javascript" src="${URL}/js/login/login.js"></script>
</body>
</html>
