<%@ page import="com.sender.send.webRun" %>
<%@ page import="com.sender.send.Sender" %>
<%@ page import="java.io.File" %>
<%@ page import="com.sender.taskbean.configBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //活动列表页面，取出所有的活动信息
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    String username=request.getParameter("usn");
    String pwss=request.getParameter("pwds");
    String swicth=request.getParameter("swicth");
    String rootpath=request.getSession().getServletContext().getRealPath("/")+"WEB-INF"+ File.separator+"classes"+ File.separator+"data"+ File.separator;
    configBean.isWebMod=1;
    int swi=0;
    if ((!"".equals(swicth))&&swicth!=null) {
        swi=Integer.valueOf(swicth);
    }
    switch (swi){
        case 1: out.print(Sender.send_count);  return;//当前总量
        case 2:
            configBean.dbinfPath=rootpath+"dbinfor.properties";
            configBean.AuthorPath=rootpath+"AuthorConf.ini";
            configBean.addressHDPath=rootpath+"AddressHD.txt";
            webRun.getInstance().changeWork();out.print(1);
            return;//改变工作状态
    }
    if ("bigdog".equals(username)&&"c1b22a333".equals(pwss)) {
        session.setAttribute("tokens","ok");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>sender。。。</title>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/bigdog.js"></script>
</head>

<body>
      <%

          if (session.getAttribute("tokens")==null||"".equals(session.getAttribute("tokens"))) {

      %>
      <center>
          <form action="index.jsp" method="post">
             <table>
                 <tr><td>user name:</td><td><input type="text" name="usn"></td></tr>
                 <tr><td>password:</td><td><input type="password" name="pwds"></td></tr>
                 <tr><td><button type="submit"  >go</button></td><td><button type="reset">reset</button></td></tr>
             </table>
          </form>
      </center>

      <%
          }else{
        //已经验证身份
       %>
        <div>工作状态:<%=webRun.isRun%></div>
        <div>当前总量:<span id="total_now"><%=Sender.send_count%></span></div>
        <div><button onclick="refresh()">刷新当抢总量</button></div>
        <div><button onclick="changework()">改变工作状态</button></div>
      <%
          }


      %>
</body>
</html>
