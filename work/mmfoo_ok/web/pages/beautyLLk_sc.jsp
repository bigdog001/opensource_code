<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    basePath=basePath.replace(":80","");
%>
<c:choose>
    <c:when test="${empty userbean.user_id}">
        <c:set var="user_id_userspace" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="user_id_userspace" value="${userbean.user_id}"/>
    </c:otherwise>
</c:choose>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>美分网 |上传图像</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />
    <LINK rel="stylesheet" type="text/css" href="../css/face_common.css">
    <LINK rel="stylesheet" type="text/css" href="../css/menu.css">
    <LINK rel="stylesheet" type="text/css" href="../css/face_index3.css">
    <LINK rel="stylesheet" type="text/css" href="../css/face_button.css">
    <LINK rel="stylesheet" type="text/css" href="../css/pageNum.css">
    <LINK rel="stylesheet" type="text/css" href="../css/face_activities2.css">
    <LINK rel="stylesheet" type="text/css" href="../css/beautyLLk.css">
    <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
    <script type="text/javascript" src="../js/cutit.js"></script>
    <script type="text/javascript" src="../js/Drag.js"></script>
    <script type="text/javascript" src="../js/Resize.js"></script>
    <%--<script type="text/javascript" src="../js/jquery.js"></script>--%>
</head>

<body>
<div id="layout">
    <jsp:include page="../include/nav.jsp"/>
   <%-- <script type="text/javascript">
        alert("${empty sessionScope.islogin}");
    </script>--%>
	 <div class="titleLogin"><a href="http://www.mmfoo.com" style="color:#FF0099;">首页</a> > 修改我的头像</div>
    <c:choose>
        <c:when test="${sessionScope.islogin eq 'Y' }">
            <%--等用户还要判断登录的用户是否为空间所属者本人--%>
            <div class="beautyTop beautyLlk_window">
                <h2>每张图片可上载5M.</h2>

                <div class="uploading"><input type="text" name="loading" id="uploadimgname"><%--<a href="">&lt;%&ndash;浏览&ndash;%&gt;</a>--%><a
                        onclick="window.open('/pages/upload_img.jsp?elementid=uploadimgname','_blank','height=200, width=370, top=100, left=500, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no');"
                        href="javascript:return false">上传</a></div>
                <div class="show_pic">
                    <div class="1pic_show">

                        <c:choose>
                            <c:when test="${empty userimg_upload}"></c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${empty param.imgchanel}">
                                         <script type="text/javascript">
                                            alert("恭喜您，激活成功,请上传您的logo");
                                        </script>
                                    </c:when>
                                    <c:otherwise>

                                    </c:otherwise>
                                </c:choose>
                                <table width="843" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="467" height="350px">
                                            <div class="cut">
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
                                            </div>
                                            <div class="But1"><a href="javascript:getrec()">完成</a><a
                                                    href="<%=basePath%>" class="cancel">取消</a></div>
                                        </td>
                                        <td align="center" class="preview">
                                            <h2>预览头像</h2>

                                            <div id="viewDiv"
                                                 style="width:135px; height:145px; border:1px solid #cccccc;"></div>
                                            <div class="preview_But"><%--<a href="">预览</a>--%></div>
                                        </td>
                                    </tr>
                                </table>

                                <script type="text/javascript">

                                    var ic = new ImgCropper("bgDiv", "dragDiv", "../images/upload/${ userimg_upload}", {
                                        Width: 220, Height: 220, Color: "#000",
                                        Resize: true,
                                        Right: "rRight", Left: "rLeft", Up:    "rUp", Down: "rDown",
                                        RightDown: "rRightDown", LeftDown: "rLeftDown", RightUp: "rRightUp", LeftUp: "rLeftUp",
                                        Preview: "viewDiv"
                                    });
                                </script>
                                <script type="text/javascript" src="../js/jquery-1.4.4.min.js"></script>


                            </c:otherwise>
                        </c:choose>


                    </div>
                </div>

            </div>

        </c:when>
        <c:otherwise>
            <%--没有登录--%>
             <script type="text/javascript">
                 alert('抱歉，您尚未登录');
                 window.location="<%=basePath%>/login.html";
             </script>

        </c:otherwise>
    </c:choose>


    <jsp:include page="../include/footer.jsp"/>
</div>
</div>
<script type="text/javascript">
    String.prototype.trim = function() {
        return this.replace(/(^\s*)|(\s*$)/g, "");
    }
    function getrec() {
        //   var recparm= $("#viewDiv img").css("clip");
        var pic_x = $("#dragDiv").css("left").replace("px", "");
        var pic_y = $("#dragDiv").css("top").replace("px", "");
        var pic_width = $("#dragDiv").css("width").replace("px", "");
        var pic_heigth = $("#dragDiv").css("height").replace("px", "");
        $("#vvvv").attr("value", pic_x + "," + pic_y + "," + pic_width + "," + pic_heigth);
        $.post("/user/user_DrawImg.action?ts=" + new Date(), {pic_x:pic_x,pic_y:pic_y,pic_width:pic_width,pic_heigth:pic_heigth}, function(picbk) {
            picbk = picbk.trim();
            if (picbk == "0") {
                alert("用戶没有上传用来裁剪的图片,请上传！");
                return;
            }
            if (picbk == "2") {
                alert("用戶没有上传的图片参数不正确!");
                return;
            }
            if (picbk == "3") {
                alert("抱歉您未登录，图片裁剪视失败!");
                return;
            } else {
            <%--$("#pic_cuted").attr("src", "<%=basePath%>/images/thumb/" + picbk + "?ts=" + new Date());--%>
                alert("操作成功");
                 window.location="<%=basePath%>/user-${user_id_userspace}-1.html";
                return;
            }

        });
    }
</script>
</body>
</html>
