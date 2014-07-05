<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<script type="text/javascript" src="../js/cutit.js"></script>
<script type="text/javascript" src="../js/Drag.js"></script>
<script type="text/javascript" src="../js/Resize.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/cutit.css">

<c:choose>
    <c:when test="${empty  sessionScope.userimg }">

    </c:when>
    <c:otherwise>
        <table width="700" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="300">
                    <div id="bgDiv">
                        <div id="dragDiv">
                            <div id="rRightDown"></div>
                            <div id="rLeftDown"></div>
                            <div id="rRightUp"></div>
                            <div id="rLeftUp"></div>
                            <div id="rRight"></div>
                            <div id="rLeft"></div>
                            <div id="rUp"></div>
                            <div id="rDown"></div>
                        </div>
                    </div>
                </td>
                <td align="center">
                    <div id="viewDiv" style="width:300px; height:300px;"></div>
                </td>
            </tr>
        </table>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${empty  sessionScope.userimg }">

    </c:when>
    <c:otherwise>
        <%--<script type="text/javascript" src="../js/uus.js"></script>--%>
        <script type="text/javascript">

var ic = new ImgCropper("bgDiv", "dragDiv", "<%=basePath%>/images/upload/${sessionScope.userimg }", {
	Width: 300, Height: 400, Color: "#000",
	Resize: true,
	Right: "rRight", Left: "rLeft", Up:	"rUp", Down: "rDown",
	RightDown: "rRightDown", LeftDown: "rLeftDown", RightUp: "rRightUp", LeftUp: "rLeftUp",
	Preview: "viewDiv"
});
        </script>
        <script type="text/javascript" src="../js/jquery-1.4.4.min.js"></script>
        <input type="button" value="go" onclick="javascript:getrec()"/>
        <input type="text" id="vvvv" style="width: 500px;">
        <img src="" id="pic_cuted">
    </c:otherwise>
</c:choose>
 <input type="button" value="上传" onclick="javascript:window.open('/pages/upload.jsp','_blank','height=200, width=370, top=100, left=500, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no');">
<br><br><br>

</body>
<script type="text/javascript">
    String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
    function getrec(){
     //   var recparm= $("#viewDiv img").css("clip");
        var pic_x= $("#dragDiv").css("left").replace("px","");
        var pic_y= $("#dragDiv").css("top").replace("px","");
        var pic_width= $("#dragDiv").css("width").replace("px","");
        var pic_heigth= $("#dragDiv").css("height").replace("px","");
    $("#vvvv").attr("value",pic_x+","+pic_y+","+pic_width+","+pic_heigth);
   $.post("/user/user_DrawImg.action?ts="+new Date(),{pic_x:pic_x,pic_y:pic_y,pic_width:pic_width,pic_heigth:pic_heigth},function(picbk){
   picbk=picbk.trim();
       if(picbk=="0"){
         alert("用戶没有上传用来裁剪的图片,请上传！");
           return;
       } if(picbk=="2"){
         alert("用戶没有上传的图片参数不正确!");
           return;
       }if(picbk=="3"){
         alert("图片裁剪视失败!");
           return;
       }else{
        $("#pic_cuted").attr("src","<%=basePath%>/images/thumb/"+picbk+"?ts="+new Date());
            return;
       } 

   });
}
</script>
</html>
