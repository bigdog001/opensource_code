<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
  <mfo:getuser/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美分网-活动细则</title>
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/common.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/menu.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/index.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/index2.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/button.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/pageNum.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/activities.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
</head>

<body>
<div id="layout">
  <jsp:include page="../include/nav.jsp"/>
    <mfo:getmmhd hdid="${param.hdid}"/>
   <%--<c:set scope="request" var="mmfoohd" value="${mmfoohd}"></c:set>--%>
    <%--<c:out value="${empty mmfoohd}" />--%>
  <div class="activitiesBox">
    <div class="doContent">
      <h2>${mmfoohd.hdname}</h2>
      <div class="detail">
        <ul>
          <li class="tl">活动时间：</li>
          <li class="tr">${mmfoohd.starttime}--${mmfoohd.endtime}</li>
        </ul>
        <ul>
          <li class="tl">活动内容：</li>
          <li class="tr"><p>
              ${mmfoohd.hdcontent}
          </p></li>
        </ul>
      </div>
      <div class="join">       <button class="funcB" onclick="joinhd('${mmfoohd.hdid}');">立即参加</button></div>
      <!--joiner-->
        <%--//查询所有参与了此活动的人--%>
        
      <div class="joiner">
        <h4>&nbsp;&nbsp;&nbsp;TA们还参加了此次活动：</h4>
          <mfo:gethduser hdid="${param.hdid}" size="5" page="1"/>
          <c:forEach var="users" items="${topuser}">  
                   <div class="cell">
                   <a href="<%=basePath%>/pages/personal_album-${users.user_id}.html" target="_blank"><img src="<%=basePath%>/images/thumb/${users.userthumb}"/></a>
                   <h5><a href="<%=basePath%>/pages/personal_album-${users.user_id}.html" target="_blank">${users.truename}</a></h5>
                 </div>
             </c:forEach>
      </div>
    </div>
 
  </div>
<div class="update">
    <%--取得美女數據--%>
         <mfo:getuser/>
    <h3><span>美分最美女</span></h3>
    <div class="facePhoto"><a href="#"><img src="<%=basePath%>/images/thumb/${userindex.userimg}" width="162" /></a></div>
    <div class="rankInfo">
          <h4><a href="#">${userindex.truename}</a></h4>
          <h6><span>得票数：${userindex.visit_cnt}</span><span>评论数：${userindex.discuss}</span></h6>
          <h6><span><mfo:gethdname user_id="${userindex.user_id}"/></span></h6>
        </div>
        <div class="someInfo">
         <ul>
           <li>${userindex.school}</li>
           <li><fmt:setLocale value="zh"/>
                    <fmt:formatDate value="${userindex.birthday}" pattern="yyyy年MM月dd日"/></li>
           <li>${userindex.province} ${userindex.city}</li>
         </ul>
        </div>
  </div>
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
