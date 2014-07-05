<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="bigdog" uri="/bigdog-tags" %>
<!DOCTYPE html>
<!-- saved from url=(0016)http://e.360.cn/ -->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>浏览目录</title>
    <%--<link href="./css/a49b3710a007a22c.css" rel="stylesheet" type="text/css">--%>
    <link href="./js/jqueryFileTree/jqueryFileTree.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="./js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="./js/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="./js/jqueryFileTree/jqueryFileTree.js"></script>
    <script type="text/javascript" src="./js/ok.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#container_id').fileTree({
                root: '<%=request.getRealPath("/")%>',
                script: 'js/jqueryFileTree/connectors/jqueryFileTree.jsp',
                expandSpeed: 1000,
                collapseSpeed: 1000,
                multiFolder: false
            }, function (file) {
//                window.open('openfile.action?filepath='+file, '', 'height=600,width=800,top=90,left=50,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
                filepath = file;
                if (filepath.indexOf(".JPG") > 0 || filepath.indexOf(".jpg") > 0 || filepath.indexOf(".jar") > 0 || filepath.indexOf(".png") > 0 || filepath.indexOf(".jpg") > 0 || filepath.indexOf(".bmp") > 0 || filepath.indexOf(".gif") > 0 || filepath.indexOf(".class") > 0) {
                    alert("文件类型无法打开.");
                } else {
                    $("#filepathshow").html(file);
                    openfile(file);
                }
            });
        });
    </script>
    <style type="text/css">
        div {
            border: 1px;
            height: 100%;
        }

        .left {
            float: left;
            width: 300px;
            margin: 5 5 5 5px;
        }

        .right {
            float: left;
            width: 1000px;
        }

    </style>
</head>
<body>
<div>
    <div id="container_id" class="left"></div>
    <div id="" class="right">
        <img src="./image/t014042de8ea12aaadb.gif" style="display: none" id="loadingimg"/>
        <button onclick="openUpload()">上传文件</button>
        <div id="filepathshow"></div><br/>
        <textarea id="filecontent" cols="125" rows="30" name="filecontent"></textarea>
        <br/>
        <input type="button" value="保存" onclick="updateFile()" id="submitbutton"/>
    </div>

</div>
</body>
</html>