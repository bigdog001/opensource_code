/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-5-17
 * Time: 0:25:20
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
            alert("退出成功");
            window.location.reload();
        }
    });
}

function userlogin(param) {
    //        alert(param);
    var email = $("#email_login").val();
    var password = $("#password").val();
    var checkcoe = $("#checkcoe").val();
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
    if (checkcoe == "") {
        alert("请输入验证码！");
        return;
    }
    //    对验证码进行核对
    $.get("/user/user_CheckCode.action?ts=" + new Date(), {checkcoe:checkcoe}, function(s) {
        s = s.trim();
        if (s == "1") {
            $.post("/user/user_Login.action?tt=" + new Date(), {email:email,password:password}, function(lbk) {
                lbk = lbk.trim();
                if (lbk == "0") {
                    alert("抱歉！密码错误或账号未激活，登陸失敗！");
                } else {
                    alert("登陸成功！");
                    window.location = "/user-" + lbk + "-1.html";

                }
            });
        } else {
            alert("验证码错误!");
            return;
        }
    });
    /*
     if ($("#agreeid").attr("checked") == true) {


     } else {
     alert("您未阅读并同意《美分网服务条款》!");
     return;
     }*/


}
function userregist(param) {
    //        alert(param);
    var nickname = $("#nickname").val();
    var email = $("#email").val();
    var password = $("#password").val();
    var repassword = $("#repassword").val();
    var checkcoe = $("#checkcoe").val();
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
    if (repassword == "") {
        alert("请输入确认密碼！");
        return;
    }
    if (checkcoe == "") {
        alert("请输入验证码！");
        return;
    }
    if ($("#agreeid").attr("checked") == true) {
    } else {
        alert("您未阅读并同意《美分网服务条款》!");
        return;
    }

    //    对验证码进行核对
    $.get("/user/user_CheckCode.action?ts=" + new Date(), {checkcoe:checkcoe}, function(s) {
        s = s.trim();
        if (s == "1") {
            //            判断此email代表的用户是否存在
            $.get("/user/user_exsits.action?ts=" + new Date(), {email:email}, function(cz) {
                cz = cz.trim();
                if (cz == "0") {
                    //此邮箱不存在
                    $.post("/user/user_SaveUser.action?tt=" + new Date(), {checkcoe:checkcoe,nickname:nickname,email:email,password:password}, function(lbk) {
                        lbk = lbk.trim();
                        if (lbk == "1") {
                            alert("恭喜注册成功！,我们已经向您的邮箱发送了激活链接,请前往邮箱激活");
                            window.location = param;
                        } else {
                            alert("抱歉！注册失敗！");
                        }
                    });
                } else {
                    alert("抱歉！此邮箱被占用！");
                    return;
                }
            });

        } else {
            alert("验证码错误!");
            return;
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
            //             window.location=urnows;
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
            //             window.location=urnows;
            return;
        }

    });
}

function addFriends(param) {
    param = param.trim();
    //    alert(param);
    $.get("/user/user_addfriends.action", {friendis:param}, function(fribk) {
        fribk = fribk.trim();
        if (fribk == "1") {
            alert("恭喜，成功发送请求，等待对方审核！！");
            return;
        }
        if (fribk == '4') {
            alert("您已经请求加此人为好友了！等待对方审核！！");
            return;
        }
    });
}

function tiket(param1, param2) {
    param1 = param1.trim();
    param2 = param2.trim();
    $.get("/user/user_tiket.action?ts=" + new Date(), {user_id:param1,who_put_tick:param2}, function(fribk) {
        fribk = fribk.trim();
        if (fribk == "1") {
            alert("恭喜,投票成功！！");
            return;
        }
        if (fribk == '2') {
            alert("十分钟内不能重复投票！！");
            return;
        }
    });
}

function see_user(param1, param2) {
    param1 = param1.trim();
    param2 = param2.trim();
    $.get("/user/user_usersee.action?ts=" + new Date(), {who_see:param1,who_be_see:param2}, function(seebk) {
        seebk = seebk.trim();
        if (seebk == '3') {
            alert("抱歉，请登录！！");
            return;
        }
        if (seebk == '2') {
            alert("您已经关注过此用户！！");
            return;
        }
        if (seebk == '1') {
            alert("成功关注了此用户！！");
            return;
        }
        if (seebk == '4') {
            alert("您已经被对方拉黑");
            return;
        }
    });
}

function public_mymessage(user_id) {

    var my_short_message = FCKeditorAPI.GetInstance("my_short_message");
    my_short_message.UpdateLinkedField();
    var my_short_message_val = my_short_message.GetXHTML();

    user_id = user_id.trim();
    if (my_short_message_val == "") {
        alert("请输入您的心情！");
        return;
    }
    //    向服务器发送此用户的心情数据
    $.post("/user/user_publishmessage.action?ts=" + new Date(), {my_short_message_val:my_short_message_val,user_id:user_id}, function(messagebk) {
        messagebk = messagebk.trim();
        if (messagebk == "1") {
            alert("恭喜！发送成功。。");
            window.location.reload();
            return;
        }
        if (messagebk == "3") {
            alert("抱歉！您尚未登录。。");
            return;
        } else {
            alert("抱歉！您尚未登录。。");
            return;
        }
    });
}

function delete_shortMessage(mesageid) {
    mesageid = mesageid.trim();
    $.get("/user/user_deleteMessage.action?ts=" + new Date(), {mesageid:mesageid}, function(messabk) {
        messabk = messabk.trim();
        if (messabk == "1") {
            alert("删除成功。。");
            window.location.reload();
            return;
        }
        if (messabk == "3") {
            alert("抱歉！您尚未登录。。");
            return;
        } else {
            alert("抱歉！删除失败。。");
            return;
        }
    });
}


function addImg() {
    var imgsetadd = $("#imgset_add").val().trim();
    $.post("/user/PictureAction_archiveimg.action?ts=" + new Date(), {setid:imgsetadd}, function(ss) {
        ss = ss.trim();
        if (ss == "3") {
            alert("抱歉！您未登录。。");
            return;
        }
        if (ss == "1") {
            alert("恭喜！操作成功。。");
            return;
        }
        if (ss == "4") {
            alert("抱歉！您没有上传任何照片。。");
            return;
        } else {
            alert("抱歉！您未登录。。");
            return;
        }
    });
}

function addImgSet(param) {
    param = param.trim();
    var setname = $("#setname").val();
    var setdiscribe = $("#setdiscribe").val();
    if (setname == "") {
        alert("请输入名称");
        return;
    }
    //alert(param+":"+setname+":"+setdiscribe);
    //      将数据发往服务器
    $.post("/user/user_addImgSet.action?ts=" + new Date(), {setname:setname,setdiscribe:setdiscribe,user_id:param}, function(bk) {
        bk = bk.trim();
        if (bk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
        }
        if (bk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function loveit(param) {
    param = param.trim();
    $.get("/user/user_loveit.action?ts=" + new Date(), {setid:param}, function(llbk) {
        llbk = llbk.trim();
        if (llbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
        }
        if (llbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function deleteimgset(param, param1) {
    param = param.trim();
    param1 = param1.trim();
    $.get("/user/user_deleteImgSet.action?ts=" + new Date(), {setid:param}, function(llbk) {
        llbk = llbk.trim();
        if (llbk == "1") {
            alert("恭喜！您的操作成功。。");
            window.location = "/usershow-" + param1 + ".html";
        }
        if (llbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function saveImgsetEdit(param) {
    param = param.trim();
    var setname = $("#setname").val();
    var setdiscribe = $("#setdiscribe").val();
    if (setname == "") {
        alert("去输入名称");
        return;
    }
    $.get("/user/user_saveImgSetedite.action?ts=" + new Date(), {setid:param,setname:setname,setdiscribe:setdiscribe}, function(llbk) {
        llbk = llbk.trim();
        if (llbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
        }
        if (llbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function sendingmsg(user_id_from) {
    user_id_from = user_id_from.trim();
    var user_email_to = $("#user_email_to").val();
    var user_msg = $("#user_msg").val();
    if (user_email_to == "") {
        alert("请输入收信人的email");
        return;
    }
    if (!emailCheck(user_email_to)) {
        alert("请输入正确格式的收信人email");
        return;
    }
    if (user_msg == "") {
        alert("请输入私信内容");
        return;
    }
    //在发私信之前判断收件人是否存在

    //    发送私信
    $.post("/user/user_savepm.action?ts=" + new Date(), {user_email_to:user_email_to,user_msg:user_msg,user_id_from:user_id_from}, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            document.getElementById('light').style.display = 'none';
            document.getElementById('fade').style.display = 'none';
            return;
        }
        if (pmbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });

}

function deletemypm(my_user_id) {
    //删除所有发给我的私信
    my_user_id = my_user_id.trim();
    $.get("/user/user_deletmypm.action?ts=" + new Date(), {user_id:my_user_id}, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
        }
        if (pmbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        }
        if (pmbk == "4") {
            alert("抱歉！您不是此空间的主人，无权删除。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function checkbochange(param) {
    if (param.checked) {
        $("input[name='checkbox_user']").attr("checked", "true");
    } else {
        $("input[name='checkbox_user']").removeAttr("checked");
    }

}

function deleteSelected(user_id) {
    user_id = user_id.trim();
    var len = $("input[name=checkbox_user][checked]").size();
    var xx = 0;
    $("input[name=checkbox_user][checked]").each(function() {
        //由于复选框一般选中的是多个,所以可以循环输出,向服务器循环发出多次删除请求
        $.get("/user/user_deletpmsingle.action?ts=" + new Date(), {pmid:$(this).val(),user_id:user_id}, function(pmbk) {
            pmbk = pmbk.trim();
            if (pmbk == "1") {
                xx++;
                if (xx == len) {
                    alert("恭喜！您的操作成功。。");
                    window.location.reload();
                }
                return;
            }
            if (pmbk == "3") {
                alert("抱歉！您未登录。。");
                return;
            }
            if (pmbk == "4") {
                alert("抱歉！您不是此空间的主人，无权删除。。");
                return;
            } else {
                alert("抱歉！您操作失败。。");
                return;
            }
        });
        //        alert(xx);

    });

}

function UnSelectAll() {
    $("input[name='allselect']").removeAttr("checked");
}

function repm_msg(user_email_to, user_id_from, pmid) {
    user_email_to = user_email_to.trim();
    user_id_from = user_id_from.trim();
    //    alert(user_email_to+","+user_id_from+","+pmid);
    var re_pm_msg = $("#re_pm_msg").val();
    if (re_pm_msg == "") {
        alert("请输入要回复给对方的消息!");
        return;
    }
    $.post("/user/user_saverepmmsg.action?ts=" + new Date(), {user_email_to_re:user_email_to,user_msg:re_pm_msg,user_id_from:user_id_from,pmid:pmid}, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
        }
        if (pmbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function chleave() {
    var nn = $("#re_pm_msg").val().length;
    $("#leavetotal").html(300 - nn);
}

function unsee(my_user_id, duifang_user_id) {
    my_user_id = my_user_id.trim();
    duifang_user_id = duifang_user_id.trim();
    $.post("/user/user_unsee.action?ts=" + new Date(), {my_user_id:my_user_id,duifang_user_id:duifang_user_id}, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            window.location.reload();
        }
        if (pmbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function addBlacker(my_user_id, blacker_user_id) {
    my_user_id = my_user_id.trim();
    blacker_user_id = blacker_user_id.trim();
    $.post("/user/user_addBlacker.action?ts=" + new Date(), {my_user_id:my_user_id,blacker_user_id:blacker_user_id}, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            window.location.reload();
        }
        if (pmbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function savejichu(user_id) {
    user_id = user_id.trim();
    var jichu = $("#jichu").val();
    if (jichu == "") {
        alert(" 请输入信息。");
        return;
    }
    $.post("/user/user_savejichu.action?ts=" + new Date(), {my_user_id:user_id,jichu:jichu}, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
            //            window.location.reload();
        }
        if (pmbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
function saveaihao(user_id) {
    user_id = user_id.trim();
    var aihao = $("#aihao").val();
    if (aihao == "") {
        alert(" 请输入信息。");
        return;
    }
    $.post("/user/user_aihao.action?ts=" + new Date(), {my_user_id:user_id,aihao:aihao}, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
            //            window.location.reload();
        }
        if (pmbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
function savejinli(user_id) {
    user_id = user_id.trim();
    var jinli = $("#jinli").val();
    if (jinli == "") {
        alert(" 请输入信息。");
        return;
    }
    $.post("/user/user_jinli.action?ts=" + new Date(), {my_user_id:user_id,jinli:jinli}, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
            //            window.location.reload();
        }
        if (pmbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
function savejiaoyu(user_id) {
    user_id = user_id.trim();
    var jiaoyu = $("#jiaoyu").val();
    if (jiaoyu == "") {
        alert(" 请输入信息。");
        return;
    }
    $.post("/user/user_peixun.action?ts=" + new Date(), {my_user_id:user_id,jiaoyu:jiaoyu}, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
            //            window.location.reload();
        }
        if (pmbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
function interest(hdid) {
    hdid = hdid.trim();
    $.post("/user/user_interest.action?ts=" + new Date(), {hdid:hdid}, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
            //            window.location.reload();
        }
        if (pmbk == "3") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
function adminlogin() {
    var email_login = $("#email_login").val();
    var password = $("#password").val();
    if (email_login == "") {
        alert(" 请输入用户名。");
        return;
    }
    if (password == "") {
        alert(" 请输入密码。");
        return;
    }
    $.get("/admins/admin_login.action?ts=" + new Date(), {email_login:email_login,password:password}, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！登陆成功。。");
            window.location = "/admin/index.jsp";
        } else {
            alert("抱歉！登陆失败。。");
            return;
        }
    });
}

function pleasego(user_id) {
    user_id = user_id.trim();
    $.post("/admins/admin_pleasego.action?ts=" + new Date(), {user_id:user_id}, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
function unpleasego(user_id) {
    user_id = user_id.trim();
    $.post("/admins/admin_unpleasego.action?ts=" + new Date(), {user_id:user_id}, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function deleteUser(user_id) {
    user_id = user_id.trim();
    $.post("/admins/admin_deleteUser.action?ts=" + new Date(), {user_id:user_id}, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function deleteHd(hdid) {
    hdid = hdid.trim();
    $.post("/admins/admin_deleteHd.action?ts=" + new Date(), {hdid:hdid}, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function editeHd() {
    var hdid = $("#hdid").val();
    var hdname = $("#hdname").val();
    var orders = $("#orders").val();
    var hdtitle = $("#hdtitle").val();
    var hdabstract = $("#hdabstract").val();


    //    var hdcontent = $("#hdcontent").val();


    var contents = FCKeditorAPI.GetInstance("hdcontent");
    contents.UpdateLinkedField();
    var hdcontent = contents.GetXHTML();

    var starter_user_id = $("#starter_user_id").val();
    var payfeed = $("#payfeed").val();
    var jiangxian = $("#jiangxian").val();
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var hdface = $("#hdface").val();
    var hd_thumb = $("#hd_thumb").val();
    $.post("/admins/admin_editHd.action?ts=" + new Date(), {
        hdid:hdid,
        hdname         :hdname         ,
        orders         :orders         ,
        hdtitle        :hdtitle        ,
        hdabstract     :hdabstract     ,
        hdcontent      :hdcontent      ,
        starter_user_id:starter_user_id,
        payfeed        :payfeed        ,
        jiangxian      :jiangxian      ,
        starttime      :starttime      ,
        endtime        :endtime        ,
        hdface         :hdface         ,
        hd_thumb       :hd_thumb
    }, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
function addHd() {
    var hdname = $("#hdname").val();
    var orders = $("#orders").val();
    var hdtitle = $("#hdtitle").val();
    var hdabstract = $("#hdabstract").val();

    //    var hdcontent = $("#hdcontent").val();
    var contents = FCKeditorAPI.GetInstance("hdcontent");
    contents.UpdateLinkedField();
    var hdcontent = contents.GetXHTML();

    var starter_user_id = $("#starter_user_id").val();
    var payfeed = $("#payfeed").val();
    var jiangxian = $("#jiangxian").val();
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var hdface = $("#hdface").val();
    var hd_thumb = $("#hd_thumb").val();
    $.post("/admins/admin_addHd.action?ts=" + new Date(), {
        hdname         :hdname         ,
        orders         :orders         ,
        hdtitle        :hdtitle        ,
        hdabstract     :hdabstract     ,
        hdcontent      :hdcontent      ,
        starter_user_id:starter_user_id,
        payfeed        :payfeed        ,
        jiangxian      :jiangxian      ,
        starttime      :starttime      ,
        endtime        :endtime        ,
        hdface         :hdface         ,
        hd_thumb       :hd_thumb
    }, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function deleteSlide(imgid) {
    imgid = imgid.trim();
    $.post("/admins/admin_deleteSlide.action?ts=" + new Date(), {
        imgid         :imgid
    }, function(pmbk) {
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
function editeindeximg() {
    var imgid = $("#imgid").val();
    var oedds = $("#oedds").val();
    var img = $("#img").val();
    var thumb = $("#thumb").val();
    var truename = $("#truename").val();
    var city = $("#city").val();
    var star = $("#star").val();
    var target_url = $("#target_url").val();
    $.post("/admins/admin_editeindeximg.action?ts=" + new Date(), {
        imgid     :imgid     ,
        oedds     :oedds     ,
        img       :img       ,
        thumb     :thumb     ,
        truename  :truename  ,
        city      :city      ,
        star      :star      ,
        target_url:target_url
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
function addindeximg() {
    var oedds = $("#oedds").val();
    var img = $("#img").val();
    var thumb = $("#thumb").val();
    var truename = $("#truename").val();
    var city = $("#city").val();
    var star = $("#star").val();
    var target_url = $("#target_url").val();
    $.post("/admins/admin_addindeximg.action?ts=" + new Date(), {
        oedds     :oedds     ,
        img       :img       ,
        thumb     :thumb     ,
        truename  :truename  ,
        city      :city      ,
        star      :star      ,
        target_url:target_url
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
function updateNews() {
    var newsid = $("#newsid").val();
    var newstitle = $("#newstitle").val();


    //    var hdcontent = $("#hdcontent").val();
    var contents = FCKeditorAPI.GetInstance("newscontent");
    contents.UpdateLinkedField();
    var newscontent = contents.GetXHTML();
    //    alert(newscontent);
    $.post("/admins/admin_updateNews.action?ts=" + new Date(), {
        newsid     :newsid     ,
        newstitle       :newstitle       ,
        newscontent     :newscontent
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。。");
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您未登录。。");
            return;
        } else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function forgetpwd() {
    var email = $("#email").val();
    if (email == "") {
        alert("请输入email");
        return;
    }
    if (!emailCheck(email)) {
        alert("请输入正确格式的email");
        return;
    }
    $.post("/admins/admin_forgetpwd.action?ts=" + new Date(), {
        email     :email
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。系统已经向您的邮箱发送了新的密码");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });

}

function comment(user_id, user_becomment, setid) {
    var commenttt = $("#commenttt").val();
    if (commenttt == "") {
        alert("请输入评论内容！");
        return;
    }
    $.post("/user/user_comment.action?ts=" + new Date(), {
        user_id     :user_id ,
        user_becomment:user_becomment,
        setid:setid ,
        content:commenttt
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });

}

function UpdateLianluoXinxi() {
    var email = $("#email").val();
    if (email == "") {
        alert("请输入email");
        return;
    }
    if (!emailCheck(email)) {
        alert("请输入正确格式的email");
        return;
    }
    var nickname = $("#nickname").val();
    if (nickname == "") {
        alert("请输入昵称");
        return;
    }
    var msn = $("#msn").val();
    if (msn == "") {
        alert("请输入msn");
        return;
    }
    var phone = $("#phone").val();
    if (phone == "") {
        alert("请输入phone");
        return;
    }
    var qqnumber = $("#qqnumber").val();
    if (qqnumber == "") {
        alert("请输入qqnumber");
        return;
    }
    $.post("/user/user_UpdateLianluoXinxi.action?ts=" + new Date(), {
        email     :email ,
        msn:msn,
        phone:phone ,
        qqnumber:qqnumber,
        nickname:nickname
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function Updateaihao() {
    var lovemusic = $("#lovemusic").val();
    if (lovemusic == "") {
        alert("请输入喜欢的音乐");
        return;
    }
    var lovestar = $("#lovestar").val();
    if (lovestar == "") {
        alert("请输入喜欢的明星");
        return;
    }
    var lovemovie = $("#lovemovie").val();
    if (lovemovie == "") {
        alert("请输入喜欢的电影");
        return;
    }
    var lovetv = $("#lovetv").val();
    if (lovetv == "") {
        alert("请输入喜欢的电视");
        return;
    }
    var lovesport = $("#lovesport").val();
    if (lovesport == "") {
        alert("请输入喜欢的运动");
        return;
    }
    var lovereading = $("#lovereading").val();
    if (lovereading == "") {
        alert("请输入喜欢看的书");
        return;
    }
    $.post("/user/user_Updateaihao.action?ts=" + new Date(), {
        lovemusic     :lovemusic ,
        lovestar:lovestar,
        lovemovie:lovemovie ,
        lovetv:lovetv ,
        lovesport:lovesport ,
        lovereading:lovereading
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            //            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
function UpdateJingli() {
    var jinli = $("#jinli").val();
    if (jinli == "") {
        alert("请输入个人经历");
        return;
    }

    $.post("/user/user_UpdateJingli.action?ts=" + new Date(), {
        jinli     :jinli
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            //            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}


function UpdateJingli() {
    var jinli = $("#jinli").val();
    if (jinli == "") {
        alert("请输入个人经历");
        return;
    }

    $.post("/user/user_UpdateJingli.action?ts=" + new Date(), {
        jinli     :jinli
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            //            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

// 作品
function UpdateZuopin() {
    var zuopin = $("#zuopin").val();
    if (zuopin == "") {
        alert("请输信息");
        return;
    }

    $.post("/user/user_UpdateZuopin.action?ts=" + new Date(), {
        zuopin     :zuopin
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            //            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
//  展览
function UpdateZhanlan() {
    var zhanlan = $("#zhanlan").val();
    if (zhanlan == "") {
        alert("请输信息");
        return;
    }

    $.post("/user/user_UpdateZhanlan.action?ts=" + new Date(), {
        zhanlan     :zhanlan
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            //            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

//   媒体采访
function UpdateMeiticaifang() {
    var meiticaifang = $("#meiticaifang").val();
    if (meiticaifang == "") {
        alert("请输信息");
        return;
    }

    $.post("/user/user_UpdateMeiticaifang.action?ts=" + new Date(), {
        meiticaifang     :meiticaifang
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            //            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
//  出席活动
function UpdateChuxihuodong() {
    var chuxihuodong = $("#chuxihuodong").val();
    if (chuxihuodong == "") {
        alert("请输信息");
        return;
    }

    $.post("/user/user_UpdateChuxihuodong.action?ts=" + new Date(), {
        chuxihuodong     :chuxihuodong
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            //            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
//  培训
function UpdatePeixun() {
    var peixun = $("#peixun").val();
    if (peixun == "") {
        alert("请输信息");
        return;
    }

    $.post("/user/user_UpdatePeixun.action?ts=" + new Date(), {
        peixun     :peixun
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            //            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
//  获奖
function UpdateHuojiang() {
    var huojiang = $("#huojiang").val();
    if (huojiang == "") {
        alert("请输信息");
        return;
    }

    $.post("/user/user_UpdateHuojiang.action?ts=" + new Date(), {
        huojiang     :huojiang
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            //            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}
//   学历
function UpdateEducation() {
    var education = $("#education").val();
    if (education == "") {
        alert("请输信息");
        return;
    }

    $.post("/user/user_UpdateEducation.action?ts=" + new Date(), {
        education     :education
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            //            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

function SaveBaseInfor() {
    var EntTime = $("#EntTime").val();
    if (EntTime == "") {
        alert("请输入身日期");
        return;
    }
    var user_star = $("#user_star").val();
    if (user_star == "") {
        alert("请输入星座");
        return;
    }
    var worspace = $("#worspace").val();
    if (worspace == "") {
        alert("请输入工作地");
        return;
    }
    var maleflag = $("input[name='maleflag'][checked]").val();
    if (maleflag == "") {
        alert("请选择性别");
        return;
    }
    var hometown = $("#hometown").val();
    var blood = $("#blood").val();
    var height = $("#height").val();
    var heavy = $("#heavy").val();
    $.post("/user/user_SaveBaseInfor.action?ts=" + new Date(), {
        birthday     :EntTime  ,
        user_star     :user_star  ,
        worspace     :worspace  ,
        sex     :maleflag  ,
        hometown     :hometown  ,
        blood     :blood  ,
        height     :height  ,
        heavy     :heavy
    }, function(pmbk) {
        pmbk = pmbk.trim();
        if (pmbk == "1") {
            alert("恭喜！您的操作成功。");
            //            window.location.reload();
            return;
        }
        if (pmbk == "0") {
            alert("抱歉！您操作失败。。尚未登录");
            return;
        }
        else {
            alert("抱歉！您操作失败。。");
            return;
        }
    });
}

var isIe = (document.all) ? true : false;
//设置select的可见状态
function setSelectState(state) {
    var objl = document.getElementsByTagName('select');
    for (var i = 0; i < objl.length; i++) {
        objl[i].style.visibility = state;
    }
}
function mousePosition(ev) {
    if (ev.pageX || ev.pageY) {
        return {x:ev.pageX, y:ev.pageY};
    }
    return {
        x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,y:ev.clientY + document.body.scrollTop - document.body.clientTop
    };
}
//弹出方法
function showMessageBox(wTitle, content, pos, wWidth) {
    closeWindow();
    var bWidth = parseInt(document.documentElement.scrollWidth);
    var bHeight = parseInt(document.documentElement.scrollHeight);
    if (isIe) {
        setSelectState('hidden');
    }
    var back = document.createElement("div");
    back.id = "back";
    var styleStr = "top:0px;left:0px;position:absolute;background:#666;width:" + bWidth + "px;height:" + bHeight + "px;";
    styleStr += (isIe) ? "filter:alpha(opacity=0);" : "opacity:0;";
    back.style.cssText = styleStr;
    document.body.appendChild(back);
    showBackground(back, 50);
    var mesW = document.createElement("div");
    mesW.id = "mesWindow";
    mesW.className = "mesWindow";
    mesW.innerHTML = "<div class='mesWindowTop'><a href='javascript:closeWindow()' ><img src='../images/close1.gif'></a></div><div class='mesWindowContent' id='mesWindowContent'>" + content + "</div><div class='mesWindowBottom'></div>";
    styleStr = "top:50px;width:" + wWidth + "px;";
    mesW.style.cssText = styleStr;
    document.body.appendChild(mesW);
}
//让背景渐渐变暗
function showBackground(obj, endInt) {
    if (isIe) {
        obj.filters.alpha.opacity += 1;
        if (obj.filters.alpha.opacity < endInt) {
            setTimeout(function() {
                showBackground(obj, endInt)
            }, 5);
        }
    } else {
        var al = parseFloat(obj.style.opacity);
        al += 0.01;
        obj.style.opacity = al;
        if (al < (endInt / 100)) {
            setTimeout(function() {
                showBackground(obj, endInt)
            }, 5);
        }
    }
}
//关闭窗口
function closeWindow() {
    if (document.getElementById('back') != null) {
        document.getElementById('back').parentNode.removeChild(document.getElementById('back'));
    }
    if (document.getElementById('mesWindow') != null) {
        document.getElementById('mesWindow').parentNode.removeChild(document.getElementById('mesWindow'));
    }
    if (isIe) {
        setSelectState('');
    }
}
//测试弹出
function testMessageBox(ev, param) {
    var objPos = mousePosition(ev);
    messContent = "<div class='beautyTop beautyLlk_window'><h2>邀请好友关注我<input type='button' name='' value=''></h2><div class='uploading'>选择常用的邀请方式，邀请好友加入微博。收到邀请的人注册后，就会自动关注你哦！</div><div class='show_pic'><h2>方法一：邀请链接</h2>通过QQ，MSN，电子邮件发送邀请链接给你的朋友<br>注册成功后他们后自动成为你的粉丝<br><input type='text' class='textFs' name='' id='inventurl'><a href='javascript:pastit()'><img src='../images/fas.gif'></a></div><div class='show_pic show_pic1'><h2>方法二：输入邮件,多个邮箱之间用英文逗号隔开</h2><input type='text' class='textFs' name='' id='inventemail'><a href='javascript:email_send()'><img src='../images/fas.gif'></a><span>请输入邮箱地址；多个邮箱地址请用';'隔开</span></div></div>";
    showMessageBox('邀请朋友', messContent, objPos, 706);
    $("#inventurl").attr("value", "http://www.mmfoo.com/regist.html?catogry=" + param);
}

function pastit() {
    var clipp = $("#inventurl").val();
    alert("网站已经成功复制到剪切板");
    if (window.clipboardData) {    alert(123);
        window.clipboardData.clearData();
        window.clipboardData.setData("Text", clipp);
        alert("网站已经成功复制到剪切板");
    } else if (navigator.userAgent.indexOf("Opera") != -1) {  
        window.location = clipp;
        alert("网站已经成功复制到剪切板");
    }
}

function email_send(){
  var email_invent_targets = $("#inventemail").val();
    if(email_invent_targets==""){
        alert("请输入您好友的email");
       return;
    }          
    $.post("/admins/admin_sendEmailInvent.action?ts="+new Date(),{email_invent_targets:email_invent_targets},function(bk){
        bk=bk.trim();
        if(bk=="1"){
             alert("发送成功");
            return;
        }if(bk=="3"){
            alert("您尚未登录");
            return;
        }if(bk=="2"){
           alert("您尚未登录");
            return;
        }if(bk=="0"){
         alert("输入的email地址列为空");
            return;
        } 
    });
}

function testMessageBox1(ev) {
    var objPos = mousePosition(ev);
    messContent = "<div class='beautyTop beautyLlk_window1'><div class='uploading uploading1'>我邀请注册的用户<span>(共1人)</span></div><div class='show_pic show_pic2'><ul><li class='pane'><div class='box_1'><a href=''><img src='../images/meinv5.jpg'></a></div><div class='box_2'><span><a href=''>范冰冰</a></span><h2>北京 粉丝><span>29170</span>人</h2></div><div class='box_3'><a href=''><img src='../images/concern.gif'></a></div></li><li class='pane'><div class='box_1'><a href=''><img src='../images/meinv5.jpg'></a></div><div class='box_2'><span><a href=''>范冰冰</a></span><h2>北京 粉丝><span>29170</span>人</h2></div><div class='box_3'><a href=''><img src='../images/concern.gif'></a></div></li><li class='pane'><div class='box_1'><a href=''><img src='../images/meinv5.jpg'></a></div><div class='box_2'><span><a href=''>范冰冰</a></span><h2>北京 粉丝><span>29170</span>人</h2></div><div class='box_3'><a href=''><img src='../images/concern.gif'></a></div></li></ul></div><div class='page'><a href=''>首页</a><a href=''>1</a><a href=''>2</a><a href=''>3</a><a href=''>4</a><a href=''>下一页</a><a href=''>尾页</a></div></div>";
    showMessageBox('邀请朋友', messContent, objPos, 706);
}

function testMessageBoxName(ev) {
    var objPos = mousePosition(ev);
    messContent = "<div class='beautyTop beautyLlk_window1'><div class='uploading uploading1'>编辑昵称</span></div><div id='modifyName'><input type='text' name='' id=''/><a style=' color:#EC008C;margin-left:16px; font-family:'宋体'' href=''><img src='../images/modi.gif'></a></div></div>";
    showMessageBox('修改昵称', messContent, objPos, 706);
}
function testMessageBoxWeb(ev) {
    var objPos = mousePosition(ev);
    messContent = "<div class='beautyTop beautyLlk_window1'><div class='uploading uploading1'>个性化域名</div><div id='modifyWeb'><input type='text' name='' id=''/><a style=' color:#EC008C;margin-left:16px; font-family:'宋体'' href=''><img src='../images/modi.gif'></a></div></div>";
    showMessageBox('个性化域名', messContent, objPos, 706);
}