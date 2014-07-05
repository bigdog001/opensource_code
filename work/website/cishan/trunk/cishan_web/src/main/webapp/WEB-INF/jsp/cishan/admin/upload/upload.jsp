<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<! DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>File Upload</title>
</head>
<body>
<s:form action="fileUpload.action" method="post" enctype="multipart/form-data">
    <s:file name="upload" label="File" />
    <s:submit />
</s:form>
</body>
</html>