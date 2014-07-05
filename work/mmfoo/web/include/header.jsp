<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2011-5-15
  Time: 22:15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/common.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/menu.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/thickbox.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/index.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/button.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/form_common.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/uni-form.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/default.uni-form.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/join_process.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/join_gete.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/dialog.css">
<LINK rel="stylesheet" type="text/css" href="<%=basePath%>/css/ext.css">
<link rel="stylesheet" href="<%=basePath%>/css/slide_grid.css" type="text/css" media="all">
<link rel="stylesheet" href="<%=basePath%>/css/slide_style.css" type="text/css" media="all">
<link href="<%=basePath%>/css/dock_common.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>/css/dock_index.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/sample.css?t=1"/>
<link rel="stylesheet" href="<%=basePath%>/css/style-demo2.css"/>
<link rel="stylesheet" href="<%=basePath%>/css/signUp2.css"/>
<link rel="stylesheet" href="<%=basePath%>/css/reveal.css"/>
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/thickbox_plus.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jqFancyTransitions.1.8.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.mouseslide-2.0.demo.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/script.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.viewline.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.reveal.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>

 



<script type="text/javascript">
    $(document).ready(function() {
        $("#viewline").viewline(
        {
            arrow_content:        false,
            vertical_align:        false,
            smart_width:         true,
            viewline_width:        900, /* 0 = window width (default) / Any int value */
            auto_play:            true,
            interval:            4000,
            pauseonover:        true,
            arrow_wrapper:        '#banner', /* null (default) / Any ID (#id) or Class (.class) */
            animation:            'fade', /* dropdown (default) / popup / fade */
            animation_in:        400,
            animation_out:        200,
            animation_delay:    0
        });
    });
</script>
<!--[if IE 6]>
<script src="DD_belatedPNG.js" mce_src="DD_belatedPNG.js"></script>
<script type="text/javascript"> /* EXAMPLE */ DD_belatedPNG.fix('.timer,.avatarPromote'); </script>
<![endif]-->
<!--[if lt IE 7]>
<script type="text/javascript" src="<%=basePath%>/js/ie6_script_other.js"></script>
<![endif]-->
<!--[if lt IE 9]>
<script type="text/javascript" src="<%=basePath%>/js/html5.js"></script>
<![endif]-->