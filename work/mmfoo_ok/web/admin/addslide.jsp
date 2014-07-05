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
        <c:when test="${empty param.imgid}">
            <c:set var="imgid" value="1"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="imgid" value="${param.imgid}"></c:set>
        </c:otherwise>
    </c:choose>
      
</head>
增加首页轮播图 <br/><br/><br/>

<table border="1px">

    <tr>
        <td>排序</td>
        <td><input type="text" id="oedds"></td>
    </tr>
    <tr>
        <td>大图</td>
        <td><input type="text" id="img"><input
                type="button" value="上传"
                onclick="javascript:window.open('<%=basePath%>/pages/upload.jsp?elementid=img','_blank','height=200, width=370, top=100, left=500, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no');"/></td>
    </tr>
    <tr>
        <td>缩略图</td>
        <td><input type="text" id="thumb"><input
                type="button" value="上传"
                onclick="javascript:window.open('<%=basePath%>/pages/upload.jsp?elementid=thumb','_blank','height=200, width=370, top=100, left=500, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no');"/></td>
    </tr>
    <tr>
        <td>真实姓名</td>
        <td><input type="text" id="truename"></td>
    </tr>
    <tr>
        <td>城市</td>
        <td><input type="text" id="city" ></td>
    </tr>
    <tr>
        <td>星座</td>
        <td><input type="text" id="star" ></td>
    </tr>
    <tr>
        <td>链接地址</td>
        <td><input type="text" id="target_url"></td>
    </tr>

    <tr>
        <td><input type="button" onclick="addindeximg()" value="增加"></td> <td> </td>
    </tr>
</table>

</html>