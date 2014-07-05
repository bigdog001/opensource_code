<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="mfo" uri="mmfoo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>美分网——个人中心-${sessionScope.login_email}</title>
    <LINK rel=stylesheet type=text/css href="<%=basePath%>/css/personalCenter_common.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/personalCenter.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/mostBeauty.css">
<LINK rel=stylesheet type=text/css href="<%=basePath%>/css/feeling.css">
		  <%--<script type="text/javascript" src="<%=basePath%>/js/tab.js"></script>--%>
		  <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
		  <script type="text/javascript" src="<%=basePath%>/js/ok.js"></script>
		  <script type="text/javascript">
				window.onload=function(){
					 var tabtype={trigger:'click',tabCurrentClass:'newclass',auto:false,timer:1000,delay:300 };
					//  var tabtype={trigger:'mouseover',tabCurrentClass:'newclass',delay:300,auto:true,timer:1500};
					 tabInit(tabtype,['at','ac','o'],['bt','bc'])
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
            <h3 style="background: url(<%=basePath%>/images/feelingIcon2.gif) no-repeat 1px 10px"><span
                    style="padding-left: 20px">心情</span><span id="at" class="ttb"
                                                              style="background:none repeat scroll 0 0 #ED008C;color:#FFFFFF;text-align:center"
                                                              onclick="set1('at','bt','ac','bc')">我的心情</span> <span
                    id="bt" class="ttb" style="margin-left:0px;" onclick="set1('bt','at','bc','ac')">好友心情</span></h3>

            <div id="ac" class="myFeel" style="display:block" >
                <div class="msgBox">
                    <h4><span>写心情</span></h4>
                    <textarea class="inn" id="my_short_message"></textarea>
                    <button class="funcB" onclick="public_mymessage('${userbean.user_id}')">发布</button>
                </div>

                  <mfo:get_shortmessage_byuser_id user_id="${userbean.user_id}" page="1" size="5"/>
                    <c:forEach var="sm" items="${getshortmessagebyemail}">
                        <div class="item">
                            <div class="avatar"><a href="<%=basePath%>/pages/personal_album-${userbean.user_id}.html" target="_blank"><img
                                    src="<%=basePath%>/images/${userbean.userthumb}"/></a></div>
                            <div class="rightInfo">
                                <h5><a href="<%=basePath%>/pages/personal_album-${userbean.user_id}.html"
                                       target="">${userbean.truename}</a>
                                    <%-- 截字操作 --%>
                                    <c:choose>
                                        <c:when test="${fn:length(sm.messagecontent) > 20}">
                                            <c:out value="${fn:substring(sm.messagecontent, 0, 20)}......"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${sm.messagecontent}"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <%-- 截字操作 --%>
                                    <span>
                                        <mfo:getTimeDistance start="${sm.messagetime}"/>

                                    </span></h5><a href="javascript:delete_shortMessage('${sm.mesageid}')" class="funcA">删除</a>
                            </div>
                        </div>
                    </c:forEach>

                
             
            </div>
            <div id="bc" class="myFeel" style="display:none" >
                <div class="item mt">
                    <div class="avatar"><a href="#" target="_blank"><img src="images/avatar_samll.jpg"/></a></div>
                    <div class="rightInfo">
                        <h5><a href="#" target="#">章子怡</a>你可以更新状态，让好友们知道你在做什么...<span>4秒前</span></h5>
                        <a href="#" class="funcA mr">回复</a><a href="#" class="funcA">转发</a>

                        <div class="rep">
                            <INPUT onblur="if(this.value==''){this.value='添加回复'}" class="text"
                                   onfocus="if(this.value=='添加回复'){this.value=''}"
                                   value="添加回复">
                            <INPUT class="submit" type="submit" value="收起">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- footer -->
        <jsp:include page="../include/footer.jsp"/>
    </div>
</div>
</body>
</html>
