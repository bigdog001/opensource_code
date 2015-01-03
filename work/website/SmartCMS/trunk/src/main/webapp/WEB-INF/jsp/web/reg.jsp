<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- saved from url=(0076)http://i.360.cn/reg?src=pcw_adsystem&destUrl=http%3A%2F%2Fe.360.cn%2Fhome### -->
<html lang="en-US"><!--<![endif]-->
<head>
    <link rel="shortcut icon" href="image/favicon.ico" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <title>注册-360个人中心</title>
    <meta name="Keywords" content="360个人中心">
    <meta name="Description" content="360个人中心">
    <script>var G_start_time = new Date;</script>
    <script type="text/javascript">
        window.QHDomain = {'i360': 'http://i.360.cn', 'login_http': 'http://login.360.cn', 'login_https': 'https://login.360.cn', 'js_login': 'http://js.login.360.cn', '1360': 'http://www.1360.com', 'qihoo': 'http://www.qihoo.com', 'so': 'http://www.so.com', 'woxihuan': 'http://www.woxihuan.com', 'yunpan': 'http://yunpan.360.cn', 'help': 'http://help.360.cn', 'baike': 'http://baike.360.cn', 'rdLoginUrl': {"qihoo": "http:\/\/login.qihoo.com", "woxihuan": "http:\/\/login.woxihuan.com", "1360": "http:\/\/login.1360.com", "so": "http:\/\/login.so.com", "360pay": "http:\/\/login.360pay.cn", "leidian": "http:\/\/login.leidian.com", "qikoo": "http:\/\/login.qikoo.com"}, 'captcha': 'http://i.360.cn/reg/dogetcap?i360', 'jifen': 'http://jifen.360.cn', 'src': 'pcw_adsystem'
        };
        window.QHUserInfo = {'loginStatus': '', /*****1为登录，0为未登录*******/'figure': '', 'qid': '', 'userName': '', 'nickName': '', 'loginEmail': ''};
        window.qCrumb = "";
    </script>
    <link rel="stylesheet" href="http://i.360.cn/static/css/reset.css?v=3d07ddcf.css">
    <link rel="stylesheet" href="http://i.360.cn/static/css/pass_index.css?v=055c4c79.css">
    <link rel="stylesheet" href="http://i.360.cn/static/css/v2212s.css?v=c73a3f0e.css">
    <script type="text/javascript" src="./js/base_import.js"></script>
    <script type="text/javascript" src="./js/placeholder.js"></script>
    <!--[if IE 6]>
    <script src="./js/DD_belatedPNG.js" type="text/javascript"></script>
    <![endif]-->
    <link href="./css/css_reg.css" rel="stylesheet" type="text/css">
</head>
<body style="height: 783px; min-height: 783px;">
<div id="doc"><!--通用头部-->
    <div id="hd" class="clearfix" style="padding-top: 61.44px;">
        <div class="logo"><a href="http://i.360.cn/" hidefocus="true"></a></div>
    </div>
    <!--内容部分-->

    <div class="info" style="margin-top: 84.48px;">
        <span><a target="_blank" href="./">360首页</a></span>
        <span class="split">|</span>
        <span><a class="loginAccount" text="登陆" onfocus="this.blur()"
                 href="./loginpage.action">已有360帐号</a></span>
    </div>
    <div id="bd" class="quc-clearfix reg-wrapper2">
        <div class="reg-nav">
            <ul id="regWays" class="quc-clearfix">
                <li data-type="email" class="borderright cur">
                    <a href="#"><span
                            class="email-icon">邮箱注册</span></a>
                </li>
                <div style="width:1px;float:left;border-right: 1px dashed #ccc;height: 50px;margin-top: 1%;"></div>
                <li data-type="tel" class="ncur">               <%----%>
                    <a href="#"><span class="tel-icon">手机注册</span></a>
                </li>
            </ul>
        </div>
        <div class="reg-content">
            <div id="regWrap">
                <div id="modRegWrap" class="mod-qiuser-pop">
                    <iframe src="" name="qucpspregIframe" style="display:none"></iframe>
                    <form id="qucpspregForm" method="post" name="qucpspregForm" target="qucpspregIframe"
                          onsubmit="return QHPass.regUtils.beforeSubmit()" action="http://i.360.cn/reg/doregAccount">
                        <dl class="reg-wrap">
                            <dt><span id="regTitle"></span></dt>
                            <div class="ipbox"><b class="r1"></b><b class="r2"></b><b class="r5"></b>

                                <div class="con">
                                    <dd class="botborder" style="z-index: 10;">
                                        <div class="quc-clearfix reg-item"><label for="loginEmail"
                                                                                  class="wid2">邮箱：</label><span
                                                class="input-bg"><div class="ac_wrap"
                                                                      style="width: 373px; top: 46px; left: -1px; display: none;">
                                            <div unselectable="on" class="ac_wrap_inner">
                                                <div unselectable="on" class="ac_menu_ctn">
                                                    <ul unselectable="on" class="ac_menu"></ul>
                                                </div>
                                            </div>
                                        </div><input type="text" id="loginEmail" name="account" maxlength="100"
                                                     autocomplete="off" class="ipt tipinput" tabindex="1"
                                                     suggestwidth="373px"></span><b
                                                class="icon-loginEmail icon-info"></b></div>
                                        <div class="msgtipsleft tipwrapper-loginEmail">
                                            <div class="msgtipsright"><span id="tips-loginEmail"
                                                                            class="text-tips tips-loginEmail"></span>
                                            </div>
                                        </div>
                                    </dd>
                                    <dd class="botborder">
                                        <div class="quc-clearfix reg-item"><label for="nickname"
                                                                                  class="wid2">昵称：</label><span
                                                class="input-bg"><input type="text" id="nickname" name="nickName"
                                                                        maxlength="14" autocomplete="off"
                                                                        class="ipt tipinput" tabindex="2"></span><b
                                                class="icon-nickname icon-info"></b></div>
                                        <div class="msgtipsleft tipwrapper-nickname">
                                            <div class="msgtipsright"><span id="tips-nickname"
                                                                            class="text-tips tips-nickname"></span>
                                            </div>
                                        </div>
                                    </dd>
                                    <dd class="botborder">
                                        <div class="quc-clearfix reg-item"><label for="password"
                                                                                  class="wid2">密码：</label><span
                                                class="input-bg"><input type="password" id="password" autocomplete="off"
                                                                        maxlength="20" class="ipt tipinput"
                                                                        tabindex="3"></span><b
                                                class="icon-password icon-info"></b></div>
                                        <div class="msgtipsleft tipwrapper-password">
                                            <div class="msgtipsright"><span id="tips-password"
                                                                            class="text-tips tips-password"></span>
                                            </div>
                                        </div>
                                    </dd>
                                    <dd>
                                        <div class="quc-clearfix reg-item"><label for="rePassword"
                                                                                  class="wid3">确认密码：</label><span
                                                class="input-bg"><input type="password" id="rePassword"
                                                                        autocomplete="off" maxlength="20"
                                                                        class="ipt tipinput" tabindex="4"></span><b
                                                class="icon-rePassword icon-info"></b></div>
                                        <div class="msgtipsleft tipwrapper-rePassword">
                                            <div class="msgtipsright"><span id="tips-rePassword"
                                                                            class="text-tips tips-rePassword"></span>
                                            </div>
                                        </div>
                                    </dd>
                                </div>
                                <b class="r5"></b><b class="r3"></b><b class="r4"></b></div>
                            <div id="phraseLi" class="phraseLi  quc-clearfix" style="display: block;">
                                <div class="ipbox phraseip1 "><b class="r1"></b><b class="r2"></b><b class="r5"></b>

                                    <div class="con">
                                        <dd class="rem"><label for="phrase" class="wid3">验证码：</label><span
                                                class="verify-code"><input type="text" style="padding: 13px 0px;"
                                                                           tabindex="5" maxlength="4" id="phrase"
                                                                           name="phrase" class="ipt1 tipinput tipinput1"
                                                                           autocomplete="off"></span><b
                                                class="icon-phrase icon-info"></b>

                                            <div class="msgtipsleft  tipwrapper-phrase">
                                                <div class="msgtipsright"><span id="tips-phrase" class="text-tips">请输入图中的字母或数字，不区分大小写</span>
                                                </div>
                                            </div>
                                        </dd>
                                    </div>
                                    <b class="r5"></b><b class="r3"></b><b class="r4"></b></div>
                                <span class="yz"><img width="99" height="50"
                                                      style="cursor: pointer;margin: 3px 0 0 10px;vertical-align: middle;"
                                                      id="wm" src="./image/dogetcap"><a class="ml8 fac"
                                                                                        href="http://i.360.cn/reg?src=pcw_adsystem&destUrl=http%3A%2F%2Fe.360.cn%2Fhome#nogo"
                                                                                        id="refreshCaptcha">换一张</a></span>
                            </div>
                            <dd class="rules"><label><input type="checkbox" name="is_agree" id="is_agree" tabindex="6"
                                                            checked="checked" value="1" class="isAgreeRules"><span>我已经阅读并同意</span></label><a
                                    href="http://i.360.cn/pub/protocol.html" class="fac1"
                                    target="_blank">《360用户服务条款》</a>

                                <div id="regGlobal_tips"
                                     class="reg-global-error reg-global-success reg-global-loading"></div>
                            </dd>
                            <dd class="submit"><input type="submit" onfocus="this.blur()" id="regSubmitBtn" value=""
                                                      class="btn-register"></dd>
                        </dl>
                        <input id="srcreg" type="hidden" value="pcw_adsystem" name="src"><input
                            id="loginEmailActiveFlag" type="hidden" value="1" name="loginEmailActiveFlag"><input
                            id="pageType" type="hidden" value="gbk" name="charset"><input id="accoutType" type="hidden"
                                                                                          value="1"
                                                                                          name="acctype"><input
                            id="pwdmethod" type="hidden" value="1" name="pwdmethod"><input id="proxy" type="hidden"
                                                                                           value="http://i.360.cn/psp_jump.html"
                                                                                           name="proxy"><input
                            id="topassword" type="hidden" value="" name="password"><input id="torePassword"
                                                                                          type="hidden" value=""
                                                                                          name="rePassword"><input
                            id="callback" type="hidden" value="parent.QHPass.regUtils.submitCallback"
                            name="callback"><input id="captchaFlag" type="hidden" value="1" name="captchaFlag"><input
                            id="captchaAppId" type="hidden" value="i360" name="captchaAppId"></form>
                </div>
            </div>
        </div>
    </div>


    <div id="ft">Copyright&#169;2005-2013 360.CN All Rights Reserved 360安全中心</div>
</div>
<script type="text/javascript" src="./js/import_reg.js"></script>
<script type="text/javascript">

    var vcTime = 'ILuWgjArAqyFL7c9QyZXCg',
            destUrl = 'http%3A%2F%2Fe.360.cn%2Fhome',
            isShowCaptcha = '1',
            regtype = '';

    ;
    (function () {
        //加载左侧的注册方式和右侧注册内容
        function initSideBarAndReg(pemail, ptel, pname) {

            var email = pemail ? '<li data-type="email" class= "ncur borderright"  ><a  href="###"><span class="email-icon">邮箱注册</span></a></li><div style="width:1px;float:left;border-right: 1px dashed #ccc;height: 50px;margin-top: 1%;"></div>' : '',
                    tel = ptel ? '<li data-type="tel" class= "ncur"  ><a  href="###"><span class="tel-icon">手机注册</span></a></li>' : '',
            //	name  = pname ? '<li data-type="name" class= "ncur"  ><a  href="###"><span class="uname-icon">用户名注册</span></a></li>':'',
                    str = '',
                    type = regtype;

            //str = email + tel + name;
            str = email + tel;

            $('#regWays').html(str);

            switch (type) {
                case "tel":
                    $("#bd").addClass("reg-wrapper2");
                    $("ul li:eq(1)").removeClass('ncur').addClass('cur');
                    regWay(type, 'regWrap');
                    break;
                /*	case "name":
                 $("ul li:eq(2)").removeClass('ncur').addClass('cur');
                 regWay(type,'regWrap');
                 break;*/
                default:
                    $("ul li:eq(0)").removeClass('ncur').addClass('cur');
                    regWay('email', 'regWrap');
            }

            $('#regWays').click(function (event) {
                var tar = event.target,
                        parent = $(tar).closest('li'),
                        type = $(parent)[0].getAttribute('data-type');

                if ($(parent).hasClass('cur'))return;

                $("li[data-type]").removeClass('cur').addClass('ncur');

                $(parent).removeClass('ncur').addClass('cur');
                if (type == "tel") {
                    $("#bd").addClass("reg-wrapper2");
                } else {
                    $("#bd").removeClass("reg-wrapper2");
                }
                //加上placeholder
                regWay(type, 'regWrap');
                $('input[placeholder]').placeholder();
                event.preventDefault();
            });
        }

        //邮箱注册回调
        function nomalRegCallback() {
            if (destUrl) {
                try {
                    var url = decodeURIComponent(destUrl)
                    location.replace(url);
                } catch (e) {
                    location.replace(QHDomain.i360)
                }

            } else {
                location.replace(QHDomain.i360)
            }


        };
        //手机注册回调
        function telRegCallback() {
            var url = '/profile/chusername?from=reg';
            if (destUrl) {
                url += '&destUrl=' + destUrl;
                location.replace(url);
            } else {
                location.replace(url)
            }

        };

        //添加suggest
        function addSuggest() {
            function g(id) {
                return document.getElementById(id);
            };
            $(g("loginEmail")).attr('suggestWidth', '373px').closest('dd').css('zIndex', 10);
            var cb1 = new ComboBox({oText: g("loginEmail"),
                refreshData: function () {
                    var val = this.oText.value.split("@")[0],
                            mail = (this.oText.value.split("@")[1] || '').toLowerCase(),
                            mails = ['@sina.com', '@163.com', '@qq.com', '@126.com', '@vip.sina.com', '@sina.cn', '@hotmail.com', '@gmail.com', '@sohu.com', '@yahoo.com', '@139.com', '@189.cn'],
                            ar = [];
                    for (var i = 0; i < mails.length; i++) {
                        if (mails[i].indexOf(mail) > -1) ar.push(val + mails[i]);
                    }
                    this.itemsData = ar;
                },
                onselectitem: function () {

                    var me = this;
                    setTimeout(function () {
                        $('#nickname').focus();
                    }, 10);
                    $(me.oText).trigger('blur');
                },
                onenter: function () {
                    var me = this;
                    setTimeout(function () {
                        $('#nickname').focus();
                    }, 10);
                }
            });
        }

        //注册方式选择
        function regWay(type, dwrap) {
            switch (type) {

                case 'email':
                    QHPass.showReg(nomalRegCallback, {
                        regway: 'email',
                        type: 'normal',
                        wrap: dwrap,
                        nickname: true,
                        onlyName: false
                    });
                    addSuggest();
                    break;
                case 'tel':
                    QHPass.showReg(telRegCallback, {

                        type: 'normal',
                        regway: 'tel',
                        wrap: dwrap,
                        nickname: true
                    });
                    break;
                /*	case 'name':
                 QHPass.showReg(nomalRegCallback,{
                 onlyName:true,
                 type:'normal',
                 regway:'name',
                 wrap:dwrap
                 });
                 break;*/
            }

            $(".has-account").remove();
            $("a,button").focus(function () {
                this.blur()
            });

        }

        initSideBarAndReg('email', 'tel');

        $(".rules label span").live("click", function (e) {
            $(".hovercheck").toggleClass("checked");
        });
    })()
</script>

<script>/*背景图片自适应*/
var resolutionHeight = window.screen.height;
$("#hd").css("padding-top", (resolutionHeight * 0.08) + "px");
$("div.info").css("margin-top", (resolutionHeight * 0.11) + "px");
if (resolutionHeight < 770) {
    $("body").css("height", "130%");
} else if (resolutionHeight < 901) {
    $("body").css("height", "115%");
} else if (resolutionHeight > 1200) {
    $("body").addClass("bodybig");
}
var scrollHeight = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight);
var clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
var maxHeight = Math.max(clientHeight, scrollHeight);
$("body").css("min-height", maxHeight + "px");
$(window).resize(function () {
    var scrollHeight1 = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight);
    var clientHeight1 = document.documentElement.clientHeight || document.body.clientHeight;
    var maxHeight1 = Math.max(clientHeight1, scrollHeight1);
    $("body").css("height", maxHeight1 + "px");
});
/**统计****/monitor.setProject('360passport').getTrack().getClickAndKeydown();
/**页面性能统计****/QHPass.wpo();
/*placeholder*/
$('input[placeholder]').placeholder();</script>
<!--[if IE 6]>
<script>DD_belatedPNG.fix('.logo,.reg-wrapper,.loginbtn_sina, .loginbtn_fx, .loginbtn_msn, .loginbtn_rr,.loginbtn_ty,.registerNew,.retraction');</script>
<![endif]-->
<!--
<br>	38f340592bea519a	==	page_load
<br>	38f340592bea519a	==	page_load:0.013

--></body>
</html>