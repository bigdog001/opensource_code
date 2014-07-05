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
<c:choose>
    <c:when test="${empty param.user_id}">
        <c:set var="user_id_userspace" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="user_id_userspace" value="${param.user_id}"/>
    </c:otherwise>
</c:choose>
<%-- 消息是发给谁的？ --%>
<c:choose>
    <c:when test="${empty param.user_id_to}">
        <c:set var="user_id_to" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="user_id_to" value="${param.user_id_to}"/>
    </c:otherwise>
</c:choose>
<%--  所对应消息的pmid --%>
<c:choose>
    <c:when test="${empty param.msg_id}">
        <c:set var="msg_id" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="msg_id" value="${param.msg_id}"/>
    </c:otherwise>
</c:choose>
<%--  找出此消息将要投递到的用户的用户对象 --%>
<mfo:getUserById user_id="${user_id_to}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><c:choose>
        <c:when test="${empty userbean}"></c:when>
        <c:otherwise>${userbean.nickname} 个人网站</c:otherwise>
    </c:choose>美分网MMFOO |私信</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <meta name="GENERATOR" content="Macromedia Dreamweaver MX"/>
    <meta http-equiv="Pics-label"
          Contect="(PICS－1.1'http://www.rsac.org/ratingsv01.html' I gen comment 'RSACi North America Sever' by 'inet@microsoft.com' for 'http://www.microsoft.com' on '1997.06.30T14:21－0500' r(n0 s0 v0 l0))"/>

    <meta name="Robots" content="all"/>
    <meta http-equiv="MSThemeCompatible" content="Yes"/>
    <meta name="MSSmartTagsPreventParsing" content="TRUE"/>
    <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
    <link href="<%=basePath%>/css/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <%--<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.2.min.js"></script>--%>
    <%--<script type="text/javascript" src="<%=basePath%>/js/jquery.easing.1.3.js"></script>--%>
    <script type="text/javascript" src="<%=basePath%>/js/slide.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>

</head>

<body>
<jsp:include page="../include/user_space_nav.jsp"/>

<div class="w994">
    <jsp:include page="../include/user_space_left.jsp"/>
    <%--  此处消息必须要在本人登陆后才看得见  --%>
    <c:choose>
    <c:when test="${sessionScope.islogin eq 'Y' }">
    <c:choose>
    <c:when test="${user_id_userspace eq userbean.user_id}">
        <%--    登陆后才看得见 ----------------------------------- --%>

    <div class="body_right left">
        <div class="fsss">
            <font class="ec008c"><a href="<%=basePath%>/mymsg-${user_id_userspace}.html">返回所有私信</a></font></div>
        <div class="mf_pmd">
            <div class="mf_pmd_fx">
                <dl>
                    <dt>
                    <div class="fbtxt0">
                        <font><img src="<%=basePath%>/images/icon_22.gif" width="26" height="16"/>
                            发私信给：${userbyid.truename}</font>
                        <font style="padding-left:180px;">你还可以输入<strong id="leavetotal">300</strong>字</font>
                    </div>
                    <div class="fbtxt"><input type="text" value="请在此处输入您回复给 ${userbyid.truename} 的消息.." id="re_pm_msg"
                                              onkeypress="chleave()"/></div>
                    <div class="fbtxt1"><img src="<%=basePath%>/images/icon_21.gif"/></div>
                    </dt>
                    <dd><a href="<%=basePath%>/user-${userbean.user_id}-1.html"><img
                            src="<%=basePath%>/images/thumb/${userbean.userthumb}" width="49" height="52"
                            alt="${userbean.truename}"/></a>
                    </dd>
                </dl>
                <input type="button" value="回复" onclick="repm_msg('${user_id_to}','${user_id_userspace}','${msg_id}')">
            </div>

            <div class="mf_pmd_hf">
                <dl>
                    <dt>
                            <%--  取出本次的私信对象  --%>
                            <mfo:GetPrivateMessageByPmid pmid="${msg_id}"/>
                    <div class="fbtxt_"><%--发送给--%><font
                            class="ec008c">${userbyid.truename}说:</font>：${pmsingle.pm_content}<font class="cDGray">
                        <fmt:formatDate value="${pmsingle.createtime}"
                                        pattern="yyyy-MM-dd:HH:mm:ss "/><%--(6月7号 14：01)--%></font>
                    </div>
                    <div class="fbtxt_2"><img src="<%=basePath%>/images/icon_23.gif"/></div>
                    </dt>
                    <dd><a href="<%=basePath%>/user-${userbean.user_id}-1.html"><img
                            src="<%=basePath%>/images/thumb/${userbean.userthumb}" width="49" height="52"
                            alt="${userbean.truename}"/></a>
                    </dd>
                </dl>
            </div>

        </div>
            <%-- 此时列出我和对方间所有的往来信件  --%>
        <mfo:GetMyPrivateMessage_Double page="1" size="5" user_id_from="${user_id_to}"
                                        user_id_to="${user_id_userspace}"/>
            <%--  对方给我的信件 ------------------   --%>
        <c:forEach var="pmsing" items="${pmdouble}">
            <div class="mf_pmd_hfs">
                <dl>
                    <dt><a href="<%=basePath%>/user-${userbyid.user_id}-1.html"><img
                            src="<%=basePath%>/images/thumb/${userbyid.userthumb}" width="49" height="52"/></a></dt>
                    <dd>
                        <div class="fb_txt2"><img src="<%=basePath%>/images/icon_24.gif" width="9" height="21"/></div>
                        <div class="fb_txt"><font class="ec008c">${userbyid.truename}说</font>：${pmsing.pm_content}
                            <font class="cDGray"> <fmt:formatDate value="${pmsing.createtime}"
                                                                  pattern="yyyy-MM-dd:HH:mm:ss "/><%--(6月7号 14：01)--%></font>

                            <div class="fb_txt3"><a
                                    href="<%=basePath%>/remsg-${user_id_userspace}-${userbyid.user_id}-${pmsing.pmid}.html">回复</a>
                            </div>
                        </div>
                    </dd>
                </dl>
            </div>
            <%--  ------------------ 对方给我的信件   --%>
            <%--  我给对方的回信 有可能对于此私信对方有多次回复-----------------  --%>
            <mfo:GetMyPrivateMessage_reDouble page="1" size="5" user_id_to="${user_id_userspace}"
                                              user_id_from="${userbyid.user_id}" pmid="${msg_id}"/>
            <c:forEach items="${repmdouble}" var="repmdoublesingle">
                <div class="mf_pmd_hf">
                    <dl>
                        <dt>
                        <div class="fbtxt_">我发送给<font
                                class="ec008c">${userbyid.truename}</font>：${repmdoublesingle.pm_content}<font
                                class="cDGray"> <fmt:formatDate value="${repmdoublesingle.createtime}"
                                                                pattern="yyyy-MM-dd:HH:mm:ss "/><%--(6月7号 14：01)--%></font>
                        </div>
                        <div class="fbtxt_2"><img src="<%=basePath%>/images/icon_23.gif"/></div>
                        </dt>
                        <dd><a href="<%=basePath%>/user-${userbean.user_id}-1.html"><img
                                src="<%=basePath%>/images/thumb/${userbean.userthumb}" width="49" height="52"
                                alt="${userbean.truename}"/></a></dd>
                    </dl>
                </div>
            </c:forEach>


        </c:forEach>
            <%--  -----------------我给对方的回信  --%>


    </div>
</div>


    <%--   ----------------------------------- 登陆后才看得见  --%>


</c:when>
<c:otherwise>
    <%--   非本人  --%>
</c:otherwise>
</c:choose>
</c:when>
<c:otherwise>
    <%--未登陆--%>
    <script type="text/javascript">
        alert('请先登陆！');
        var url_now = document.location.href;
        window.location = '<%=basePath%>/pages/login.jsp?url_target=' + url_now;
    </script>
</c:otherwise>
</c:choose>


<div id="foot_x">
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
