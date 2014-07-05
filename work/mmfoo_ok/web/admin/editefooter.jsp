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
    <script type="text/javascript" src="<%=basePath%>/fckeditor/fckeditor.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/calenderJS.js"></script>
</head>

增加活动<br/><br/><br/>
<c:choose>
    <c:when test="${empty param.newsid}">
        <c:set var="newsid" value="1"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="newsid" value="${param.newsid}"></c:set>
    </c:otherwise>
</c:choose>
<%-- 页脚对象  --%>
<mfo:GetSingleFooterNews newid="${param.newsid}"  />

<table border="1px">
    <tr>
        <td>标题</td>
        <td><input type="text" id="newstitle" value="${newssingle.title}"/><input type="hidden" id="newsid"
                                                                             value="${newssingle.newsid}"/></td>
    </tr>
    <tr>
        <td>内容</td>
        <td><textarea id="newscontent" >${newssingle.content}</textarea></td>
    </tr>

    <tr>
        <td><input type="button" value="修改" onclick="updateNews()"/></td>
        <td></td>
    </tr>
</table>
<script type="text/javascript">

      var oFCKeditor;
    oFCKeditor = new FCKeditor('newscontent');
    oFCKeditor.BasePath = "/fckeditor/";
    oFCKeditor.Width = "600px";
    oFCKeditor.Height = "300px";
    oFCKeditor.ReplaceTextarea();
</script>
</html>