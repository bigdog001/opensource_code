<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--<%@ taglib prefix="mfo" uri="mmfoo" %>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美分网</title>
  <jsp:include page="../include/header.jsp"/>
</head>
          
<body>
<div id="layout">
  <jsp:include page="../include/nav.jsp"/>
  <div class="inputBox" style="margin-left: 0px; float: left; width: 60%;">

       <c:choose>
        <c:when test="${empty  sessionScope.login_email }">
            <c:redirect url="/"/>
                <%--<mfo:islogin email="${sessionScope.login_email}"></mfo:islogin>--%>
        </c:when>
        <c:otherwise>
            <h3 class="tttt">欢迎你加入美分网！分享美丽，分享快乐！</h3>
    <p style="line-height: 24px; padding-right: 20px;">我们已经发了一份邮件到你的电子邮箱（<a href="mailto:${sessionScope.login_email}">${sessionScope.login_email}</a>）中，里面包含了你的美分网用户名、密码等信息，请妥善保管好！<br />
    <strong>现在你可以选择右侧的活动页面进行报名！</strong>
    </p>

        </c:otherwise>
    </c:choose>
 

  
  </div>
  <div class="love">
    <a href="
    <c:choose>
        <c:when test="${sessionScope.islogin eq 'Y' }">
          /pages/join_step1-1.html
        </c:when>
        <c:otherwise>
         javascript:alert('尚未登录,请登录!') ;$('#myModal').click();return
        </c:otherwise>
    </c:choose>
    "><img src="../images/btn1.jpg" /></a>
    <a href="
        <c:choose>
        <c:when test="${sessionScope.islogin eq 'Y' }">
         /pages/join_step1-2.html
        </c:when>
        <c:otherwise>
         javascript:alert('尚未登录,请登录!') ;$('#myModal').click();return
        </c:otherwise>
    </c:choose>
    "><img src="../images/btn2.jpg" /></a>
  </div>
  

 <jsp:include page="../include/footer_mail.jsp"/>
</body>
</html>
