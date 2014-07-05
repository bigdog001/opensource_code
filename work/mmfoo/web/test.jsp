<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.cotton.tool.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Timestamp t1=new Timestamp(System.currentTimeMillis());
    Timestamp t2=new Timestamp(System.currentTimeMillis()-(1000*60*60*24*2+1000*60*60*7+1000*60*55+1000*2));
    long ss=t1.getTime()-t2.getTime();
%>

<%=scoreTool.getTimeDifference(t1,t2)%>