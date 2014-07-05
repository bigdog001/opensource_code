<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/personal.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/button.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/tab_personal.css">
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
</head>

<body>
<jsp:include page="../include/user_top.jsp"/>
<c:if test="${empty param.user_id}">
    <c:set var="param.user_id" value="1"/>
</c:if>
<mfo:getUserById user_id="${param.user_id}"/>
<div id="layout">
    <div class="promoteBox">
        <div class="avatarPromote"><img src="<%=basePath%>/images/${userbyid.userimg}"/></div>
        <jsp:include page="../include/user_nav.jsp"/>
        <div class="slide">
            <jsp:include page="../include/user_left.jsp"/>
            <div class="content">
                <!--开始-->
                <div class="fresh">
                   <%--得到我最近的心情--%>
                    <mfo:get_shortmessage_byuser_id user_id="${userbyid.user_id}" page="1" size="5"/>
                    <c:forEach var="sm" items="${getshortmessagebyemail}">
                        <div class="item">
                            <div class="avatar"><a href="<%=basePath%>/pages/personal_album-${userbyid.user_id}.html" target="_blank"><img
                                    src="<%=basePath%>/images/${userbyid.userthumb}"/></a></div>
                            <div class="rightInfo">
                                <h6><a href="<%=basePath%>/pages/personal_album-${userbyid.user_id}.html"
                                       target="">${userbyid.truename}</a>
                                    <%-- 截字操作 --%>
                                    <c:choose>
                                        <c:when test="${fn:length(sm.messagecontent) > 20}">
                                            <c:out value="${fn:substring(sm.messagecontent, 0, 20)}......"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${sm.messagecontent}"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <%-- 截字操作 --%>
                                    <span>
                                        <mfo:getTimeDistance start="${sm.messagetime}"/>

                                    </span></h6>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <!--结束-->
            </div>
        </div>
    </div>
    <jsp:include page="../include/footer.jsp"/>
</div>

</body>
</html>
