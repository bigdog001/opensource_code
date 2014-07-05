function textchange(e,t){
var n=$(e).children(":first");
n.next().slideUp(1e3),n.insertAfter($(e).children(":last")).css("display","block"),setTimeout(function(){textchange(e,t)},t)
}
QHPass.resConfig.src="pcw_adsystem",QHPass.resConfig.loginOpts.validatelm="1",QHPass.resConfig.loginOpts.loginType=["normal","mobile"],
QHPass.resConfig.loginOpts.thirdLogin=[],
QHPass.resConfig.postCharset="utf-8",
QHPass.resConfig.loginAfterSetName=!1,
$(function(){
var e=new XPC("crm_reg_iframe");
e.on("message",function(e){e&&$("#crm-reg-close").click()});
var t="http://e.360.cn/",n=$("body").attr("name");
$(".navbar-nav li."+n).addClass("active"),
$("#banner").Play({autoplay:!0,interval:6e3}),
$("#login_button").on("click",function(){QHPass.login(t,{afterRender:function(){$(".reg-new-account").live("click",function(){$("#mod_quc_pop").hide(),$("#reg-crm-wrap").load("/static/front/html/special/crmreg.html",function(){$("#reg_crm_pop").show(),$(".pop-dia-close").live("click",function(){$("#reg_crm_pop").hide()}),$(".show").on("click",function(){var e='<iframe src="http://crm.360.cn/ka/api/register" name="crm_reg_iframe" frameborder="0" scrolling="no" marginHeight="0" marginWidth="0" width="100%" height="560"></iframe>';$("#reg-crm-wrap").html(e)}),$(".search").on("click",function(){$("#mod_quc_pop").show(),$("#reg_crm_pop").hide()}),$(".login_now").on("click",function(){$("#reg_crm_pop").hide(),$(".clk-quc-login").click()})})})}})}),textchange(".news_li",6e3)})