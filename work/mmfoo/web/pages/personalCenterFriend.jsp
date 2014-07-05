<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>美分网——个人中心</title>
    <LINK rel=stylesheet type=text/css href="<%=basePath%>/css/personalCenter_common.css">
    <LINK rel=stylesheet type=text/css href="<%=basePath%>/css/personalCenter.css">
    <LINK rel=stylesheet type=text/css href="<%=basePath%>/css/mostBeauty.css">
    <LINK rel=stylesheet type=text/css href="<%=basePath%>/css/ablumn.css">
    <LINK rel=stylesheet type=text/css href="<%=basePath%>/css/button.css">
    <LINK rel=stylesheet type=text/css href="<%=basePath%>/css/friend.css">
    <script type="text/javascript" src="js/tab.js"></script>
    <script type="text/javascript">
        window.onload = function() {
            var tabtype = {trigger:'click',tabCurrentClass:'newclass',auto:false,timer:1000,delay:300 };
            //  var tabtype={trigger:'mouseover',tabCurrentClass:'newclass',delay:300,auto:true,timer:1500};
            tabInit(tabtype, ['at','ac','o'], ['bt','bc'], ['ct','cc'], ['dt','dc'])
        }
    </script>
</head>

<body>
<div id="layout">
    <jsp:include page="../include/nav_user.jsp"/>
    <!--./top-->
    <!--content begin-->
    <div id="content">
      <jsp:include page="../include/userleft.jsp"/>
        <!--right begin-->
        <div id="right">
            <h3 style="background: url(images/friendIcon2.gif) no-repeat 1px 10px"><span
                    style="padding-left: 20px">好友</span><span id="at" class="ttb">好友列表</span> <span id="bt" class="ttb"
                                                                                                    style="margin-left:0px;">查找好友</span><span
                    id="ct" class="ttb" style="margin-left:0px;">邀请好友</span><span id="dt" class="ttb"
                                                                                  style="margin-left:0px;">好友的邀请</span>
            </h3>

            <div id="ac" class="myAblum">
                <div class="importantes">
                    <p><span>当前共有 1个好友。</span><br/>
                        好友列表按照好友热度高低排序。<br/>
                        好友热度是系统根据您与好友之间交互的动作自动累计的一个参考值，数值越大，表示您与这位好友互动越频繁。 </p>
                </div>
                <div class="clr"></div>
                <div class="friendContent">
                    <div class="friendCell"><a href="#" target="_blank"><img src="images/avatar_samll.jpg"/></a>
                        <h5>章子怡</h5>
                        <h6>8秒前</h6>
                        <a href="#" class="funcA">删除</a></div>
                    <div class="friendCell"><a href="#" target="_blank"><img src="images/avatar_samll.jpg"/></a>
                        <h5>章子怡</h5>
                        <h6>8秒前</h6>
                        <a href="#" class="funcA">删除</a></div>
                    <div class="friendCell"><a href="#" target="_blank"><img src="images/avatar_samll.jpg"/></a>
                        <h5>章子怡</h5>
                        <h6>8秒前</h6>
                        <a href="#" class="funcA">删除</a></div>
                </div>
            </div>
            <!--查找好友-->
            <div id="bc" class="friAblum">
                <div class="albumProject">
                    <ul>
                        <li class="tl" style="width: 130px;">请输入搜索关键词：</li>
                        <li class="tr">
                            <input type="text" class="otherForm"/>
                        </li>
                    </ul>
                    <div class="submitArea">
                        <button style="margin-left: 35px;">查找</button>
                    </div>
                    </ul>
                </div>
            </div>
            <!--邀请好友-->
            <div id="cc" class="friAblum">
                <div class="invateBox">
                    <h4><span>我的好友邀请链接</span></h4>

                    <p>您可以通过QQ、MSN等IM工具，或者发送邮件，把下面的链接告诉你的好友，邀请他们加入进来。 </p>

                    <div class="linkBox btmB">
                        <input class="link"/>
                        <button>复制</button>
                    </div>
                </div>
                <!--邮箱邀请-->
                <div class="invateBox">
                    <h4><span>给好友发送Email邀请</span></h4>

                    <p>通过Email发送邮件的方式，邀请你的好友。多个Email使用","分割。</p>

                    <div class="linkBox">
                        <div class="msgBox2">
                            <button>从地址簿中添加</button>
                        </div>
                        <div class="msgBox">
                            <h4>好友Email地址：</h4>
                            <textarea class="emailBox"></textarea>
                        </div>
                        <div class="invate">
                            <h4>想对好友说的话：</h4>
                            <textarea class="emailBox2"></textarea>
                            <button class="funcB">发送邀请</button>
                        </div>
                        <div class="preview">
                            <h4>预览邀请函</h4>

                            <div class="mail">
                                <div class="avatar"><img src="images/avatar_samll.jpg"/>
                                    <h5>范冰冰</h5>
                                </div>
                                <div class="con">
                                    <p>Hi，我是<span>admin</span>，在美分网上建立了个人主页，邀请你也加入并成为我的好友。<br/>
                                        <br/>
                                        请加入到我的好友中，你就可以通过我的个人主页了解我的近况，分享我的照片，随时与我保持联系。<br/>
                                        <br/>
                                        邀请附言：<br/>
                                        <br/>
                                        <span>请你点击以下链接，接受好友邀请：</span><br/>
                                        <a href="#" target="_blank">http://sws.ebudong.net/uc/invite.php?u=1&c=b6cb580a2f294aa1 </a><br/>
                                        <br/>
                                        <span>如果你拥有美分网上面的账号，请点击以下链接查看我的个人主页：</span><br/>
                                        <a href="#" target="_blank">http://sws.ebudong.net/uc/space.php?uid=1 </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--好友的邀请-->
            <div id="dc" class="friAblum">
                <div class="importantes">
                    <p><a href="#">忽略所有好友申请（慎用）</a>|<a href="#" class="mL4">批准所有好友申请</a><br/>
                        <span>请选定好友的申请进行批准或者忽略。</span></p>
                </div>
                <div class="clr"></div>
                <div class="friendContent">
                    <div class="friendCell"><a href="#" target="_blank"><img src="images/avatar_samll.jpg"/></a>
                        <h5>章子怡</h5>
                        <h6>8秒前</h6>
                        <a href="#" class="funcA">批准申请</a></div>
                    <div class="friendCell"><a href="#" target="_blank"><img src="images/avatar_samll.jpg"/></a>
                        <h5>章子怡</h5>
                        <h6>8秒前</h6>
                        <a href="#" class="funcA">批准申请</a></div>
                    <div class="friendCell"><a href="#" target="_blank"><img src="images/avatar_samll.jpg"/></a>
                        <h5>章子怡</h5>
                        <h6>8秒前</h6>
                        <a href="#" class="funcA">批准申请</a></div>
                </div>
            </div>
        </div>
        <!-- footer -->
        <jsp:include page="../include/footer.jsp"/>
    </div>
</div>
</body>
</html>
