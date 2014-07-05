<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美分网——个人中心</title>
<LINK rel=stylesheet type=text/css href="css/personalCenter_common.css">
<LINK rel=stylesheet type=text/css href="css/personalCenter.css">
<LINK rel=stylesheet type=text/css href="css/mostBeauty.css">
<LINK rel=stylesheet type=text/css href="css/ablumn.css">
<LINK rel=stylesheet type=text/css href="css/button.css">
</head>

<body>
<div id="layout">
  <div class="topBar">
    <div class="logoP"><a href="#"><img src="images/logo.jpg" width="125" /></a></div>
    <ul>
      <li><a href="#">我的个人中心</a></li>
      <li><a href="#">我的美分主页</a></li>
    </ul>
    <div class="sig"> <a href="#">返回美分首页</a>|<a href="#">退出</a> </div>
    <FORM id="search-form" action="">
      <FIELDSET>
        <INPUT onblur="if(this.value==''){this.value='请输入搜索关键词'}" class="text" onfocus="if(this.value=='请输入搜索关键词'){this.value=''}" 
value="请输入搜索关键词">
        <INPUT class="submit" type="submit" value="">
      </FIELDSET>
    </FORM>
  </div>
  <!--./top--> 
  <!--content begin-->
  <div id="content">
    <div id="left">
      <div class="avatar"><img src="images/avatar_big.jpg" /></div>
      <h5>范冰冰</h5>
      <ul class="per">
        <li>关注：<span>24</span>&nbsp;&nbsp;得票：<span>24</span></li>
      </ul>
      <ul class="sideNav">
        <li class="current1">心情</li>
        <li style="background: url(images/albumIcon.jpg) no-repeat 45px 9px"><a href="">相册</a></li>
        <li style="background: url(images/friendIcon.jpg) no-repeat 45px 9px"><a href="">好友</a></li>
        <li style="background: url(images/msgIcon.jpg) no-repeat 45px 9px"><a href="">消息</a></li>
        <li style="background: url(images/setIcon.jpg) no-repeat 45px 9px"><a href="">设置</a></li>
      </ul>
      <div class="visitor">
        <h3><span>最近来访</span></h3>
        <div class="cell">
          <a href="#"><img src="images/avatar_samll.jpg" /></a>
          <h4><a href="#">范冰冰</a></h4>
        </div>
        <div class="cell">
          <a href="#"><img src="images/avatar_samll.jpg" /></a>
          <h4><a href="#">范冰冰</a></h4>
        </div>
        <div class="cell">
          <a href="#"><img src="images/avatar_samll.jpg" /></a>
          <h4><a href="#">范冰冰</a></h4>
        </div>
        <div class="cell">
          <a href="#"><img src="images/avatar_samll.jpg" /></a>
          <h4><a href="#">范冰冰</a></h4>
        </div>
      </div>
    </div>
    <!--right begin-->
    <div id="right">
      <h3 style="background: url(images/albumIcon2.gif) no-repeat 1px 10px"><span style="padding-left: 20px">相册</span></h3>
      <div class="myAblum">
        <div class="importantes">
          <button class="funcB mr2">编辑照片信息</button>
          <a href="#" class="funcD">返回相册</a></div>
        <div class="clr"></div>
        <div class="ablumContent">
          <h4><span>编辑照片：</span></h4>
          <div class="edit">
            <p>提示：您可以指定一张图片作为当前相册的封面图片，但是，在下次上传新的图片后，系统会自动选择一张新图片来更新本相册的封面图片。</p>
            <ul>
              <li>
                <div class="x">
                  <input type="checkbox" />
                </div>
                <div class="y"><img src="images/slide3.jpg" width="160" /></div>
                <div class="z">
                  <textarea class="editInput"></textarea>
                </div>
              </li>
              <li>
                <div class="x">
                  <input type="checkbox" />
                </div>
                <div class="y"><img src="images/slide3.jpg" width="160" /></div>
                <div class="z">
                  <textarea class="editInput"></textarea>
                </div>
              </li>
              <li class="control">
                <input type="checkbox" />
                <a href="#">全选</a><a href="#" class="funcA mR2 noF">更新</a><a href="#" class="funcA noF">删除</a> </li>
            </ul>
            <p>删除图片提示：如果你要删除的图片出现在你的日志、话题中，删除后，会导致内容里面的图片同时无法显示。</p>
          </div>
        </div>
      </div>
    </div>
    <!-- footer -->
    <div id="footer">
      <ul class="listFoot ml40">
        <li>关于</li>
        <li><a href="#">什么是美分</a></li>
        <li><a href="#">美分团队</a></li>
        <li><a href="#">加入我们</a></li>
      </ul>
      <ul class="listFoot">
        <li>合作</li>
        <li><a href="#">市场与商业合作</a></li>
        <li><a href="#">合作链接</a></li>
        <li><a href="#">联络方式</a></li>
        <li><a href="#">投资与资本</a></li>
      </ul>
      <ul class="listFoot">
        <li>服务/帮助</li>
        <li><a href="#">在线客服</a></li>
        <li><a href="#">防骗说明</a></li>
      </ul>
      <ul class="listFoot mrBorder">
        <li>更多</li>
        <li><a href="#">美分声明</a></li>
        <li><a href="#">隐私政策</a></li>
        <li><a href="#">使用条款</a></li>
        <li></li>
      </ul>
      <ul class="listFoot" style="width: 230px; float: left; margin-left: 40px;">
        <li>邮件订阅</li>
        <li>
          <FORM id="subscribe" action="">
            <FIELDSET>
              <INPUT onblur="if(this.value==''){this.value='请输入邮箱地址'}" class="text" onfocus="if(this.value=='请输入邮箱地址'){this.value=''}" value="请输入邮箱地址">
              <INPUT class="submit" type="submit" value="订阅">
            </FIELDSET>
          </FORM>
        </li>
        <li></li>
      </ul>
    </div>
    <div class="trailor"><span>&copy; 2011</span><a href="#">美分网</a> <span>(MMFOO.COM), All rights reserved. </span>辽ICP证090052号</div>
  </div>
</div>
</body>
</html>
