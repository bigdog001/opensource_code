<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="zh"/>
<%
    //活动列表页面，取出所有的活动信息
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    basePath=basePath.replace(":80","");
%>
<c:choose>
    <c:when test="${empty param.user_id}">
        <c:set var="user_id_userspace" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="user_id_userspace" value="${param.user_id}"/>
    </c:otherwise>
</c:choose>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><c:choose>
        <c:when test="${empty userbean}"></c:when>
        <c:otherwise>${userbean.nickname} 个人网站</c:otherwise>
    </c:choose>美分网MMFOO |美分展示</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
   <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <meta name="GENERATOR" content="Macromedia Dreamweaver MX"/>
    <meta http-equiv="Pics-label"
          Contect="(PICS－1.1'http://www.rsac.org/ratingsv01.html' I gen comment 'RSACi North America Sever' by 'inet@microsoft.com' for 'http://www.microsoft.com' on '1997.06.30T14:21－0500' r(n0 s0 v0 l0))"/>

    <meta name="Robots" content="all"/>
    <meta http-equiv="MSThemeCompatible" content="Yes"/>
    <meta name="MSSmartTagsPreventParsing" content="TRUE"/>
   <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
    <link href="<%=basePath%>/css/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/slide.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
    <style type="text/css">
        <!--
        * {
            margin: 0px;
            padding: 0px;
        }

        body {
            margin: 0px;
            font-size: 12px;
            line-height: 18px;
        }

        .mvzs {
            height: 250px;
            width: 223px;
            position: relative;
        }

        .mvzs a img {
            height: 96px;
            width: 128px;
            border: none;
        }

        .mvzs a span {
            margin-top: -9000px;
            margin-left: -9000px;
            position: absolute;
        }

        .mvzs a:hover {
            background-color: #FFF;
        }

        .mvzs a:hover span {
            height: 250px;
            width: 223px;
            position: absolute;
            left: 0px;
            top: 0px;
            border: 0px solid #F90;
            padding: 0px;
            background: #F896D1;
            filter: alpha(opacity = 75);
            opacity: 0.8;
            display: block;
            text-decoration: none;
            cursor: pointer;
            margin: 0px;
            color: #ffffff;
            font-family: "微软雅黑";
            font-size: 40px;
            line-height: 250px;
        }

        -->
    </style>
</head>

<body>
<jsp:include page="../include/user_space_nav.jsp"/>

<div id="main_body">
    <div class="bjda_bt"><span>
        <c:choose>
            <c:when test="${sessionScope.islogin eq 'Y' }">
                <%--等用户还要判断登录的用户是否为空间所属者本人--%>
                <c:choose>
                    <c:when test="${sessionScope.userbean.user_id==user_id_userspace}">
                        <a href="<%=basePath%>/newphoto-${sessionScope.userbean.user_id}.html"><img
                                src="<%=basePath%>/images/bn_fbzp.gif" width="100" height="27"/></a>
                    </c:when>
                    <c:otherwise>
                        <%--虽然登录了 但是登录者不是空间的主人--%>
                    </c:otherwise>
                </c:choose>

            </c:when>
            <c:otherwise>
                <%--没有登录--%>

            </c:otherwise>
        </c:choose>
    </span></div>
    <div class="mfzs">
        <ul>
            <%--取当前空间的主人所有的相册--%>
            <mfo:getImgsetbyid user_id="${user_id_userspace}" size="4" page="1"/>
            <c:choose>
                <c:when test="${fn:length(userimgset)>0}">
                    <c:forEach var="imgset" items="${userimgset}">
                        <li>
                                <%--取此相册下一共有几张照片--%>
                            <mfo:getImgTotalBysetid setid="${imgset.setid}"/>
                            <div class="mfzs_time"><span class="ec008c">${picintheset}</span>&nbsp;&nbsp;<font
                                    class="f24px cDGray"><fmt:formatDate value="${imgset.createtime}"
                                                                         pattern="yyyy/MM/dd"/></font></div>
                            <mfo:getSetFace setid="${imgset.setid}"/>
                            <div class="mvzs"><a
                                    href="<%=basePath%>/photoShow-${imgset.setid}-${user_id_userspace}.html"><img
                                    src="<%=basePath%>/images/<c:choose><c:when test="${empty setface.thumb_img}">meinv8.jpg</c:when><c:otherwise>${setface.thumb_img}</c:otherwise></c:choose>"
                                    width="223" height="170"
                                    alt="查看相册"/><span>查看图片</span></a>
                                <span class="f24px">${imgset.setname}</span></div>
                            <div class="zsdb">
                                <dl>
                                    <dt>
                                        <img src="<%=basePath%>/images/icon_7.gif" width="16" height="16"/> <font
                                            class="f12px">${imgset.favor_cnt}</font>
                                        <img src="<%=basePath%>/images/icon_8.gif" width="16" height="16"/> <font
                                            class="f12px">${imgset.see_cnt}</font>
                                    </dt>
                                    <dd>
                                            <%--<img src="<%=basePath%>/images/icon_9.gif" width="16" height="16"/> <font class="f12px">100</font>--%>
                                    </dd>
                                </dl>
                            </div>
                        </li>

                    </c:forEach>
                </c:when>
                <c:otherwise>
                    抱歉，当前没有相册！
                </c:otherwise>
            </c:choose>


        </ul>
    </div>
</div>

<div id="foot_x">
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
