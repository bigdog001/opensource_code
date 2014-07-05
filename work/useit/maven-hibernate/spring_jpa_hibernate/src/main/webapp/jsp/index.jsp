<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>Index</title>
	<s:head />
</head>
<body>
<br/><hr/>

    <s:form action="mumAction_login" method="post">
        <s:textfield name="username" key="username"/>
        <s:password  name="password" key="password"/>
        <s:submit label="submit"/><s:reset label="reset"/>

    </s:form>
</body>
</html>
	