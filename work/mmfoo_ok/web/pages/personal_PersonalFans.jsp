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
<c:choose>
    <c:when test="${empty param.user_id}">
        <c:set var="user_id_userspace" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="user_id_userspace" value="${param.user_id}"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${empty param.fansflag}">
        <c:set var="fansflag" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="fansflag" value="${ param.fansflag}"/>
    </c:otherwise>
</c:choose>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><c:choose>
        <c:when test="${empty userbean}"></c:when>
        <c:otherwise>${userbean.nickname} 个人网站</c:otherwise>
    </c:choose>美分网MMFOO |粉丝</title>
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
    <%--<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.2.min.js"></script>--%>
    <%--<script type="text/javascript" src="<%=basePath%>/js/jquery.easing.1.3.js"></script>--%>
    <script type="text/javascript" src="<%=basePath%>/js/slide.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>

</head>

<body>
<jsp:include page="../include/user_space_nav.jsp"/>

<div class="w994">
    <jsp:include page="../include/user_space_left.jsp"/>


    <c:choose>
        <c:when test="${sessionScope.islogin eq 'Y' }">
            <%-- 登录用户必须是空间的主人才看得见 --%>
            <c:choose>
                <c:when test="${sessionScope.userbean.user_id==user_id_userspace}">
                    <%--  关注部分只有本人登陆才看得见------------------------- --%>
                    <%--  根据fansflag的值去取要显示的数据 --%>
                    <mfo:GetFansDisplayByFansFlag fansflag="${fansflag}" my_user_id="${user_id_userspace}" page="1"
                                                  size="5"/>

                    <div class="body_right left">
                        <div class="fsss">
                            <input name="" type="text" value=" 输入昵称或备注"/>
                            <input type="image" class="submit" value="搜索" src="../images/submit1.gif"/>
                        </div>
                        <div class="fslist">
                            <dl>
                                <dt style="border-top:none"></dt>
                                <dd style="border-top:none">操作</dd>
                            </dl>
                            <c:forEach var="fanss_" items="${usersbyfans}">
                                <dl>
                                    <dt>
                                    <div class="fs_ left"><img src="<%=basePath%>/images/thumb/${fanss_.userthumb}" width="49" height="52"/></div>
                                    <div class="fs_ms left">
                                        <p>
                                            <span><a href="<%=basePath%>/user-${fanss_.user_id}-1.html" class="ec008c">${fanss_.nickname}</a></span><br/>
                                            <span>${fanss_.city} 粉丝 > <font color="ed008c">
                                                <mfo:GetLeftNumber user_id="${fanss_.user_id}"/>
                                                ${fanss}</font> 人</span>
                                        </p>
                                    </div>
                                    </dt>
                                    <dd>
                                        <div class="fs_bn"><a href="#"><img src="<%=basePath%>/images/bn_bz.gif" width="66"
                                                                            height="22"/></a></div>
                                        <div class="fs_bn"><a href="javascript:unsee('${user_id_userspace}','${fanss_.user_id}')"><img src="<%=basePath%>/images/bn_jc.gif" width="66"
                                                                            height="22"/></a></div>
                                        <div class="fs_bn"><a href="javascript:addBlacker('${user_id_userspace}','${fanss_.user_id}')">拉黑</a></div>
                                    </dd>
                                </dl>
                            </c:forEach>


                          
                        </div>

                    </div>


                    <%--  -------------------------关注部分只有本人登陆才看得见 --%>
                </c:when>
                <c:otherwise>
                    <%--虽然登录了 但是登录者不是空间的主人--%>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
                      <%--用户未登录--%>
            <script type="text/javascript">
                alert('请先登陆！');
                var url_now=window.location.href;
                window.location='<%=basePath%>/pages/login.jsp?url_target='+url_now ;
            </script>
        </c:otherwise>
    </c:choose>


</div>


<div id="foot_x">
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
