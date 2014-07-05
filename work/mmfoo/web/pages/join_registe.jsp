<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
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
  
    <form class="uniForm" action="#">
  <fieldset class="inlineLabels">
  <div class="buttonHolder tttt" style="border:0px;">快速注册：</div>
    <div class="ctrlHolder" style="width: 350px;">
      <label >输入邮箱地址：</label>
      <input class="textInput medium" value="" size="35" type="text" alt="您将使用此邮箱登陆" title="您将使用此邮箱登陆" id="email"<%-- onblur="userCheck()"--%>><%--<img src="../images/loading.gif" alt="">--%> <a href="javascript:userCheck()" id="mailchk">验证</a>
       <br> <br> <label >输入验证码：</label>
      <input   class="textInput medium" value="" size="35" type="text" onkeyup="codecheck()" id="codevalue"><img src="checkcode.jsp" alt="点击更换验证码" id="m_l15" onclick="codechange()" title="点击更换验证码" >
       <p class="formHint" id="img_ch">.</p>                                                                                 
    </div>
  
    <div class="buttonHolder" style="border: 0px;"><button class="secondaryAction" type=""><%--保存并返回--%></button><button id="savenext" class="primaryAction" style="background:#777" onclick="save_next()">保存并下一步</button></div>
  </fieldset>
</form>
  
  </div>
  <div class="love">
     <a href="<c:choose>
        <c:when test="${sessionScope.islogin eq 'Y' }">/pages/join_step1-1-2.html
        </c:when>
        <c:otherwise>javascript:alert('尚未登录,请登录!') ;
        </c:otherwise>
    </c:choose>
    "><img src="../images/btn1.jpg" /></a>
    <a href="<c:choose>
        <c:when test="${sessionScope.islogin eq 'Y' }">
         /pages/join_step1-2-2.html
        </c:when>
        <c:otherwise>javascript:alert('尚未登录,请登录!') ;
        </c:otherwise>
    </c:choose>
    "><img src="../images/btn2.jpg" /></a>
  </div>
  
   <jsp:include page="../include/footer.jsp"/>
</div>
 
</body>
</html>
