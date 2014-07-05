<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
 <mfo:islogin/>
 <div class="topBar">
      <div class="logoP"><a href="<%=basePath%>"><img src="<%=basePath%>/images/logo.jpg" width="125" /></a></div>
      <ul>
        <li><a href="#">我的个人中心</a></li>
        <li><a href="<%=basePath%>/pages/personal_album-${userbean.user_id}.html">我的美分主页</a></li>
      </ul>
      <div class="sig"> <a href="<%=basePath%>">返回美分首页</a>
            <c:choose>
            <c:when test="${sessionScope.islogin eq 'Y' }">
                |<a href="javascript:logout(window.location.href)">退出</a>
            </c:when>
            <c:otherwise>
             |<a
                href="login.html?height=280;width=423" data-reveal-id="myModal" class="thickbox">登录</a>   
            </c:otherwise>
        </c:choose>


      </div>
      <FORM id="search-form" action="">
        <FIELDSET>
          <INPUT onblur="if(this.value==''){this.value='请输入搜索关键词'}" class="text" onfocus="if(this.value=='请输入搜索关键词'){this.value=''}"
value="请输入搜索关键词">
          <INPUT class="submit" type="submit" value="">
        </FIELDSET>
      </FORM>
  </div>
