<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>美分网</title>
    <jsp:include page="include/header.jsp"/>
     <fmt:setLocale value="zh"/>
</head>
<body>
<div id="layout">

    <jsp:include page="include/nav.jsp"/>
    <div class="promoteBox">
        <div class="timer">
            <h1>30</h1>
        </div>
        <div class="avatarPromote">
            <div class="avatar"><a href="#"><img src="images/avatar_samll.jpg"/></a></div>
            <ul>
                <li><a href="#">范冰冰</a><a href="#" class="vote2">投票</a></li>
                <li><span>大连外国语大学</span></li>
            </ul>
        </div>
        <!--slide begin-->
        <div class="slide">
            <section id="slider">
                <div class="container_24">
                    <div class="wrapper">
                        <div class="grid_24">
                            <div id="slideshowHolder"><img src="images/slider1.jpg"> <a href="#"></a> <img
                                    src="images/slide2.jpg"> <a href="#"></a> <img src="images/slide3.jpg"> <a
                                    href="#"></a>
                                <img src="images/slide4.jpg"> <a href="#"></a> <img src="images/slide5.jpg"> <a
                                        href="#"></a></div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
    <!--dock begin-->
    <div class="dock">
        <div id="banner">
            <div id="viewline">
                <div><img src="images/avatar_big.jpg" alt="A"/> <span
                        class="caption">This is a caption for the content</span></div>
                <div><img src="images/avatar_big.jpg" alt="B"/> <span
                        class="caption">This is a caption for the content</span></div>
                <div><img src="images/avatar_big.jpg" alt="C"/> <span
                        class="caption">This is a caption for the content</span></div>
                <div><img src="images/avatar_big.jpg" alt="D"/> <span
                        class="caption">This is a caption for the content</span></div>
                <div><img src="images/avatar_big.jpg" alt="E"/> <span
                        class="caption">This is a caption for the content</span></div>
                <div><img src="images/avatar_big.jpg" alt="F"/> <span
                        class="caption">This is a caption for the content</span></div>
            </div>
        </div>
    </div>
    <!--dock end-->
    <div class="faceShow">
        <h3><img src="images/beautyBestTitle.jpg"/></h3>
        <%--取得美女數據--%>
         <mfo:getuser/>
        <div class="rankBox2">
            <div class="numone2">
                <div class="rankAvatar">
                    <div class="avatar"><a href="<%=basePath%>/pages/personal_album-${userindex.user_id}.html"><img src="<%=basePath%>/images/thumb/${userindex.userimg}"/></a></div>
                </div>
                <div class="rankInfo">
                    <h4><a href="<%=basePath%>/pages/personal_album-${userindex.user_id}.html">${userindex.truename}</a></h4>
                    <h6><span>得票数：${userindex.visit_cnt}</span></h6>
                    <h6><span>评论数：${userindex.discuss}</span></h6>
                    <h6><span><mfo:gethdname user_id="${userindex.user_id}"/>
                    </span></h6>
                </div>
            </div>
        </div>
        <div class="someInfo2">
            <ul>
                <li>${userindex.school}</li>
                <li>  
                    <fmt:formatDate value="${userindex.birthday}" pattern="yyyy年MM月dd日"/>
                    </li>
                <li>${userindex.province} ${userindex.city}</li>
                <li>${userindex.height}</li>
            </ul>
        </div>
    </div>
    <!--beauty top end-->
    <!--newbie begin-->
    <div class="faceShow">
        <h3><img src="images/newbieTitle.jpg"/></h3>
        <%--取最新加入的用户--%>
         <mfo:getusernew/>
        <div class="rankBox2">
            <div class="numone2">
                <div class="rankAvatar">
                    <div class="avatar"><a href="<%=basePath%>/pages/personal_album-${usernew.user_id}.html"><img src="<%=basePath%>/images/thumb/${usernew.userimg}"/></a></div>
                </div>
                <div class="rankInfo">
                    <h4><a href="<%=basePath%>/pages/personal_album-${usernew.user_id}.html">${usernew.truename}</a></h4>
                    <h6><span>得票数：${usernew.visit_cnt}</span></h6>
                    <h6><span>评论数：${usernew.discuss}</span></h6>
                    <h6><span><mfo:gethdname user_id="${usernew.user_id}"/></span></h6>
                </div>
            </div>
        </div>
        <div class="someInfo2">
            <ul>
                <li>${usernew.school}</li>
                <li><fmt:formatDate value="${usernew.birthday}" pattern="yyyy年MM月dd日"/></li>
                <li>${usernew.province} ${usernew.city}</li>
                <li>${usernew.height}</li>
            </ul>
        </div>
    </div>
    <!--newbie end-->
    <!--news begin-->
    <div class="news">
        <h3><img src="images/newsTitle.jpg"/></h3>
       <mfo:getusertop size="4" page="1"/>
         <c:forEach var="topU" items="${usetop}">
               <div class="cell">
            <div class="avatar"><a href="<%=basePath%>/pages/personal_album-${topU.user_id}.html" target="_blank"><img src="<%=basePath%>/images/thumb/${topU.userthumb}"/></a></div>
            <div class="info">
                <p><a href="<%=basePath%>/pages/personal_album-${topU.user_id}.html" target="_blank">${topU.truename}</a>最新的一条心情的前20个字</p>
                <span><fmt:formatDate value="${usernew.reg_time}" pattern="yyyy-MM-dd HH:mm:ss"/><%--最新的一条心情的发送时间--%></span></div>
        </div>
           </c:forEach>
         
    </div>
    <!--news end-->
    <!--doings begin-->
    <div class="doings">
        <h3><img src="<%=basePath%>/images/huodongTitle.jpg"/></h3>
       <mfo:gettophd page="1" size="2"/>
        
        <c:forEach var="topHD" items="${tophd}">
                <div class="cell">
                   <div class="doingsPicture"><a href="/pages/activitiesDetail-${topHD.hdid}.html" target="_blank"><img src="<%=basePath%>/images/${topHD.hdface}" width="178"/></a></div>
                   <div class="doingsRight"><span class="titl"><a href="/pages/activitiesDetail-${topHD.hdid}.html" target="_blank">${topHD.hdtitle}</a></span><br/>
                       <span class="tim">
                    <fmt:formatDate value="${topHD.starttime}" pattern="yyyy年MM月dd日"/>
                       </span><br/>
                       <span class="des">${topHD.hdabstract}</span><br/>
                       <button class="funcB" onclick="joinhd('${topHD.hdid}');">参加活动</button>
                   </div>
               </div>
           </c:forEach>
    </div>
    <!--doings end-->
    <!--update begin-->
    <div class="update">
        <h3><img src="images/updateTitle.jpg"/></h3>

        <div class="cell">
            <div class="da">3/30</div>
            <div class="event">日的阳光，穿透晨雾照在树林间，远的像还没睡醒</div>
        </div>
        <div class="cell">
            <div class="da">12/18</div>
            <div class="event">日的阳光，穿透晨雾照在树林间，远处教还没睡醒</div>
        </div>
        <div class="cell">
            <div class="da">12/2</div>
            <div class="event">日的阳光，穿透晨雾照在树林间，远处的教学楼好</div>
        </div>
        <div class="cell">
            <div class="da">11/30</div>
            <div class="event">日的阳光，穿透晨雾照在树林间，远处教还没睡醒</div>
        </div>
        <div class="cell">
            <div class="da">9/27</div>
            <div class="event">日的阳光，穿透晨雾照在树林间，远处的教学楼好</div>
        </div>
        <div class="cell">
            <div class="da">12/2</div>
            <div class="event">日的阳光，穿透晨雾照在树林间，远处的教学楼好</div>
        </div>
        <div class="cell">
            <div class="da">11/30</div>
            <div class="event">日的阳光，穿透晨雾照在树林间，远处教还没睡醒</div>
        </div>
        <div class="cell">
            <div class="da">9/27</div>
            <div class="event">日的阳光，穿透晨雾照在树林间，远处的教学楼好</div>
        </div>
    </div>
    <!--update end-->
    <jsp:include page="include/footer.jsp"/>
</div>
</body>
</html>
