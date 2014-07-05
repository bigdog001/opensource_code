<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2011-5-15
  Time: 22:15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/thickbox.css">
<link rel="stylesheet" href="<%=basePath%>/css/signUp2.css"/>
<link rel="stylesheet" href="<%=basePath%>/css/reveal.css"/>

<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/personalCenter_common.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/personalCenter.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/mostBeauty.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/ablumn.css">

<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/feeling.css">

<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/thickbox_plus.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.reveal.js"></script>
<%--<script type="text/javascript" src="<%=basePath%>/js/tab.js"></script>--%>
<script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/photo.js"></script>