<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>文章管理</title>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <link rel="stylesheet" href="./res/base/css/admincss.css"/>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="./res/base/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="./res/base/ok.js"></script>

    <style>
        #accordion-resizer-2 {
            padding: 10px;
            width: 1290px;
            height: 550px;
        }
        div#users-contain table {
            margin: 1em 0;
            border-collapse: collapse;
            width: 100%;
        }
        div#users-contain table td, div#users-contain table th {
            border: 1px solid #eee;
            padding: .2em 2px;
            text-align: left;
        }
    </style>
</head>
<body>
<div id="accordion-resizer-2" class="ui-widget-content">
    <div id="accordion-2">
        <table id="users" class="ui-widget ui-widget-content">
            <thead>
            <tr class="ui-widget-header ">
                <th>---</th>
                <th>
                    <c:choose>
                        <c:when test="${id eq 0}">
                          增加新数据
                        </c:when>
                        <c:otherwise>
                            更新数据 ,ID:${id}
                        </c:otherwise>
                    </c:choose>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>标题</td>
                <td><input id="title" name="title" type="text" value="<c:if test="${id != 0}"><s:property value="title"/></c:if>"/></td>
            </tr>
            <tr>
                <td>图片</td>
                <td><input id="imgname" name="imgname" type="text" value="<c:if test="${id != 0}"><s:property value="imgname"/></c:if>"/><c:if test="${id != 0}"><a href="./upload/<s:property value='imgname'/>" target="_blank">查看图片</a></c:if></td>
            </tr>
            <tr>
                <td>数据描述</td>
                <td><input id="description" name="description" type="text" value="<c:if test="${id != 0}"><s:property value="description"/></c:if>"/></td>
            </tr>
            <tr>
                <td>排序</td>
                <td><input id="catagory_order" name="catagory_order" type="text" value="<c:if test="${id != 0}"><s:property value="Catagory_order"/></c:if>"/></td>
            </tr>
            <tr>
                <td>正文</td>
                <td><textarea name="content" id="content"><c:if test="${id != 0}"><s:property value="content"/></c:if></textarea></td>
            </tr>

            <tr>
                <td><button onclick="persistArticle('<s:property value="id"/>')">提交</button></td>
                <td></td>
            </tr>

            <tr class="ui-widget-header ">
                <th>---</th>
                <th>---</th>

            </tr>
            </tbody>


        </table>
    </div>
</div>
<script type="text/javascript">
    editor = CKEDITOR.replace('content');
</script>
</body>
</html>