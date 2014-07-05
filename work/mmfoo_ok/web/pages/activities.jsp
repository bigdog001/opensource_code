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
    </c:choose>美分网MMFOO |美分活动</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
   <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_common.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/menu.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_index3.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_button.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/pageNum.css">
    <LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/face_activities2.css">
    <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
</head>

<body>
<div id="layout">

    <jsp:include page="../include/nav.jsp"/>
    <%--取所有的活动对象 如果没有指定是第几页 那么取第一页  此标签同时能计算出一共有多少页--%>
    <c:choose>
        <c:when test="${empty param.pageindex}">
            <c:set var="pageindex" value="1"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageindex" value="${param.pageindex}"></c:set>
        </c:otherwise>
    </c:choose>
    <%--此标签同时能计算出一共有多少页 页数存储在request中 名称为 pagetotal--%>
    <mfo:gettophd page="${pageindex}" size="2"/>
    <div class="beautyTop">
        <h3><span class="title">美分活动</span></h3>

        <div class="activitiesBox">
            <div class="doings">
                <c:choose>
                    <%--//判断活动对象是否为空--%>
                    <c:when test="${fn:length(tophd)>0}">
                        <%--有数据--%>
                        <c:forEach var="mmfoohds" items="${tophd}">
                            <div class="cell">
                                <div class="doingsPicture"><a href="/mfhd-${mmfoohds.hdid}.html" target="_blank"><img
                                        src="<%=basePath%>/images/upload/${mmfoohds.hdface}"
                                        width="306"/></a></div>
                                <div class="doingsRight"><span class="titl"><a href="/mfhd-${mmfoohds.hdid}.html"
                                                                               target="_blank">${mmfoohds.hdtitle}</a></span><br/>
                                <span class="tim">
                                   <fmt:formatDate value="${mmfoohds.createtime}" pattern="yyyy年MM月dd日"/>
                                    </span><br/>
                                    <span class="des"> ${mmfoohds.hdabstract}</span><br/>
                                        <%--<button class="funcB">详情</button>--%>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        抱歉！目前没有任何活动！！
                    </c:otherwise>
                </c:choose>


            </div>
            <!--分页开始-->
            <div class="clr"></div>
            <div class="jogger">
                <c:choose>
                    <c:when test="${empty pagetotal}"></c:when>
                    <c:otherwise>
                        <c:if test="${1<param.pageindex}">
                            <a href="<%=basePath%>/mmfoohd-${i-1}.html"> < </a>
                        </c:if>
                        <c:forEach var="i" begin="1" end="${pagetotal}" step="1">
                            <c:choose>
                                <c:when test="${i==param.pageindex}"><span class="current">${i}</span> </c:when>
                                <c:otherwise>
                                    <a href="<%=basePath%>/mmfoohd-${i}.html">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${pagetotal>param.pageindex}">
                            <a href="<%=basePath%>/mmfoohd-${i+1}.html"> > </a>
                        </c:if>

                    </c:otherwise>
                </c:choose>

            </div>
            <!--分页结束-->
        </div>
    </div>
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
