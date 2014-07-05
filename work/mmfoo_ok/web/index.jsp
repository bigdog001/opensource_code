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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>美分网MMFOO——青春、阳光、时尚、健康的美眉风采展示平台</title>
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
    <meta name="keywords" content="美分网,美女,美眉,校园,时尚,阳光,大连,北京,全国 " />
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_common.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/menu.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/thickbox.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_index.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/button.css">
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/thickbox_plus.js"></script>

    <!-- skin stylesheets -->
    <link rel="stylesheet" href="<%=basePath%>/js/pe.kenburns/themes/neutral/skin.min.css"/>
    <!-- import jQuery -->
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.5.2.min.js"></script>
    <!-- import plugin -->
    <script type="text/javascript" src="<%=basePath%>/js/pe.kenburns/jquery.pixelentity.kenburnsSlider.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/slide.css">
    <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
    <!-- activate plugin -->
    <script>
        jQuery(function($) {
            $(".peKenBurns").peKenburnsSlider()
        })
    </script>
</head>

<body>
<div id="layout">
    <jsp:include page="include/nav.jsp"/>
    <!--slide开始-->
    <div class="peKenBurns" data-mode="swipe" data-logo="disabled" data-controls="inner" data-shadow="disabled">
       <mfo:GetIndexImg page="1" size="8"/>
         <c:forEach var="imgindex" items="${ingindex}">
                   <div data-delay="5" data-thumb="<%=basePath%>/images/upload/${imgindex.thumb}">
            <a href="${imgindex.target_url}"><img
                    src="<%=basePath%>/images/blank.png"
                    data-src="<%=basePath%>/images/upload/${imgindex.img}"
                    alt="${imgindex.truename}"/></a>

            <h1>${imgindex.truename}  ${imgindex.city}  ${imgindex.star} </h1>
        </div>
         </c:forEach>
    </div>
    <!--slide结束-->
    <div class="clearit"></div>
    <div class="faceShow">
        <h3><img src="images/beautyBestTitle.jpg"/></h3>
<%-- 取本月最美美女 --%>
         <mfo:getuser/>
        <div class="rankBox2">
            <div class="numone2">
                <div class="rankAvatar">
                    <div class="avatar"><a href="<%=basePath%>/user-${userindex.user_id}-1.html"><img src="<%=basePath%>/images/${userindex.userimg}"/></a></div>
                </div>
                <div class="rankInfo">
                    <h4><a href="<%=basePath%>/user-${userindex.user_id}-1.html">${userindex.nickname}</a></h4>
                    <h6><span>得票数：${userindex.visit_cnt}</span></h6>
                    <h6><span>评论数：${userindex.discuss}</span></h6>
                    <h6><span><mfo:gethdname user_id="${userindex.user_id}"/></span></h6>
                </div>
            </div>
        </div>
        <div class="someInfo2">
            <ul>
                <li>${userindex.school}</li>
                <li><fmt:formatDate value="${userindex.birthday}" pattern="yyyy年MM月dd日"/></li>
                <li>${userindex.province} ${userindex.city}</li>
                <li>${userindex.height}</li>
            </ul>
        </div>
    </div>
    <!--beauty top end-->
    <!--newbie begin-->
    <div class="faceShow">
        <%--取最新加入的用户--%>
         <mfo:getusernew/>
        <h3><img src="<%=basePath%>/images/newbieTitle.jpg"/></h3>

        <div class="rankBox2">
            <div class="numone2">
                <div class="rankAvatar">
                    <div class="avatar"><a href="<%=basePath%>/user-${usernew.user_id}-1.html"><img src="<c:choose><c:when test="${empty topU.userthumb}"><%=basePath%>/images/avatar_big.jpg</c:when><c:otherwise><%=basePath%>/images/${usernew.userimg}</c:otherwise></c:choose>"/></a></div>
                </div>
                <div class="rankInfo">
                    <h4><a href="<%=basePath%>/user-${usernew.user_id}-1.html">${usernew.nickname}</a></h4>
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
        <h3><img src="<%=basePath%>/images/newsTitle.jpg"/></h3>
        <mfo:getusertop size="4" page="1"/>
        <c:forEach var="topU" items="${usetop}">

            <div class="cell">
                <div class="avatar"><a href="<%=basePath%>/user-${topU.user_id}-1.html" target="_blank"><img
                        src="<%=basePath%>/images/thumb/${topU.userthumb}"/></a></div>
                <div class="info">
                    <p><a href="<%=basePath%>/user-${topU.user_id}-1.html" target="_blank">${topU.nickname}</a>
                        刚刚成功注册了美分网，拥有了属于自己的美分展示页面！大家快来看看我的美丽时刻吧!
                            <%--  查找某人的最后一条微博 --%>
                       <%-- <mfo:GetShortMessageByRecently user_id="${topU.user_id}"/>
                        <c:choose>
                            <c:when test="${fn:length(weiboRecently.messagecontent) > 20}">
                                <c:out value="${fn:substring(weiboRecently.messagecontent, 0, 20)}......"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${weiboRecently.messagecontent}"/>
                            </c:otherwise>
                        </c:choose>--%>

                    </p>
                    <span><fmt:formatDate value="${topU.reg_time}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
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
                <div class="doingsPicture"><a href="/mfhd-${topHD.hdid}.html" target="_blank"><img
                        src="<%=basePath%>/images/upload/${topHD.hdface}" width="178"/></a></div>
                <div class="doingsRight"><span class="titl"><a href="/mfhd-${topHD.hdid}.html"
                                                               target="_blank">${topHD.hdtitle}</a></span><br/>
                       <span class="tim">
                    <fmt:formatDate value="${topHD.starttime}" pattern="yyyy年MM月dd日"/>
                       </span><br/>
                    <span class="des">${topHD.hdabstract}</span><br/>
                    <button class="funcB" onClick="joinhd('${topHD.hdid}');">参加活动</button>
                </div>
            </div>
        </c:forEach>

    </div>
    <!--doings end-->
    <!--update begin-->
    <div class="update">
        <h3><img src="images/updateTitle.jpg"/></h3>
        <c:forEach begin="13" step="1" end="20" var="iis">
            <mfo:GetSingleFooterNews newid="${iis}"  />
           <div class="cell">
            <div class="da">${newssingle.title}</div>
            <div class="event">${newssingle.content}</div>
        </div>
        </c:forEach>
    
    </div>
    <!--update end-->
    <jsp:include page="include/footer.jsp"/>
</div>
</body>
</html>
