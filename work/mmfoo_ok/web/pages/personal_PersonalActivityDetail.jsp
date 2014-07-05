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
    basePath = basePath.replace(":80", "");
%>

<c:choose>
    <c:when test="${empty param.hd_id}">
        <c:set var="hd_id" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="hd_id" value="${param.hd_id}"/>
    </c:otherwise>
</c:choose>
<mfo:addmmfooHDVisit_cnt hdid="${hd_id}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><c:choose>
        <c:when test="${empty userbean}"></c:when>
        <c:otherwise>${userbean.nickname} 个人网站</c:otherwise>
    </c:choose>美分网MMFOO |集结号详情</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 "/>
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <meta name="GENERATOR" content="Macromedia Dreamweaver MX"/>
    <meta http-equiv="Pics-label"
          Contect="(PICS－1.1'http://www.rsac.org/ratingsv01.html' I gen comment 'RSACi North America Sever' by 'inet@microsoft.com' for 'http://www.microsoft.com' on '1997.06.30T14:21－0500' r(n0 s0 v0 l0))"/>
    <meta name="Robots" content="all"/>
    <meta http-equiv="MSThemeCompatible" content="Yes"/>
    <meta name="MSSmartTagsPreventParsing" content="TRUE"/>
    <link href="favicon.ico" rel="shortcut icon"/>
    <link href="<%=basePath%>/css/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
    <%--<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>--%>
    <%--<script type="text/javascript" src="../js/jquery.easing.1.3.js"></script>--%>
    <script type="text/javascript" src="<%=basePath%>/js/slide.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>

</head>

<body>
<jsp:include page="../include/user_space_nav.jsp"/>


<div class="jijiehao_detail">
    <div class="w735 left" style=" margin-top:10px;">
        <div class="jjh_bt f14px fB"><img src="<%=basePath%>/images/icon_2.gif" width="15" height="16"/> 集结号</div>
        <div class="jjh_page">
            <%--  根据活动id找此活动 --%>
            <mfo:getmmhd hdid="${hd_id}"/>
            <dl>
                <dt class="f14px"><strong class="ec008c">集结号</strong> >${mmfoohd.hdname}  </dt>
                <%--<dd><a href="#">上一个活动</a> / <a href="#">下一个活动</a></dd>--%>
            </dl>
        </div>
        <div class="jjh_title">
            <h1>${mmfoohd.hdname}</h1>
            <dl>
                <dt><a href="#"><img src="<%=basePath%>/images/upload/${mmfoohd.hdface}"/></a></dt>
                <dd>时 间：
                    <fmt:formatDate value="${mmfoohd.starttime}" pattern="yyyy年MM月dd:HH:mm:ss"/> - <fmt:formatDate
                            value="${mmfoohd.endtime}" pattern="yyyy年MM月dd:HH:mm:ss"/>
                </dd>
                <dd>费 用：${mmfoohd.payfeed}</dd>
                <dd>发起人：
                    <%--  根据查到的用户id找用户  --%>
                    <mfo:getUserById user_id="${mmfoohd.starter_user_id}"/>
                    ${userbyid.truename}</dd>
                <dd>奖项设置：${mmfoohd.jiangxian}</dd>
                <dd>
                    <c:choose>
                        <c:when test="${sessionScope.islogin eq 'Y' }">
                            <a href="javascript:joinhd('${hd_id}');"><img src="<%=basePath%>/images/jinto.gif"
                                                                          width="98" height="27"/></a>
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:alert('请登录！');var urlnow=window.location.href;window.location='<%=basePath%>/pages/login.jsp?url_target='+urlnow"><img
                                    src="<%=basePath%>/images/jinto.gif" width="98" height="27"/></a>
                        </c:otherwise>
                    </c:choose>


                    <a href="javascript:interest('${hd_id}')"><img src="<%=basePath%>/images/gxq.gif" width="73"
                                                                   height="27"/></a></dd>
                <dd class="ec008c">(
                    <mfo:GetMmfoohd_user_total hdid="${hd_id}"/>
                    ${mmfoohd_user_total}位美女参加，${mmfoohd.intrest_cnt}人感兴趣 )
                </dd>
            </dl>
        </div>
        <div class="jjh_jj">
            <p>
                <strong>活动简介：</strong><br/>

                ${mmfoohd.hdabstract}
        </div>
        <div class="jjh_phb">
            <mfo:getuserorderbytime size="10" page="${pagenow}"/>
            <p><strong>美女排行榜：</strong></p>
            <ul>
                <c:forEach var="users" items="${topuser}">
                    <li>
                        <a href="<%=basePath%>/user-${users.user_id}-1.html"><img src="<%=basePath%>/images/${users.userimg}" width="162" height="203"></a>

                        <div style="margin-left:8px;"><a href="<%=basePath%>/user-${users.user_id}-1.html"
                                                         style="color:#ED008C; font-family:'宋体'; font-size:14px; font-weight:700;">${users.truename}</a>
                            <br>得票数：<span style=" display:inline; color:#999999">${users.visit_cnt}</span> 粉丝数：<span
                                    style=" display:inline; color:#999999">${users.point_cnt}</span>
                        </div>
                    </li>
                </c:forEach>

            </ul>
        </div>
    </div>
    <div class="w230 right">
        <%-- 根据时间找出所有的美分活动 --%>
        <mfo:gettophd page="1" size="2"/>
        <div class="jijiehao_news">
            <dl>
                <dt><strong>集结号动态</strong></dt>
                <dd><a href="#"><%--更多--%></a></dd>
            </dl>

            <ul>
                <c:forEach items="${tophd}" var="mmf_hd">
                    <li><a href="<%=basePath%>/myactiveview-${mmf_hd.hdid}.html" class="f14px">${mmf_hd.hdname}</a><br/>
                        <fmt:formatDate value="${mmf_hd.createtime}" pattern="yyyy年MM月dd:HH:mm:ss"/> <br/>
                        浏览( <i>${mmf_hd.visit_cnt}</i> ) | 评论( <i>${mmf_hd.discus_cnt}</i> )<br/>
                    </li>
                </c:forEach>

            </ul>
        </div>
        <div class="newsren right">
            <dl>
                <dt><strong>新人推荐</strong></dt>
                <dd><a href="#"><%--更多--%></a></dd>
            </dl>
            <script type="text/javascript" src="js/jquery.js"></script>
            <script type="text/javascript">
                $(document).ready(function() {

                    $(".pane .delete").click(function() {
                        $(this).parents(".pane").animate({ opacity: 'hide' }, "slow");
                    });

                });
            </script>
            <ul>
                <mfo:getusertop page="1" size="5"/>
                <c:forEach var="usersingle" items="${usetop}">
                <li class="pane">
                    <div class="box_1"><a href="<%=basePath%>/user-${usersingle.user_id}-1.html"><img
                            src="<%=basePath%>/images/thumb/${usersingle.userthumb}" width="49" height="52"/></a>
                    </div>
                    <div class="box_2">
                        <span>${usersingle.truename}</span>
                        <c:choose>
                            <c:when test="${sessionScope.islogin eq 'Y' }">
                                <%-- 自己不能关注自己--%>
                                <c:choose>
                                    <c:when test="${usersingle.email eq userbean.email}">
                                        <a href="javascript:alert('不能关注自己！')"><img
                                                src="<%=basePath%>/images/concern.gif" width="60" height="22"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:see_user('${userbean.user_id}','${usersingle.user_id}')"><img
                                                src="<%=basePath%>/images/concern.gif" width="60" height="22"/></a>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <a href="javascript:alert('请先登陆！');window.location='<%=basePath%>/pages/login.jsp?url_target='+window.location.href"><img
                                        src="<%=basePath%>/images/concern.gif"
                                        width="60" height="22"/></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <img src="images/btn-delete.gif" alt="delete" class="delete"/>
                </li>
                </c:forEach>

        </div>
    </div>
</div>

<div id="foot_x">
    <jsp:include page="../include/footer.jsp"/>
</div>

</body>
</html>
