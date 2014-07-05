<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美分网</title>
<jsp:include page="../include/header.jsp"/>
</head>

<body>
<div id="layout">
  <div id="top">
    <div class="logo"><a href="<%=basePath%>"><img src="images/logo.jpg" /></a></div>
    <div class="signin">
     <c:choose>
            <c:when test="${sessionScope.islogin eq 'Y' }">
                <a href="javascript:logout(window.location.href)">退出</a>|<a
                href="/pages/personalCenter.html" <%--class="thickbox"--%>>欢迎您:${sessionScope.login_email}</a>
            </c:when>
            <c:otherwise>
                <a href="<%=basePath%>/pages/join_registe-2.html">免费注册</a>|<a
                href="<%=basePath%>/pages/login.html?height=280;width=423" data-reveal-id="myModal" class="thickbox">登录</a>
            </c:otherwise>
        </c:choose>
    </div>
  </div>
  <div class="extLeft">
    <jsp:include page="../include/extleft.jsp"/>
  </div>
  <div class="extRight">
       <mfo:shownews newsid="${param.newsid}"/>
     <p>美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着"敢于展现，娱乐精神"的品牌理念，美分网赢得了数十万大连新青年的青睐。</p>
<p>目前美分网拥有最海量的个人自拍写真原创图片库，自2011年3月上线以来，美分网一直深化与上下游产业链的合作，创造性地开发了"YYBT"合作共赢计划，与全国数百家电视台、影视公司、唱片公司、传媒机构等达成战略合作伙伴。</p>
<p>自成立以来，美分网保持着高速发展的步调，以自拍图片为载体，以用户投票活动为手段，开创差异化的视觉营销新模式，短短数月注册会员数已经突破十万级，作为新生代地区广告媒介，美分网赢得了众多广告客户的关注。</p>
<p>依托中青创业的技术优势，美分网站在巨人的肩膀上腾飞，以互联网的精神创新、坚持分享及开放，打造为输出大连新生代青年形象和娱乐精神的视觉媒体平台。</p>
</p> 
<div class="llaa"></div>
  </div>
  <div class="trailor"><span>&copy; 2011</span><a href="#">美分网</a> <span>(MMFOO.COM), All rights reserved. </span>辽ICP证090052号</div>
</div>
</body>
</html>
