<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>registe</title>
</head>
<body>
<s:form action="mumAction_reg" method="post">
    <s:textfield name="username" label="username"/>
    <s:password name="password" label="password"/>
    <s:textfield name="phone" label="phone"/>
    <s:textfield name="email" label="email"/>
    <s:submit label="submit"/>
</s:form>
</body>
</html>