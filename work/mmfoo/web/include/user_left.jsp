<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<%--记录访问了我的空间的人的足迹情况 start  --%>

<%--如果没有登录的话就不记录 只记录已经登录的用户的访问情况--%>
<c:choose>
    <c:when test="${sessionScope.islogin eq 'Y' }">
        <%--记录访问情况 --%>
        <%--如果登录用户访问的是自己的用户空间的话 那么久不在记录范围内--%>
        <c:choose>
            <c:when test="${userbyid.user_id == userbean.user_id}"></c:when>
            <c:otherwise>
                <mfo:savevisitfooter my_user_id="${userbyid.user_id}" friends_user_id="${userbean.user_id}"/>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>

<%--记录访问了我的空间的人的足迹情况 end--%>
<div class="side">
    <h3><a href="#">${userbyid.truename}</a></h3>

    <h2>加入了<mfo:gethdname user_id="${param.user_id}"/></h2>
    <ul class="paraBox">
        <li><img src="<%=basePath%>/images/add_black.jpg"/>受关注数：${userbyid.point_cnt}</li>
        <li><img src="<%=basePath%>/images/vote_black.jpg"/>得票数：${userbyid.visit_cnt}</li>
    </ul>
    <ul class="buttonBox">
        <%--加关注的时候要先登录--%>
        <c:choose>
            <c:when test="${sessionScope.islogin eq 'Y' }">
                <%-- 自己不能关注自己--%>
                  <c:choose>
                       <c:when test="${userbyid.email eq userbean.email}"> <li><a href="javascript:alert('不能关注自己！')" class="vote2">加关注</a></li></c:when>
                      <c:otherwise>
                             <li><a href="javascript:see_user('${userbean.user_id}','${userbyid.user_id}')" class="fans">加关注</a></li>
                      </c:otherwise>
                  </c:choose>
            </c:when>
            <c:otherwise>
               <li><a href="javascript:alert('请先登陆！')" class="fans">加关注</a></li>  
            </c:otherwise>
        </c:choose>

            <c:choose>
                <c:when test="${sessionScope.islogin eq 'Y' }">
                        <c:choose>
                            <c:when test="${userbyid.email eq userbean.email}">
                                <li><a href="javascript:alert('不能给自己投票！')" class="vote2">投她一票</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:tiket('${param.user_id}','${userbean.user_id}')" class="vote2">投她一票</a></li>
                            </c:otherwise>
                        </c:choose>
                </c:when>
                <c:otherwise>
                               <li><a href="javascript:alert('您尚未登录')" class="vote2">投她一票</a></li>
                </c:otherwise>
            </c:choose>




        <c:choose>
            <c:when test="${sessionScope.islogin eq 'Y' }">
                <c:choose>
                    <c:when test="${userbyid.email eq userbean.email}">
                        <li><a href="javascript:alert('抱歉，您不能加自己为好友！')" class="addFriend">加好友</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="javascript:addFriends('${userbyid.user_id}')" class="addFriend">加好友</a></li>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <li><a href="javascript:alert('请先登录!')" class="addFriend">加好友</a></li>
            </c:otherwise>
        </c:choose>

    </ul>
    <h4>TA的好友</h4>
    <ul class="friGroup">
        <mfo:getfriendsbyid user_id="${param.user_id}" page="1" size="3"/>
        <c:forEach var="user_" items="${friendsbyid}">
            <li><a href="<%=basePath%>/pages/personal_album-${user_.user_id}.html"><img
                    src="<%=basePath%>/images/${user_.userimg}" width="49"/></a><h5>${user_.truename}</h5></li>
        </c:forEach>
    </ul>
</div>