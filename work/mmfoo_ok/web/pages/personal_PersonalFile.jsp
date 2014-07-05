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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><c:choose>
        <c:when test="${empty userbean}"></c:when>
        <c:otherwise>${userbean.nickname} 个人网站</c:otherwise>
    </c:choose>美分网MMFOO |用户档案</title>
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

</head>

<body>
<jsp:include page="../include/user_space_nav.jsp"/>

<div id="main_body">
    <div class="bjda_bt"><span>
        <c:choose>
            <c:when test="${sessionScope.islogin eq 'Y' }">

                <c:choose>
                    <c:when test="${user_id_userspace eq userbean.user_id}">
                        <a href="<%=basePath%>/userfileedite-${user_id_userspace}.html"><img
                                src="<%=basePath%>/images/bn_bj.gif" width="98" height="27"/></a>
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>

            </c:otherwise>
        </c:choose>
        

</span></div>
    <div class="bjda_info">
        <dl>
            <dt>
                <%-- 根据用户id取用户 --%>
                <mfo:getUserById user_id="${user_id_userspace}"/>
                <strong>基础信息</strong>
            <table width="100%" border="0" cellspacing="0" cellpadding="0"
                   style=" margin-top:10px; font-size:14px; line-height:26px; color:#666;">
                <tr>
                    <td width="70" align="right">出生日期：</td>
                    <td style=" padding-left:30px;color:#333;"><fmt:formatDate value="${userbyid.birthday}"
                                                                               pattern="yyyy年MM月dd日"/>
                    </td>
                </tr>
                <tr>
                    <td width="70" align="right">星座：</td>
                    <td style=" padding-left:30px; color:#333;">${userbyid.user_star}</td>
                </tr>
                <tr>
                    <td width="70" align="right">所在地：</td>
                    <td style=" padding-left:30px;color:#333;">${user_star.city}</td>
                </tr>
            </table>
            <strong>学历</strong>

            <div class="infobj">${userbyid.education}
            </div>
            <strong>获奖</strong>

            <div class="infobj">${userbyid.huojiang}
            </div>
            </dt>
            <dd>
                <strong>联络信息</strong>
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"
                       style=" margin-top:10px; font-size:14px; line-height:26px; color:#666;">
                    <tr>
                        <td width="70" align="right">EMAIL：</td>
                        <td style=" padding-left:10px;color:#333;">${userbyid.email}</td>
                    </tr>
                    <tr>
                        <td width="70" align="right">MSN：</td>
                        <td style=" padding-left:10px; color:#333;">${userbyid.user_msn}</td>
                    </tr>
                    <tr>
                        <td width="70" align="right">手机：</td>
                        <td style=" padding-left:10px;color:#333;">${userbyid.mobile}</td>
                    </tr>
                    <tr>
                        <td width="70" align="right">QQ：</td>
                        <td style=" padding-left:10px;color:#333;">${userbyid.qqnumber}</td>
                    </tr>
                    <tr>
                        <td width="70" align="right">其他：</td>
                        <td style=" padding-left:10px;color:#333;">&nbsp;</td>
                    </tr>
                </table>

            </dd>
        </dl>
    </div>

</div>

<div id="foot_x">
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
