<%--
  User: webinglin
Date: 2017/4/23
--%>
<%
    request.setAttribute("URL", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
%>

<script type="text/javascript">
    var basePath = '${URL}';

    // 创建子命名空间
    var web = {
        ns : function(ns) {
            if (!ns || !ns.length) {
                return null;
            }
            var levels = ns.split(".");
            var nsobj = web;
            for ( var i = (levels[0] == "web") ? 1 : 0; i < levels.length; ++i) {
                nsobj[levels[i]] = nsobj[levels[i]] || {};
                nsobj = nsobj[levels[i]];
            }
            return nsobj;
        }
    }
</script>

<link rel="stylesheet" href="${URL}/css/common/platease.css" >
<link rel="stylesheet" href="${URL}/plugins/jquery-ui-1.12.1/jquery-ui.css" >
<script type="text/javascript" src="${URL}/js/common/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${URL}/plugins/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script type="text/javascript" src="${URL}/js/common/form-jquery.js"></script>
