<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title ><spring:message code="gethelp" /></title>
        <meta charset="utf-8">
        <jsp:include page="./include/header.jsp"/>
    </head>
    <body name="home">
        <jsp:include page="./include/nav.jsp"/>
        <div id="wrapper">
            <div class="masthead">
                <div class="container">
                    <div class="slider">
                        <jsp:include page="./include/gethelpContent.jsp"/>



                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="./include/footer.jsp"/>
    </body>
</html>