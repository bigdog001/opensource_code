<%@page import="java.io.File"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="bigdog" uri="/bigdog-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>浏览目录</title>

        <link href="./res/base/jQueryFileTree/jqueryFileTree.css" rel="stylesheet" type="text/css">

        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script type="text/javascript" src="./res/base/jQueryFileTree/jqueryFileTree.js"></script>
        <script type="text/javascript" src="./res/base/ok.js"></script>


        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">     
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

        <link rel=stylesheet href="./res/base/codemirror/doc/docs.css">
        <link rel="stylesheet" href="./res/base/codemirror/lib/codemirror.css">
        <link rel="stylesheet" href="./res/base/codemirror/addon/fold/foldgutter.css" />
        <script src="./res/base/codemirror/lib/codemirror.js"></script>
        <script src="./res/base/codemirror/addon/fold/foldcode.js"></script>
        <script src="./res/base/codemirror/addon/fold/foldgutter.js"></script>
        <script src="./res/base/codemirror/addon/fold/brace-fold.js"></script>
        <script src="./res/base/codemirror/addon/fold/xml-fold.js"></script>
        <script src="./res/base/codemirror/addon/fold/markdown-fold.js"></script>
        <script src="./res/base/codemirror/addon/fold/comment-fold.js"></script>
        <script src="./res/base/codemirror/mode/javascript/javascript.js"></script>
        <script src="./res/base/codemirror/mode/xml/xml.js"></script>
        <script src="./res/base/codemirror/mode/markdown/markdown.js"></script>


        <style type="text/css">            
            .CodeMirror {
              /* Set height, width, borders, and global font properties here */
                font-family: monospace;
                height: auto;
                border: 1px solid #ccc; /*add by jackqqxu*/
                font-family: Monaco, Menlo, Consolas, 'COURIER NEW', monospace;/*add by jackqqxu*/
                font-size: 12px;
            }
            .CodeMirror-scroll {
              /* Set scrolling behaviour here */
              overflow: auto;
              max-height: 800px;
              min-height: 200px
            }
        </style>

        <%
            String path_ = request.getRealPath("/");
            path_ = path_.replace(File.separator, File.separator + File.separator);
        %> 
    </head>
    <body>      
        <div class="container">

            <div class="row">
                <div id="filetree" class="col-md-2"> </div>
                <div id="" class="col-md-10">
                    <img src="./image/loading.gif" style="display: none" id="loadingimg"/>
                    <button onclick="openUpload()">上传文件</button>
                    <div id="filepathshow"></div><br/>
                    <textarea id="filecontent"   name="filecontent"></textarea>
                    <br/>
                    <input type="button" value="保存" onclick="updateFile()" id="submitbutton"/>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        var editor;

        $(document).ready(function() {
            $('#filetree').fileTree({
                root: '<%=path_%>',
                script: './res/base/jqueryFileTree/connectors/jqueryFileTree.jsp',
                expandSpeed: 300,
                collapseSpeed: 300,
                multiFolder: false
            }, function(file) {
                filepath = file;
                if (filepath.indexOf(".JPG") > 0 || filepath.indexOf(".jpg") > 0 || filepath.indexOf(".jar") > 0 || filepath.indexOf(".png") > 0 || filepath.indexOf(".jpg") > 0 || filepath.indexOf(".bmp") > 0 || filepath.indexOf(".gif") > 0 || filepath.indexOf(".class") > 0) {
                    alert("文件类型无法打开.");
                } else {
                    $("#filepathshow").html(file);
                    openfile(file);
                }
            });


            editor = CodeMirror.fromTextArea(document.getElementById("filecontent"), {
                lineNumbers: true,
                extraKeys: {"Ctrl-Space": function(cm) {
                        CodeMirror.simpleHint(cm, CodeMirror.javascriptHint);
                    }}
            });
            $(".CodeMirror-scroll").hover(function() {
                $(this).get(0).style.cursor = "text";
            });





        });


        function openfile(filepath) {
            var url_ = "openfile.action";
            $("#loadingimg").attr({"display": "block"});
            $("#submitbutton").attr({"disabled": "disabled"});
            $.ajax({url: url_,
                type: 'POST',
                data: {filepath: filepath},
                dataType: 'text',
                timeout: 3000,
                error: function() {
                    alert('Error loading document');
                },
                success: function(result) {
//            alert(result);
//            $("#filecontent").html(result);
//                    $("#filecontent").val(result);
                    editor.setValue(result);

                    $("#submitbutton").removeAttr("disabled");
                    $("#loadingimg").attr({"display": "none"});
                }

            });
        }


        function updateFile() {
//    var filepath = $("#filepath").val();
//            var filecontent = $("#filecontent").val();
            var filecontent = editor.getValue();

//            var filecontent =editor.toTextArea();
//            var filecontent =editor.getTextArea().value
//            
//            
//            alert(filecontent);
            var url_ = "updatefile.action";
//    alert(filepath);
//    alert(filecontent);
            $.ajax({url: url_,
                type: 'POST',
                data: {filepath: filepath, filecontent: filecontent},
                dataType: 'text',
                timeout: 3000,
                error: function() {
                    alert('Error loading document');
                },
                success: function(result) {
                    alert(result);
                }

            });
        }

    </script>
</html>