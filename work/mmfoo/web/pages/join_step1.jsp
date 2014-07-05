<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美分网</title>
<jsp:include page="../include/header.jsp"/>
</head>
   <mfo:islogin/>
<body>
<div id="layout">
  <jsp:include page="../include/nav.jsp"/>
  <div class="step"><img src="images/step1.jpg" /></div>
<div class="inputBox" style="border: 0px">
    <form class="uniForm" action="#">
  <fieldset class="inlineLabels">
    <div class="ctrlHolder" style="margin-left:200px">
      <label>真实姓名：</label>
      <input class="textInput medium" value="" size="35" type="text" id="truename">
      <p class="formHint">请输入您的真实姓名.</p>
    </div>
  
    <div class="ctrlHolder" style="margin-left:200px">
      <label>出生日期：</label>
      <input class="textInput medium" value="" size="35" type="text" id="birthday">
      <p class="formHint">请选择你的出生日期.</p>
    </div>
  <div class="ctrlHolder" style="margin-left:200px">
      <label>身高</label>
      <input class="textInput thin" value="" id="height" size="35" type="text"><div style="float: left">CM</div>
      <p class="formHint">请填写你的身高.</p>
    </div>
    <div class="ctrlHolder" style="margin-left:200px">
      <label>学校：</label>
      <select class="medium" id="school">
        <option value="">大连海事大学</option>
        <option value="">东北财经大学</option>
        <option value="">大连理工大学</option>
      </select>
      <p class="formHint">请选择你的就读学校.</p>
    </div>
  
    <div class="ctrlHolder" style="margin-left:200px">
      <label>出生地：</label>
      <input class="textInput medium" value="" size="35" type="text" id="hometown">
      <p class="formHint">请填写你的家乡.</p>
    </div>
  
    <div class="ctrlHolder" style="margin-left:200px">
      <label>血型：</label>
      <select class="thin" id="blood">
        <option value="">A型</option>
        <option value="">B型</option>
        <option value="">AB型</option>
        <option value="">O型</option>
      </select>
      <p class="formHint">请选择你血型.</p>
    </div>
  
    <div class="ctrlHolder" style="margin-left:200px">
      <label>QQ号：</label>
      <input class="textInput medium" value="" size="35" type="text" id="qqnumber">
      <p class="formHint">请输入你的QQ号码.</p>
    </div>
  
    <div class="ctrlHolder" style="margin-left:200px">
      <label>手机号码：</label>
      <input class="textInput medium" value="" size="35" type="text" id="mobile">
      <p class="formHint">请输入你的手机号码方便联系.</p>
    </div>
  
    <div class="ctrlHolder" style="margin-left:200px">
      <label>电子邮箱：</label>
      <input class="textInput medium" value="${sessionScope.login_email}" size="35" type="text" id="email">
      <p class="formHint">请输入你的E-mail邮箱，我们会把最新动态和活动信息通过新闻信的形式发给你.</p>
    </div>
  
    <div class="ctrlHolder" style="margin-left:200px">
      <label >美女宣言：</label>
      <textarea cols="25" rows="25" id="declaration"></textarea>
      <p class="formHint">请填写你想要说的话，可以是你的爱好、性格、理想、心情、美丽经验等等，让大家更加了解美丽的你.</p>
    </div>
    <div class="buttonHolder"><button class="secondaryAction" type="submit" style="margin-left:300px"><%--保存并返回--%></button><button class="primaryAction" type="submit">保存并下一步</button></div>
  </fieldset>
</form>
  
  </div>
  
 <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
