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
    basePath=basePath.replace(":80","");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><c:choose>
        <c:when test="${empty userbean}"></c:when>
        <c:otherwise>${userbean.nickname} 个人网站</c:otherwise>
    </c:choose>美分网MMFOO |美分活动详情</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_common.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/menu.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_index.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_index2.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/button.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/pageNum.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_activities.css">
    <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
 <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
 <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
</head>
             <c:choose>
          <c:when test="${empty param.hdid}">
               <c:set var="hdid" value="1"/>
          </c:when>
          <c:otherwise>
               <c:set var="hdid" value="${ param.hdid}"/>
          </c:otherwise>
      </c:choose>
<mfo:getmmhd hdid="${hdid}"/>
<body>
<div id="layout">
   <jsp:include page="../include/nav.jsp"/>
  <div class="titleLogin"><a href="http://www.mmfoo.com" style="color:#FF0099;">首页</a> > <a href="http://www.mmfoo.com/mmfoohd.html" style="color:#FF0099;">美分活动</a> > ${mmfoohd.hdtitle}</div>
  <div class="activitiesBox">

    <div class="doContent">
        <%--根据id取活动数据--%>

      <h2>${mmfoohd.hdtitle}</h2>
      <div class="detail">
        <ul>
          <li class="tl">活动时间：</li>
          <li class="tr">
          <fmt:formatDate value="${mmfoohd.createtime}" pattern="yyyy年MM月dd日"/>
              </li>
        </ul>
        <ul>
          <li class="tl">活动内容：</li>
          <li class="tr"><p> ${mmfoohd.hdcontent}</p></li>
        </ul>
      </div>
      <div class="join">       <button class="funcB" onClick="joinhd('${mmfoohd.hdid}');">立即参加</button></div>
      <!--joiner-->
      <div class="joiner">
        <h4>&nbsp;&nbsp;&nbsp;TA们还参加了此次活动：</h4>

          <mfo:gethduser hdid="${param.hdid}" size="5" page="1"/>

          
          <c:forEach var="users" items="${topuser}">

           <div class="cell">
                   <a href="<%=basePath%>/user-${users.user_id}-1.html" target="_blank"><img src="<%=basePath%>/images/thumb/${users.userthumb}"/></a>
                   <h5><a href="<%=basePath%>/user-${users.user_id}-1.html" target="_blank">${users.truename}</a></h5>
                 </div>
          </c:forEach>
       
      </div>
    </div>
 
  </div>
<div class="update">
    <%--取得美女數據--%>
         <mfo:getuser/>
    <h3><span>美分最美女</span></h3>
    <div class="facePhoto"><a href="<%=basePath%>/user-${userindex.user_id}-1.html"><img src="<%=basePath%>/images/${userindex.userimg}" width="162" /></a></div>
    <div class="rankInfo">
          <h4><a href="<%=basePath%>/user-${userindex.user_id}-1.html">${userindex.truename}</a></h4>
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
