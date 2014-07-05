<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美分网——个人中心</title>
<LINK rel=stylesheet type=text/css href="css/personalCenter_common.css">
<LINK rel=stylesheet type=text/css href="css/jFade.css">
<LINK rel=stylesheet type=text/css href="css/personalCenter.css">
<LINK rel=stylesheet type=text/css href="css/mostBeauty.css">
<LINK rel=stylesheet type=text/css href="css/ablumn.css">
<LINK rel="stylesheet" type="text/css" href="css/pageNum.css">
<script type='text/javascript' src='js/jquery.min.js'></script>
<script type='text/javascript' src='js/jfade.js'></script>
<script type="text/javascript">  
	$().ready(function() {
		$('.jfade_image').jfade();
	});  
</script>  
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
          <button class="funcB mr2">上传照片</button><button class="funcB">管理相册</button><a href="#" class="funcD">返回我的相册</a>
        </div>
        <div class="clr"></div>
        <div class="ablumContent">
          <div style="height: 15px;"></div>
          <div id="images">
          <a href="#" class="jfade_image"><img src="images/card02.jpg" /></a>
          <a href="#" class="jfade_image"><img src="images/card04.jpg" /></a>
          <a href="#" class="jfade_image"><img src="images/card09.jpg" /></a>
          <a href="#" class="jfade_image"><img src="images/card02.jpg" /></a>
          <a href="#" class="jfade_image"><img src="images/card04.jpg" /></a>
          <a href="#" class="jfade_image"><img src="images/card09.jpg" /></a>
        </div>
        <!--分页开始-->
      <div class="clr"></div>
      <div class="jogger"><span class="disabled"> < </span><span class="current">1</span><a href="#?page=2">2</a><a href="#?page=3">3</a><a href="#?page=4">4</a><a href="#?page=5">5</a><a href="#?page=6">6</a><a href="#?page=7">7</a>...<a href="#?page=199">199</a><a href="#?page=200">200</a><a href="#?page=2"> > </a></div>
      <!--分页结束-->
          
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
