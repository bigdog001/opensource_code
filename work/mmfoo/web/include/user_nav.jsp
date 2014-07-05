<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2011-5-24
  Time: 22:20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="cardtab" value="param.cardtab" />
  <div class="timer">
      <ul id="explore-nav">
        <li id="ex-featured"><a href="personal_album-${param.user_id}.html" 
                                <c:choose>
                                    <c:when test="${cardtab==1}"> class="current"</c:when>
                                </c:choose>
                               >美女秀</a></li>
        <li id="ex-core"><a href="personal_file-${param.user_id}.html" <c:choose>
                                    <c:when test="${cardtab==2}"> class="current"</c:when>
                                </c:choose>>美女档案</a></li>
        <li id="ex-jquery"><a href="personal_index-${param.user_id}.html" <c:choose>
                                    <c:when test="${cardtab==3}"> class="current"</c:when>
                                </c:choose>>美女新鲜事</a></li>
      </ul>
    </div>