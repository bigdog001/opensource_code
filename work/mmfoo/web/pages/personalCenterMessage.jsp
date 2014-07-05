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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美分网——个人中心</title>
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/personalCenter_common.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/personalCenter.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/mostBeauty.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/message.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/feeling.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/friend.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/button.css">
		  <script type="text/javascript" src="js/tab.js"></script>
		  <script type="text/javascript">
				window.onload=function(){
					 var tabtype={trigger:'click',tabCurrentClass:'newclass',auto:false,timer:1000,delay:300 };
					//  var tabtype={trigger:'mouseover',tabCurrentClass:'newclass',delay:300,auto:true,timer:1500};
					 tabInit(tabtype,['at','ac','o'],['bt','bc'],['ct','cc'])
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
      <h3 style="background: url(images/msgIcon2.gif) no-repeat 1px 10px"><span style="padding-left: 20px">消息</span><span id="at" class="ttb">短消息</span> <span id="bt" class="ttb" style="margin-left:0px;">通知</span><span id="ct" class="ttb" style="margin-left:0px;">发短消息</span></h3>
      <div id="ac" class="myFeel">
        
        <div class="item mT">
          <div class="avatar"><a href="#" target="_blank"><img src="images/avatar_samll.jpg" /></a></div>
          <div class="rightInfo">
            <h5><a href="#" target="#">章子怡</a><br />你可以更新状态，让好友们知道你在做什么...<span>4秒前</span></h5>
            <a href="#" class="funcA">查看详情</a><a href="#" class="funcA mL5">删除</a>
          </div>
        </div>
      </div>
      <div id="bc" class="myFeel">
        <div class="importantes">
          <p><span>提示：当你感觉有些同志对你造成骚扰，请点击通知右侧的"删除"，屏蔽此类通知。</span></p>
        </div>
        <div class="noticeBox">
          <ul class="notice">
            <li>
              <div class="co">
                <p>·你的好友Lucky 7已经成功升级为VIP4。等级越高，特权越多哦！</p>
                <span>2010-12-2</span>
              </div>
              <a href="#" class="funcA">删除</a>
            </li>
            <li>
              <div class="co">
                <p>·你的好友Lucky 7已经成功升级为VIP4。等级越高，特权越多哦！</p>
                <span>2010-12-2</span>
              </div>
              <a href="#" class="funcA">删除</a>
            </li>
            <li>
              <div class="co">
                <p>·你的好友Lucky 7已经成功升级为VIP4。等级越高，特权越多哦！</p>
                <span>2010-12-2</span>
              </div>
              <a href="#" class="funcA">删除</a>
            </li>
          </ul>
        </div>
      </div>
      <div id="cc" class="myFeel">

        <div class="messageProject">
                      <ul>
                        <li class="tl">收件人：</li>
                        <li class="tr">
                          <input type="text"  />
                        </li>
                      </ul>
                      <ul>
                        <li class="tl">内容：</li>
                        <li class="tr">
                          <textarea></textarea>
                        </li>
                      </ul>
                      <div class="submitArea"><button>确定</button></div>
                      </ul>
                    </div>
      </div>
    </div>
    <!-- footer -->
    <jsp:include page="../include/footer.jsp"/>
  </div>
</div>
</body>
</html>
