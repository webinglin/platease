/**
 * 登录页面脚本
 * Created by webinglin on 2017/4/7.
 */

$(function () {

    // 登录
   $("#loginBtn").click(function(){
        var userName = $("#userName").val();
        var password = $("#password").val();

        if(!userName || userName.length<4) {
            alert("用户名不能少于四个字符");
            return false;
        }
        if(!password || password.length<6) {
            alert("密码不能少于6个字符")
            return false;
        }

        // 密码进行MD5加密
        $("#password").val($.md5(password));

        return true;

    });


});