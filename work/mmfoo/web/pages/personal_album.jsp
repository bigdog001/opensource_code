<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="mfo" uri="mmfoo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>美分网</title>
    <jsp:include page="../include/user_header.jsp"/>
</head>

<body>
<jsp:include page="../include/user_top.jsp"/>
<c:if test="${empty param.user_id}">
    <c:set var="param.user_id" value="1"/>
</c:if>

<mfo:getUserById user_id="${param.user_id}"/>
<div id="layout">

    <div class="promoteBox">
        <div class="avatarPromote"><img src="<%=basePath%>/images/${userbyid.userimg}"/></div>
        <jsp:include page="../include/user_nav.jsp"/>
        <div class="slide">
            <jsp:include page="../include/user_left.jsp"/>
            <div class="content">
                <h5>
          <span>
          <form class="uniForm" action="#">
              <fieldset class="inlineLabels">
                  <div class="ctrlHolder">
                      <label style="font-size: 12px; font-weight: normal; color: #666; width: 40px">选择</label>
                      <select class="long">
                          <mfo:getImgsetbyid user_id="${param.user_id}" page="1" size="10"/>

                          <c:forEach var="imgset" items="${userimgset}">
                              <option value="${imgset.setid}">${imgset.setname}</option>
                          </c:forEach>

                      </select>
                  </div>
              </fieldset>
          </form>
          </span>
                    相册名称
                </h5>
                <div class="showBox">
                    <c:choose>
                        <c:when test="${fn:length(userimgset)>0}">
                            <%--说明此时此用户有相册存在，如果没有指定取哪个相册下的数据  那么就取第一个相册里的所有照片--%>
                            <%--判断是否有指定相册id--%>
                            <c:choose>
                                <c:when test="${empty param.setid}">
                                    <%--如果没有指定相册的id的话--%>
                                    <c:set var="imgsetid" value="${userimgset[0].setid}"/>
                                </c:when>
                                <c:otherwise>
                                    <%--如果有指定相册的id的话--%>
                                    <c:set var="imgsetid" value="${param.setid}"/>
                                </c:otherwise>
                            </c:choose>

                            <%--根据此id找出此相册下的所有照片名字--%>
                            <mfo:getimgsbysetid setid="${imgsetid}" page="1" size="5"/>

                            <%--js图片浏览开始--%>

                            <link type="text/css" href="<%=basePath%>/css/hdPic_new_v1.0.2.css" rel="stylesheet"
                                  media="screen"/>
                            <script type="text/javascript" src="<%=basePath%>/js/hd_min_v1.0.0.js"/>
                            <div id="Cnt-Main-Article-QQ">
                                <div id="photo-warp"
                                     style="background:url('<%=basePath%>/images/ajax-loader.gif') no-repeat center center">
                                    <div class="photo-warp-inner" id="photo-warp-inner" style="visibility:hidden;">
                                        <div class="mainArea" id="mainArea">
                                            <div id="preArrow" title="上一张"></div>
                                            <div id="nextArrow" title="下一张"></div>
                                            <div id="gotolast"></div>
                                            <div id="gotolast_inner">
                                                <p>已经浏览到最后一张</p>
                                                <a href="#" id="rePlay">重新浏览</a><a href="/" id="urlgoto"> </a></div>
                                            <a href="javascript:void(0);" id="bigHref"><img
                                                    src="<%=basePath%>/images/ajax-loader.gif" id="Display"
                                                    style="cursor:pointer;margin:0 auto;visibility:hidden;"
                                                    title="click to see  next"/></a>

                                            <div id="loading" style="display:none"></div>
                                            <div class="picTips picTips_png" id="picTips" style="display:none">
                                                <div class="titleArea" id="titleArea"></div>
                                            </div>
                                            <div href="javascript:void(0);" class="buttonArea" id="buttonArea"
                                                 title="隐藏图片注释">隐藏
                                            </div>
                                            <div class="openTips" style="display:none" id="openTips" title="打开图片注释">
                                                查看图注
                                            </div>
                                        </div>
                                        <div class="blank"></div>
                                        <div class="photoList-wrap"><a href="javascript:void(0)" class="photo-Up"
                                                                       id="Up"
                                                                       onfocus='this.blur()' title="向前"></a>

                                            <div class="photo-List" id="photo-List">
                                                <ul id="smallPhoto">
                                                </ul>
                                                <div id="noDiv"></div>
                                            </div>
                                            <a href="javascript:void(0)" class="photo-Down" id="Down"
                                               onfocus='this.blur()'
                                               title="向后"></a></div>
                                        <div id="scrollbar"><a href="javascript:void(0)" id="scrollbar-in"
                                                               title="拖动工具条以快速查看图片"></a>
                                        </div>
                                    </div>
                                </div>
                                <div id="flashCff"></div>
                                <div id="contTxt"></div>
                                <div id="PGViframe"></div>
                                <script>
                                    //JS版本
                                    var GroupjsUrl = "<%=basePath%>/js/hd_min_v1.0.0.js";
                                </script>

                                <script>
                                    var isLoadData = false;
                                    //将相册下的图片加载到这里
                                      <c:set value="${fn:length(imgs_first_set)}" var="setsize"/>
                                      <c:set value="0" var="tmp_size"/>
                                    var photoJson = [
                                        <c:forEach var="user_img" items="${imgs_first_set}"><c:set value="${tmp_size+1}" var="tmp_size"/>
                                        {showtit:'',showtxt:'',smallpic:'<%=basePath%>/images/${user_img.thumb_img}','bigpic':'<%=basePath%>/images/${user_img.imgname}'}
                                            <c:choose>
                                                <c:when test="${tmp_size==setsize }"></c:when>
                                                <c:otherwise>,</c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    ];
                                    isLoadData = true;
                                </script>
                                <script>
                                    function GroupLoadJs(file, callback) {
                                        var head = document.getElementsByTagName('head')[0];
                                        var js = document.createElement('script');
                                        js.setAttribute('type', 'text/javascript');
                                        js.setAttribute('src', file);
                                        head.appendChild(js);
                                        js.onload = js.onreadystatechange = function() {
                                            if (js.readyState && js.readyState != 'loaded' && js.readyState != 'complete') return;
                                            js.onreadystatechange = js.onload = null;
                                            if (callback) callback();
                                        }
                                        return false;
                                    }
                                    GroupLoadJs(GroupjsUrl, function() {
                                        if (isLoadData) {
                                            picShow.Picsite = "";
                                            picShow.lastUrl = "/";
                                            picShow.defatLink = "/";
                                            picShow.SiteName = "news";
                                            picShow.Loader();
                                            picShow.$("photo-warp-inner").style.visibility = "visible";
                                        }
                                    })
                                    window.onload = function() {
                                        picShow.setTit();
                                    }</script>
                            </div>
                            <%--js图片浏览结束--%>

                        </c:when>
                        <c:otherwise>
                            此用户没有相册
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../include/footer.jsp"/>
</div>

</body>
</html>
