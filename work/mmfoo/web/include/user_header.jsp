<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/personal.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/button.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/tab_personal.css">
<link rel="stylesheet" href="<%=basePath%>/css/personal_grid.css" type="text/css" media="all">
<link rel="stylesheet" href="<%=basePath%>/css/personal_style.css" type="text/css" media="all">
<LINK rel="stylesheet" type="<%=basePath%>/text/css" href="css/form_common.css">
<LINK rel="stylesheet" type="<%=basePath%>/text/css" href="css/uni-form.css">
<LINK rel="stylesheet" type="<%=basePath%>/text/css" href="css/default.uni-form.css">
<LINK rel="stylesheet" type="<%=basePath%>/text/css" href="css/photo.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
<%--<script type="text/javascript" src="<%=basePath%>/js/photo.js"></script>--%>
<script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>