<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="bigdog" uri="/bigdog-tags" %>
<!DOCTYPE html>
<!-- saved from url=(0016)http://e.360.cn/ -->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:text name="adminlogin"/></title>
    <link href="./res/base/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="bg" id="mid">
    <form action="admin.action" method="post">
    <table>
        <caption>admin login</caption>
        <tr><td>user name:</td><td><input type="text" name="username"/></td></tr>
        <tr><td>password:</td><td><input type="password" name="passwd"/></td></tr>
        <tr><td><input type="submit" value="submit"/></td><td><input type="reset" value="reset"/></td></tr>
    </table>
    </form>
</div>

</body>
</html>