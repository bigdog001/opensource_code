<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<fmt:setLocale value="zh"/>
<%
    //活动列表页面，取出所有的活动信息
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    basePath = basePath.replace(":80", "");
%>
<c:choose>
    <c:when test="${empty param.user_id}">
        <c:set var="user_id_userspace" value="1"/>
    </c:when>
    <c:otherwise>
        <c:set var="user_id_userspace" value="${param.user_id}"/>
    </c:otherwise>
</c:choose>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><c:choose>
        <c:when test="${empty userbean}"></c:when>
        <c:otherwise>${userbean.nickname} 个人网站</c:otherwise>
    </c:choose>美分网MMFOO |用户档案</title>
    <meta name="keywords" content="美分网 美女 美眉 校园 阳光 大连 北京 全国 " />
    <meta name="description" content="美分网是大连唯一的个人风采展现网站，在大连地区各高校校园具有广泛的影响力。本着“敢于展现，娱乐精神”的品牌理念，美分网赢得了数十万大连新青年的青睐。" />        
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <meta name="GENERATOR" content="Macromedia Dreamweaver MX"/>
    <meta http-equiv="Pics-label"
          Contect="(PICS－1.1'http://www.rsac.org/ratingsv01.html' I gen comment 'RSACi North America Sever' by 'inet@microsoft.com' for 'http://www.microsoft.com' on '1997.06.30T14:21－0500' r(n0 s0 v0 l0))"/>
    
    <meta name="Robots" content="all"/>
    <meta http-equiv="MSThemeCompatible" content="Yes"/>
    <meta name="MSSmartTagsPreventParsing" content="TRUE"/>
   <link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico" type="image/x-icon" />
    <link href="../css/style.css" rel="stylesheet" type="text/css" media="screen"/>
   <link type="text/css" rel="stylesheet" href="../css/calendar.css" >
<script type="text/javascript" src="../js/calendar.js" ></script>
<script type="text/javascript" src="../js/calendar-zh.js" ></script>
<script type="text/javascript" src="../js/calendar-setup.js"></script>
    <script type="text/javascript" src="../js/jquery.js"></script>
    <script type="text/javascript" src="../js/ok.js"></script>
    <script type="text/javascript" src="../js/tabc.js"></script>

    <style type="text/css">
        /*日记的选项卡*/
        .bjda_nav li {
            margin-top: 7px;
        }

        div.tab_zzjs {
            float: left;
            width: 168px;
            margin-top: 15px;
        }

        div.tab_zzjs ul {
            margin: 0;
            padding: 0;
            list-style: none
        }

        div.tab_zzjs ul li {
            float: left;
            padding: 0 5px 2px 0;
            color: #333333;
        }

        div.tab_zzjs ul li.s {
            padding-bottom: 0;
        }

        div.tab_zzjs a {
            width: 158px;
            display: block;
            font-family: "宋体";
            font-size: 14px;
            font-weight: 700;
            color: #333333;
            padding: 2px 5px;
            height: 29px;
            line-height: 29px;
        }

        div.tab_zzjs li.s a {
            background: #FFCFE4;
            color: #333333;
            height: 27px;
            padding-bottom: 2px;
            font-weight: bold;
            cursor: default;
        }

        div.tab_zzjs a:hover {
            background: #FFCFE4;
            color: #333333;
        }

        div.tab_zzjs li.s a:hover {
            text-decoration: none;
        }

        div.content {
            border: solid 1px #CCCCCC;
            width: 700px;
            padding: 5px 15px 50px;
            float: left;
            margin-top: 16px;
        }

        .goback {
            font-size: 16px;
            font-weight: 700;
            font-family: "宋体";
            line-height: 40px;
            border-bottom: 1px solid #CCCCCC;
            color: #333333;
            margin-bottom: 15px;
        }

        #c_contact span {
            line-height: 24px;
            font-size: 12px;
        }

        .box {
            padding-left: 15px;
            margin: 10px 0
        }

        .font14 {
            font-size: 14px;
            font-family: Verdana, "宋体", sans-serif;
            line-height: 24px;
        }

        .borderOn {
            width: 400px;
            height: 60px
        }

        .experience_borderOn {
            width: 600px;
            height: 300px
        }

        .button {
            padding: 20px 0 0 280px;
        }

        .butSave {
            background: url(../images/fileedit.jpg) no-repeat;
            width: 78px;
            height: 27px;
            line-height: 27px;
            text-align: center;
            color: #ffffff
        }

        .workLable {
            font-size: 14px;
            color: #666666;
            font-family: "宋体";
            font-weight: 700;
            padding: 0 0 0 50px
        }

        .bd_1 {
            border: 1px solid #A0CBED;
            width: 250px;
            height: 26px;
            font-size: 14px;
            font-family: "宋体";
            line-height: 26px;
            margin: 10px 20px 10px 0px;
        }

        .c {
            margin: 0px;
        }

        .workLable .gC1 {
            width: 150px;
            height: auto;
            margin: 0
        }

        .workLable .title {
            line-height: 30px;
        }

        .imgXing {
            border: 1px solid #333333
        }

        .pLine {
            margin: 8px 0;
        }

        .pLine select {
            height: 22px;
            width: 50px
        }

        .font18 {
            font-size: 14px;
            font-family: Verdana, "宋体", sans-serif;
            line-height: 36px;
        }

        .l {
            float: left;
            margin-right: 40px;
        }
    </style>
</head>

<body onLoad="init_wwwzzjsnet('tab_zzjs')">
<mfo:UpdateSpace_see_cnt user_id="${user_id_userspace}"/>
<mfo:getUserById user_id="${user_id_userspace}"/>

<div id="topx">
    <div class="w994">
        <div class="logo left"><a href="<%=basePath%>" title="美分网"><img src="<%=basePath%>/images/logo.gif" width="115"
                                                                        height="41" alt="美分网"/></a></div>
        <div class="mainmenu_ss left">
            <div class="mainmenu left">
                <ul>

                    <c:choose>
                        <c:when test="${sessionScope.islogin eq 'Y' }">
                            <%--等用户还要判断登录的用户是否为空间所属者本人--%>

                            <li><a href="<%=basePath%>/user-${userbean.user_id}-4.html"
                                    <c:choose>
                                        <c:when test="${param.navs==4}">class="aurr_"</c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                    >我的美分网</a></li>


                        </c:when>
                        <c:otherwise>
                            <%--没有登录--%>

                        </c:otherwise>
                    </c:choose>


                    <li><a href="<%=basePath%>/user-${user_id_userspace}-1.html">首页</a></li>
                    <li><a href="<%=basePath%>/userfile-${user_id_userspace}-2.html" class="aurr_">档案</a></li>
                    <li><a href="<%=basePath%>/usershow-${user_id_userspace}-3.html">美分展示</a></li>
                </ul>
            </div>
            <div class="topsearch right">

                  <c:choose>
                    <c:when test="${sessionScope.islogin eq 'Y' }">
                        <%--等用户还要判断登录的用户是否为空间所属者本人--%>
                        <c:choose>
                            <c:when test="${sessionScope.userbean.user_id==user_id_userspace}">
                                <div class="acc left">
                                    <ul id=nav>
                                        <li style="background:url(<%=basePath%>/images/icon_1.gif) right no-repeat ;"><a
                                                href="#">账号</a>
                                            <ul style=" padding-top:5px;">
                                                <li><img
                                                        src="<%=basePath%>/images/<c:choose><c:when test="${empty userbyid.userthumb}">thumb/avatar_samll.jpg</c:when><c:otherwise>${userbyid.userthumb}</c:otherwise></c:choose>"
                                                        width="49" height="52"
                                                        align="left"
                                                        style=" padding-right:5px;"/>
                                                    <span>范冰冰</span></li>
                                                <li><a href="<%=basePath%>/userfile-${user_id_userspace}-2.html"
                                                       style="border:none">账号设置</a></li>
                                                <li><a href="javascript:logout()"
                                                       style="border:none">退出账号</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <%--虽然登录了 但是登录者不是空间的主人--%>
                            </c:otherwise>
                        </c:choose>

                    </c:when>
                    <c:otherwise>
                        <%--没有登录--%>
                        <div class="acc left">
                            <ul>
                                <li>
                                    <a href="javascript:loginit()">登陆</a>
                                </li>
                            </ul>

                        </div>
                        <script type="text/javascript">
                            function loginit() {
                                var url_now = window.location.href;
                                window.location = '<%=basePath%>/pages/login.jsp?url_target=' + url_now;
                            }
                        </script>
                    </c:otherwise>
                </c:choose>

                <form action="<%=basePath%>/user/user_UserSearche.action" method="post" name="mmfoosearch">
                    <div class="topss left">搜索：
                        <input type="text" name="truename" value="找MM、找活动" onfocus="if(value=='找MM、找活动'){value='${param.truename}'}" onblur="if(value==''){value='找MM、找活动';}"/><input type="image" class="submit"
                                                                                             value="搜索"
                                                                                             src="<%=basePath%>/images/submit_q.gif"
                                                                                             onclick="javascript:document.mmfoosearch.submit()"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="w994">
    <div class="peo left">
             <dl>
            <dt><a href="#" title="#"><img
                    src="<%=basePath%>/images/<c:choose><c:when test="${empty userbyid.userimg}">avatar_big.jpg</c:when><c:otherwise>${userbyid.userimg}</c:otherwise></c:choose>"
                    alt="#"/></a>

                <c:choose>
                    <c:when test="${islogin eq 'Y' }">
                        <%--等用户还要判断登录的用户是否为空间所属者本人--%>
                        <c:choose>
                            <c:when test="${sessionScope.userbean.user_id==user_id_userspace}">
                                <a href="/pages/beautyLLk_sc.jsp?imgchanel=A"
                                   style=" color:#EC008C; font-size:12px; font-family:'宋体'; line-height:32px;margin-left:35px;">修改我的头像</a>
                            </c:when>
                            <c:otherwise>
                                <%--虽然登录了 但是登录者不是空间的主人--%>
                            </c:otherwise>
                        </c:choose>

                    </c:when>
                    <c:otherwise>
                        <%--没有登录--%>

                    </c:otherwise>
                </c:choose>

            </dt>
            <dd><strong>${userbyid.truename}</strong> <i><%--在线--%></i>
                <c:choose>
                    <c:when test="${sessionScope.islogin eq 'Y' }">
                        <%--等用户还要判断登录的用户是否为空间所属者本人--%>
                        <c:choose>
                            <c:when test="${userbean.user_id==user_id_userspace}">

                                <a href="" style=" color:#EC008C;margin-left:10px;">编辑昵称</a>
                            </c:when>
                            <c:otherwise>
                                <%--虽然登录了 但是登录者不是空间的主人--%>
                            </c:otherwise>
                        </c:choose>

                    </c:when>
                    <c:otherwise>
                        <%--没有登录--%>

                    </c:otherwise>
                </c:choose>

            </dd>
            <dd><%=basePath%>/user-${userbyid.user_id}-1.html</dd>
            <dd class="txt">${userbyid.city}</dd>
            <dd class="txt">参加的活动</dd>
            <dd class="txt">
                <%--  查出某人所参加的所有活动 --%>
                <mfo:GetUserHds size="5" page="1" user_id="${userbyid.user_id}"/>
                <c:choose>
                    <c:when test="${fn:length(userhds)>0}">
                        <c:forEach var="userhd" items="${userhds}">
                            <img src="<%=basePath%>/images/upload/${userhd.hd_thumb}" width="32" height="32"
                                 alt="${userhd.hdabstract}" title="${userhd.hdabstract}"/>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        未参加任何活动
                    </c:otherwise>
                </c:choose>

                <%--<img src="<%=basePath%>/images/none.gif" width="32" height="32"/>--%>
                <%--<img src="<%=basePath%>/images/none.gif" width="32" height="32"/>--%>
                <%--<img src="<%=basePath%>/images/none.gif" width="32" height="32"/>--%>
            </dd>
            <dd>
                <c:choose>
                <c:when test="${sessionScope.islogin eq 'Y' }">
                    <%-- 自己不能关注自己--%>
                <c:choose>
                <c:when test="${userbyid.email eq userbean.email}"> <a
                    href="javascript:javascript:alert('不能关注自己！')"><img src="images/icon1.jpg"/></a> </c:when>
                <c:otherwise>
                <a href="javascript:javascript:see_user('${userbean.user_id}','${userbyid.user_id}')"><img
                        src="images/icon1.jpg"/></a>
                </c:otherwise>
                </c:choose>
                </c:when>
                <c:otherwise>
                <a href="javascript:javascript:alert('请先登陆！');window.location='<%=basePath%>/pages/login.jsp?url_target='+window.location.href"><img
                        src="images/icon1.jpg"/></a>
                </c:otherwise>
                </c:choose>

                <%--短信功能选注释--%>
                <%--  <a href="#"><img src="images/icon2.jpg"/></a></dd>--%>
        </dl>
    </div>
    <div class="peo_concern right">
          <ul>
            <li style="border-right:1px #ccc solid">
                <strong>${userbyid.point_cnt}</strong><span>粉丝</span></li>
            <li style="border-right:1px #ccc solid">
                <strong>
                    <%--取我关注的人的总数--%>
                    ${i_see}
                </strong><span>关注</span></li>
            <li><strong>
                <%--取我发表的微波的总数--%>
                ${message_total}
            </strong><span>微博</span></li>
        </ul>
    </div>
</div>

<div id="main_body">

<div class="bjda_bt"><span>
    <c:choose>
                <c:when test="${sessionScope.islogin eq 'Y' }">
                    <%-- 自己不能关注自己--%>
                <c:choose>
                <c:when test="${userbyid.email eq userbean.email}"><a href="<%=basePath%>/user-${user_id_userspace}-1.html"><img src="<%=basePath%>/images/bn_tc.gif" width="78" height="27"/></a> </c:when>
                <c:otherwise>

                </c:otherwise>
                </c:choose>
                </c:when>
                <c:otherwise>

                </c:otherwise>
                </c:choose>
</span></div>
<div class="bjda_nav left">
   

    <div class="tab_zzjs" id="tab_zzjs">
        <ul>
            <li class="s"><a href="#" id="h_contact">联络信息</a></li>
            <li><a href="#" id="h_hobby">爱好</a></li>
            <li><a href="#" id="h_experience">经历</a></li>
            <li><a href="#" id="h_works">作品</a></li>
            <li><a href="#" id="h_show">展览</a></li>
            <li><a href="#" id="h_interview">媒体采访</a></li>
            <li><a href="#" id="h_activity">出席活动</a></li>
            <li><a href="#" id="h_cultivate">培训</a></li>
            <li><a href="#" id="h_prize">获奖</a></li>
            <li><a href="#" id="h_education">学历</a></li>
            <li><a href="#" id="h_base">基础信息</a></li>
            <%--<li><a href="#" id="h_workLabel">工作标签</a></li>--%>
        </ul>
    </div>
</div>
<!--内容区-->
<div class="content">
<div id="c_contact">
    <h3 class="goback">联络信息</h3>
    <span>特别注意：如你是美女/型男，或从事模特/演员等行业，请勿留个人联系信息!如需合作请使用美分站内短信联络！进而提防恶意骚扰，保护个人人身财产安全！</span>
	<div class="box">
        <h4 class="font14">昵称</h4>
        <textarea id="nickname" name="" class="borderOn">${userbyid.nickname}</textarea>
    </div>
    <div class="box">
        <h4 class="font14">Email</h4>
        <textarea id="email" name="" class="borderOn" onBlur="javascript:alert('此email作为您的登录email，请谨慎修改!');">${userbyid.email}</textarea>
    </div> 
    <div class="box">
        <h4 class="font14">MSN</h4>
        <textarea id="msn" name="" class="borderOn">${userbyid.user_msn}</textarea>
    </div>
    <div class="box">
        <h4 class="font14">手机</h4>
        <textarea id="phone" name="" class="borderOn">${userbyid.mobile}</textarea>
    </div>
    <div class="box">
        <h4 class="font14">QQ</h4>
        <textarea id="qqnumber" name="" class="borderOn">${userbyid.qqnumber}</textarea>
    </div>
    <%--<div class="box">
        <h4 class="font14">其他</h4>
        <textarea id="contact_other" name="" class="borderOn"></textarea>
    </div>--%>
    <div class="button">
        <input type="button" onFocus="this.blur()" value="保 存" class="butSave" onClick="UpdateLianluoXinxi()">
    </div>
</div>
<!--联络信息结束-->
<div id="c_hobby">
    <h3 class="goback">爱好</h3>

    <div class="box">
        <h4 class="font14">喜欢的音乐</h4>
        <textarea id="lovemusic" name="" class="borderOn">${userbyid.lovemusic}</textarea>
    </div>
    <div class="box">
        <h4 class="font14">喜欢的明星</h4>
        <textarea id="lovestar" name="" class="borderOn">${userbyid.lovestar}</textarea>
    </div>
    <div class="box">
        <h4 class="font14">喜欢的电影</h4>
        <textarea id="lovemovie" name="" class="borderOn">${userbyid.lovemovie}</textarea>
    </div>
    <div class="box">
        <h4 class="font14">喜欢的电视</h4>
        <textarea id="lovetv" name="" class="borderOn">${userbyid.lovetv}</textarea>
    </div>
    <div class="box">
        <h4 class="font14">喜欢的运动</h4>
        <textarea id="lovesport" name="" class="borderOn">${userbyid.lovesport}</textarea>
    </div>
    <div class="box">
        <h4 class="font14">喜欢看的书</h4>
        <textarea id="lovereading" name="" class="borderOn">${userbyid.lovereading}</textarea>
    </div>
   <%-- <div class="box">
        <h4 class="font14">其他</h4>
        <textarea id="hobby_other" name="" class="borderOn"></textarea>
    </div>--%>
    <div class="button">
        <input type="button" onFocus="this.blur()" value="保 存" class="butSave" onClick="Updateaihao()">
    </div>
</div>
<!--爱好结束-->
<div id="c_experience">
    <h3 class="goback">经历</h3>

    <div class="box">
        <h4 class="font14">经历描述</h4>
        <textarea id="jinli" name="" class="borderOn experience_borderOn">${userbyid.jinli}</textarea>
    </div>
    <div class="button">
        <input type="button" onFocus="this.blur()" value="保 存" class="butSave" onClick="UpdateJingli()">
    </div>
</div>
<!--经历结束-->
<div id="c_works">
    <h3 class="goback">作品</h3>

    <div class="box">
        <h4 class="font14">作品描述</h4>
        <textarea id="zuopin" name="" class="borderOn experience_borderOn">${userbyid.zuopin}</textarea>
    </div>
    <div class="button">
        <input type="button" onFocus="this.blur()" value="保 存" class="butSave" onClick="UpdateZuopin()">
    </div>
</div>
<!--作品结束-->
<div id="c_show">
    <h3 class="goback">展览</h3>

    <div class="box">
        <h4 class="font14">展览描述</h4>
        <textarea id="zhanlan" name="" class="borderOn experience_borderOn">${userbyid.zhanlan}</textarea>
    </div>
    <div class="button">
        <input type="button" onFocus="this.blur()" value="保 存" class="butSave" onClick="UpdateZhanlan()">
    </div>
</div>
<!--展览结束-->
<div id="c_interview">
    <h3 class="goback">媒体采访</h3>

    <div class="box">
        <h4 class="font14">媒体采访描述</h4>
        <textarea id="meiticaifang" name="" class="borderOn experience_borderOn">${userbyid.meiticaifang}</textarea>
    </div>
    <div class="button">
        <input type="button" onFocus="this.blur()" value="保 存" class="butSave" onClick="UpdateMeiticaifang()">
    </div>
</div>
<!--媒体采访结束-->
<div id="c_activity">
    <h3 class="goback">出席活动</h3>

    <div class="box">
        <h4 class="font14">出席活动描述</h4>
        <textarea id="chuxihuodong" name="" class="borderOn experience_borderOn">${userbyid.chuxihuodong}</textarea>
    </div>
    <div class="button">
        <input type="button" onFocus="this.blur()" value="保 存" class="butSave" onClick="UpdateChuxihuodong()">
    </div>
</div>
<!--出席活动结束-->
<div id="c_cultivate">
    <h3 class="goback">培训</h3>

    <div class="box">
        <h4 class="font14">相关培训描述</h4>
        <textarea id="peixun" name="" class="borderOn experience_borderOn">${userbyid.peixun}</textarea>
    </div>
    <div class="button">
        <input type="button" onFocus="this.blur()" value="保 存" class="butSave" onClick="UpdatePeixun()">
    </div>
</div>
<!--培训结束-->
<div id="c_prize">
    <h3 class="goback">获奖</h3>

    <div class="box">
        <h4 class="font14">获奖情况描述</h4>
        <textarea id="huojiang" name="" class="borderOn experience_borderOn">${userbyid.huojiang}</textarea>
    </div>
    <div class="button">
        <input type="button" onFocus="this.blur()" value="保 存" class="butSave" onClick="UpdateHuojiang()">
    </div>
</div>
<!--获奖结束-->
<div id="c_education">
    <h3 class="goback">学历</h3>

    <div class="box">
        <h4 class="font14">学历情况描述</h4>
        <textarea id="education" name="" class="borderOn experience_borderOn">${userbyid.education}</textarea>
    </div>
    <div class="button">
        <input type="button" onFocus="this.blur()" value="保 存" class="butSave" onClick="UpdateEducation()">
    </div>
</div>
<!--学历结束-->
<div id="c_base">
    <h3 class="goback">基础信息</h3>

    <form method="" name="">
    	<div class="pLine" style="border-bottom:1px dashed #cccccc;padding:0 0 20px 0;">
            <h4 class="font18">个性化域名修改：</h4>
            <input type="text" value="" id="" name="" class="bd_1 c" style="width:380px">
        </div>
        <div class="pLine">
            <h4 class="font18">出生日期：</h4>
            <input type="text" id="EntTime" name="EntTime" onClick="return showCalendar('EntTime', 'y-mm-dd');"   class="bd_1 c" value="${userbyid.birthday}"/>
            &nbsp;&nbsp;
            <input type="checkbox" onClick="">&nbsp;隐藏年龄
        </div>
        <div class="pLine"><h4 class="font18">星座：</h4><input type="text" value="${userbyid.user_star}" name="xingzuo" class="bd_1 c" id="user_star"></div>
        <div class="pLine"><h4 class="font18">工作地：</h4><input type="text" value="${userbyid.worspace}" name="" class="bd_1 c" id="worspace"></div>
        <div class="pLine">
            <div class="male l">
                <input type="radio" value="1" <c:choose><c:when test="${userbyid.sex eq '1'}">checked="checked" </c:when><c:otherwise></c:otherwise></c:choose> name="maleflag">男
            </div>
            <div class="female l">
                <input type="radio" value="0"  <c:choose><c:when test="${userbyid.sex eq '0'}">checked="checked" </c:when><c:otherwise></c:otherwise></c:choose> name="maleflag">女
            </div>
        </div>
        <br>

        <div class="pLine"><span
                style="margin-left:120px;*margin:0;line-height:60px">下面这些资料不是必填的。而且，你没有填写的部分将不会被显示。</span></div>
        <div class="pLine"><h4 class="font18">家乡：</h4><input type="text" value="${userbyid.hometown}" name="" class="bd_1 c" id="hometown"></div>
        <div class="pLine"><h4 class="font18">血型：</h4><input type="text" value="${userbyid.blood}" name="" class="bd_1 c" id="blood"></div>
        <div class="pLine"><h4 class="font18">身高：</h4><input type="text" value="${userbyid.height}" name="" class="bd_1 c" id="height">　CM</div>
        <div class="pLine"><h4 class="font18">体重：</h4><input type="text" value="${userbyid.heavy}" name="" class="bd_1 c" id="heavy">　KG</div>

        <div class="button">
            <input type="button" onFocus="this.blur()" value="保 存" class="butSave" onClick="SaveBaseInfor()">
        </div>
    </form>

</div>
<!--基础信息-->
<%--<div id="c_workLabel">
<h3 class="goback">工作标签</h3>

<div class="workLable">
<p class="title">服务项目&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价格区间<span
        style="font-size:12px">(单位:元)</span></p>

<form method="post" id="profileJobLabelForm">
<p>
    <input type="text" class="bd_1" value="例如：人像摄影" name="">
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select> -
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select>
</p>
<p>
    <input type="text" class="bd_1" value="" name="">
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select> -
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select>
</p>
<p>
    <input type="text" class="bd_1" value="" name="">
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select> -
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select>
</p>
<p>
    <input type="text" class="bd_1" value="" name="">
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select> -
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select>
</p>
<p>
    <input type="text" class="bd_1" value="" name="">
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select> -
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select>
</p>
<p>
    <input type="text" class="bd_1" value="" name="">
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select> -
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select>
</p>
<p>
    <input type="text" class="bd_1" value="" name="">
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select> -
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select>
</p>
<p>
    <input type="text" class="bd_1" value="" name="">
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select> -
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select>
</p>
<p>
    <input type="text" class="bd_1" value="" name="">
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select> -
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select>
</p>
<p>
    <input type="text" class="bd_1" value="" name="">
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select> -
    <select price="" name="" class="bd_1 gC1">
        <option value="-1">价格选择</option>
        <option value="100">100元</option>
        <option value="200">200元</option>
        <option value="300">300元</option>
        <option value="400">400元</option>
        <option value="500">500元</option>
        <option value="600">600元</option>
        <option value="700">700元</option>
        <option value="800">800元</option>
        <option value="900">900元</option>
        <option value="1000">1,000元</option>
        <option value="1500">1,500元</option>
        <option value="2000">2,000元</option>
        <option value="2500">2,500元</option>
        <option value="3000">3,000元</option>
        <option value="3500">3,500元</option>
        <option value="4000">4,000元</option>
        <option value="4500">4,500元</option>
        <option value="5000">5,000元</option>
        <option value="6000">6,000元</option>
        <option value="7000">7,000元</option>
        <option value="8000">8,000元</option>
        <option value="9000">9,000元</option>
        <option value="10000">1万元</option>
        <option value="15000">1.5万元</option>
        <option value="20000">2万元</option>
        <option value="25000">2.5万元</option>
        <option value="30000">3万元</option>
        <option value="35000">3.5万元</option>
        <option value="40000">4万元</option>
        <option value="45000">4.5万元</option>
        <option value="50000">5万元</option>
        <option value="60000">6万元</option>
        <option value="70000">7万元</option>
        <option value="80000">8万元</option>
        <option value="90000">9万元</option>
        <option value="100000">10万元</option>
    </select>
</p>
</form>
<div class="button"><input type="button" onfocus="this.blur()" value="保 存" class="butSave"></div>
</div>
</div>
<!--工作标签结束-->--%>

</div>
</div>

<div id="foot_x">
    <jsp:include page="../include/footer.jsp"/>
</div>
</body>
</html>
