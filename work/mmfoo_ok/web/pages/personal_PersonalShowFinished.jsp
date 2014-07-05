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
<c:choose>
    <c:when test="${empty param.setid}">
        <c:set var="user_setid_userspace" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="user_setid_userspace" value="${param.setid}"/>
    </c:otherwise>
</c:choose>
<%--每浏览一次次页面 相册的浏览次数加一--%>
<mfo:AddImgSetViewCnt setid="${user_setid_userspace}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><c:choose>
        <c:when test="${empty userbean}"></c:when>
        <c:otherwise>${userbean.nickname} 个人网站</c:otherwise>
    </c:choose>美分网MMFOO |上传照片</title>
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
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/slide.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>

</head>

<body>
<jsp:include page="../include/user_space_nav.jsp"/>

<div id="main_body" style="border:none">
    <div class="bjda_bt">
        <dl>
            <dt style="float:left;">
            <form name="showphoto" action="<%=basePath%>/photoShow-${user_id_userspace}.html" method="post">
                <select id="setid" onchange="document.showphoto.submit()" name="setid">
                    <%--加载本人所有的相册分类--%>
                    <mfo:getImgsetbyid user_id="${user_id_userspace}" size="100" page="1"/>
                    <c:forEach var="sets" items="${userimgset}">
                        <option value="${sets.setid}" <c:choose>
                            <c:when test="${sets.setid==param.setid}">selected=selected</c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose>>${sets.setname}</option>
                    </c:forEach>

                </select>
            </form>
            </dt>
            <dd style="float:right;"><a href="<%=basePath%>/usershow-${user_id_userspace}-3.html">返回我的展示</a></dd>
        </dl>
    </div>
    <%--判断登陆者是否为本人--%>
    <c:choose>
        <c:when test="${sessionScope.islogin eq 'Y' }">

            <c:choose>
                <c:when test="${user_id_userspace eq userbean.user_id}">
                    <%--取相册的封面--%>
                    <mfo:getSetFace setid="${user_setid_userspace}"/>
                    <div class="mfzs_psf">
                        <div class="mfzs_psf_">
                            <dl>
                                <dt><img
                                        src="<%=basePath%>/images/<c:choose><c:when test="${empty setface.thumb_img}">meinv8.jpg</c:when><c:otherwise>${setface.thumb_img}</c:otherwise></c:choose>
                <c:out value=""/>" width="233" height="247"/></dt>
                                    <%--根据相册id将相册对象取出--%>
                                <mfo:GetImgSetBySetId setid="${user_setid_userspace}"/>
                                <dd class="f24px fB">${imgsetsingle.setname}</dd>
                                <dd class="cDGray fB"><fmt:formatDate value="${imgsetsingle.createtime}"
                                                                      pattern="yyyy/MM/dd"/></dd>
                                <dd class="cDGray f14px">${imgsetsingle.setdiscribe}</dd>
                                <dd><a href="<%=basePath%>/newphoto-${user_id_userspace}.html"><img
                                        src="<%=basePath%>/images/bn_sczp.gif" width="100" height="27"/></a>
                                    <a href="<%=basePath%>/editephotoset-${user_setid_userspace}-${user_id_userspace}.html"><img
                                            src="<%=basePath%>/images/bn_bj1.gif" width="80" height="27"/></a>
                                    <a href="javascript:deleteimgset('${user_setid_userspace}','${user_id_userspace}')"><img
                                            src="<%=basePath%>/images/bn_sc.gif" width="80" height="27"/></a></dd>
                            </dl>
                        </div>
                        <div class="mfzs_psf_r">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td height="124" align="right" valign="top"><fmt:formatDate
                                            value="${imgsetsingle.createtime}" pattern="yyyy/MM/dd"/></td>
                                </tr>
                                <tr>
                                    <td height="124" align="right" valign="bottom">
                                        &nbsp;&nbsp;<img src="<%=basePath%>/images/picture2.gif" width="16"
                                                         height="15"/>
                                        <span>${imgsetsingle.see_cnt}</span>
                                        &nbsp;&nbsp;<img src="<%=basePath%>/images/icon_7.gif" width="16" height="16"/>
                                        <font>${imgsetsingle.favor_cnt}</font>
                                            <%--   &nbsp;&nbsp;<img src="../images/icon_8.gif" width="16" height="16"/>
                                            <font>100</font>
                                            &nbsp;&nbsp;<img src="../images/icon_10.gif" width="16" height="16"/>
                                            <font>100</font>
                                            &nbsp;&nbsp;<img src="../images/icon_11.gif" width="16" height="16"/>
                                            <font>100</font>
                                            &nbsp;&nbsp;<img src="../images/icon_9.gif" width="16" height="16"/>
                                            <font>100</font>--%>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>


        </c:otherwise>
    </c:choose>

    <%-- --%>
    <div class="zfplsc">
           <ul>
            <%--<li>收藏（31）</li>--%>
            <li>
                <mfo:GetCommentTotal setid="${user_setid_userspace}"/>
                评论（${CommentTotal}）</li>
            <%--<li>转发（100）</li>--%>
        </ul> 
    </div>
    <div class="mfzs_psf_mvs">
        <%-- 根据相册id取出所有的相片--%>
        <mfo:getimgsbysetid setid="${user_setid_userspace}" page="1" size="10"/>

        <c:forEach var="imgs_in_set" items="${imgs_first_set}">
            <img src="<%=basePath%>/images/upload/54zzb/${imgs_in_set.imgname}" width="609" height="912"/>
        </c:forEach>


    </div>
    <div class="mfzs_xh"><a href="javascript:loveit('${user_setid_userspace}')"><img src="../images/bn_xh.gif"
                                                                                     width="159" height="53"/></a>
    </div>
    <c:choose>
        <c:when test="${sessionScope.islogin eq 'Y' }">
            <div class="conmentP">
                <h2>评论（140字）：</h2>
                <textarea cols="70" rows="9" name="" style="height:110px;line-height:24px; font-family:'宋体'" id="commenttt"></textarea><a
                    href="javascript:comment('${userbean.user_id}','${user_id_userspace}','${user_setid_userspace}')"><img src="<%=basePath%>/images/comment.jpg" width="73" height="31"></a>

                <div class="clear"></div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="mfzs_denglu f14px">
                <br/>
                <br/>

                <p>
                    欢迎加入美分网，来发表你的观点！<br/>
                    <a href="<%=basePath%>/regist.html" class="ec008c">注册</a> |
                    <a href="<%=basePath%>/pages/login.jsp" class="ec008c">登录</a></p>

            </div>
        </c:otherwise>
    </c:choose>

          <mfo:GetSetComment setid="${user_setid_userspace}" page="1" size="5"/>
    <c:choose>
        <c:when test="${fn:length(Comments)>0}">
             <c:forEach var="Comment" items="${Comments}">
        <mfo:getUserById user_id="${Comment.userid}"/>
           <div class="mfzs_pl">
        <dl>
            <dt><a href="<%=basePath%>/user-${userbyid.user_id}-1.html"><img src="<%=basePath%>/images/thumb/${userbyid.userthumb}" width="49" height="52"/></a></dt>
            <dd class="f14px"><span>${userbyid.truename}：</span>${Comment.content}</dd>
            <dd class="f12px cDGray"> <mfo:getTimeDistance start="${Comment.createtime}"/></dd>
        </dl>
    </div>
    </c:forEach>
        </c:when>
        <c:otherwise>
            无任何评论
        </c:otherwise>
    </c:choose>


</div>

<div id="foot_x">
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
