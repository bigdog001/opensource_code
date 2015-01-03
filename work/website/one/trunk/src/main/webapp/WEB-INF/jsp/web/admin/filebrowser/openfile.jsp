<%--
  Created by IntelliJ IDEA.
  User: bigdog
  Date: 10/4/13
  Time: 12:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${filepath}</title>

    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <link rel="stylesheet" href="./res/base/css/admincss.css"/>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="./res/base/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="./res/base/ok.js"></script>
</head>
<body>

${filepath}<br/><br/>

<form>
<input style="display: none" id="filepath" value="${filepath}" />
   <textarea id="content" cols="150" rows="30" name="content">${filecontent}</textarea>
<input type="button" value="go" onclick="updateFile()" >
</form>


<script type="text/javascript">
     editor = CKEDITOR.replace('content',{
     on: {
     instanceReady: function() {
     this.setMode('source');
     }},
     startupMode: 'wysiwyg'
     });
     alert('${filecontent}');
//     alert('---->'+editor.getData());
    editor.setData('${filecontent}', function() {
        this.checkDirty();
        this.updateElement();
    });
</script>


</body>
</html>