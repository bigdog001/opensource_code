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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><c:choose>
        <c:when test="${empty userbean}"></c:when>
        <c:otherwise>${userbean.nickname} 个人网站</c:otherwise>
    </c:choose>美分网MMFOO |用户中心</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <meta name="GENERATOR" content="Macromedia Dreamweaver MX"/>
    <meta http-equiv="Pics-label"
          Contect="(PICS－1.1'http://www.rsac.org/ratingsv01.html' I gen comment 'RSACi North America Sever' by 'inet@microsoft.com' for 'http://www.microsoft.com' on '1997.06.30T14:21－0500' r(n0 s0 v0 l0))"/>
    <meta name="Keywords" content="关键字"/>
    <meta name="Description" content="描述"/>
    <meta name="Robots" content="all"/>
    <meta http-equiv="MSThemeCompatible" content="Yes"/>
    <meta name="MSSmartTagsPreventParsing" content="TRUE"/>
    <link href="favicon.ico" rel="shortcut icon"/>
    <link href="<%=basePath%>/css/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/slide.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
    <script type="text/javascript">
        function tab(a, b, c) {
            for (i = 1; i <= b; i++) {
                if (c == i) {
                    // 判断选择模块
                    document.getElementById(a + "_mo_" + i).style.display = "block";  // 显示模块内容
                    document.getElementById(a + "_to_" + i).className = "no";   // 改变菜单为选中样式
                }
                else {
                    // 没有选择的模块
                    document.getElementById(a + "_mo_" + i).style.display = "none"; // 隐藏没有选择的模块
                    document.getElementById(a + "_to_" + i).className = "q";  // 清空没有选择的菜单样式
                }
            }
        }
    </script>
<script>

</script>
</head>

<body>
<jsp:include page="../include/user_space_nav.jsp"/>
<c:choose>
    <c:when test="${empty param.user_id}">
        <c:set var="user_id_userspace" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="user_id_userspace" value="${param.user_id}"/>
    </c:otherwise>
</c:choose>
<div class="w994">

    <jsp:include page="../include/user_space_left.jsp"/>
    <div class="body_right left">

        <div class="newsren right">
            <dl>
                <dt><strong>新人推荐</strong></dt>
                <%--<dd><a href="#">更多</a></dd>--%>
            </dl>
            <script type="text/javascript" src="js/jquery.js"></script>
            <script type="text/javascript">
                $(document).ready(function() {

                    $(".pane .delete").click(function() {
                        $(this).parents(".pane").animate({ opacity: 'hide' }, "slow");
                    });

                });
            </script>
            <ul>
                <%--以时间顺序取所有的新人--%>
                <mfo:getusertop page="1" size="5"/>
                <c:forEach var="usersingle" items="${usetop}">
                    <li class="pane">
                        <div class="box_1"><a href="<%=basePath%>/user-${usersingle.user_id}-1.html"><img src="<%=basePath%>/images/thumb/${usersingle.userthumb}" width="49" height="52"/></a></div>
                        <div class="box_2">
                            <span>${usersingle.truename}</span>
                             <c:choose>
            <c:when test="${sessionScope.islogin eq 'Y' }">
                <%-- 自己不能关注自己--%>
                  <c:choose>
                       <c:when test="${usersingle.email eq userbean.email}">
                            <a href="javascript:alert('不能关注自己！')"><img src="<%=basePath%>/images/concern.gif" width="60" height="22"/></a>
                      </c:when>
                      <c:otherwise>
                           <a href="javascript:see_user('${userbean.user_id}','${usersingle.user_id}')"><img src="<%=basePath%>/images/concern.gif" width="60" height="22"/></a>
                      </c:otherwise>
                  </c:choose>
            </c:when>
            <c:otherwise>
                 <a href="javascript:alert('请先登陆！');  window.location='<%=basePath%>/pages/login.jsp?url_target='+window.location.href "><img src="<%=basePath%>/images/concern.gif" width="60" height="22"/></a>
            </c:otherwise>
        </c:choose>
                        </div>
                        <img src="images/btn-delete.gif" alt="delete" class="delete"/>
                    </li>
                </c:forEach>

            </ul>
        </div>

        <div class="tab_menu left">
            <ul class="tab">
                <%--<li id="tab_to_1" class="no"><a href="#">全部微博</a></li>--%>
<%--<li id="tab_to_2"><a href="#">我的微博</a></li>--%>
                <li id="tab_to_1" class="no"><a href="#">TA的微博</a></li>
                <%--<li id="tab_to_2"><a href="#" onmouseover="tab('tab',4,2)">我的微博</a></li>--%>
            </ul>
        </div>
        <div class="tab_con left">

            <div class="mvxxs" id="tab_mo_1">
                <%--按时间顺序取出所有的微博数据--%>
                <mfo:getAllShortMessage page="1" size="10" user_id="${user_id_userspace}"/>
                    <c:forEach items="${allshortmessage}" var="massage_all">
                         <dl>
                             <mfo:getUserByIdsingle user_id="${massage_all.user_id}"/>
                    <dt><a href="<%=basePath%>/user-${massage_all.user_id}-1.html"><img src="<%=basePath%>/images/thumb/${userbyidsingle.userthumb}" width="45" height="52"/></a></dt>
                    <dd><a href="<%=basePath%>/user-${massage_all.user_id}-1.html">${userbyidsingle.nickname}</a> <span><%--MVP--%></span>:${massage_all.messagecontent}<br/>

                      <%--  <p><a href="#"><img src="images/meinv6.jpg"/></a></p>

                        <div class="lzzf">
                            <div class="laizi left">5月8日 11:21 来自美空微博</div>
                            <div class="zfpl right"><a href="#">转发</a>(2)&nbsp;&nbsp;&nbsp;&nbsp;<a href="#">评论</a>(3)
                            </div>
                        </div>--%> 
                    </dd>
                </dl>
                    </c:forEach>


            </div>

            <div class="mvxxs" id="tab_mo_2" style="display:none">
                <%--按时间顺序将我的所有微博取出来--%>
                <mfo:get_shortmessage_byuser_id page="1" size="5" user_id="${user_id_userspace}"/>
                    <c:forEach var="message" items="${getshortmessagebyemail}">
                            <dl>
                    <dt><a href="<%=basePath%>/user-${userbyid.user_id}-1.html"><img src="<%=basePath%>/images/thumb/${userbyid.userthumb}" width="49" height="52"/></a></dt>
                    <dd><a href="<%=basePath%>/user-${user_id_userspace}-1.html">${userbyid.truename}</a> <span><%--MVP--%></span>:${message.messagecontent}.<br/>

                       <%-- <p><a href="#"><img src="images/meinv6.jpg"/></a></p>

                        <div class="lzzf">
                            <div class="laizi left">5月30日 11:21 来自美空微博</div>
                            <div class="zfpl right"><a href="#">转发</a>(2)&nbsp;&nbsp;&nbsp;&nbsp;<a href="#">评论</a>(3)
                            </div>
                        </div>--%>
                    </dd>
                </dl>
                    </c:forEach>

            </div>
        </div>
    </div>
</div>

<div id="foot_x">
<jsp:include page="../include/footer.jsp"/>
</div>

</body>
</html>
