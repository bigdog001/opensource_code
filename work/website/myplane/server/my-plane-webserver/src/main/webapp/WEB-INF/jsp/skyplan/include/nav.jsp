<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header">
                  <div class="page-menu"> 
    <ul>
       <li<c:if test="${w eq 'index'}"> class="curr"</c:if>><c:choose> <c:when test="${w eq 'index'}"> <a href="#">首页</a> </c:when> <c:otherwise> <a href="./page_index.htm">首页</a> </c:otherwise> </c:choose></li>
       <li<c:if test="${w eq 'planes'}"> class="curr"</c:if>> <c:choose> <c:when test="${w eq 'planes'}"> <a href="#">飞行器</a> </c:when> <c:otherwise> <a href="./page_planes.htm">飞行器</a> </c:otherwise>  </c:choose> </li>
       <li<c:if test="${w eq 'customize'}"> class="curr"</c:if>> <c:choose> <c:when test="${w eq 'customize'}"> <a href="#">私人定制</a> </c:when> <c:otherwise> <a href="./page_customize.htm">私人定制</a> </c:otherwise>  </c:choose></li>
       <li<c:if test="${w eq 'pilot'}"> class="curr"</c:if>><c:choose> <c:when test="${w eq 'pilot'}"> <a href="#">飞行员计划</a> </c:when> <c:otherwise> <a href="./page_pilot.htm">飞行员计划</a> </c:otherwise>  </c:choose></li>
       <li<c:if test="${w eq 'trusteeship'}"> class="curr"</c:if>><c:choose> <c:when test="${w eq 'trusteeship'}"> <a href="#">飞机托管</a> </c:when> <c:otherwise> <a href="./page_trusteeship.htm">飞机托管</a> </c:otherwise>  </c:choose></li>                        
    </ul>
                </div>
                <div class="my-menu">
                    <div class="logo">
                        <a href="./" 
                           title="私人飞机俱乐部"><img src="./res/sky/logo.png" id="wangzhanlogo"><span id="wangzhanlogo_mp3"></span></a></div>
                    <div class="my-menu-list">
                        <ul>
                            <li class="my-menu-item meun-user">
                                <img src="./res/sky/meun-user.png"> 
                                <a id="loginhref" title="请登录" href="./page_login.htm">登录</a>,
                                <a  href="./page_registe.htm" title="">免费注册</a>
                            </li>
                            <li class="my-menu-item meun-tools dropdown">
                                <a href="http://wangzhan.360.cn/suggest#" id="navi_tools_dropdown" role="button" 
                                   onmouseover="javascript:$( & #39; #navi_tools_dropdown & #39; ).dropdown( & #39; toggle & #39; );" 
                                   class="dropdown-toggle" data-toggle="dropdown">
                                    <img src="./res/sky/meun-tools.png">关注我们</a> 
                                <ul class="dropdown-menu" style="-webkit-box-sizing: initial;-moz-box-sizing: initial;box-sizing: initial;margin-top:-5px;padding:0px;border:1px solid #0a85e8;background-color:#1b93f5;min-width:100%;text-align:left;width:100%;border-radius:0px;-webkit-box-shadow: none;box-shadow: none;background-clip: inherit;margin-left: -21px;left: inherit;" role="menu" aria-labelledby="drop3">
                                    <li role="presentation" style="border-bottom:1px solid #0a85e8;">
                                        <a style="padding:5px 25px;" role="menuitem" tabindex="-1" href="http://wangzhan.360.cn/news/download">工具下载</a>
                                    </li>
                                    <li role="presentation" style="border-bottom:1px solid #0a85e8;">
                                        <a style="padding:5px 25px;" role="menuitem" tabindex="-1" href="#" target="_blank">sina微博</a>
                                    </li>
                                    <li role="presentation" style="border-bottom:1px solid #0a85e8;">
                                        <a style="padding:5px 25px;" role="menuitem" tabindex="-1" href="#" target="_blank">腾讯微薄</a>
                                    </li>
                                </ul>
                            </li>


                        </ul>
                    </div>
                </div>
            </div>   