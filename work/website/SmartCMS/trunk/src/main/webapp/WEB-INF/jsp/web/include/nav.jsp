<%--
  Created by IntelliJ IDEA.
  User: bigdog
  Date: 9/2/13
  Time: 4:28 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="header">
    <h1 class="logo"><a href="./">360点睛营销平台</a></h1>
    <span> <a href="./" target="_blank"><s:text name="index"/></a></span></div>
<!--end header-->
<div class="nav" style="margin-bottom:0">
    <ul>
        <li <c:if test="${w eq 1}">class="cur"</c:if>><a href="./"><s:text name="index"/></a></li>
        <li <c:if test="${w eq 2}">class="cur"</c:if>><a href="./product.action"><s:text name="product"/></a></li>
        <li <c:if test="${w eq 3}">class="cur"</c:if>><a href="./help.action"><s:text name="help"/></a></li>
        <li <c:if test="${w eq 4}">class="cur"</c:if>><a href="./cooper.action"><s:text name="cooper"/></a></li>
        <li <c:if test="${w eq 5}">class="cur"</c:if>><a href="./contact.action"><s:text name="contact"/></a></li>
    </ul>
</div>