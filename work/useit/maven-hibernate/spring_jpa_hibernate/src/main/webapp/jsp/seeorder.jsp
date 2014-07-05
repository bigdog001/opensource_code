<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>registe</title>
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="/js/mum.js"></script>
</head>
<body>
 <table>
     <tr>
         <td>id</td>
         <td>name</td>
         <td>setting</td>
     </tr>

     <tr>
         <td>1</td>
         <td>pizza1</td>
         <td><a href="javascript:sendAdd('pizza1')">add</a></td>
     </tr>
     <tr>
         <td>2</td>
         <td>pizza2</td>
         <td><a href="javascript:sendAdd('pizza2')">add</a></td>
     </tr>
     <tr>
         <td>3</td>
         <td>pizza3</td>
         <td><a href="javascript:sendAdd('pizza3')">add</a></td>
     </tr>
     <tr>
         <td>4</td>
         <td>pizza4</td>
         <td><a href="javascript:sendAdd('pizza4')">add</a></td>
     </tr>
 </table>
<br>
<br>
<a href="mumAction_order.action">see result</a>
</body>
</html>