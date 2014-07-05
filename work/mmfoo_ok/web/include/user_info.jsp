<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2011-6-8
  Time: 19:31:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<fmt:setLocale value="zh"/>
<%
    //活动列表页面，取出所有的活动信息
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    basePath=basePath.replace(":80","");
%>
<c:choose>
    <c:when test="${empty param.user_id}">
        <c:set var="user_id_userspace" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="user_id_userspace" value="${param.user_id}"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${empty param.leftnav}">
        <c:set var="leftnav" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="leftnav" value="${param.leftnav}"/>
    </c:otherwise>
</c:choose>

<ul>
<li <c:choose>
        <c:when test="${leftnav==1}">class="cutr_"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> ><a href="<%=basePath%>/userfileedite-${user_id_userspace}.html">基础信息</a></li>
<li <c:choose>
        <c:when test="${leftnav==2}">class="cutr_"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>><a href="<%=basePath%>/userinfo1-${user_id_userspace}.html">经历</a></li>
<li <c:choose>
        <c:when test="${leftnav==3}">class="cutr_"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>><a href="<%=basePath%>/userinfo2-${user_id_userspace}.html">培训</a></li>
<li <c:choose>
        <c:when test="${leftnav==4}">class="cutr_"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>><a href="<%=basePath%>/userinfo3-${user_id_userspace}.html">联络信息</a></li>
<li <c:choose>
        <c:when test="${leftnav==5}">class="cutr_"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>><a href="<%=basePath%>/userinfo4-${user_id_userspace}.html">爱好</a></li>
</ul>