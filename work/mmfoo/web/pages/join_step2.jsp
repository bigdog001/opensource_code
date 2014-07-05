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
  <div class="step"><img src="../images/step2.jpg" /></div>
  <div class="inputBox" style="border: 0px">
    <h4>设置我的头像</h4>
    <h6>请选择一个新照片进行上传编辑。仅支持JPG、GIF、PNG格式图片，尺寸162像素x172像素，且小于5M。</h6>
    <div class="uploadAvatar">
      <div class="uploadFlash"><!--这里放调用flash代码--></div>
      <div class="uploaded">
        <h5>您上传的头像会自动生成3种尺寸。</h5>
        <div class="avatarA">
          <img src="images/avatar_big.jpg" />
          <h6>大尺寸头像162x172像素</h6>
        </div>
        <!--这里你看这个中尺寸是否需要，如果全站没有使用到中尺寸的，就不用这个尺寸显示了-->
        <div class="avatarB">
          <img src="images/avatar_samll.jpg" />
          <h6>中尺寸头像像素(自动生成)</h6>
        </div>
        <!---->
        <div class="avatarB">
          <img src="images/avatar_samll.jpg" />
          <h6>小尺寸头像49x52像素(自动生成)</h6>
        </div>
      </div>
    </div>
    <h6>提示：头像保存后，您可能需要刷新一下本页面(按F5键)，才能查看最新的头像效果。</h6>
    <div class="buttonHolder"><button class="primaryAction" type="submit">保存并下一步</button></div>
  </fieldset>
</form>
  
  </div>
  
  <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
