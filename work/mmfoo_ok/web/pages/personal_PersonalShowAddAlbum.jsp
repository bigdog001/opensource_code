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
    </c:choose>美分网MMFOO |增加相册</title>
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


<c:choose>
    <c:when test="${sessionScope.islogin eq 'Y' }">
        <%--等用户还要判断登录的用户是否为空间所属者本人--%>
        <c:choose>
            <c:when test="${sessionScope.userbean.user_id==user_id_userspace}">


<div id="main_body">
    <div class="bjda_bt" style="text-align:right;"><a href="<%=basePath%>/usershow-${user_id_userspace}-3.html">返回我的展示</a>
    </div>
    <div class="mfzs_psea">
        <form>
            <table width="100%" height="250" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="233" rowspan="4" valign="top"><img src="../images/meinv2.jpg" width="233" height="247"/>
                    </td>
                    <td width="10" rowspan="4" valign="top"></td>
                    <td width="80" height="30" align="right" class="fB">名字：</td>
                    <td><input type="text" name="" value="" id="setname"/></td>
                </tr>
                <tr>
                    <td width="80" height="30" align="right" valign="top" class="fB">描述：</td>
                    <td><textarea id="setdiscribe"></textarea></td>
                </tr>
                <%-- <tr>
                  <td width="80" height="30" align="right" class="cDGray">谁能浏览：</td>
                  <td>
                  <select>
                  <option>所有人</option>
                  </select>
                  </td>
                </tr>--%>
                <tr>
                    <td width="80" height="100" align="right" valign="top">&nbsp;</td>
                    <td>
                        <input type="button" <%-- src="<%=basePath%>/images/bn_bcxg.gif"--%>
                               onclick="addImgSet('${user_id_userspace}')" value="增加"/>&nbsp;&nbsp;&nbsp;
                        <input type="button"  <%-- src="<%=basePath%>/images/bn_qx.gif"--%>
                               onclick="window.location='<%=basePath%>/usershow-${user_id_userspace}.html' "
                               value="取消"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
            </c:when>
            <c:otherwise>
                <%--虽然登录了 但是登录者不是空间的主人--%>
            </c:otherwise>
        </c:choose>

    </c:when>
    <c:otherwise>
        <%--没有登录--%>
        <script type="text/javascript">
            alert('请先登陆！');
            var url_now = window.location.href;
            window.location = '<%=basePath%>/pages/login.jsp?url_target=' + url_now;
        </script>
    </c:otherwise>
</c:choose>

<div id="foot_x">
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
