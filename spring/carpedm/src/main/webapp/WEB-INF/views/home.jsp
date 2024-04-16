<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<%--  ${list[0].lb_id } --%>
${list[0].lb_id } ${list[0].lb_name }<br>
${list[1].lb_id } ${list[1].lb_name }<br>
${list[2].lb_id } ${list[2].lb_name }<br>


