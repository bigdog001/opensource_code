<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>registe</title>
</head>
<body>
<s:form action="mumAction_login" method="post">
    <s:textfield name="username" label="username"/>
    <s:password name="password" label="password"/>
    <s:submit label="login"/>
</s:form>
<br>
<br>
<a href="mumAction_doreg.action">sign up</a>
</body>
</html>