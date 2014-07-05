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
首页轮播图管理 <br/><br/><br/>
<%--  取出所有的首页轮播图 --%>
<mfo:GetIndexImg page="${page}" size="${size}"/>
<%--<mfo:getAllUsersTotal size="${size}" page="${page}"/>--%>
<table border="1px">
    <tr>
        <td>编号</td>
        <td>大图</td>
        <td>缩略图</td>
        <td>排序</td>
        <td>摘要</td>
        <td>操作</td>
    </tr>
    <c:forEach var="imgindex" items="${ingindex}">
        <tr>
            <td>${imgindex.imgid}</td>
            <td>
             <c:choose>
                    <c:when test="${empty imgindex.img}">未上传照片</c:when>
                    <c:otherwise>
                        <img src="<%=basePath%>/images/upload/${imgindex.img}" alt=""/>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${empty imgindex.thumb}">未上传缩略图</c:when>
                    <c:otherwise>
                        <img src="<%=basePath%>/images/upload/${imgindex.thumb}" alt=""/>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${imgindex.oedds}</td>
            <td>${imgindex.truename}-${imgindex.city}-${imgindex.star}</td>
            <td><a href="editeslide.jsp?imgid=${imgindex.imgid}"> 编辑 </a> &nbsp;&nbsp;&nbsp;<a href="javascript:deleteSlide('${imgindex.imgid}')"> 删除</a></td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="9"><a href="addslide.jsp">增加焦点图</a></td>
    </tr>
</table>

</html>