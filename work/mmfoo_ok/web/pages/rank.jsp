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
    </c:choose>美分网MMFOO |美分最美女</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_common.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/menu.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_index3.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/form_common.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/uni-form.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/default.uni-form.css">
    <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
</head>

<body>
<c:choose>
    <c:when test="${empty param.hdid}">
        <c:set var="hdid" value="1"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="hdid" value="${ param.hdid}"></c:set>
    </c:otherwise>
</c:choose>
<div id="layout">
    <jsp:include page="../include/nav.jsp"/>
    <div class="titleLogin"><a href="http://www.mmfoo.com" style="color:#FF0099;">首页</a> > 美分最美女</div>
    <div class="beautyTop">
        <h3> <span class="refresh">
      <form name="ranks" action="<%=basePath%>/rank.html" method="post" class="uniForm">
          <fieldset class="inlineLabels">
              <div class="ctrlHolder">
                  <label style="font-size: 12px; font-weight: normal; color: #666; width: 40px">活动：</label>
                  <select class="long" name="hdid" onChange="document.ranks.submit()">
                      <%--取所有的活动名称 将所有的活动名称加载到这里--%>
                      <mfo:gettophd page="1" size="10000"/>
                      <c:forEach var="mmfoohds" items="${tophd}">
                          <option value="${mmfoohds.hdid}"
                                  <c:choose>
                                      <c:when test="${hdid==mmfoohds.hdid}">selected=selected</c:when>
                                      <c:otherwise></c:otherwise>
                                  </c:choose>
                                  >${mmfoohds.hdname}</option>
                      </c:forEach>
                  </select>
                  <input type="submit" name="submit1" style="display:none" value="">

              </div>
          </fieldset>
      </form>
      </span><span class="title">美分最美女</span></h3>
        <h3 style="margin-bottom: 20px; border-bottom: 1px dotted #CCC; border-top: 1px dotted #ccc;">
            <a href="<%=basePath%>/rankf.html"
               <c:choose>
                   <c:when test="${empty param.byfans}">class="lable"</c:when>
                   <c:otherwise> class="current" </c:otherwise>
               </c:choose>.
               >总粉丝</a>
            <a href="<%=basePath%>/rankcn.html"
                    <c:choose>
                   <c:when test="${empty param.bycnt}">class="lable"</c:when>
                   <c:otherwise> class="current" </c:otherwise>
               </c:choose>.
                    >24小时投票榜</a></h3>

        <%--得取那一块儿的数据 取参加此活动的所有美女--%>
         <c:choose>
             <c:when test="${!empty param.byfans}">   
                 <mfo:getuserorderbyfans size="10" page="1"/>
             </c:when>
             <c:otherwise>
                     <c:choose>
                         <c:when test="${!empty param.bycnt}">
                              <mfo:getuserorderbycnt size="10" page="1"/>
                         </c:when>
                         <c:otherwise>
                              <mfo:gethduser hdid="${hdid}" page="1" size="100"></mfo:gethduser>
                         </c:otherwise>
                     </c:choose>
             </c:otherwise>
         </c:choose>


        <c:choose>
            <c:when test="${empty topuser }">
                <script type="text/javascript">
                    alert("此活动没有参与用户");
                </script>
            </c:when>
            <c:otherwise>
                <div class="rankBox">
                    <div class="numone">
                        <div class="rankAvatar"><img src="<%=basePath%>/images/top1.jpg"/>

                            <div class="avatar"><a href="<%=basePath%>/user-${topuser[0].user_id}-1.html"><img
                                    src="<%=basePath%>/images/${topuser[0].userimg}"/></a></div>
                        </div>

                        <div class="rankInfo">
                            <h4><a href="<%=basePath%>/user-${topuser[0].user_id}-1.html">${topuser[0].truename}</a></h4>
                            <h6><span>得票：${topuser[0].visit_cnt}</span><span>粉丝：${topuser[0].point_cnt}</span></h6>
                            <h6><span><mfo:gethdname user_id="${topuser[0].user_id}"/></span></h6>
                        </div>
                        <div class="someInfo">
                            <ul>
                                <li>${topuser[0].school}</li>
                                <li><fmt:formatDate value="${topuser[0].birthday}" pattern="yyyy年MM月dd日"/></li>
                                <li>${topuser[0].province} ${topuser[0].city}</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <c:set var="img_name_count" value="1"/>
                  <c:forEach var="users" items="${topuser}">
                <div class="topCell"><img src="<%=basePath%>/images/top${img_name_count}.jpg"/>
                         <c:set var="img_name_count" value="${img_name_count+1}"/>
                          <div class="avatar"><a href="<%=basePath%>/user-${users.user_id}-1.html"><img width="145px" height="154px" src="<c:choose><c:when test="${empty users.userimg}"><%=basePath%>/images/default_avatar.jpg</c:when><c:otherwise><%=basePath%>/images/${users.userimg}</c:otherwise></c:choose>"/></a></div>
                          <h4><a href="<%=basePath%>/user-${users.user_id}-1.html">${users.nickname}</a></h4>
                          <h6><span>得票：${users.visit_cnt}</span><span>粉丝：${users.point_cnt}</span></h6>
                          <h6><span><mfo:gethdname user_id="${users.user_id}"/></span></h6>
                      </div>
             </c:forEach>

                
            </c:otherwise>
        </c:choose>



    </div>
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
