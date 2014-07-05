<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>

<div id="top">
  <ul class="topNav">
    <li><a href="<%=basePath%>">美分首页</a></li>
    <li><a href="<%=basePath%>/pages/personalCenter.html">我的美分</a></li>
  </ul>
  <ul class="signLink">
    <li><a href="#">登录</a>|</li>
    <li><a href="/pages/join_registe.html">免费注册</a></li>
    <li>
      <FORM id="search-form" action="">
        <FIELDSET>
          <INPUT onblur="if(this.value==''){this.value='请输入搜索关键词'}" class="text" onfocus="if(this.value=='请输入搜索关键词'){this.value=''}"
value="请输入搜索关键词">
          <INPUT class="submit" type="submit" value="">
        </FIELDSET>
      </FORM>
    </li>
  </ul>
</div>