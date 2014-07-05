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
%>
<html>
<head>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
    <c:choose>
        <c:when test="${empty param.page}">
            <c:set var="page" value="1" scope="request"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="page" value="${param.page}" scope="request"></c:set>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${empty param.size}">
            <c:set var="size" value="20"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="size" value="${param.size}"></c:set>
        </c:otherwise>
    </c:choose>
</head>
用户列表 <br/><br/><br/>
<%--  取出所有的用户，包括为激活的和为通过审核的  --%>
<mfo:getAllUsersTotal size="${size}" page="${page}"/>
<table border="1px">
    <tr>
        <td>编号</td>
        <td>真实姓名</td>
        <td>昵称</td>
        <td>email</td>
        <td>logo照片</td>
        <td>logo缩略图</td>
        <td>是否被激活</td>
        <td>参加的活动个数</td>
        <td>是否通过审核</td>
        <td>操作</td>
    </tr>
    <c:forEach var="user" items="${UsersTotal}">
        <tr>
            <td>${user.user_id}</td>
            <td>${user.truename}</td>
            <td>${user.nickname}</td>
            <td>${user.email}</td>
            <td>
                <c:choose>
                    <c:when test="${empty user.userimg}">未上传照片</c:when>
                    <c:otherwise>
                        <img src="<%=basePath%>/images/${user.userimg}" alt=""/>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${empty user.userthumb}">无缩略图</c:when>
                    <c:otherwise>
                        <img src="<%=basePath%>/images/thumb/${user.userthumb}" alt=""/>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${user.is_activety}</td>
            <td><mfo:getMmffhds_user user_id="${user.user_id}"/>${mmfoohd_total}</td>
            <td>${user.hd_ok}</td>
            <td>

                <c:choose>
                    <c:when test="${user.hd_ok eq 'Y'}"> <span
                            style="color:red"> 已经通过审核 </span> &nbsp;&nbsp;&nbsp;</c:when>
                    <c:otherwise>
                        <a href="javascript:pleasego('${user.user_id}')"> 通过审核 </a> &nbsp;&nbsp;&nbsp;
                    </c:otherwise>
                </c:choose>

                <a
                        href="javascript:deleteUser('${user.user_id}')"> 删除 </a>
                <c:choose>
                    <c:when test="${user.hd_ok eq 'Y'}"> <span
                            style="color:red"> <a href="javascript:unpleasego('${user.user_id}')">置为未通过</a> </span> &nbsp;&nbsp;&nbsp;</c:when>
                    <c:otherwise>                        
                    </c:otherwise>
                </c:choose>

            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="9">
            <%
                String total = (String) request.getAttribute("pagetotal");
                if ("".equals(total) || total == null) {
                    total = "1";
                }
                int pagetotal = Integer.valueOf(total);

                String pagenew = (String) request.getAttribute("page");
                if ("".equals(pagenew) || pagenew == null) {
                    pagenew = "1";
                }
                int pagenow = Integer.valueOf(pagenew);
            %>

            <%
                for (int i = 1; i <= pagetotal; i++) {
            %>

            <a style="<%=i==pagenow?"color:red":""%>" href="<%=basePath%>/admin/userslist.jsp?page=<%=i%>"><%=i%>
            </a>&nbsp;
            <%
                }
            %>

        </td>
    </tr>
</table>

</html>