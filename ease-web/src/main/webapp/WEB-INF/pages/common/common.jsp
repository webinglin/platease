<%--
  User: webinglin
Date: 2017/4/23
--%>
<%
    request.setAttribute("URL", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
%>


<%--
<link rel="stylesheet" href="${URL}/plugins/bootstrap-3.3.7/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="${URL}/plugins/bootstrap-3.3.7/css/bootstrap.min.css" />
<script type="text/javascript" src="${URL}/plugins/bootstrap-3.3.7/js/bootstrap.min.js"></script>
--%>
<link rel="stylesheet" href="${URL}/plugins/jquery-ui-1.12.1/jquery-ui.css" >
<script type="text/javascript" src="${URL}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${URL}/plugins/jquery-ui-1.12.1/jquery-ui.min.js"></script>

<script type="text/javascript">
    var basePath = '${URL}';
</script>


