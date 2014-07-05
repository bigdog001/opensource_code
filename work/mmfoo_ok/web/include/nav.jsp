<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    basePath=basePath.replace(":80","");
%>

<div id="top">
    <div class="logo"><a href="<%=basePath%>"><img src="<%=basePath%>/images/logo.jpg"/></a></div>
    <div class="signin">

        <c:choose>
            <c:when test="${sessionScope.islogin eq 'Y' }">
             <mfo:getUserById user_id="${userbean.user_id}"/>
                <a href="javascript:logout()">退出</a>|<a
                href="/user-${userbean.user_id}-1.html" <%--class="thickbox"--%>>欢迎您:${userbyid.nickname}</a>
            </c:when>
            <c:otherwise>
                <a href="javascript:var url_now=window.location.href;window.location='<%=basePath%>/regist.html?url_target='+url_now">免费注册</a>|<a
                href="javascript:var url_now=window.location.href;window.location='<%=basePath%>/pages/login.jsp?url_target='+url_now" data-reveal-id="myModal" class="">登录</a>
            </c:otherwise>
        </c:choose>
    </div>
    <div style="float:right; margin-top: 8px;">
    <script type="text/javascript" charset="utf-8">
(function(){
  var _w = 72 , _h = 16;
  var param = {
    url:location.href,
    type:'3',
    count:'1', /**是否显示分享数，1显示(可选)*/
    appkey:'', /**您申请的应用appkey,显示分享来源(可选)*/
    title:'', /**分享的文字内容(可选，默认为所在页面的title)*/
    pic:'', /**分享图片的路径(可选)*/
    ralateUid:'1986339403', /**关联用户的UID，分享微博会@该用户(可选)*/
    rnd:new Date().valueOf()
  }
  var temp = [];
  for( var p in param ){
    temp.push(p + '=' + encodeURIComponent( param[p] || '' ) )
  }
  document.write('<iframe allowTransparency="true" frameborder="0" scrolling="no" src="http://hits.sinajs.cn/A1/weiboshare.html?' + temp.join('&') + '" width="'+ _w+'" height="'+_h+'"></iframe>')
})()
</script>
</div>
</div>
<ul id="nav">
    <!--[if IE9]>
    <ul id="nav2"><![endif]-->
    <li><a href="<%=basePath%>" <c:choose>
        <c:when test="${param.navs==1}">class="current"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> >首页</a></li>
    <li><a href="<%=basePath%>/regist.html" <c:choose>
        <c:when test="${param.navs==2}">class="current"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> >新人报到</a></li>
    <li><a href="<%=basePath%>/rankf.html" <c:choose>
        <c:when test="${param.navs==3}">class="current"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> >美分最美女</a></li>
    <li><a href="<%=basePath%>/gallerynew.html" <c:choose>
        <c:when test="${param.navs==4}">class="current"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> >美女连连看</a></li>
    <li class="trail"><a href="<%=basePath%>/mmfoohd.html" <c:choose>
        <c:when test="${param.navs==5}">class="current"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> >美分活动</a></li>
</ul>
