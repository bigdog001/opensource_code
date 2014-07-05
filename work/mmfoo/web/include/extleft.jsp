<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2011-5-15
  Time: 22:25:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<c:set value="${param.newsid}" var="newsid" scope="request"/>

<ul>
    <li  <c:choose>
        <c:when test="${newsid==1}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose> >
        <c:choose>
            <c:when test="${newsid==1}"><span>什么是美分</span></c:when>
            <c:otherwise><span><a href="<%=basePath%>/pages/ext-1.html">什么是美分</a></span></c:otherwise>
        </c:choose>


    </li>
    <li <c:choose>
        <c:when test="${newsid==2}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>>
        <c:choose>
            <c:when test="${newsid==2}"><span>美分团队</span></c:when>
            <c:otherwise><a href="<%=basePath%>/pages/ext-2.html">美分团队</a></c:otherwise>
        </c:choose>


    </li>
    <li <c:choose>
        <c:when test="${newsid==3}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>>
        <c:choose>
            <c:when test="${newsid==3}"><span>加入我们</span></c:when>
            <c:otherwise><a href="<%=basePath%>/pages/ext-3.html">加入我们</a></c:otherwise>
        </c:choose>


    </li>
    <li <c:choose>
        <c:when test="${newsid==4}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>>
        <c:choose>
            <c:when test="${newsid==4}"><span>市场与商业合作</span></c:when>
            <c:otherwise><a href="<%=basePath%>/pages/ext-4.html">市场与商业合作</a></c:otherwise>
        </c:choose>


    </li>
    <li <c:choose>
        <c:when test="${newsid==5}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>>
        <c:choose>
            <c:when test="${newsid==5}"><span>合作链接</span></c:when>
            <c:otherwise><a href="<%=basePath%>/pages/ext-5.html">合作链接</a></c:otherwise>
        </c:choose>

    </li>
    <li <c:choose>
        <c:when test="${newsid==6}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>>
        <c:choose>
            <c:when test="${newsid==6}"><span>联系方式</span></c:when>
            <c:otherwise><a href="<%=basePath%>/pages/ext-6.html">联系方式</a></c:otherwise>
        </c:choose>

    </li>
    <li <c:choose>
        <c:when test="${newsid==7}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>>
        <c:choose>
            <c:when test="${newsid==7}"><span>投资与资本</span></c:when>
            <c:otherwise><a href="<%=basePath%>/pages/ext-7.html">投资与资本</a></c:otherwise>
        </c:choose>

    </li>
    <li <c:choose>
        <c:when test="${newsid==8}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>>
        <c:choose>
            <c:when test="${newsid==8}"><span>投资与资本</span></c:when>
            <c:otherwise><a href="<%=basePath%>/pages/ext-8.html">在线客服</a></c:otherwise>
        </c:choose>

    </li>
    <li <c:choose>
        <c:when test="${newsid==9}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>>
        <c:choose>
            <c:when test="${newsid==9}"><span>防骗说明</span></c:when>
            <c:otherwise><a href="<%=basePath%>/pages/ext-9.html">防骗说明</a></c:otherwise>
        </c:choose>

    </li>
    <li <c:choose>
        <c:when test="${newsid==10}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>>
        <c:choose>
            <c:when test="${newsid==10}"><span>美分声明</span></c:when>
            <c:otherwise><a href="<%=basePath%>/pages/ext-10.html">美分声明</a></c:otherwise>
        </c:choose>

    </li>
    <li <c:choose>
        <c:when test="${newsid==11}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>>
        <c:choose>
            <c:when test="${newsid==11}"><span>隐私政策</span></c:when>
            <c:otherwise><a href="<%=basePath%>/pages/ext-11.html">隐私政策</a></c:otherwise>
        </c:choose>

    </li>
    <li <c:choose>
        <c:when test="${newsid==12}">class="current2"</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>>
        <c:choose>
            <c:when test="${newsid==12}"><span>使用条款</span></c:when>
            <c:otherwise><a href="<%=basePath%>/pages/ext-12.html">使用条款</a></c:otherwise>
        </c:choose>

    </li>
</ul>