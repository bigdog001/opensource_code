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
%>
<html>
<head>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
    <c:choose>
        <c:when test="${empty param.page}">
            <c:set var="page" value="1"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="page" value="${param.page}"></c:set>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${empty param.size}">
            <c:set var="size" value="40"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="size" value="${param.size}"></c:set>
        </c:otherwise>
    </c:choose>
</head>
页脚及我们每天在进步管理 <br/><br/><br/>
注：前12条为页脚内容，之后的属于我们每天在进步的内容<br><br><br>
<%--  取出所有的首页轮播图 --%>
<mfo:GetAllFooterNews page="${page}" size="${size}"/>
<%--<mfo:getAllUsersTotal size="${size}" page="${page}"/>--%>
<table border="1px">
    <tr>
        <td>编号</td>
        <td>标题</td>
        <td>上次修改时间</td>
        <td>操作</td>
    </tr>
    <c:forEach var="newss" items="${allnews}">
        <tr>
            <td style="<c:if test="${newss.newsid>12}">color:red</c:if>">${newss.newsid}</td>
            <td>${newss.title} </td>
            <td><fmt:formatDate value="${newss.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><a href="editefooter.jsp?newsid=${newss.newsid}"> 编辑 </a></td>
        </tr>
    </c:forEach>
    <tr>
        <%--<td colspan="9"><a href="addslide.jsp">增加焦点图</a></td>--%>
    </tr>
</table>
<br>
<br>
<br>


</html>