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
    basePath=basePath.replace(":80","");
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

<c:choose>
    <c:when test="${empty param.usernav_left}">
        <c:set var="usernav_left" value="2"/>
    </c:when>
    <c:otherwise>
        <c:set var="usernav_left" value="${param.usernav_left}"/>
    </c:otherwise>
</c:choose>

<mfo:getUserById user_id="${user_id_userspace}"/>
<div class="body_left left">
    <script type="text/javascript">
        function FOLDMenu(id, onlyone) {
            if (!document.getElementById || !document.getElementsByTagName) {
                return false;
            }
            this.menu = document.getElementById(id);
            this.submenu = this.menu.getElementsByTagName("ul");
            this.speed = 3;
            this.time = 10;
            this.onlyone = onlyone == true ? onlyone : false;
            this.links = this.menu.getElementsByTagName("a");
        }
        FOLDMenu.prototype.init = function() {
            var mainInstance = this;
            for (var i = 0; i < this.submenu.length; i++) {
                this.submenu[i].getElementsByTagName("span")[0].onclick = function() {
                    mainInstance.toogleMenu(this.parentNode);
                };
            }
            for (var i = 0; i < this.links.length; i++) {
                this.links[i].onclick = function() {
                    this.className = "current";
                    mainInstance.removeCurrent(this);
                }
            }
        }
        FOLDMenu.prototype.removeCurrent = function(link) {
            for (var i = 0; i < this.links.length; i++) {
                if (this.links[i] != link) {
                    this.links[i].className = " ";
                }
            }
        }
        FOLDMenu.prototype.toogleMenu = function(submenu) {
            if (submenu.className == "open") {
                this.closeMenu(submenu);
            } else {
                this.openMenu(submenu);
            }
        }
        FOLDMenu.prototype.openMenu = function(submenu) {
            var fullHeight = submenu.getElementsByTagName("span")[0].offsetHeight;
            var links = submenu.getElementsByTagName("a");
            for (var i = 0; i < links.length; i++) {
                fullHeight += links[i].offsetHeight;
            }
            var moveBy = Math.round(this.speed * links.length);
            var mainInstance = this;
            var intId = setInterval(function() {
                var curHeight = submenu.offsetHeight;
                var newHeight = curHeight + moveBy;
                if (newHeight < fullHeight) {
                    submenu.style.height = newHeight + "px";
                } else {
                    clearInterval(intId);
                    submenu.style.height = "";
                    submenu.className = "open";
                }
            }, this.time);
            this.collapseOthers(submenu);
        }

        FOLDMenu.prototype.closeMenu = function(submenu) {
            var minHeight = submenu.getElementsByTagName("span")[0].offsetHeight;
            var moveBy = Math.round(this.speed * submenu.getElementsByTagName("a").length);
            var mainInstance = this;
            var intId = setInterval(function() {
                var curHeight = submenu.offsetHeight;
                var newHeight = curHeight - moveBy;
                if (newHeight > minHeight) {
                    submenu.style.height = newHeight + "px";
                } else {
                    clearInterval(intId);
                    submenu.style.height = "";
                    submenu.className = "";
                }
            }, this.time);
        }

        FOLDMenu.prototype.collapseOthers = function(submenu) {
            if (this.onlyone) {
                for (var i = 0; i < this.submenu.length; i++) {
                    if (this.submenu[i] != submenu) {
                        this.closeMenu(this.submenu[i]);
                    }
                }
            }
        }
    </script>
    <div id="foldmenu" class="foldmenu" style="float:left;">
        <ul  <c:choose>
            <c:when test="${usernav_left==1}">class="open"</c:when>
            <c:otherwise></c:otherwise>
        </c:choose> >
            <span <c:choose>
            <c:when test="${usernav_left==1}"> style="background-color:#ED008C"</c:when>
            <c:otherwise></c:otherwise>
        </c:choose>><img src="<%=basePath%>/images/icon_2.gif" width="15" height="16"/>  集结号</span>
            <li><a href="<%=basePath%>/myactive-${user_id_userspace}.html">我的集结号</a></li>
            <%--<li><a href="#">好友的集结号</a></li>--%>
        </ul>
        <ul  <c:choose>
            <c:when test="${usernav_left==2}">class="open"</c:when>
            <c:otherwise></c:otherwise>
        </c:choose> >
            <span <c:choose>
            <c:when test="${usernav_left==2}"> style="background-color:#ED008C"</c:when>
            <c:otherwise></c:otherwise>
        </c:choose>><img src="<%=basePath%>/images/icon_6.gif" width="16" height="16"/> 微博</span>
            <li><a href="<%=basePath%>/wb-${user_id_userspace}.html">@提到我的</a></li>
            <%--<li><a href="#">我的评论</a></li>--%>
        </ul>
      <%--  <ul <c:choose>
            <c:when test="${usernav_left==3}">class="open"</c:when>
            <c:otherwise></c:otherwise>
        </c:choose> >
            <span><img src="<%=basePath%>/images/icon_3.gif" width="16" height="14"/>  礼物</span>
            <li><a href="#">分类</a></li>
            <li><a href="#">分类</a></li>
            <li><a href="#">分类</a></li>
        </ul>--%>


        <c:choose>
            <c:when test="${sessionScope.islogin eq 'Y' }">
                <%-- 登录用户必须是空间的主人才看得见 --%>
                <c:choose>
                    <c:when test="${sessionScope.userbean.user_id==user_id_userspace}">
                        <%--  关注部分只有本人登陆才看得见------------------------- --%>

                        <ul  <c:choose>
                            <c:when test="${usernav_left==4}">class="open"</c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose> >
                            <mfo:GetLeftNumber user_id="${user_id_userspace}"/>
                            <span <c:choose>
            <c:when test="${usernav_left==4}"> style="background-color:#ED008C"</c:when>
            <c:otherwise></c:otherwise>
        </c:choose>><img src="<%=basePath%>/images/icon_5.gif" width="16" height="16"/>  关注</span>
                                <%-- 取所有我关注的人和关注我的人的总和  --%>
                            <li><a href="<%=basePath%>/fans-${user_id_userspace}-1.html">全部(${total})</a></li>
                                <%--  取我和他们之间相互关注的人总数 --%>
                            <li><a href="<%=basePath%>/fans-${user_id_userspace}-2.html">相互(${hxn})</a></li>
                                <%-- 取所有关注我的人总数 即我的粉丝总数  --%>
                            <li><a href="<%=basePath%>/fans-${user_id_userspace}-3.html">粉丝(${fanss})</a></li>
                        </ul>

                        <%--  -------------------------关注部分只有本人登陆才看得见 --%>
                    </c:when>
                    <c:otherwise>
                        <%--虽然登录了 但是登录者不是空间的主人--%>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise></c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${sessionScope.islogin eq 'Y' }">
                <%-- 登录用户必须是空间的主人才看得见 --%>
                <c:choose>
                    <c:when test="${sessionScope.userbean.user_id==user_id_userspace}">
                        <%--  私信部分只有本人登陆才看得见------------------------- --%>
                        <ul <c:choose>
                            <c:when test="${usernav_left==5}">class="open"</c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose> >
                            <span <c:choose>
            <c:when test="${usernav_left==5}"> style="background-color:#ED008C"</c:when>
            <c:otherwise></c:otherwise>
        </c:choose>><img src="<%=basePath%>/images/icon_4.gif" width="16" height="12"/>  私信</span>
                            <li><a href="<%=basePath%>/mymsg-${user_id_userspace}.html">我的私信</a></li>
                            <%--<li><a href="#">2分类</a></li>--%>
                        </ul>
                        <%--  -------------------------私信部分只有本人登陆才看得见 --%>
                    </c:when>
                    <c:otherwise>
                        <%--虽然登录了 但是登录者不是空间的主人--%>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise></c:otherwise>
        </c:choose>


        <script type="text/javascript">
            window.onload = function() {
                myMenu = new FOLDMenu("foldmenu", true);
                myMenu.init();
                myMenu2 = new FOLDMenu("foldmenu2");
                myMenu2.init();
            };
        </script>
    </div>
   <%-- <div class="mfb">你现在有 <span>0</span> 美分币 | <a href="javascript:alert('稍后充值');">充值</a></div>
    <div align="t_v f14px">总访问量 ${userbyid.see_cnt} 次</div>--%>
</div>