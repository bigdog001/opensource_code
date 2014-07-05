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
    <title>美分网DESIGN</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <meta name="GENERATOR" content="Macromedia Dreamweaver MX"/>
    <meta http-equiv="Pics-label"
          Contect="(PICS－1.1'http://www.rsac.org/ratingsv01.html' I gen comment 'RSACi North America Sever' by 'inet@microsoft.com' for 'http://www.microsoft.com' on '1997.06.30T14:21－0500' r(n0 s0 v0 l0))"/>
    <meta name="Keywords" content="关键字"/>
    <meta name="Description" content="描述"/>
    <meta name="Robots" content="all"/>
    <meta http-equiv="MSThemeCompatible" content="Yes"/>
    <meta name="MSSmartTagsPreventParsing" content="TRUE"/>
    <link href="favicon.ico" rel="shortcut icon"/>
    <link href="<%=basePath%>/css/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <%--<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.2.min.js"></script>--%>
    <%--<script type="text/javascript" src="<%=basePath%>/js/jquery.easing.1.3.js"></script>--%>
    <script type="text/javascript" src="<%=basePath%>/js/slide.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>

</head>

<body>
<jsp:include page="../include/user_space_nav.jsp"/>


<c:choose>
    <c:when test="${sessionScope.islogin eq 'Y' }">

        <c:choose>
            <c:when test="${user_id_userspace eq userbean.user_id}">

                <%-- 取用户对象 --%>


                <mfo:getUserById user_id="${user_id_userspace}"/>

                <div id="main_body">
                    <div class="bjda_bt"><span><a href="<%=basePath%>/userfile-${user_id_userspace}.html"><img src="<%=basePath%>/images/bn_tc.gif" width="78" height="27"/></a>
</span></div>
                    <div class="bjda_nav left">
                        <jsp:include page="../include/user_info.jsp"/>
                    </div>
                    <div class="bjda_xg left">
                        <form>
                            <div class="bjda_xg_info">
                                <textarea id="jichu">
                                        ${userbyid.jichu}
                                </textarea>
                            </div>
                            <div class="bn_bc"><input type="button"  value="保存"  src="<%=basePath%>/images/bn_bc.gif" onclick="savejichu('${user_id_userspace}')"/></div>
                        </form>
                    </div>
                </div>
            </c:when>
            <c:otherwise>

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


<div id="foot_x">
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
