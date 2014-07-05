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
</head>
活动列表<br/><br/><br/>

<mfo:gettophd page="1" size="20"/>
<table border="1px">
    <tr>
        <td>编号</td>
        <td>活动名称</td>
        <td>活动标题</td>
        <td>操作</td>
    </tr>
    <c:forEach var="hd" items="${tophd}">
    <tr>
        <td>${hd.hdid}</td>
        <td>${hd.hdname}</td>
        <td>${hd.hdtitle}</td>
        <td><a href="editehd.jsp?hdid=${hd.hdid}" target="_blank"> 编辑 </a> &nbsp;&nbsp;&nbsp;<a
                href="javascript:deleteHd('${hd.hdid}')"> 删除</a></td>
    </tr>
    </c:forEach>
    <tr>
        <td colspan="4"><a href="addhd.jsp">增加活动</a></td>
    </tr>
</html>