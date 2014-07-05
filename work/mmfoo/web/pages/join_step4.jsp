<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
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
  <div class="step"><img src="images/step4.jpg" /></div>
  <div class="uploadFinish">
    <h3>恭喜你！你已经成功参加了<a href="#">大连美女微博控</a>的活动。</h3>
    <div class="avatarBook">
      <div class="avatar"><img src="images/avatar_big.jpg" /></div>
      <ul class="info">
        <li class="bigname">周瑜同</li>
        <li>21岁</li>
        <li>大连海事大学</li>
        <li><a href="#">&gt;进入我的个人中心</a></li>
      </ul>
    </div>
  </div>
  
    <jsp:include page="../include/footer.jsp"/>
</div>

</body>
</html>
