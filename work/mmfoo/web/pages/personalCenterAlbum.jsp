<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美分网——个人中心</title>
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/personalCenter_common.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/personalCenter.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/mostBeauty.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/ablumn.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/photo.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/photo.js"></script>
</head>

<body>
<div id="layout">
  <jsp:include page="../include/nav_user.jsp"/>
  <!--./top--> 
  <!--content begin-->
  <div id="content">
    <jsp:include page="../include/userleft.jsp"/>
    <!--right begin-->
    <div id="right">
      <h3 style="background: url(../images/albumIcon2.gif) no-repeat 1px 10px"><span style="padding-left: 20px">相册</span></h3>
      <div class="myAblum">
        <div class="importantes">
          <button class="funcB">上传照片</button>
        </div>
        <div class="clr"></div>
        <div class="ablumContent">
          <div class="item"> <a href="#"><img src="../images/1.jpg" alt="Cycliner" title="" width="180" height="180"/></a>
            <div class="caption"> <a href="http://www.sina.com.cn">进入相册</a>
              <p>相册说明</p>
            </div>
          </div>
          <div class="item"> <a href="#"><img src="../images/1.jpg" alt="Cycliner" title="" width="180" height="180"/></a>
            <div class="caption"> <a href="http://www.sina.com.cn">进入相册</a>
              <p>相册说明</p>
            </div>
          </div>
          <div class="item"> <a href="#"><img src="../images/1.jpg" alt="Cycliner" title="" width="180" height="180"/></a>
            <div class="caption"> <a href="http://www.sina.com.cn">进入相册</a>
              <p>相册说明</p>
            </div>
          </div>
          <div class="item"> <a href="#"><img src="../images/1.jpg" alt="Cycliner" title="" width="180" height="180"/></a>
            <div class="caption"> <a href="http://www.sina.com.cn">进入相册</a>
              <p>相册说明</p>
            </div>
          </div>
          <div class="item"> <a href="#"><img src="../images/1.jpg" alt="Cycliner" title="" width="180" height="180"/></a>
            <div class="caption"> <a href="http://www.sina.com.cn">进入相册</a>
              <p>相册说明</p>
            </div>
          </div>
          <div class="item"> <a href="#"><img src="../images/1.jpg" alt="Cycliner" title="" width="180" height="180"/></a>
            <div class="caption"> <a href="http://www.sina.com.cn">进入相册</a>
              <p>相册说明</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- footer -->
   <jsp:include page="../include/footer.jsp"/>
  </div>
</div>
</body>
</html>
