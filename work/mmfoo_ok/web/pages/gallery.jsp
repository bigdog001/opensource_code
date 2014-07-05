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
    <title>美分网 |美女连连看</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_common.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/menu.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_index3.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/form_common.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/uni-form.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/default.uni-form.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/pageNum.css">
    <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
</head>

<body>
<div id="layout">
    <jsp:include page="../include/nav.jsp"/>
    <c:choose>
        <c:when test="${empty param.hdid}">
            <c:set var="hdid" value="1"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="hdid" value="${ param.hdid}"></c:set>
        </c:otherwise>
    </c:choose>
    <div class="titleLogin"><a href="http://www.mmfoo.com" style="color:#FF0099;">首页</a> > 美女连连看</div>
    <div class="beautyTop">
        <h3>
      <span class="refresh">
       <form name="ranks" action="<%=basePath%>/gallery.html" method="post" class="uniForm">
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
          <c:choose>
              <c:when test="${empty param.pagenow}">
                  <c:set var="pagenow" value="1"></c:set>
              </c:when>
              <c:otherwise>
                  <c:set var="pagenow" value="${ param.pagenow}"></c:set>
              </c:otherwise>
          </c:choose>
          <%--取数据----------%>
          <c:choose>
              <c:when test="${!empty param.bynew}">
                  <mfo:getuserorderbytime size="10" page="${pagenow}"/>
              </c:when>
              <c:otherwise>
                  <c:choose>
                      <c:when test="${!empty param.bycnt}">
                          <mfo:getuserorderbycnt size="10" page="${pagenow}"/>
                      </c:when>
                      <c:otherwise>
                          <mfo:gethduser hdid="${hdid}" page="${pagenow}" size="10"></mfo:gethduser>

                          <%-- 当用户即没有点击分页链接时调用 --%>
                          <c:choose>
                              <c:when test="${!empty param.abc}"> <mfo:getuserorderbytime size="10" page="${pagenow}"/></c:when>
                              <c:otherwise></c:otherwise>
                          </c:choose>
                          <%-- 当用户即没有点击分页链接时调用 --%>
                      </c:otherwise>
                  </c:choose>
              </c:otherwise>
          </c:choose>
                <%----------  取数据--%>
          
      </span><span class="title">美女连连看</span></h3>
        <h3 style="margin-bottom: 20px; border-bottom: 1px dotted #CCC; border-top: 1px dotted #ccc;"><a
                href="<%=basePath%>/gallerynew.html"
                <c:choose>
                    <c:when test="${!empty param.bynew}">
                        class="current"
                    </c:when>
                    <c:otherwise>
                        class="lable"
                    </c:otherwise>
                </c:choose>>最新加入</a><a
                href="<%=basePath%>/gallerycnt.html"      <c:choose>
            <c:when test="${!empty param.bycnt}">
                class="current"
            </c:when>
            <c:otherwise>
                class="lable"
            </c:otherwise>
        </c:choose>>点击最多</a></h3>
        <c:choose>
            <c:when test="${empty topuser }">
                <script type="text/javascript">
                    alert("此活动没有参与用户");
                    alert("${ param.pagenow}");
                </script>
            </c:when>
            <c:otherwise>
                <c:forEach var="users" items="${topuser}">
                    <div class="topCell">
                        <div class="avatar"><a href="<%=basePath%>/user-${users.user_id}-1.html"><img width="145px" height="154px" 
                                src="<c:choose><c:when test="${empty users.userimg}"><%=basePath%>/images/default_avatar.jpg</c:when><c:otherwise><%=basePath%>/images/${users.userimg}</c:otherwise></c:choose>"/></a></div>
                        <h4><a href="<%=basePath%>/user-${users.user_id}-1.html">${users.nickname}</a></h4>
                        <h6><span>得票：${users.visit_cnt}</span><span>粉丝：${users.point_cnt}</span></h6>
                        <h6><span><mfo:gethdname user_id="${users.user_id}"/></span></h6>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <!--分页开始-->
        <div class="clr"></div>
        <div class="jogger">
            <c:choose>
                <c:when test="${empty pagetotal}"></c:when>
                <c:otherwise>
                    <c:if test="${1<param.pagenow}">
                        <a href="<%=basePath%>/gallerynew-${pagenow-1}.html"> < </a>
                    </c:if>
                    <c:forEach var="i" begin="1" end="${pagetotal}" step="1">
                        <c:choose>
                            <c:when test="${i==param.pagenow}"><span class="current">${i}</span> </c:when>
                            <c:otherwise>
                                <a href="<%=basePath%>/gallerynew-${i}.html">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${pagetotal>param.pagenow}">
                        <a href="<%=basePath%>/gallerynew-${pagenow+1}.html"> > </a>
                    </c:if>

                </c:otherwise>
            </c:choose>
        </div>
        <!--分页结束-->
    </div>
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
