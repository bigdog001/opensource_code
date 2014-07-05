<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2011-5-16
  Time: 22:51:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<div id="footer">
    <ul class="listFoot ml40">
       <li>关于</li>
        <li><a href="<%=basePath%>/pages/ext-1.html">什么是美分</a></li>
        <li><a href="<%=basePath%>/pages/ext-2.html">美分团队</a></li>
        <li><a href="<%=basePath%>/pages/ext-3.html">加入我们</a></li>
    </ul>
    <ul class="listFoot">
   <li>合作</li>
        <li><a href="<%=basePath%>/pages/ext-4.html">市场与商业合作</a></li>
        <li><a href="<%=basePath%>/pages/ext-5.html">合作链接</a></li>
        <li><a href="<%=basePath%>/pages/ext-6.html">联络方式</a></li>
        <li><a href="<%=basePath%>/pages/ext-7.html">投资与资本</a></li>
    </ul>
    <ul class="listFoot">
      <li>服务/帮助</li>
        <li><a href="<%=basePath%>/pages/ext-8.html">在线客服</a></li>
        <li><a href="<%=basePath%>/pages/ext-9.html">防骗说明</a></li>
    </ul>
    <ul class="listFoot mrBorder">
     <li>更多</li>
        <li><a href="<%=basePath%>/pages/ext-10.html">美分声明</a></li>
        <li><a href="<%=basePath%>/pages/ext-11.html">隐私政策</a></li>
        <li><a href="<%=basePath%>/pages/ext-12.html">使用条款</a></li>
      <li></li>
    </ul>
    <ul class="listFoot" style="width: 230px; float: left; margin-left: 40px;">
      <li>邮件订阅</li>
      <li>
        <FORM id="subscribe" action="">
            <FIELDSET>
              <INPUT onblur="if(this.value==''){this.value='请输入邮箱地址'}" class="text" onfocus="if(this.value=='请输入邮箱地址'){this.value=''}" value="请输入邮箱地址">
              <INPUT class="submit" type="submit" value="订阅">
            </FIELDSET>
          </FORM>
      </li>
      <li></li>
    </ul>
  </div>
  <div class="trailor"><span>&copy; 2011</span><a href="#">美分网</a> <span>(MMFOO.COM), All rights reserved. </span>辽ICP证090052号</div>
</div>
 