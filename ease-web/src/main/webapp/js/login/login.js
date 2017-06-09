/**
 * 登录页面脚本
 * Created by webinglin on 2017/4/7.
 */

$(function () {

    /* 登录 */
    $("#loginBtn").click(function () {
        var userName = $("#userName").val();
        var password = $("#password").val();

        if (!userName || userName.length < 4) {
            alert("用户名不能少于四个字符");
            return false;
        }
        if (!password || password.length < 6) {
            alert("密码不能少于6个字符");
            return false;
        }

        /* 利用RSA算法生成的公钥进行加密，服务端持有私钥 */
        try {
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(publicKey);
            var encrypted = encrypt.encrypt(password);
            $("#password").val(encrypted);
        } catch (e) {
            alert("利用公钥加密密码出错");
            return false;
        }

        return true;

    });


});