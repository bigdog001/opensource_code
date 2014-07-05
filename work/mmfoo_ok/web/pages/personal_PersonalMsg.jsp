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
    <%-- 判断是否登录  --%>
    <c:choose>
        <c:when test="${sessionScope.islogin eq 'Y' }">
            <%-- body_right 这个div中的内容只有本人登陆后才 能看见--------------------  --%>
                <c:choose>
                    <c:when test="${user_id_userspace eq userbean.user_id}">
                          <div class="body_right left">
                <div class="mf_xtxx">

                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="30" height="36" align="center" valign="middle">
                                <input type="checkbox" name="allselect" onchange="checkbochange(this)"/></td>
                            <td width="60" height="36" align="center" valign="middle">全选</td>
                            <td height="36" valign="middle" style=" padding-left:10px;"><a href="javascript:deleteSelected('${user_id_userspace}')"><img
                                    src="<%=basePath%>/images/bn_del.gif" width="70" height="27"/></a>&nbsp;&nbsp;<a
                                    href="javascript:deletemypm('${user_id_userspace}')"><img
                                    src="<%=basePath%>/images/bn_delall.gif" width="70" height="27" /></a></td>
                            <td width="110" height="36" align="center" valign="middle">
                                <a href="javascript:void(0)"
                                   onclick="document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'">
                                    <img src="<%=basePath%>/images/bn_fsx.gif" width="90" height="27"/></a>

                                <div id="light" class="white_content">
                                    <div class="close">
                                        <a href="javascript:void(0)"
                                           onclick="document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'"><img
                                                src="<%=basePath%>/images/btn-delete.gif" width="16" height="16"
                                                alt="点击关闭"/></a>
                                    </div>
                                    <form>
                                        <dl>
                                            <dt>发私信给：</dt>
                                            <dd><input type="text"
                                                       style="border:1px #ccc solid; width:204px; height:20px; line-height:20px;"
                                                       id="user_email_to"/>
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>私信内容：</dt>
                                            <dd><textarea style=" border:1px #ccc solid; width:302px; height:132px;"
                                                          id="user_msg"></textarea>
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>&nbsp;&nbsp;</dt>
                                            <dd style=" padding-top:10px;"><a href="javascript:void(0)"
                                                    <c:choose>
                                                        <c:when test="${sessionScope.islogin eq 'Y' }">
                                                            <%-- 不能给自己发私信--%>
                                                            <c:choose>
                                                                <c:when test="${user_id_userspace eq userbean.user_id}">
                                                                    <%--onclick="alert('不能给自己发私信')"--%>    onclick="sendingmsg('${user_id_userspace}')"
                                                                </c:when>
                                                                <c:otherwise>
                                                                    onclick="sendingmsg('${user_id_userspace}')"
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                            onclick="alert('请先登陆！');var url_now=document.location.href;window.location='<%=basePath%>/pages/login.jsp?url_target='+url_now"
                                                        </c:otherwise>
                                                    </c:choose>
                                                    ><img
                                                    src="<%=basePath%>/images/bn_fs.gif"/></a></dd>
                                        </dl>
                                        <dl>
                                            <dt>&nbsp;&nbsp;</dt>
                                            <dd>说明：长度不能超过300字</dd>
                                        </dl>
                                    </form>
                                </div>
                                <div id="fade" class="black_overlay"></div>
                            </td>
                        </tr>

                            <%--  读出我的所有私信 --%>
                        <mfo:GetMyPrivateMessage user_id="${user_id_userspace}" page="1" size="5"/>
                        <c:forEach var="mmfoopmsingle" items="${mmfoopm}">
                            <mfo:getUserById user_id="${mmfoopmsingle.user_id_from}"/>
                            <tr>
                                <td width="30" align="center" valign="top"
                                    style=" border-top:1px #ccc dashed; padding-top:10px;">
                                    <input type="checkbox" name="checkbox_user" value="${mmfoopmsingle.pmid}" onclick="UnSelectAll()" />
                                </td>
                                <td width="60" align="center" valign="top"
                                    style=" border-top:1px #ccc dashed; padding-top:10px;">
                                    <img width="49" height="52"
                                         src="<%=basePath%>/images/thumb/<c:choose><c:when test="${empty userbyid.userthumb}">meinv5.jpg</c:when><c:otherwise>${userbyid.userthumb}</c:otherwise></c:choose>"/>
                                </td>
                                <td valign="top" style=" padding:10px; border-top:1px #ccc dashed; margin-top:10px;">
                                    <span onclick="window.location='<%=basePath%>/user-${userbyid.user_id}-1.html'"
                                          style="cursor:pointer">来自:${userbyid.email}</span><br/>
                                        ${mmfoopmsingle.pm_content}<br/><br/>

                                    <p>共${fn:length(mmfoopm)}条对话 | <a
                                            href="<%=basePath%>/remsg-${userbyid.user_id}-${user_id_userspace}-${mmfoopmsingle.pmid}.html"
                                            class="ec008c">回复</a></p>

                                </td>
                                <td width="110" align="center" valign="top"
                                    style=" border-top:1px #ccc dashed; padding-top:10px;">
                                    <fmt:formatDate value="${mmfoopmsingle.createtime}" pattern="yyyy-MM-dd:HH:mm:ss "/>
                                </td>
                            </tr>
                        </c:forEach>


                    </table>


                </div>


            </div>

            <%-- -------------------- body_right 这个div中的内容只有本人登陆后才 能看见 --%>
                    </c:when>
                     <c:otherwise></c:otherwise>
                </c:choose>



        </c:when>
        <c:otherwise>
               <%--用户未登录--%>
            <script type="text/javascript">
                alert('请先登陆！');
                var url_now=document.location.href;
                window.location='<%=basePath%>/pages/login.jsp?url_target='+url_now ;
            </script>
        </c:otherwise>
    </c:choose>


</div>


<div id="foot_x">
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
