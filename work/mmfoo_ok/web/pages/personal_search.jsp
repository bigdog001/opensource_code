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
    <title>美分网|搜索</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <meta name="GENERATOR" content="Macromedia Dreamweaver MX"/>
    <meta http-equiv="Pics-label"
          Contect="(PICS－1.1'http://www.rsac.org/ratingsv01.html' I gen comment 'RSACi North America Sever' by 'inet@microsoft.com' for 'http://www.microsoft.com' on '1997.06.30T14:21－0500' r(n0 s0 v0 l0))"/>
     <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
    <meta name="Robots" content="all"/>
    <meta http-equiv="MSThemeCompatible" content="Yes"/>
    <meta name="MSSmartTagsPreventParsing" content="TRUE"/>
   <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
    <link href="<%=basePath%>/css/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="<%=basePath%>/css/search.css" rel="stylesheet" type="text/css" media="screen"/>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/slide.js"></script>
     <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
</head>

<body>
<jsp:include page="../include/user_space_nav.jsp"/>
<div id="main_body" class="main_contant">
    <div class="sear">     <form action="<%=basePath%>/user/user_UserSearche.action" method="post" name="mmfoosearch">
        <div class="searT"><input type="text" name="truename" value="${param.truename}" class="t_b" onclick="if(this.value=='搜索')this.value = ''" onblur="if(this.value=='')this.value = '搜索'"><a href="javascript:document.mmfoosearch.submit()">搜索</a></div>
        <div class="fl"><%--<input type="radio" name="">用户名　　　<input type="radio" name="">展示标题--%></div>
        </form>
    </div>
    <div class="result">
        <span>共找到${fn:length(searchs)}人</span>
        <ul class="searLi">
              <li style="padding:10px 0">
                <span>美分认证</span><span>身份</span><%--<span>职业</span><span>展示数量</span>--%><span>粉丝数量</span>
            </li>
            <c:forEach var="search" items="${searchs}">

            <li>
                <span>　
                    <c:choose>
                        <c:when test="${search.hd_ok eq 'Y'}">已认证</c:when>
                        <c:otherwise>未认证</c:otherwise>
                    </c:choose>
                    　</span>
                <span>
                	<div class="tu_bt">
                        <img src="<%=basePath%>/images/thumb/${search.userthumb}" width="53" height="56"/>
                        <div class="TBT">
                            <b>${search.truename}</b>
                            <mfo:gethdname user_id="${search.user_id}"/>
                        </div>
                    </div>
                </span>
               <%-- <span><a href="">歌手</a></span>
                <span><h2>0</h2>/0</span>--%>
                <mfo:getUserById user_id="${search.user_id}"/>
                <span><h2>${userbyid.point_cnt}</h2>人</span>
            </li>
          </c:forEach>
        </ul>
    </div>
</div>

<div id="foot_x">
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
