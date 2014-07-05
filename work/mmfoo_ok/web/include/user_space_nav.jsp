<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2011-6-4
  Time: 9:26:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<fmt:setLocale value="zh"/>
<%
    //活动列表页面，取出所有的活动信息
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    basePath = basePath.replace(":80", "");
%>

<%--根据用户id取此用户所有的资料--%>
<c:choose>
    <c:when test="${empty param.user_id}">
        <c:set var="user_id_userspace" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="user_id_userspace" value="${param.user_id}"/>
    </c:otherwise>
</c:choose>
<mfo:UpdateSpace_see_cnt user_id="${user_id_userspace}"/>
<mfo:getUserById user_id="${user_id_userspace}"/>

<div id="topx">
    <div class="w994">
        <div class="logo left"><a href="<%=basePath%>" title="美分网"><img src="<%=basePath%>/images/logo.gif" width="115"
                                                                        height="41"
                                                                        alt="美分网"/></a></div>
        <div class="mainmenu_ss left">
            <div class="mainmenu left">
                <ul>

                    <c:choose>
                        <c:when test="${sessionScope.islogin eq 'Y' }">
                            <%--等用户还要判断登录的用户是否为空间所属者本人--%>

                            <li><a href="<%=basePath%>/user-${userbean.user_id}-4.html"
                                    <c:choose>
                                        <c:when test="${param.navs==4}">class="aurr_"</c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                    >我的美分网</a></li>


                        </c:when>
                        <c:otherwise>
                            <%--没有登录--%>

                        </c:otherwise>
                    </c:choose>


                    <li><a href="<%=basePath%>/user-${user_id_userspace}-1.html"

                            <c:choose>
                                <c:when test="${param.navs==1}">class="aurr_"</c:when>
                                <c:otherwise></c:otherwise>
                            </c:choose>
                            >首页</a></li>
                    <li><a href="<%=basePath%>/userfile-${user_id_userspace}-2.html"
                            <c:choose>
                                <c:when test="${param.navs==2}">class="aurr_"</c:when>
                                <c:otherwise></c:otherwise>
                            </c:choose>>档案</a></li>
                    <li><a href="<%=basePath%>/usershow-${user_id_userspace}-3.html"
                            <c:choose>
                                <c:when test="${param.navs==3}">class="aurr_"</c:when>
                                <c:otherwise></c:otherwise>
                            </c:choose>>美分展示</a></li>
                </ul>
            </div>
            <div class="topsearch right">
                <SCRIPT type=text/javascript>

                    <!--
                    function menuFix() {
                        var sfEls = document.getElementById("nav").getElementsByTagName("li");
                        for (var i = 0; i < sfEls.length; i++) {
                            sfEls[i].onmouseover = function() {
                                this.className += (this.className.length > 0 ? " " : "") + "sfhover";
                            }
                            sfEls[i].onMouseDown = function() {
                                this.className += (this.className.length > 0 ? " " : "") + "sfhover";
                            }
                            sfEls[i].onMouseUp = function() {
                                this.className += (this.className.length > 0 ? " " : "") + "sfhover";
                            }
                            sfEls[i].onmouseout = function() {
                                this.className = this.className.replace(new RegExp("( ?|^)sfhover\\b"),
                                        "");
                            }
                        }
                    }
                    window.onload = menuFix;
                    //--></SCRIPT>

                <c:choose>
                    <c:when test="${sessionScope.islogin eq 'Y' }">
                        <%--等用户还要判断登录的用户是否为空间所属者本人--%>
                        <c:choose>
                            <c:when test="${sessionScope.userbean.user_id==user_id_userspace}">
                                <div class="acc left">
                                    <ul id=nav>
                                        <li style="background:url(<%=basePath%>/images/icon_1.gif) right no-repeat ;"><a
                                                href="#">账号</a>
                                            <ul style=" padding-top:5px;">
                                                <li><img
                                                        src="<%=basePath%>/images/<c:choose><c:when test="${empty userbyid.userthumb}">thumb/avatar_samll.jpg</c:when><c:otherwise>${userbyid.userthumb}</c:otherwise></c:choose>"
                                                        width="49" height="52"
                                                        align="left"
                                                        style=" padding-right:5px;"/>
                                                    <span>${userbyid.truename}</span></li>
                                                <li><a href="<%=basePath%>/userfile-${user_id_userspace}-2.html"
                                                       style="border:none">账号设置</a></li>
                                                <li><a href="javascript:logout()"
                                                       style="border:none">退出账号</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <%--虽然登录了 但是登录者不是空间的主人--%>
                            </c:otherwise>
                        </c:choose>

                    </c:when>
                    <c:otherwise>
                        <%--没有登录--%>
                        <div class="acc left">
                            <ul>
                                <li>
                                    <a href="javascript:loginit()">登陆</a>
                                </li>
                            </ul>

                        </div>
                        <script type="text/javascript">
                            function loginit() {
                                var url_now = window.location.href;
                                window.location = '<%=basePath%>/pages/login.jsp?url_target=' + url_now;
                            }
                        </script>
                    </c:otherwise>
                </c:choose>

                <form action="<%=basePath%>/user/user_UserSearche.action" method="post" name="mmfoosearch">
                    <div class="topss left">搜索：
                        <input type="text" name="truename" value="找MM、找活动" onfocus="if(value=='找MM、找活动'){value='${param.truename}'}" onblur="if(value==''){value='找MM、找活动';}"/><input type="image" class="submit"
                                                                                             value="搜索"
                                                                                             src="<%=basePath%>/images/submit_q.gif"
                                                                                             onclick="javascript:document.mmfoosearch.submit()"/>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>

<div class="w994">
    <div class="peo left">
        <dl>
            <dt><a href="#" title="#"><img
                    src="<%=basePath%>/images/<c:choose><c:when test="${empty userbyid.userimg}">avatar_big.jpg</c:when><c:otherwise>upload/${userbyid.userimg}</c:otherwise></c:choose>"
                    alt="#"/></a>

                <c:choose>
                    <c:when test="${islogin eq 'Y' }">
                        <%--等用户还要判断登录的用户是否为空间所属者本人--%>
                        <c:choose>
                            <c:when test="${sessionScope.userbean.user_id==user_id_userspace}">
                                <a href="/pages/beautyLLk_sc.jsp?imgchanel=A"
                                   style=" color:#EC008C; font-size:12px; font-family:'宋体';height:32px;line-height:32px;margin-left:35px;">修改我的头像</a>
                            </c:when>
                            <c:otherwise>
                                <%--虽然登录了 但是登录者不是空间的主人--%>
                            </c:otherwise>
                        </c:choose>

                    </c:when>
                    <c:otherwise>
                        <%--没有登录--%>

                    </c:otherwise>
                </c:choose>

            </dt>
            <dd><strong>${userbyid.truename}</strong> <i><%--在线--%></i>
                <c:choose>
                    <c:when test="${sessionScope.islogin eq 'Y' }">
                        <%--等用户还要判断登录的用户是否为空间所属者本人--%>
                        <c:choose>
                            <c:when test="${userbean.user_id==user_id_userspace}">
								<span style="color:#333333; font-size:14px; font-weight:normal">${userbyid.nickname}</span>
                                <a href="<%=basePath%>/userfileedite-${userbean.user_id}.html"<%-- onclick="testMessageBoxName(event);"--%>
                                   style="color:#EC008C;margin-left:16px; font-family:'宋体'">&nbsp; 编辑昵称</a>
                            </c:when>
                            <c:otherwise>
                                <%--虽然登录了 但是登录者不是空间的主人--%>
                            </c:otherwise>
                        </c:choose>

                    </c:when>
                    <c:otherwise>
                        <%--没有登录--%>

                    </c:otherwise>
                </c:choose>

            </dd>
            <c:choose>
                <c:when test="${islogin eq 'Y' }">
                    <%--等用户还要判断登录的用户是否为空间所属者本人--%>
                    <c:choose>
                        <c:when test="${sessionScope.userbean.user_id==user_id_userspace}">
                            <dd><%=basePath%>/user-${userbyid.user_id}-1.html<a
                                    style=" color:#EC008C;font-family:'宋体';margin-left:16px;" href="#"
                                    onclick="testMessageBoxWeb(event);">个性化域名</a></dd>
                        </c:when>
                        <c:otherwise>
                            <%--虽然登录了 但是登录者不是空间的主人--%>
                        </c:otherwise>
                    </c:choose>

                </c:when>
                <c:otherwise>
                    <%--没有登录--%>

                </c:otherwise>
            </c:choose>
            <dd class="txt" style=" height:0; line-height:0">${userbyid.city}</dd>
            <dd class="txt">参加的活动</dd>
            <dd class="txt ActionC">
                <%--  查出某人所参加的所有活动 --%>
                <mfo:GetUserHds size="5" page="1" user_id="${userbyid.user_id}"/>
                <c:choose>
                    <c:when test="${fn:length(userhds)>0}">
                        <c:forEach var="userhd" items="${userhds}">
                            <img src="<%=basePath%>/images/upload/${userhd.hd_thumb}" width="32" height="32"
                                 alt="${userhd.hdabstract}" title="${userhd.hdabstract}"/>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        未参加任何活动
                    </c:otherwise>
                </c:choose>

                <%--<img src="<%=basePath%>/images/none.gif" width="32" height="32"/>--%>
                <%--<img src="<%=basePath%>/images/none.gif" width="32" height="32"/>--%>
                <%--<img src="<%=basePath%>/images/none.gif" width="32" height="32"/>--%>
            </dd>
            <dd>
                <c:choose>
                <c:when test="${sessionScope.islogin eq 'Y' }">
                    <%-- 自己不能关注自己--%>
                <c:choose>
                <c:when test="${userbyid.email eq userbean.email}"> <a
                    href="javascript:javascript:alert('不能关注自己！')"><img src="images/icon1.jpg"/></a> </c:when>
                <c:otherwise>
                <a href="javascript:javascript:see_user('${userbean.user_id}','${userbyid.user_id}')"><img
                        src="images/icon1.jpg"/></a>
                </c:otherwise>

                </c:choose>
                </c:when>
                <c:otherwise>
                <a href="javascript:javascript:alert('请先登陆！');window.location='<%=basePath%>/pages/login.jsp?url_target='+window.location.href"><img
                        src="images/icon1.jpg"/></a>
                </c:otherwise>
                </c:choose>

                <%--短信功能选注释--%>
                <%--  <a href="#"><img src="images/icon2.jpg"/></a></dd>--%>
                 <c:choose>
                    <c:when test="${islogin eq 'Y' }">
                        <%--等用户还要判断登录的用户是否为空间所属者本人--%>
                        <c:choose>
                            <c:when test="${sessionScope.userbean.user_id==user_id_userspace}">
                                 <a href="#" onclick="testMessageBox(event,'${userbyid.email}');"><img src="images/invite.gif"/></a>
                            </c:when>
                            <c:otherwise>
                                <%--虽然登录了 但是登录者不是空间的主人--%>
                            </c:otherwise>
                        </c:choose>

                    </c:when>
                    <c:otherwise>
                        <%--没有登录--%>

                    </c:otherwise>
                </c:choose>
        </dl>
    </div>
    <div class="peo_concern right">
        <ul>
            <li style="border-right:1px #ccc solid">
                <strong>${userbyid.point_cnt}</strong><span>粉丝</span></li>
            <li style="border-right:1px #ccc solid">
                <strong>
                    <%--取我关注的人的总数--%>
                    ${i_see}
                </strong><span>关注</span></li>
            <li><strong>
                <%--取我发表的微波的总数--%>
                ${message_total}
            </strong><span><a href="<%=basePath%>/wb-${user_id_userspace}.html">微博</a></span></li>
        </ul>
    </div>
</div>