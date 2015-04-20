<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- saved from url=(0076)http://i.360.cn/login/?src=pcw_adsystem&destUrl=http%3A%2F%2Fe.360.cn%2Fhome -->
<html lang="en-US"><!--<![endif]-->
<head>
    <link rel="shortcut icon" href="image/favicon.ico" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <title>登录-360个人中心</title>
    <meta name="Keywords" content="360个人中心">
    <meta name="Description" content="360个人中心">
    <script>var G_start_time = new Date;</script>
    <script type="text/javascript">
        window.QHDomain = {'i360': 'http://i.360.cn', 'login_http': 'http://login.360.cn', 'login_https': 'https://login.360.cn', 'js_login': 'http://js.login.360.cn', '1360': 'http://www.1360.com', 'qihoo': 'http://www.qihoo.com', 'so': 'http://www.so.com', 'woxihuan': 'http://www.woxihuan.com', 'yunpan': 'http://yunpan.360.cn', 'help': 'http://help.360.cn', 'baike': 'http://baike.360.cn', 'rdLoginUrl': {"qihoo": "http:\/\/login.qihoo.com", "woxihuan": "http:\/\/login.woxihuan.com", "1360": "http:\/\/login.1360.com", "so": "http:\/\/login.so.com", "360pay": "http:\/\/login.360pay.cn", "leidian": "http:\/\/login.leidian.com", "qikoo": "http:\/\/login.qikoo.com"}, 'captcha': 'http://i.360.cn/reg/dogetcap?i360', 'jifen': 'http://jifen.360.cn', 'src': 'pcw_adsystem'};
        window.QHUserInfo = {'loginStatus': '', /*****1为登录，0为未登录*******/'figure': '', 'qid': '', 'userName': '', 'nickName': '', 'loginEmail': ''};
        window.qCrumb = "";</script>
    <link rel="stylesheet" href="http://i.360.cn/static/css/reset.css?v=3d07ddcf.css">
    <link rel="stylesheet" href="http://i.360.cn/static/css/pass_index.css?v=055c4c79.css">
    <link rel="stylesheet" href="http://i.360.cn/static/css/v2212s.css?v=c73a3f0e.css">

    <script type="text/javascript" src="./res/page_test/js/base_import.js"></script>
    <script type="text/javascript" src="./res/page_test/js/placeholder.js"></script>
    <!--[if IE 6]>
    <script src="/static/js/base/DD_belatedPNG.js?v=cb43010f.js" type="text/javascript"></script><![endif]-->
    <link href="./res/page_test/css/css_login.css" rel="stylesheet" type="text/css">
</head>
<body style="height: 130%; min-height: 609px;">
<div id="doc"><!--通用头部-->
    <div id="hd" class="clearfix" style="padding-top: 61.44px;">
        <div class="logo"><a href="http://i.360.cn/" hidefocus="true"></a></div>
    </div>
    <!--内容部分-->
    <div class="info" style="margin-top: 84.48px;">
        <span><a target="_blank" href="./">360首页</a></span>
        <span class="split">|</span>
	<span>
        <a class="registerNew" text="注册" onfocus="this.blur()"
           href="./regpage.action">注册新帐号</a>
    </span>
    </div>
    <div id="bd" class="quc-clearfix reg-wrapper11">
        <!--<div class="reg-wrapper-bottom">-->
        <div class="content">
            <div style="padding: 60px 0px 0px 160px; color: rgb(174, 176, 175); height: 42px; display: none;"
                 id="loginLoading">
                <img src="./image/t014042de8ea12aaadb.gif" height="42" width="42">
                <span style="position:relative;padding-left:8px;top:12px;">正在加载中,请稍候...</span>
            </div>
            <div id="loginWrap" style="">
                <div id="modLoginWrap" class="mod-qiuser-pop">
                    <iframe style="display:none" name="loginiframe"></iframe>
                    <form id="loginForm" method="post" target="loginiframe"
                          onsubmit="QHPass.loginUtils.submit();return false;" class="quc-clearfix">
                        <dl class="login-wrap">
                            <dt><span id="loginTitle"></span></dt>
                            <div class="ipbox">
                                <b class="r1"></b>
                                <b class="r2"></b>
                                <b class="r5"></b>

                                <div class="con">
                                    <dd class="botborder" style="z-index: 10;">
                                        <div class="quc-clearfix login-item">
                                            <label for="loginAccount">帐号：</label>

                                            <div class="ac_wrap"
                                                 style="width: 374px; top: 46px; left: -1px; display: none;">
                                                <div unselectable="on" class="ac_wrap_inner">
                                                    <div unselectable="on" class="ac_menu_ctn">
                                                        <ul unselectable="on" class="ac_menu"></ul>
                                                    </div>
                                                </div>
                                            </div>
                                            <input placeholder="手机号/用户名/邮箱" type="text" tabindex="1" id="loginAccount"
                                                   name="username" autocomplete="off" maxlength="100"
                                                   class="ipt tipinput1" suggestwidth="374px">

                                            <div class="errortipsleft icon-loginAccount">
                                                <div class="errortipsright">
                                                    <span id="tips-loginAccount" class="reg-tips-wrong"></span>
                                                </div>
                                            </div>
                                        </div>
                                    </dd>
                                    <dd class="password">
                                        <div class="quc-clearfix login-item">
                                            <label for="lpassword">密码：</label>
                                            <input placeholder="请输入您的密码" type="password" tabindex="2" id="lpassword"
                                                   name="password" maxlength="20" autocomplete="off"
                                                   class="ipt tipinput1">

                                            <div class="errortipsleft icon-lpassword">
                                                <div class="errortipsright">
                                                    <span id="tips-lpassword" class="reg-tips-wrong"></span>
                                                </div>
                                            </div>
                                        </div>
                                    </dd>
                                </div>
                                <b class="r5"></b>
                                <b class="r3"></b>
                                <b class="r4"></b>
                            </div>
                            <dd class="find">
                                <label><span id="iskeepalive" class="checked checkbox"></span>自动登录</label>
                                <a href="http://i.360.cn/findpwd/" target="_blank" class="fac" id="findPwd">找回密码</a>
                            </dd>
                            <div id="phraseLoginwarp" style="display:none">
                                <div class="ipbox phraseip">
                                    <b class="r1"></b>
                                    <b class="r2"></b>
                                    <b class="r5"></b>

                                    <div class="con">
                                        <dd class="rem">
                                            <label for="phraseLogin" class="wid3">验证码：</label>
                                            <span class="verify-code">
                                                <input type="text" style="padding: 13px 0px;" tabindex="3" maxlength="4"
                                                       id="phraseLogin" name="phrase" class="ipt1 tipinput1"
                                                       autocomplete="off">
                                            </span>

                                            <div class="errortipsleft  icon-phraseLogin">
                                                <div class="errortipsright">
                                                    <span id="tips-phraseLogin" class="reg-tips-wrong">请输入图中的字母或数字，不区分大小写</span>
                                                </div>
                                            </div>
                                        </dd>
                                    </div>
                                    <b class="r5"></b>
                                    <b class="r3"></b>
                                    <b class="r4"></b>
                                </div>
                                <span class="yz"><img width="99" height="50"
                                                      style="cursor: pointer;margin: 3px 0 0 10px;vertical-align: middle;"
                                                      id="lwm">
                                    <a class="ml8 fac"
                                       href="http://i.360.cn/login/?src=pcw_adsystem&destUrl=http%3A%2F%2Fe.360.cn%2Fhome#nogo"
                                       id="refreshCaptchaLogin">换一张</a></span>
                            </div>
                            <dd class="submit"><span><input type="submit" onfocus="this.blur()" id="loginSubmit"
                                                            value="" class="btn-login"></span>

                                <div class="global-tips">
                                    <div id="error_tips" class=""></div>
                                </div>
                            </dd>
                            <dd class="other"><span class="title">您也可以通过以下方式登录：</span>
                                <span class="login-ways">
                                    <a href="http://i.360.cn/login/?src=pcw_adsystem&destUrl=http%3A%2F%2Fe.360.cn%2Fhome#"
                                       onclick="QHPass.loginUtils.loginuse('Sina','','',''); return false;"
                                       class="loginbtn_sina" title="新浪微博登录"></a>
                                    <a href="http://i.360.cn/login/?src=pcw_adsystem&destUrl=http%3A%2F%2Fe.360.cn%2Fhome#"
                                       onclick="QHPass.loginUtils.loginuse('RenRen','','',''); return false;"
                                       class="loginbtn_rr" title="人人登录"></a>
                                    <a href="http://i.360.cn/login/?src=pcw_adsystem&destUrl=http%3A%2F%2Fe.360.cn%2Fhome#"
                                       onclick="QHPass.loginUtils.loginuse('Msn','','',''); return false;"
                                       class="loginbtn_msn" title="Msn登录"></a>
                                    <span class="retraction"></span>
                                    <div class="retractionBox">
                                        <div class="retractionBoxinner">
                                            <a href="http://i.360.cn/login/?src=pcw_adsystem&destUrl=http%3A%2F%2Fe.360.cn%2Fhome#"
                                               onclick="QHPass.loginUtils.loginuse('Fetion','','',''); return false;"
                                               class="loginbtn_fx" title="飞信"></a>
                                            <a href="http://i.360.cn/login/?src=pcw_adsystem&destUrl=http%3A%2F%2Fe.360.cn%2Fhome#"
                                               onclick="QHPass.loginUtils.loginuse('Telecom','','',''); return false;"
                                               class="loginbtn_ty" title="天翼登录"></a>
                                        </div>
                                        <div class="thirdarrow"></div>
                                    </div></span></dd>
                            <dd class="quick-login-wrap">
                                <a href="http://i.360.cn/login/?src=pcw_adsystem&destUrl=http%3A%2F%2Fe.360.cn%2Fhome###"
                                   id="gotoQuickLogin"
                                   class="fa2 quick-login">切换到快速登录模式</a>
                            </dd>
                        </dl>
                    </form>
                </div>
            </div>
            <div id="qloginWrap" style="height: 250px; text-indent: -1000000px;"></div>

            <div class="otherMessage clearfix" style="display: none;">
                <div>
                    <div id="accountLogin"><a hidefocus="true" onclick="normalLogin();return false"
                                              href="javascript:void(0)">使用其他帐号登录</a></div>
                    <!--<div class="split">|</div>
                    <div class="findpwd"><a hidefocus="true" href="http://i.360.cn/findpwd">忘记密码？</a></div>-->
                </div>

            </div>
        </div>
        <!--</div>-->
    </div>
    <div id="ft">Copyright&#169;2005-2013 360.CN All Rights Reserved 360安全中心</div>
</div>

<script type="text/javascript" src="./js/import_login.js"></script>
<script>
    var destUrl = 'http://e.360.cn/home';
    QHPass.resConfig.cookie_domains = ["1360|qihoo|woxihuan|so|360pay"];
    $(function () {
        var flag_qlogin = true,
                iPhone = navigator.userAgent.match(/iPhone/i),
                iPod = navigator.userAgent.match(/iPod/i),
                andRoid = navigator.userAgent.match(/Android/i);

        //邮箱用户登录suggest
        function addSuggest() {
            function g(id) {
                return document.getElementById(id);
            };
            $(g("loginAccount")).attr('suggestWidth', '374px').closest('dd').css('zIndex', 10);
            var cb1 = new ComboBox({
                oText: g("loginAccount"),
                refreshData: function () {
                    var val = this.oText.value.split("@")[0],
                            location = this.oText.value.indexOf("@"),
                            mail = (this.oText.value.split("@")[1] || '').toLowerCase(),
                            mails = ['@sina.com', '@163.com', '@qq.com', '@126.com', '@vip.sina.com', '@sina.cn', '@hotmail.com', '@gmail.com', '@sohu.com', '@yahoo.com', '@139.com', '@189.cn'],
                            ar = [];
                    for (var i = 0; i < mails.length; i++) {
                        if (location == (this.oText.value.length - 1)) {
                            if (mails[i].indexOf(mail) > -1) {
                                ar.push(val + mails[i]);
                            }
                        } else if (location > -1) {
                            if (mails[i].indexOf(mail) > -1) {
                                ar.push(val + mails[i]);
                            }
                        }
                    }
                    this.itemsData = ar;
                    $('.ac_wrap').css("left", "-1px");
                    $('.ac_wrap_inner').css("width", "371px");
                },
                onselectitem: function () {
                    var me = this;
                    setTimeout(function () {
                        $('#lpassword').focus();
                    }, 10);
                    $(me.oText).trigger('blur');
                },
                onenter: function () {
                    var me = this;
                    setTimeout(function () {
                        $('#lpassword').focus();
                    }, 10);
                }
            });
        };

        function loginCallback() {
            if (destUrl) {
                location.replace(destUrl)
            } else {
                location.replace("http://i.360.cn")
            }

        };

        window.loadQlogin = function () {
            $("#loginWrap").hide();
            $("#loginLoading").show();
            $("#bd").removeClass("reg-wrapper11").addClass("reg-wrapper-quick");
            var src = "http://axlogin.passport.360.cn/ptlogin.php",
                    address = src + "?nextUrl=http://" + location.host + "/psp_jump.html&domain_list=" + "&t=" + Math.random(),
                    iframe = '<iframe frameborder="0" src="' + address + '" width="100%" scrolling="no" height="100%" id="pspPtloginIframe" allowTransparency="true"></iframe>';
            $("#qloginWrap").html(iframe);
            $("#qloginWrap").css('text-indent', '-1000000px').show();

        }

        window.normalLogin = function () {
            QHPass.showLogin(loginCallback, {
                thirdLogin: ['sina|renren|msn|fetion|Telecom', '', ''],
                captFlag: true,
                type: 'normal',
                wrap: 'loginWrap'
            });

            $(".no-account").remove();
            $("#qloginWrap").html('');
            $("#loginLoading").hide();
            $("#loginWrap").show();
            $("#accontLogin,div.otherMessage").hide();
            $("#bd").removeClass("reg-wrapper-quick").addClass("reg-wrapper11");
            if (flag_qlogin) {
                $("#modLoginWrap").append('<div class="returnQL"><a hidefocus="true"  onclick="loadQlogin();return false" href="#">返回快速登录</a></div>')
            }
            $("a,button").focus(function () {
                this.blur()
            });
            addSuggest();
        }

        QHPass.ptLogin = function (ret) {
            // s 值 0 为登录成功，1为ax有帐号，2为ax没有帐号或者异常
            if (ret.s == 0) {
                loginCallback();
            } else if (ret.s == 1) {
                $("#qloginWrap").css('text-indent', '0').show("normal", function () {
                    $("div.otherMessage").show();
                });
                $("#loginLoading").hide();
                $("#accontLogin").show();

            } else {
                flag_qlogin = false;
                normalLogin();
            }
        }

        //启动登录
        if (flag_qlogin && !iPhone && !iPod && !andRoid) {
            loadQlogin();
        } else {
            normalLogin();
        }
    });
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
<br>	26051fc3a9295885	==	page_load
<br>	26051fc3a9295885	==	page_load:0.008

--></body>
</html>