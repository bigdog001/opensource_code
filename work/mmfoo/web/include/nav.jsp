<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<div id="top">
    <div class="logo"><a href="<%=basePath%>/index-1.html"><img src="<%=basePath%>/images/logo.jpg"/></a></div>
    <div class="signin">

        <c:choose>
            <c:when test="${sessionScope.islogin eq 'Y' }">
                <a href="javascript:logout(window.location.href)">退出</a>|<a
                href="/pages/personalCenter-1.html" <%--class="thickbox"--%>>欢迎您:${sessionScope.login_email}</a>
            </c:when>
            <c:otherwise>
                <a href="<%=basePath%>/pages/join_registe-2.html">免费注册</a>|<a
                href="<%=basePath%>/pages/login.html?height=280;width=423" data-reveal-id="myModal" class="thickbox">登录</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<ul id="nav">
    <!--[if IE9]>
    <ul id="nav2"><![endif]-->
    <li><a href="<%=basePath%>/index-1.html" <c:choose>
        <c:when test="${param.navs==1}">class="current"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> >首页</a></li>
    <li><a href="<%=basePath%>/pages/join_registe-2.html" <c:choose>
        <c:when test="${param.navs==2}">class="current"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> >新人报到</a></li>
    <li><a href="<%=basePath%>/index-3.jsp" <c:choose>
        <c:when test="${param.navs==3}">class="current"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> >美分最美女</a></li>
    <li><a href="<%=basePath%>/index-4.jsp" <c:choose>
        <c:when test="${param.navs==4}">class="current"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> >美女连连看</a></li>
    <li class="<%--trail--%>"><a href="<%=basePath%>/index-5.jsp" <c:choose>
        <c:when test="${param.navs==5}">class="current"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> >美分活动</a></li>
</ul>
