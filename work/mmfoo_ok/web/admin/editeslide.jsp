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
编辑首页轮播图 <br/><br/><br/>
<%--  取出所有的首页轮播图 --%>
<mfo:GetImdeImg imgid="${imgid}" />
<table border="1px">
    <tr>
        <td> </td>
        <td><input type="hidden" id="imgid" value="${ingindexss.imgid}"></td>
    </tr>
    <tr>
        <td>排序</td>
        <td><input type="text" id="oedds" value="${ingindexss.oedds}"></td>
    </tr>
    <tr>
        <td>大图</td>
        <td><img src="<%=basePath%>/images/upload/${ingindexss.img}" alt=""><input type="text" id="img" value="${ingindexss.img}"><input
                type="button" value="上传"
                onclick="javascript:window.open('<%=basePath%>/pages/upload.jsp?elementid=img','_blank','height=200, width=370, top=100, left=500, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no');"/></td>
    </tr>
    <tr>
        <td>缩略图</td>
        <td><img src="<%=basePath%>/images/upload/${ingindexss.thumb}" alt=""><input type="text" id="thumb" value="${ingindexss.thumb}"><input
                type="button" value="上传"
                onclick="javascript:window.open('<%=basePath%>/pages/upload.jsp?elementid=thumb','_blank','height=200, width=370, top=100, left=500, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no');"/></td>
    </tr>
    <tr>
        <td>真实姓名</td>
        <td><input type="text" id="truename" value="${ingindexss.truename}"></td>
    </tr>
    <tr>
        <td>城市</td>
        <td><input type="text" id="city" value="${ingindexss.city}"></td>
    </tr>
    <tr>
        <td>星座</td>
        <td><input type="text" id="star" value="${ingindexss.star}"></td>
    </tr>
    <tr>
        <td>链接地址</td>
        <td><input type="text" id="target_url" value="${ingindexss.target_url}"></td>
    </tr>

    <tr>
        <td><input type="button" onclick="editeindeximg()" value="修改"></td> <td> </td>
    </tr>
</table>

</html>