<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">			
        <title>蓝天私人飞机会员注册</title>
        <meta charset="utf-8">
        <meta name="Keywords" content="私人飞机,私人飞机俱乐部,飞行员俱乐部,飞行员培训,国内最大的私人飞机俱乐部,属于自己的飞机,青春飞翔在蓝天">
        <meta name="Description" content="蓝天私人飞机俱乐部是国内最完善的私人飞机提供商,涵盖飞行器研发制造,飞行员学校,飞机托管等服务!">        
        <jsp:include page="../include/header.jsp"/>
    </head>
    <body style="">
        <!-- 页面内容开始 -->
        <div id="pagecontainer">
            <jsp:include page="../include/nav.jsp"/>
            <div class="main-wrap">
                <div class="type-wrap">
                    <div class="login">                        
                        <div id="loginPanel" class="login-box reg-tab">
                            <div id="regist" style="display: block;">
                                <div id="modRegWrap" class="mod-qiuser-pop">
                                    <iframe src="" name="qucpspregIframe" style="display:none"></iframe>
                                    <form id="qucpspregForm" method="post" name="qucpspregForm" target="qucpspregIframe" onsubmit="return QHPass.regUtils.beforeSubmit()" action="http://i.360.cn/reg/doregAccount">
                                        <dl class="reg-wrap">
                                            <dt>
                                            <span id="qucRegGuide"></span>
                                            <div id="regGlobal_tips" class="reg-global-error reg-global-success reg-global-loading">

                                            </div>
                                            </dt>
                                            <dd>
                                                <div class="quc-clearfix reg-item">
                                                    <label for="loginEmail">邮箱</label>
                                                    <span class="input-bg"><input type="text" id="loginEmail" name="account" maxlength="100" autocomplete="off" class="ipt tipinput" tabindex="1" placeholder="您的常用邮箱"></span>
                                                    <b class="icon-loginEmail icon-wrong" style="display: inline;"></b>
                                                </div>
                                                <span id="tips-loginEmail" class="text-tips tips-loginEmail reg-tips-wrong" style="display: inline;">请输入您的常用邮箱，<a target="_blank" class="fac" href="http://reg.email.163.com/mailregAll/reg0.jsp"> 没有邮箱？</a></span>
                                            </dd>
                                            <dd>
                                                <div class="quc-clearfix reg-item"><label for="password">密码</label><span class="input-bg"><input type="password" id="password" autocomplete="off" class="ipt tipinput" tabindex="2" placeholder="设置您的登录密码"></span>
                                                    <b class="icon-password"></b></div>
                                                <span id="tips-password" class="text-tips tips-password">6-20个字符，（区分大小写）</span>
                                            </dd>
                                            <dd>
                                                <div class="quc-clearfix reg-item"><label for="rePassword">确认密码</label>
                                                    <span class="input-bg"><input type="password" id="rePassword" autocomplete="off" class="ipt tipinput" tabindex="3" placeholder="确认密码"></span>
                                                    <b class="icon-rePassword "></b></div>
                                                <span id="tips-rePassword" class="text-tips tips-rePassword">请再次输入密码</span>
                                            </dd>
                                            <dd class="rem" id="phraseLi" style="display: none;">
                                                <label for="phrase">验证码</label>
                                                <span class="verify-code"><input type="text" maxlength="4" id="phrase" name="phrase" class="ipt1 tipinput" autocomplete="off" tabindex="4" placeholder="验证码"></span>
                                                <span class="yz"><img width="99" height="35" style="cursor: pointer;" id="wm"><a class="fac ml8" href="#nogo" id="refreshCaptcha">换一张</a></span>
                                                <b class="icon-phrase"></b>
                                                <p class="phrase-tips"><span id="tips-phrase" class="tips-phrase">请输入图中的字母或数字，不区分大小写</span></p>
                                            </dd>
                                            <dd class="rules">
                                                <label style="" for="is_agree"><input type="checkbox" name="is_agree" id="is_agree" tabindex="5" checked="checked" value="1">我已经阅读并同意</label>
                                                <a href="http://i.360.cn/pub/protocol.html" class="fac" target="_blank">《360用户服务条款》</a>
                                            </dd>
                                            <dd class="submit">
                                                <input type="submit" onfocus="this.blur()" text="立即注册" id="regSubmitBtn" value="立即注册" class="btn-register quc-psp-gstat">
                                                <input type="reset" title="" class="login-btn2" value="取消">
                                            </dd>
                                        </dl>

                                    </form>
                                </div>
                            </div>

                        </div>
                    </div>

                </div>

            </div>            
            <jsp:include page="../include/footer.jsp"/>
        </div>
    </body>

</html>