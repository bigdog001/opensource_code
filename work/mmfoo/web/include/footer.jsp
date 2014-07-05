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
        <li>微博关注：</li>
        <li><a href="http://weibo.com/mmfoo" target="_blank"><img src="<%=basePath%>/images/sina.jpg"/></a></li>
    </ul>
</div>
<div class="trailor"><span>&copy; 2011</span><a href="<%=basePath%>">美分网</a> <span>(MMFOO.COM), All rights reserved. </span>辽ICP证090052号
</div>