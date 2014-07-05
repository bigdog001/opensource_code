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
		  <LINK rel=stylesheet type=text/css href="<%=basePath%>/css/feeling.css">
		  <LINK rel=stylesheet type=text/css href="<%=basePath%>/css/setting.css">
          <LINK rel=stylesheet type=text/css href="<%=basePath%>/css/friend.css">
		  <script type="text/javascript" src="<%=basePath%>/js/tab.js"></script>
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
                <h3 style="background: url(<%=basePath%>/images/setIcon2.gif) no-repeat 1px 10px"><span style="padding-left: 20px">个人设置</span><span id="at" class="ttb">个人资料</span> <span id="bt" class="ttb" style="margin-left:0px;">我的头像</span><span id="ct" class="ttb" style="margin-left:0px;">隐私设置</span></h3>
                <div id="ac" class="myFeel">
                  <FORM id="sheet-form" action="">
                    <FIELDSET>
                      <div class="sheetItem">
                        <label for=""> 真实姓名：</label>
                        <p>范冰冰</p>
                      </div>
                      <div class="sheetItem">
                        <p class="label"> 出生日期： </p>
                        <ul class="alternate">
                          <li style="width: 100px;">
                            <div style="width: 70px; border: 1px solid #ccc; overflow: hidden; float: left;">
                              <p style="padding: 0px; margin: 0px; overflow: hidden; border: 1px solid #fff;">
                                <select style="vertical-align:middle; width: 72px; margin: -2px; padding: 3px; ">
                                  <option value="">1989</option>
                                </select>
                              </p>
                            </div>
                            <span>年</span> </li>
                          <li style="width: 80px;">
                            <div style="width: 40px; border: 1px solid #ccc; overflow: hidden;float: left;">
                              <p style="padding: 0px; margin: 0px; overflow: hidden; border: 1px solid #fff;">
                                <select style="vertical-align:middle; width: 42px; margin: -2px; padding: 3px; ">
                                  <option value="">7</option>
                                </select>
                              </p>
                            </div>
                            <span>月</span> </li>
                          <li style="width: 80px;">
                            <div style="width: 50px; border: 1px solid #ccc; overflow: hidden;float: left;">
                              <p style="padding: 0px; margin: 0px; overflow: hidden; border: 1px solid #fff;">
                                <select style="vertical-align:middle; width: 52px; margin: -2px; padding: 3px; ">
                                  <option value="">12</option>
                                </select>
                              </p>
                            </div>
                            <span>日</span> </li>
                        </ul>
                      </div>
                      <div class="sheetItem">
                        <label for=""> 出生地：</label>
                        <input name="" id="" value="" size="35" maxlength="50" type="text" class="textInput sizeC"/>
                      </div>
                      <div class="sheetItem">
                        <label for=""> 身高：</label>
                        <input name="" id="" value="" size="5" maxlength="5" type="text" class="textInput sizeB"/>
                        cm </div>
                      <div class="sheetItem">
                        <label for="">血型：</label>
                        <div style="width: 50px; border: 1px solid #ccc; overflow: hidden;">
                          <p style="padding: 0px; margin: 0px; overflow: hidden; border: 1px solid #fff;">
                            <select style="vertical-align:middle; width: 52px; margin: -2px; padding: 3px; ">
                              <option value="">B型</option>
                            </select>
                          </p>
                        </div>
                      </div>
                      <div class="sheetItem">
                        <label for=""> 职业：</label>
                        <input name="" id="" value="" size="35" maxlength="50" type="text" class="textInput sizeC"/>
                      </div>
                      <div class="sheetItem">
                        <label for=""> 兴趣爱好：</label>
                        <input name="" id="" value="" size="35" maxlength="50" type="text" class="textInput sizeD"/>
                      </div>
                      <div class="sheetItem">
                        <label for=""> QQ：</label>
                        <input name="" id="" value="" size="35" maxlength="50" type="text" class="textInput sizeC"/>
                      </div>
                      <div class="sheetItem">
                        <label for=""> 手机号码：</label>
                        <input name="" id="" value="" size="35" maxlength="50" type="text" class="textInput sizeC"/>
                      </div>
                      <div class="sheetItem">
                        <label for=""> 电子邮箱：</label>
                        <input name="" id="" value="" size="35" maxlength="50" type="text" class="textInput sizeD"/>
                      </div>
                    </FIELDSET>
                    <div class="btnHold">
                      <button type="submit" class="funcB">继续下一项</button>
                      <button type="submit" class="funcB mL7">保存</button>
                    </div>
                  </FORM>
                </div>
                <div id="bc" class="myFeel">
                  <div class="invateBox btmB">
                    <h4><span>当前我的头像</span></h4>
                    <p>如果您还没有设置自己的头像，系统会显示为默认头像，您需要自己上传一张新照片来作为自己的个人头像。 </p>
                    <div class="avatarPreview"><img src="images/6a.jpg" /></div>
                  </div>
                  <!--设置新头像-->
                  <div class="invateBox">
                    <h4><span>设置我的头像</span></h4>
                    <p>请选择一个新照片进行上传编辑。<br />提示：头像保存后，您可能需要刷新一下本页面(按F5键)，才能查看最新的头像效果。</p>
                    
                  </div>
                </div>
                <div id="cc" class="myFeel">
                  <div class="invateBox">
                    <h4><span>个人隐私设置</span></h4>
                    <p>你可以完全控制哪些人可以看到你的主页上面的内容</p>
                    <FORM id="sheet-form" action="">
                    <FIELDSET>
                      <div class="sheetItem">
                        <label for="" class="tt">个人主页：</label>
                        <div style="width: 110px; border: 1px solid #ccc; overflow: hidden;">
                          <p style="padding: 0px; margin: 0px; overflow: hidden; border: 1px solid #fff;">
                            <select style="vertical-align:middle; width: 112px; margin: -2px; padding: 3px; ">
                              <option value="">全站用户可见</option>
                              <option value="">尽好友可见</option>
                              <option value="">尽自己户可见</option>
                            </select>
                          </p>
                        </div>
                      </div>
                      <div class="sheetItem">
                        <label for="" class="tt">好友列表：</label>
                        <div style="width: 110px; border: 1px solid #ccc; overflow: hidden;">
                          <p style="padding: 0px; margin: 0px; overflow: hidden; border: 1px solid #fff;">
                            <select style="vertical-align:middle; width: 112px; margin: -2px; padding: 3px; ">
                              <option value="">全站用户可见</option>
                              <option value="">尽好友可见</option>
                              <option value="">尽自己户可见</option>
                            </select>
                          </p>
                        </div>
                      </div>
                      <div class="sheetItem">
                        <label for="" class="tt">个人动态：</label>
                        <div style="width: 110px; border: 1px solid #ccc; overflow: hidden;">
                          <p style="padding: 0px; margin: 0px; overflow: hidden; border: 1px solid #fff;">
                            <select style="vertical-align:middle; width: 112px; margin: -2px; padding: 3px; ">
                              <option value="">全站用户可见</option>
                              <option value="">尽好友可见</option>
                              <option value="">尽自己户可见</option>
                            </select>
                          </p>
                        </div>
                      </div>
                      <div class="sheetItem">
                        <label for="" class="tt">相册：</label>
                        <div style="width: 110px; border: 1px solid #ccc; overflow: hidden;">
                          <p style="padding: 0px; margin: 0px; overflow: hidden; border: 1px solid #fff;">
                            <select style="vertical-align:middle; width: 112px; margin: -2px; padding: 3px; ">
                              <option value="">全站用户可见</option>
                              <option value="">尽好友可见</option>
                              <option value="">尽自己户可见</option>
                            </select>
                          </p>
                        </div>
                        <p class="formHint">本隐私设置仅在其他用户查看您主页时有效；<br />相关浏览权限需要在每个相册中单独设置方可完全生效。</p>
                      </div>
                      <div class="sheetItem">
                        <label for="" class="tt">活动：</label>
                        <div style="width: 110px; border: 1px solid #ccc; overflow: hidden;">
                          <p style="padding: 0px; margin: 0px; overflow: hidden; border: 1px solid #fff;">
                            <select style="vertical-align:middle; width: 112px; margin: -2px; padding: 3px; ">
                              <option value="">全站用户可见</option>
                              <option value="">尽好友可见</option>
                              <option value="">尽自己户可见</option>
                            </select>
                          </p>
                        </div>
                        <p class="formHint">本隐私设置仅在其他用户查看您主页时有效；</p>
                      </div>
                      <div class="sheetItem">
                        <label for="" class="tt">投票：</label>
                        <div style="width: 110px; border: 1px solid #ccc; overflow: hidden;">
                          <p style="padding: 0px; margin: 0px; overflow: hidden; border: 1px solid #fff;">
                            <select style="vertical-align:middle; width: 112px; margin: -2px; padding: 3px; ">
                              <option value="">全站用户可见</option>
                              <option value="">尽好友可见</option>
                              <option value="">尽自己户可见</option>
                            </select>
                          </p>
                        </div>
                        <p class="formHint">本隐私设置仅在其他用户查看您主页时有效；</p>
                      </div>
                    </FIELDSET>
                    <div class="btnHold">
                      <button type="submit" class="funcB">保存设置</button>
                    </div>
                  </FORM>
                  </div>
                </div>
              </div>
              <!-- footer -->
              <jsp:include page="../include/footer.jsp"/>
            </div>
          </div>
</body>
</html>
