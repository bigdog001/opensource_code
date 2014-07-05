<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    basePath=basePath.replace(":80","");
%>
<c:choose>
    <c:when test="${empty param.newsid}">
        <c:set var="newsid" value="1"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="newsid" value="${param.newsid}"></c:set>
    </c:otherwise>
</c:choose>
<%-- 页脚对象  --%>
<mfo:GetSingleFooterNews newid="${newsid}"  />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>美分网 |关于美分网</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
    <%--<jsp:include page="../include/header.jsp"/>--%>
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/commonext.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/ext.css">
    <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
</head>

<body>
<div id="layout">
    <div id="top">
        <div class="logo"><a href="<%=basePath%>"><img src="<%=basePath%>/images/logo.jpg"/></a></div>
        <div class="signin">
            <c:choose>
                <c:when test="${sessionScope.islogin eq 'Y' }">
                    <a href="javascript:logout(window.location.href)">退出</a>|<a
                    href="/user-${userbean.user_id}-1.html" <%--class="thickbox"--%>>欢迎您:${sessionScope.login_email}</a>
                </c:when>
                <c:otherwise>
                    <a href="<%=basePath%>/regist.html">免费注册</a>|<a
                    href="<%=basePath%>/login.html" data-reveal-id="myModal" class="thickbox">登录</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="extLeft">
        <jsp:include page="../include/extleft.jsp"/>
    </div>
    <div class="extRight">
        <mfo:shownews newsid="${newsid}"/>
        <div class="llaa"></div>
    </div>
    <div class="trailor"><span>&copy; 2011</span><a href="#">美时美分网络科技有限公司</a> <span>((WWW.MMFOO.COM), All rights reserved. </span>辽ICP备10014101号-7
    </div>
</div>
</body>
</html>
