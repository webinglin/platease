<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html id="$LOGIN_PAGE">
<head>
    <title>PLATEASE</title>
    <meta charset="utf-8">
    <%@include file="/WEB-INF/pages/common/common.jsp" %>
    <link rel="stylesheet" href="${URL}/css/login/login.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<div class="login-dialog">
    <div class="login-form">
        <div class="head">
            <img src="${URL}/images/login/mem.png" alt=""/>
        </div>
        <div class="plat-title">基础平台(Platease)</div>
        <form action="${URL}/login" method="POST">
            <li>
                <input type="text" name="userName" id="userName" class="text" value="用户名"
                       onfocus="this.value = '';" onblur="if (this.value == '') {this.value = '用户名';}">
                <span class="icon user"></span>
            </li>
            <li>
                <input type="password" name="password" id="password" value="Password"
                       onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Password';}">
                <span class="icon lock"></span>
            </li>
            <div class="p-container">
                <input type="submit" id="loginBtn" value="登&nbsp;&nbsp;录">
                <div class="clear"></div>
                <div style="color: red;">
                    ${error_msg}
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    var publicKey = '${publicKey}';
</script>
<script type="text/javascript" src="${URL}/plugins/jsencrypt/jsencrypt.min.js"></script>
<script type="text/javascript" src="${URL}/js/login/login.js"></script>
</body>
</html>
