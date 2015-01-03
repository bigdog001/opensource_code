<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="bigdog" uri="/bigdog-tags" %>
<!DOCTYPE html>
<!-- saved from url=(0016)http://e.360.cn/ -->
<html>
<head>
    <jsp:include page="include/head.jsp"/>
    <title>亚洲犯罪率调查</title>
</head>
<body>
<!--start nav-->
<jsp:include page="include/nav.jsp"/>
<!--end nav-->
<div class="bg"> 
    <div class="wrap">
        <div id="slides">
            <div id="pic_box">
                <a id="url1" class="pic_paly" href="http://e.360.cn/html/product.html?id=8" target="_blank"
                   style="display: none;">
                    <img src="./res/page_test/image/f001.jpg" width="726" height="289">
                </a>
                <a id="url2" class="pic_paly" style="display: block;" href="http://e.360.cn/html/product.html?id=9"
                   target="_blank">
                    <img src="./res/page_test/image/f002.jpg" width="726" height="289">
                </a>
                <a id="url3" class="pic_paly" style="display: none;" href="http://e.360.cn/html/product.html?id=10"
                   target="_blank">
                    <img src="./res/page_test/image/f003.jpg" width="726" height="289">
                </a>
                <a id="url4" class="pic_paly" style="display: none;" href="http://e.360.cn/html/product.html?id=6"
                   target="_blank">
                    <img src="./res/page_test/image/f004.png" width="726" height="289">
                </a>
                <a id="url5" class="pic_paly" style="display: none;" href="http://e.360.cn/html/product.html?id=11"
                   target="_blank">
                    <img src="./res/page_test/image/f005.png" width="726" height="289">
                </a>

                <div id="page_size">
                    <a href="javascript:;" id="changen1" class="page_sizea"></a>
                    <a href="javascript:;" id="changen2" class="page_sizea change_on"></a>
                    <a href="javascript:;" id="changen3" class="page_sizea"></a>
                    <a href="javascript:;" id="changen4" class="page_sizea"></a>
                    <a href="javascript:;" id="changen5" class="page_sizea"></a>
                </div>
            </div>
        </div>
        <div class="boxr">
            <div class="login" id="mod_quc_pop">
                <h2>欢迎使用亚洲犯罪率调查网！<bigdog:getIt id="8" showit="2"></bigdog:getIt></h2>

                <div id="login">
                    <div id="modLoginWrap" class="mod-qiuser-pop">
                        <iframe style="display:none" name="loginiframe"></iframe>
                        <form id="loginForm" method="post" target="loginiframe"
                              onsubmit="QHPass.loginUtils.submit();return false;">
                            <dl class="login-wrap">
                                <dt><span id="loginTitle"></span></dt>
                                <dd>
                                    <div class="quc-clearfix login-item"><label for="loginAccount">帐号</label><span
                                            class="input-bg"><input placeholder="手机号/用户名/邮箱" type="text" tabindex="1"
                                                                    id="loginAccount" name="username" autocomplete="off"
                                                                    maxlength="100" class="ipt tipinput1"></span><b
                                            class="tips-wrong icon-loginAccount"></b><span id="tips-loginAccount"
                                                                                           class="tips-msg "></span>
                                    </div>
                                </dd>
                                <dd class="password">
                                    <div class="quc-clearfix login-item"><label for="lpassword">密码</label><span
                                            class="input-bg"><input placeholder="请输入您的密码" type="password" tabindex="2"
                                                                    id="lpassword" name="password" maxlength="20"
                                                                    autocomplete="off" class="ipt tipinput1"></span><b
                                            class="tips-wrong icon-lpassword"></b><span id="tips-lpassword"
                                                                                        class="tips-msg"></span></div>
                                </dd>
                                <dd class="find"><label for="iskeepalive"><input type="checkbox" tabindex="4"
                                                                                 name="iskeepalive" id="iskeepalive"
                                                                                 checked="checked"> 下次自动登录</label><a
                                        href="http://i.360.cn/findpwd/" target="_blank" class="fac"
                                        id="findPwd">忘记密码？</a></dd>
                                <dd class="rem" id="phraseLoginwarp" style=""><label for="phraseLogin">验证码</label><span
                                        class="verify-code"><input type="text" tabindex="3" maxlength="4"
                                                                   id="phraseLogin" name="phrase" class="ipt1 tipinput1"
                                                                   autocomplete="off"></span><span class="yz"><img
                                        width="99" height="35" id="lwm" src="./res/page_test/image/image.php" title="换一张"><a
                                        class="ml8 fac" href="http://e.360.cn/#nogo"
                                        id="refreshCaptchaLogin">换一张</a></span>

                                    <p><b class="tips-wrong  icon-phraseLogin"></b>
                                        <span id="tips-phraseLogin" class="tips-phrase">请输入图中的字母或数字，不区分大小写</span>
                                    </p>
                                </dd>
                                <dd class="submit">
                                  <span>
                                    <input type="submit" onfocus="this.blur()" id="loginSubmit" value=""
                                           class="btn-login quc-psp-gstat">
                                    <a href="./regpage.action" class="fac reg-new-account">注册新帐号</a>
                                  </span>
                                </dd>
                                <dd class="global-tips">
                                    <div id="error_tips" class=""></div>
                                </dd>
                            </dl>
                        </form>
                    </div>
                </div>
                <div class="oth-link">现在就想加入？<a style="text-decoration:underline"
                                                href="./regpage.action">立即注册</a>！
                </div>
            </div>
            <!--end login-->
        </div>
        <!--end boxr-->
    </div>
    <!--end wrap-->
</div>
<!--end bg-->
<div class="area">
    <div class="lft">
        <div class="odds">
            <div class="tit">我们的优势</div>
            <!--end tit-->
            <dl>
                <dt class="adds-ico1"><a href="http://e.360.cn/html/service/product.html" target="_blank"></a></dt>
                <dd>
                    <h3><a href="http://e.360.cn/html/service/product.html" target="_blank">超过90%的市场渗透率</a></h3>
                    整合360导航，360搜索等多个平台的流量入口，月活跃用户超过4亿
                </dd>
            </dl>
            <dl>
                <dt class="adds-ico2"><a href="http://e.360.cn/html/service/product.html" target="_blank"></a></dt>
                <dd>
                    <h3><a href="http://e.360.cn/html/service/product.html" target="_blank">精准定位您的目标用户</a></h3>
                    通过关键词、地域、时间等多种定位方式定向目标用户，使推广更有效
                </dd>
            </dl>
            <dl>
                <dt class="adds-ico3"><a href="http://e.360.cn/html/service/product.html" target="_blank"></a></dt>
                <dd>
                    <h3><a href="http://e.360.cn/html/service/product.html" target="_blank">人性化操作，专业统计</a></h3>
                    操作简单，多种专业统计数据报告，让推广和优化更便捷高效
                </dd>
            </dl>
        </div>
        <!--end odds-->
        <div class="step">
            <div class="tit">推广流程</div>
            <!--end tit-->
            <div class="stepc">
                <a href="http://e.360.cn/help?hid=1-2" target="_blank"><span class="step-ico1"></span>开通账户</a> <em></em>
                <a href="http://e.360.cn/help?hid=2-6" target="_blank"><span class="step-ico2"></span>设置预算</a> <em></em>
                <a href="http://e.360.cn/help?hid=2-8" target="_blank"><span class="step-ico3"></span>设置定向</a> <em></em>
                <a href="http://e.360.cn/help?hid=2-4" target="_blank"><span class="step-ico4"></span>制作创意</a> <em></em>
                <a href="http://e.360.cn/help?hid=3-1" target="_blank"><span class="step-ico5"></span>开始投放</a> <em></em>
                <a href="http://e.360.cn/help?hid=4-1" target="_blank"><span class="step-ico6"></span>查看效果</a> <em></em>
                <a href="http://e.360.cn/help?hid=5-1" target="_blank"><span class="step-ico7"></span>优化推广</a>
            </div>
            <!--end stepc-->
        </div>
        <!--end step-->
    </div>
    <!--end lft-->
    <div class="rit">
        <div class="new">
            <div class="tit"><a href="http://e.360.cn/html/notice/list.html" target="_blank">最新公告</a> <a class="more"
                                                                                                         href="http://e.360.cn/html/notice/list.html"
                                                                                                         target="_blank">更多&gt;&gt;</a>
            </div>
            <!--end tit-->
            <ul class="login_notice">

                <li><a href="http://e.360.cn/html/notice/101.html" target="_blank" style="color:#ff0000">【通知】猜你喜欢将于6月13日启用新样式</a>
                </li>

                <li><a href="http://e.360.cn/html/notice/100.html" target="_blank">【公告】谨防冒充360公司名义进行销售</a></li>

                <li><a href="http://e.360.cn/html/notice/99.html" target="_blank">【通知】5月19日系统升级将暂停账户操作</a></li>

            </ul>


        </div>
        <!--end new-->
        <div class="h30"></div>
        <div class="new">
            <div class="tit"><a href="http://e.360.cn/help" target="_blank">常见问题</a> <a class="more"
                                                                                        href="http://e.360.cn/help"
                                                                                        target="_blank">更多&gt;&gt;</a>
            </div>
            <!--end tit-->
            <ul>
                <li><a href="http://e.360.cn/help?hid=1-1" target="_blank">什么是360点睛营销平台？</a></li>
                <li><a href="http://e.360.cn/help?hid=1-1" target="_blank">360点睛营销平台的优势是什么？</a></li>
                <li><a href="http://e.360.cn/help?hid=1-2" target="_blank">如何在360点睛营销平台开通账户？</a></li>
            </ul>
        </div>
        <!--end new-->
    </div>
    <!--end rit-->
</div>
<!--end area-->
<div class="bg1">
    <div class="wrap">
        <div class="case">
            <div class="tit">合作案例</div>
            <!--end tit-->
            <ul>
                <li><a href="http://e.360.cn/html/case/case.html?cid=0" target="_blank"><img src="./res/page_test/image/case_kb.jpg"
                                                                                             width="60"
                                                                                             height="60"><span>库巴购物网，库巴科技（北京）有限公司旗下电子商务平台，已发展为国内领先的家电产品网购服务提供商。</span></a>
                </li>
                <li><a href="http://e.360.cn/html/case/case.html?cid=1" target="_blank"><img src="./res/page_test/image/case_zp.jpg"
                                                                                             width="60"
                                                                                             height="60"><span>珍品网，专注国际顶级奢侈品牌的大型B2C电子商务网站，涵盖多个时尚大牌并首创了"时尚顾问服务"。</span></a>
                </li>
                <li><a href="http://e.360.cn/html/case/case.html?cid=2" target="_blank"><img src="./res/page_test/image/case_zn.jpg"
                                                                                             width="60"
                                                                                             height="60"><span>钻石小鸟，国内最早从事网络钻石销售的专业珠宝品牌，也是中国网络钻石销售第一品牌。</span></a>
                </li>
            </ul>
        </div>
        <!--end case-->
        <div class="footer"> Copyright © 360安全网址. All Rights Reserved. 京ICP证080047号</div>
        <!--end footer-->
    </div>
    <!--end wrap-->
</div>
<!--end bg-->
<!--author:xiaoang@360.cn-->
<script type="text/javascript" async="" src="./res/page_test/js/ga.js"></script>
<script src="./res/page_test/js/172.js"></script>
<script src="./res/page_test/js/index.js"></script>
<script type="text/javascript">
    var sDefaultUrl = "http://e.360.cn/home?";
</script>
<script type="text/javascript" src="./res/page_test/js/login.js"></script>


</body>
</html>