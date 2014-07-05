<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美分网</title>
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/personal.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/button.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/tab_personal.css">
<link rel="stylesheet" href="<%=basePath%>/css/personal_grid.css" type="text/css" media="all">
<link rel="stylesheet" href="<%=basePath%>/css/personal_style.css" type="text/css" media="all">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/photo.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/fileList.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/photo.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
</head>

<body>
<jsp:include page="../include/user_top.jsp"/>
<%--当没有用户id参数的时候 将参数设置为1--%>
<c:if test="${empty param.user_id}">
    <c:set var="param.user_id" value="1"/>
</c:if>
<%--取此user_id的用户对象数据--%>
<mfo:getUserById user_id="${param.user_id}"/>
 <fmt:setLocale value="zh"/>
<div id="layout">
  <div class="promoteBox">
    <div class="avatarPromote"><img src="<%=basePath%>/images/${userbyid.userimg}"/></div>
   <jsp:include page="../include/user_nav.jsp"/>
    <div class="slide">
       <jsp:include page="../include/user_left.jsp"/>
      <div class="content">
        <table>
	<caption>${userbyid.truename}的档案</caption>
	<tbody>
 	<tr>
		<th scope="row" class="column1">姓名：</th>
		<td>${userbyid.truename}</td>

	</tr>	
 	 <tr class="odd">
		<th scope="row" class="column1">出生日期：</th>
		<td><fmt:formatDate value="${userbyid.birthday}" pattern="yyyy年MM月dd日"/>
            </td>

	</tr>	
 	<tr>
		<th scope="row" class="column1">身高：</th>	
		<td>${userbyid.height}</td>

	</tr>	
 	<tr class="odd">
		<th scope="row" class="column1">学校：</th>
		<td>${userbyid.school}</td>

	</tr>	
 	<tr>
		<th scope="row" class="column1">出生地：</th>
		<td>${userbyid.province} ${userbyid.city}</td>

	</tr>	
 	<tr class="odd">
		<th scope="row" class="column1">血型：</th>
		<td>${userbyid.blood}</td>

	</tr>	
 	<tr>
		<th scope="row" class="column1">QQ号码：</th>
		<td>${userbyid.qqnumber}</td>

	</tr>	
 	<tr class="odd">
		<th scope="row" class="column1">手机号码：</th>
		<td>${userbyid.mobile}</td>

	</tr>	
 	<tr>
		<th scope="row" class="column1">电子邮箱：</th>
		<td>${userbyid.email}</td>

	</tr>	
 	<tr class="odd">
		<th scope="row" class="column1">美女宣扬：</th>
		<td>
        ${userbyid.declaration}    
		</td>

	</tr>	
	</tbody>
</table>
      </div>
    </div>
  </div>
   <jsp:include page="../include/footer.jsp"/>
</div>

</body>
</html>
