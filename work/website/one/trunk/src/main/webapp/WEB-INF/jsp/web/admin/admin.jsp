<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>admin console</title>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <script src="./res/base/ok.js"></script>
    <link rel="stylesheet" href="./res/base/css/admincss.css"/>
    <script>
        $(function () {
            $("#tabs").tabs({
                beforeLoad: function (event, ui) {
                    ui.jqXHR.error(function () {
                        ui.panel.html(
                                "Couldn't load this tab. We'll try to fix this as soon as possible. " +
                                        "If this wouldn't be a demo.");
                    });
                }
            });
        });
    </script>
</head>
<body>

<div id="tabs">
    <ul>
        <li><a href="ShowTab.action?tab=1">用户</a></li>
        <li><a href="ShowTab.action?tab=2">文章</a></li>
        <li><a href="ShowTab.action?tab=3">Tab 3</a></li>
        <li><a href="ShowTab.action?tab=4">Tab 4</a></li>
        <li><a href="ShowTab.action?tab=5">Tab 5</a></li>
    </ul>
</div>
</body>
</html>