<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    basePath=basePath.replace(":80","");
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <title>文档上传</title>
  <link href="<%=basePath%>/css/default.css" rel="stylesheet" type="text/css" />
   	<script type="text/javascript" src="<%=basePath%>/js/swfupload/swfupload.js"></script>
   	<script type="text/javascript" src="<%=basePath%>/js/swfupload/swfupload.queue.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/swfupload/fileprogress.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/swfupload/handlers.js" charset="gb2312"></script>
    

   	<!-- 初始化swfupload 对象-->
   <script type="text/javascript">
		var upload1, upload2;

		window.onload = function() {
			upload1 = new SWFUpload({
				// Backend Settings
				upload_url: "/user/PictureAction_FileUpload.action", //这里的action不可以删除
				post_params: {"picSESSID" : "songhao"},
				file_post_name: "file",
				// File Upload Settings
				file_size_limit : "102400",	// 100MB  //文件大小设定
				file_types : "*.gif;*.jpg;*.png",//文件上传类型现在
				file_types_description : "All Files",
				file_upload_limit : "10",
				file_queue_limit : "0",

				// Event Handler Settings (all my handlers are in the Handler.js file)
				file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,

				// Button Settings   button的图片
				button_image_url : "<%=basePath%>/images/XPButtonUploadText_61x22.png",
				button_placeholder_id : "spanButtonPlaceholder1",
				button_width: 61,
				button_height: 22,
				
				// Flash Settings
				flash_url : "<%=basePath%>/js/swfupload/swfupload.swf",
				

				custom_settings : {
					progressTarget : "fsUploadProgress1",
					cancelButtonId : "btnCancel1"
				
				},
				
				// Debug Settings
				debug: false
			});
	     }
	    
	</script>
  </head>
  
  <body style="width: 515px; ">
  
  <div id="content">
	<h2>上传文档</h2>
    <s:form action="/user/PictureAction_FileUpload" method="post" name="thisform" namespace="/" enctype="multipart/form-data">
    	<p>本界面只能上传jpg、png、gif文件</p>
		<table>
			<tr valign="top">
				<td>
					<div>
						<div class="fieldset flash" id="fsUploadProgress1">
							<span class="legend">文件上传（100MB）</span>
						</div>
						<div style="padding-left: 5px;">
							
							<span id="spanButtonPlaceholder1"></span>
							<input id="btnUpload1" type="button" value="开始上传" onclick="upload1.startUpload()"  style="margin-left: 2px; height: 22px; font-size: 8pt;" />			
							<input id="btnCancel1" type="button" value="中断上传" onclick="cancelQueue(upload1);" disabled="disabled" style="margin-left: 2px; height: 22px; font-size: 8pt;" />
							<br />
						</div>
					</div>
				</td>
				</tr>
		</table>
    </s:form>
    </div>
  </body>

</html>
