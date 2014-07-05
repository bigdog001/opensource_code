<%-- 
    Document   : nav
    Created on : Jan 4, 2014, 9:59:17 AM
    Author     : bigdog
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div id="header">
    <div class="container"> <a href="javascript:;" class="logo"> </a>
        <ul class="header-links">
            <li><a href="#" target="_blank">登录</a></li>
            <li><a href="#" target="_blank">注册</a></li>
            <li><a href="http://www.weibo.com/u/3336692422/home" target="_blank">新浪微博</a></li>
            <li><a href="http://t.qq.com/cishanchina" target="_blank">腾讯微博</a></li>
        </ul>
    </div>
</div>



  



<div id="nav" class="navbar">

    <div class="container">
        <ul class="navbar-nav">
            <li class="home_<c:if test="${w eq 1}"> active</c:if>"> <c:choose> <c:when test="${w eq 1}"> <a href="#"><spring:message code="index" /></a> </c:when> <c:otherwise> <a href="./index.htm"><spring:message code="index" /></a> </c:otherwise>  </c:choose> </li>
            <li class="service<c:if test="${w eq 2}"> active</c:if>"> <c:choose> <c:when test="${w eq 2}"> <a href="#"><spring:message code="gethelp" /></a> </c:when> <c:otherwise> <a href="./gethelp.htm"><spring:message code="gethelp" /></a> </c:otherwise>  </c:choose> </li>
            <li class="case<c:if test="${w eq 3}"> active</c:if>">  <c:choose> <c:when test="${w eq 3}"> <a href="#"><spring:message code="giveHelp" /></a> </c:when> <c:otherwise> <a href="./givehelp.htm"><spring:message code="giveHelp" /></a> </c:otherwise>  </c:choose> </li>
            <li class="news<c:if test="${w eq 4}"> active</c:if>"><a href="javascript:alert('<spring:message code="Supervise" />');"><spring:message code="Supervise" /></a></li>
            <li class="help<c:if test="${w eq 5}"> active</c:if>"><a href="javascript:alert('<spring:message code="FoodBank" />');"><spring:message code="FoodBank" /></a></li>
            <li class="contact<c:if test="${w eq 6}"> active</c:if>"><a href="javascript:alert('<spring:message code="AboutUs" />');"><spring:message code="AboutUs" /></a></li>
        </ul>
    </div>
</div>