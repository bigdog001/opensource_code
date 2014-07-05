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
    <c:when test="${empty param.hdid}">
        <c:set var="hdid" value="1"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="hdid" value="${param.hdid}"></c:set>
    </c:otherwise>
</c:choose>
<%-- 根据hdid取此活动对象  --%>
<mfo:getmmhd hdid="${hdid}"/>

<table border="1px">
    <tr>
        <td>活动名称</td>
        <td><input type="text" id="hdname" value="${mmfoohd.hdname}"/><input type="hidden" id="hdid"
                                                                             value="${mmfoohd.hdid}"/></td>
    </tr>
    <tr>
        <td>活动排序</td>
        <td><input type="text" id="orders" value="${mmfoohd.orders}"/></td>
    </tr>
    <tr>
        <td>活动标题</td>
        <td><input type="text" id="hdtitle" value="${mmfoohd.hdtitle}"/></td>
    </tr>
    <tr>
        <td>活动摘要</td>
        <td><input type="text" id="hdabstract" value="${mmfoohd.hdabstract}"/></td>
    </tr>
    <tr>
        <td>活动内容</td>
        <td><textarea id="hdcontent">${mmfoohd.hdcontent}</textarea></td>
    </tr>
    <tr>
        <td>活动发起人的id</td>
        <td><input type="text" id="starter_user_id" value="${mmfoohd.starter_user_id}"/></td>
    </tr>
    <tr>
        <td>活动费用</td>
        <td><input type="text" id="payfeed" value="${mmfoohd.payfeed}"/></td>
    </tr>
    <tr>
        <td>活动奖项</td>
        <td><input type="text" id="jiangxian" value="${mmfoohd.jiangxian}"/></td>
    </tr>
    <tr>
        <td>活动开始时间</td>
        <td><input type="text" id="starttime" value="${mmfoohd.starttime}" onFocus="HS_setDate(this)"/></td>
    </tr>
    <tr>
        <td>活动结束时间</td>
        <td><input type="text" id="endtime" value="${mmfoohd.endtime}" onFocus="HS_setDate(this)"/></td>
    </tr>
    <tr>
        <td>活动大封面</td>
        <td><img src="<%=basePath%>/images/upload/${mmfoohd.hdface}" alt=""/><input type="text" id="hdface"
                                                                             value="${mmfoohd.hdface}"/><input
                type="button" value="上传"
                onclick="javascript:window.open('<%=basePath%>/pages/upload.jsp?elementid=hdface','_blank','height=200, width=370, top=100, left=500, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no');"/>
        </td>
    </tr>
    <tr>
        <td>活动缩略图</td>
        <td><img src="<%=basePath%>/images/upload/${mmfoohd.hd_thumb}" alt=""/><input type="text" id="hd_thumb"
                                                                               value="${mmfoohd.hd_thumb}"/><input
                type="button" value="上传"
                onclick="javascript:window.open('<%=basePath%>/pages/upload.jsp?elementid=hd_thumb','_blank','height=200, width=370, top=100, left=500, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no');"/>
        </td>
    </tr>

    <tr>
        <td><input type="button" value="修改" onclick="editeHd()"/></td>
        <td></td>
    </tr>
</table>
<script type="text/javascript">

      var oFCKeditor;
    oFCKeditor = new FCKeditor('hdcontent');
    oFCKeditor.BasePath = "/fckeditor/";
    oFCKeditor.Width = "600px";
    oFCKeditor.Height = "300px";
    oFCKeditor.ReplaceTextarea();
</script>
</html>