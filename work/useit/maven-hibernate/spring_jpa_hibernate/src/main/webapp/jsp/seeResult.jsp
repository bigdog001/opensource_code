<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.opensymphony.xwork2.util.ValueStack" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <title>result</title>
    <body>
    <center>
    <table>
   <s:iterator value="pizzas" id="pz" >
   <tr> <td style="color: #dee5bf"><s:property value="#pz"/></td></tr>
   </s:iterator>
    </table>
    </center>
    </body>
</head>
</html>
