<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
    //活动列表页面，取出所有的活动信息
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    basePath=basePath.replace(":80","");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美分网--用户注册</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_common.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/menu.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_index3.css">
<link  href="<%=basePath%>/css/validate.css" rel="stylesheet" type="text/css" />
<%--<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.2.min.js"></script>--%>
<%--<script type="text/javascript" src="<%=basePath%>/js/validate.pack.js"></script>--%>
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>

</head>
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
  <div class="titleLogin"><a href="http://www.mmfoo.com" style="color:#FF0099;">首页</a> > 注册</div>
  <div class="beautyTop">
    <img src="<%=basePath%>/images/slogan.jpg" class="slogan" />
    <form name="validateForm" action="" method="post">
    <div class="login">
      <ul>
        <li><img src="<%=basePath%>/images/loginInfo.jpg" /></li>
      </ul>
      <ul>
        <li class="tl">昵称：</li>
        <li class="tr"><input name="flightno" type="text" id="nickname" onblur="javascript:var nickname=$('#nickname').val();if(nickname.length<3){$('#nickname_').html('昵称必须大于3个字符'); return;}else{$('#nickname_').html('');return;}" />  <span name="easyTip" id="nickname_" style="color:red">   </span></li>
      </ul>
      <ul>
        <li class="tl">登陆邮箱：</li>
        <li class="tr"><input name="flightno" type="text" id="email" onblur="javascript:var email=$('#email').val();if(!emailCheck(email)){$('#email_').html('email格式错误'); return;}else{$('#email_').html('');return;}" />  <span name="easyTip" id="email_" style="color:red">  </span></li>
      </ul>
      <ul>
        <li class="tl">密码：</li>
        <li class="tr"><input name="flightno" type="password" id="password" onblur="javascript:var password=$('#password').val();if(password.length<6){$('#password_').html('密码位数必须大于6'); return;}else{$('#password_').html('');return;}" />  <span name="easyTip" id="password_" style="color:red">  </span></li>
      </ul>
      <ul>
        <li class="tl">确认密码：</li>
        <li class="tr"><input name="flightno" type="password" id="repassword"  />  <span name="easyTip" id="repassword_" style="color:red"> </span></li>
      </ul>
      <ul>
        <li class="tl">验证码：</li>
        <li class="tr"><input name="flightno" type="text" class="codd" id="checkcoe" /> <img
                            id="m_l15" src="<%=basePath%>/pages/checkcode.jsp" alt="" onclick="codechange()"> </li>
      </ul>
      <ul>
        <li class="tl"><input type="button" class="submitLoginregist" onclick="userregist('${url_target}')" style=" background:url(<%=basePath%>/images/submit_login.jpg); width: 111px; height: 27px; cursor: pointer; margin-top: 20px;"></li>
        <li class="tr"><input name="flightno" type="checkbox" class="agree" id="agreeid"/>  <span>已经阅读并同意<a href="#">《美分网服务条款》</a></span></li>
      </ul>
    </div>
    </form>
  
</div>
  <jsp:include page="../include/footer.jsp"/>
  </div>
</body>
</html>
