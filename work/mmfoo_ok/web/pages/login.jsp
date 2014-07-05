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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>美分网</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_common.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/menu.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_index3.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_button.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/pageNum.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_activities2.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/beautyLLk.css">
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>

</head>
<script>
     
    <%--alert("${param.aa}");--%>
    <%--alert("${param.url_target}");--%>
</script>
   <%-- 接收用户提交的目标地址 --%>
  <c:choose>
      <c:when test="${empty param.url_target}">
              <c:set var="url_target" value="<%=basePath%>"/>
      </c:when>
      <c:otherwise>
              <c:set var="url_target" value="${ param.url_target}"/>
      </c:otherwise>
  </c:choose>
<body>
<div id="layout">
  <jsp:include page="../include/nav.jsp"/>
  <div class="titleLogin"><a href="http://www.mmfoo.com" style="color:#FF0099;">首页</a> > 登录</div>
    <div class="beautyLlk_login">
        <div class="loginInfo">
            <div class="head"><img src="<%=basePath%>/images/login.jpg" width="180" height="29"></div>
            <div class="userLogin">
                <span>Email:</span><input type="text" name="email" id="email_login" ><br>
                <span>密　码:</span><input type="password" name="pswd" id="password"><br>
                <span>验证码:</span><input type="password" name="pswd" id="checkcoe"> <img
                            id="m_l15" src="<%=basePath%>/pages/checkcode.jsp" alt="" onClick="codechange()">
            </div>
            <%--<div class="userLogin_But"><a href="javascript:userlogin('${url_target}')">登录</a>   旧的登录机制，从哪个页面登录的就跳到哪个页面    --%>
            <div class="userLogin_But"><a href="javascript:userlogin('${url_target}')">登录</a>

                <div class="rem"><input type="checkbox" name="remBox" class="checkbox">记住状态 | <a href="<%=basePath%>/pages/reset_pswd.html" class="forget">忘记密码</a>
                </div>
            </div>
        </div>
    </div>
  <jsp:include page="../include/footer.jsp"/>
</div>
</div>
</body>
</html>
