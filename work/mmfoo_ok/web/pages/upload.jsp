<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
    //活动列表页面，取出所有的活动信息
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    basePath=basePath.replace(":80","");
%>
<c:choose>
    <c:when test="${empty param.elementid}">
        <c:set var="elementid" value="1"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="elementid" value="${param.elementid}"></c:set>
    </c:otherwise>
</c:choose>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>请上传图像</title>

    <style>
        .prog-border {
            height: 15px;
            width: 205px;
            background: #fff;
            border: 1px solid #000;
            margin: 0;
            padding: 0;
        }

        .prog-bar {
            height: 11px;
            margin: 2px;
            padding: 0px;
            background: #178399;
            font-size: 10pt;
        }

        body {
            font-family: Arial, Helvetica, sans-serif;
            font-size: 10pt;
        }
    </style>

    <script src="<%=basePath%>/js/prototype.js" type="text/javascript"></script>
    <script type="text/javascript" language="JavaScript">
        var updater = null;

        function startStatusCheck() {
            var o = document.getElementById("file_uppl_");
            var pos = o.value.lastIndexOf("\\");
            var filename = o.value.substring(pos + 1);//文件名
             var _parentWin = window.opener;
           _parentWin.document.getElementById("${elementid}").value=filename;
            //设置上传按钮为不可用状态，避免多次提交
            $('submitButton').disabled = true;
            //创建周期性发送请求的Ajax对象
            updater = new Ajax.PeriodicalUpdater(
                    'status',
                    '/fileupload',
            {asynchronous:true, frequency:1, method: 'get', parameters: 'c=status&t=' + new Date(), onFailure: reportError});
        }
        //出错时处理方法
        function reportError(request) {
            $('submitButton').disabled = false;

            $('status').innerHTML = '<div class="error"><b>Error communicating with server. Please try again.</b></div>';
        }
        //上传完毕后,取消周期性获取进度状态，将最终的状态显示在客户端
        function killUpdate(message) {

            $('submitButton').disabled = false;
            if (null != updater) {
                //停止刷新获取进度
                updater.stop();
            }
            if (message != '')//如果有错误信息，则显示出来
            {
                $('status').innerHTML = '<div class="error"><b>Error processing results: ' + message + '</b></div>';
            }
            else//如果没有错误信息
            {
                //获取上传文件的完成状态，显示到客户端
                new Ajax.Updater('status',
                        '/fileupload',
                {asynchronous:true, method: 'get', parameters: 'c=status', onFailure: reportError});
            }
        }
    </script>
</head>
<body>

<!-- 这个是隐藏的<ifame>作为表单提交后处理的后台目标-->
<iframe id='target_upload' name='target_upload' src=''
        style='display: none'></iframe>
<!-- 当表单提交后,调用startStatusCheck()方法-->


<form enctype="multipart/form-data" name="fileform" method="post"
      action="/fileupload" onsubmit="startStatusCheck();"
      target="target_upload">
    <BR>
   <font color="red"> 请选择上传尺寸合适的图片文件,并保证两次上传的文件不同名：</font>
    <BR>
    <input name="importFile" type="file" id="file_uppl_">
    <input id="submitButton" type="submit" value="上传"/>
</form>
<!-- 这里显示进度条 -->
<div id="status"></div>
</body>
</html>
