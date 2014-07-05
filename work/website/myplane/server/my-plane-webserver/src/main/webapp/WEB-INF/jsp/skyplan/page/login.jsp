<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">			
        <title>蓝天私人飞机会员登录</title>
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
                    <div id="loginPanel" class="login-box login-tab">
                    <div id="login">
                        <div id="modLoginWrap" class="mod-qiuser-pop">                            
                            <form id="loginForm" method="post" target="loginiframe" onsubmit="QHPass.loginUtils.submit();return false;">
                                <dl class="login-wrap">
                                    <dt><span id="loginTitle"></span></dt>
                                    <dd>
                                        <div class="quc-clearfix login-item">
                                            <label for="loginAccount">帐号</label>
                                            <span class="input-bg">
                                                <input placeholder="手机号/用户名/邮箱" type="text" tabindex="1" id="loginAccount" name="username" autocomplete="off" maxlength="100" class="ipt tipinput1">
                                            </span>
                                            <b class="tips-wrong icon-loginAccount"></b>
                                            <span id="tips-loginAccount" class="tips-msg "></span>
                                        </div>
                                    </dd>
                                    <dd class="password">
                                        <div class="quc-clearfix login-item">
                                            <label for="lpassword">密码</label>
                                            <span class="input-bg"><input placeholder="请输入您的密码" type="password" tabindex="2" id="lpassword" name="password" maxlength="20" autocomplete="off" class="ipt tipinput1"></span>
                                            <b class="tips-wrong icon-lpassword"></b>
                                            <span id="tips-lpassword" class="tips-msg"></span>
                                        </div>
                                    </dd>
                                    <dd class="find">
                                        <label for="iskeepalive"><input type="checkbox" tabindex="4" name="iskeepalive" id="iskeepalive" checked="checked"> 下次自动登录</label>
                                        <a href="http://i.360.cn/findpwd/" target="_blank" class="fac" id="findPwd">忘记密码？</a>
                                    </dd>
                                    <dd class="rem" id="phraseLoginwarp" style="display:block">
                                        <label for="phraseLogin">验证码</label>
                                        <span class="verify-code">
                                            <input type="text" tabindex="3" maxlength="4" id="phraseLogin" name="phrase" class="ipt1 tipinput1" autocomplete="off">
                                        </span>
                                        <span class="yz"><img width="99" height="35" id="lwm"><a class="ml8 fac" href="#nogo" id="refreshCaptchaLogin">换一张</a></span>
                                        <p><b class="tips-wrong  icon-phraseLogin"></b><span id="tips-phraseLogin" class="tips-phrase">请输入图中的字母或数字，不区分大小写</span></p>
                                    </dd>
                                    <dd class="submit">
                                        <span>
                                            <input type="submit" onfocus="this.blur()" id="loginSubmit" value="登录" class="btn-login quc-psp-gstat">
                                            <input type="reset" title="" class="login-btn2" value="取消">
                                            <a href="###" class="fac reg-new-account">注册新帐号</a>
                                        </span>
                                    </dd>
                                   
                                    <dd class="global-tips"><div id="error_tips" class=""></div></dd>
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