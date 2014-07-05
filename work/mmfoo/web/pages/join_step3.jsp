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
  <div class="step"><img src="images/step3.jpg" /></div>
  <div class="inputBox" style="border: 0px">

    <div class="buttonHolder"><button class="primaryAction" type="submit">保存并下一步</button></div>
  </fieldset>
</form>
  
  </div>
  
   <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
