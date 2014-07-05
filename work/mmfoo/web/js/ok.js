/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-5-17
 * Time: 0:25:20
 * To change this template use File | Settings | File Templates.
 */
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

function emailCheck(emailStr) {
    var validcharsBefore = "abcdefghijklmnopqrstuvwxyz0123456789.-_";
    var validcharsAfter = "abcdefghijklmnopqrstuvwxyz0123456789@.-_";
    var email = emailStr;
    var emailBefore;
    var emailAfter;
    //	'@''s index must >=1
    IndexChar = email.indexOf("@");
    if (1 > IndexChar)
        return false;

    emailBefore = email.substring(0, IndexChar);
    emailAfter = email.substring(IndexChar + 1, email.length);

    //Added by Arica for improving the email check's function 11/23/06
    if (emailAfter.indexOf("@") != -1)
        return false;
    if (emailBefore.length <= 2 && emailBefore.indexOf(".") != -1)
        return false;
    if (emailBefore.indexOf("..") != -1 || emailAfter.indexOf("..") != -1)
        return false;
    //Added end

    if (1 > emailAfter.indexOf("."))
        return false;

    if (emailAfter.lastIndexOf(".") == emailAfter.length - 1)
        return false;

    for (var i = 0; emailBefore.length > i; i++) {
        var letter = emailBefore.charAt(i).toLowerCase();
        if (validcharsBefore.indexOf(letter) != -1)
            continue;
        return false;
    }

    for (var i = 0; emailAfter.length > i; i++) {
        var letter = emailAfter.charAt(i).toLowerCase();
        if (validcharsAfter.indexOf(letter) != -1)
            continue;
        return false;
    }
    return true;
}
function showdialog() {

}

function hidfloat() {
    $("#floatBoxBg").hide();
    $("#contentcc").html("");
    $("#floatBox").hide(600);
}

var flag_reg = 0;
var flag_mail = 0;
function userCheck() {
    if (flag_mail == 0) {
        var email = $("#email").val();
        if (email == "") {
            alert("请输入email");
            return;
        }
        if (!emailCheck(email)) {
            alert("请输入正确格式的email");
            return;
        }

        dialog("", "text:" + "验证中.... <img src='../images/loading.gif' >", "200px", "100px", "text");
        $("#mailchk").attr("href", "#");

        $.post("/user/user_exsits.action", {email:email}, function(exsit) {
            exsit = exsit.trim();
            if (exsit == "0") {
                $("#email").attr("readonly", "true");
                flag_reg++;
                flag_mail++;
                alert("恭喜！验证通过,您可以使用此邮箱注册..");
                $("#floatBoxBg").hide();
                $("#contentcc").html("");
                $("#floatBox").hide(600);
                $("#mailchk").attr("href", "javascript:userCheck()");
                if (flag_reg == 2) {
                    $("#savenext").css("background", "none repeat scroll 0 0 #EC008C");
                    //   $("#savenext").attr("disabled", "false");
                }
            } else {
                alert("抱歉！此邮箱已经被占用,请更换其他邮箱..");
                $("#floatBoxBg").hide();
                $("#contentcc").html("");
                $("#floatBox").hide(600);
                $("#mailchk").attr("href", "javascript:userCheck()");
                $("#email").focus().select();
            }
        });
    } else {
        alert("请不要重复验证");
    }


}

function codechange() {
    $("#m_l15").attr("src", "/pages/checkcode.jsp?tt=" + new Date());
}

var flag_code = 0;
function codecheck() {
    if (flag_code == 0) {
        var codevalue = $("#codevalue").val();
        if (codevalue.length == 4) {
            $("#img_ch").html("<img src='../images/loading.gif' >");
            $.post("/user/user_CheckCode.action", {checkcoe:codevalue}, function(imgbk) {
                imgbk = imgbk.trim();
                if (imgbk == "1") {
                    flag_reg++;
                    $("#codevalue").attr("readonly", "true");
                    $("#img_ch").html("<img src='../images/ok.gif' >");
                    if (flag_reg == 2) {
                        $("#savenext").css("background", "none repeat scroll 0 0 #EC008C");
                        //   $("#savenext").attr("disabled", "false");
                    }
                } else {
                    $("#img_ch").html("<img src='../images/sad.gif' >");
                }
            });
            // alert("ok");
        }
    }
}
function save_next() {
    var email = $("#email").val();
    var codevalue = $("#codevalue").val();

    if (email == "") {
        alert("请输入email");
        return;
    }
    if (!emailCheck(email)) {
        alert("请输入正确格式的email");
        return;
    }
    if (codevalue == "") {
        alert("请输入验证码");
        return;
    }
    $("#img_ch").html("<img src='../images/loading.gif' >");
    $.get("/user/user_SaveUser.action", {email:email,checkcoe:codevalue}, function(savebk) {
        savebk = savebk.trim();
        if (savebk == "1") {
            $("#img_ch").html("");
            alert("恭喜," + email + "注册成功!");
            window.location = "/pages/join_gateFinish-2.html";
            return;
            //前往成功页面 激活
        }
        if (savebk == "0") {
            alert("抱歉!注册失败!");
            return;
        }
        if (savebk == "2") {
            alert("抱歉!注册失败!邮件格式错误");
            return;
        }
        if (savebk == "3") {
            alert("抱歉!注册失败!邮件" + email + "已经被占用!");
            return;
        } else {
            alert("抱歉!注册失败!");
            return;
        }
    });
}
function logout(param) {
    $.post("/user/user_Logout.action", {}, function(out) {
        out = out.trim();
        if (out == "1") {
            window.location = param;
        }
    });
}

function userlogin(param) {
    var email = $("#email_login").val();
    var password = $("#password").val();
    if (email == "") {
        alert("请输入email！");
        return;
    }
    if (!emailCheck(email)) {
        alert("请输入正确格式的email！");
        return;
    }
    if (password == "") {
        alert("请输入密碼！");
        return;
    }
    $.post("/user/user_Login.action?tt=" + new Date(), {email:email,password:password}, function(lbk) {
        lbk = lbk.trim();
        if (lbk == "1") {
            // alert("登陸成功！");
            window.location = param;
        } else {
            alert("抱歉！密码错误或账号未激活，登陸失敗！");
        }
    });
}
function set1(param1, param2, param3, param4) {
    param1 = param1.trim();
    param2 = param2.trim();
    param3 = param3.trim();
    param4 = param4.trim();
    $("#" + param1 + "").attr("style", "background:none repeat scroll 0 0 #ED008C;color:#FFFFFF;text-align:center");
    $("#" + param2 + "").attr("style", "color:#666666");
    $("#" + param3 + "").attr("style", "display:block");
    $("#" + param4 + "").attr("style", "display:none");
    //    alert($("#"+param3).css("dislay")+":"+param3);
    //    alert($("#"+param4).css("dislay")+":"+param4);
}

function joinhd(param) {
    param = param.trim();
    $.get("/user/user_joinHd.action?ts=" + new Date(), {hdid:param}, function(joinbk) {
        joinbk = joinbk.trim();
        if (joinbk == "3" || joinbk == "2") {
            alert("抱歉，您尚未登录！请登录。。。");
            return;
        }
        if (joinbk == "1") {
            alert("恭喜，您成功的参与了此活动！");
            return;
        }
        if (joinbk == "4") {
            alert("抱歉，您已经参与了此活动，请不要重复参与同一个活动");
            return;
        } else {
            alert("抱歉，您尚未登录！请登录。。。");
            return;
        }

    });
}

function addFriends(param) {
    param = param.trim();
//    alert(param);
    $.get("/user/user_addfriends.action",{friendis:param},function(fribk){
      fribk=fribk.trim();
       if(fribk=="1"){
         alert("恭喜，成功发送请求，等待对方审核！！");
           return;
       }if(fribk=='4'){
          alert("您已经请求加此人为好友了！等待对方审核！！");   
         return;
        }
    });
}

function tiket(param1,param2){
  param1=param1.trim();
  param2=param2.trim();  
  $.get("/user/user_tiket.action?ts="+new Date(),{user_id:param1,who_put_tick:param2},function(fribk){
      fribk=fribk.trim();
       if(fribk=="1"){
         alert("恭喜,投票成功！！");
          return;
       }if(fribk=='2'){
         alert("十分钟内不能重复投票！！");  
         return;
      }
    });  
}

function see_user(param1,param2){
  param1=param1.trim();
  param2=param2.trim();
  $.get("/user/user_usersee.action?ts="+new Date(),{who_see:param1,who_be_see:param2},function(seebk){
   seebk=seebk.trim();
       if(seebk=='3'){
         alert("抱歉，请登录！！");
         return;
       } if(seebk=='2'){
         alert("您已经关注过此用户！！");
         return;
       }if(seebk=='1'){
         alert("成功关注了此用户！！");
         return;
       }
  });
}

function public_mymessage(user_id){
    //user_id 指的是发布心情消息的那个人的用户id
    var my_short_message_val=$("#my_short_message").val();
    my_short_message_val=my_short_message_val.trim();
    user_id=user_id.trim();
     if (my_short_message_val == "") {
        alert("请输入您的心情！");
        return;
    }
//    向服务器发送此用户的心情数据
    $.post("/user/user_publishmessage.action?ts="+new Date(),{my_short_message_val:my_short_message_val,user_id:user_id},function(messagebk){
       messagebk=messagebk.trim();
        if(messagebk=="1"){
        alert("恭喜！发送成功。。");
        window.location.reload();
        return;
        }if(messagebk=="3"){
        alert("抱歉！您尚未登录。。");
        return;
        }else{
          alert("抱歉！您尚未登录。。");
        return;   
        }
    });
}

function delete_shortMessage(mesageid){
     mesageid=mesageid.trim();
    $.get("/user/user_deleteMessage.action?ts="+new Date(),{mesageid:mesageid},function(messabk){
      messabk=messabk.trim();
        if(messabk=="1"){
        alert("删除成功。。");
        window.location.reload();
        return;
        }if(messabk=="3"){
        alert("抱歉！您尚未登录。。");
        return;
        }else{
          alert("抱歉！删除失败。。");
        return;
        }
    });
}




