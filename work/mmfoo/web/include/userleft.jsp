<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2011-5-15
  Time: 22:25:58
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>

<div id="left">
    <div class="avatar"><img src="../images/avatar_big.jpg"/></div>
    <h5>范冰冰</h5>
    <ul class="per">
        <li>关注：<span>24</span>&nbsp;&nbsp;得票：<span>24</span></li>
    </ul>
    <ul class="sideNav">

        <c:choose>
            <c:when test="${param.usernav==1 }">
                <li class="current1"> 心情</li>
            </c:when>
            <c:otherwise>
                <li style="background: url(../images/feelingIcon.gif) no-repeat 45px 9px"><a
                        href="<%=basePath%>/pages/personalCenter-1.html"> 心情</a></li>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${param.usernav==2 }">
                <li class="current2"> 相册</li>
            </c:when>
            <c:otherwise>
                <li style="background: url(../images/albumIcon.jpg) no-repeat 45px 9px"><a
                        href="<%=basePath%>/pages/personalCenterAlbum-2.html"> 相册 </a></li>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${param.usernav==3 }">
                <li class="current2"> 好友</li>
            </c:when>
            <c:otherwise>
                <li style="background: url(../images/friendIcon.jpg) no-repeat 45px 9px"><a
                        href="<%=basePath%>/pages/personalCenterFriend-3.html"> 好友 </a></li>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${param.usernav==4 }">
                <li class="current2"> 消息</li>
            </c:when>
            <c:otherwise>
                <li style="background: url(../images/msgIcon.jpg) no-repeat 45px 9px"><a
                        href="<%=basePath%>/pages/personalCenterMessage-4.html"> 消息 </a></li>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${param.usernav==5 }">
                <li class="current5"> 设置</li>
            </c:when>
            <c:otherwise>
                <li style="background: url(../images/setIcon.jpg) no-repeat 45px 9px"><a
                        href="<%=basePath%>/pages/personalCenterSetting-5.html"> 设置 </a></li>
            </c:otherwise>
        </c:choose>

    </ul>
    <div class="visitor">
        <mfo:getUserAccessMyspace my_user_id="${userbean.user_id}" page="1" size="6"/>
        <h3><span>最近来访</span></h3>
          <c:choose>
              <c:when test="${fn:length(WhoVisitMe)>0}">
                  <c:forEach var="visitme" items="${WhoVisitMe}">
            <div class="cell">
                <a href="<%=basePath%>/pages/personal_album-${visitme.user_id}.html"><img src="<%=basePath%>/images/${visitme.userthumb}"/></a>
                <h4><a href="<%=basePath%>/pages/personal_album-${visitme.user_id}.html">${visitme.truename}</a></h4>
            </div>
        </c:forEach>
              </c:when>
              <c:otherwise>
                  最近无访客。。。
              </c:otherwise>
          </c:choose>


    </div>
</div>