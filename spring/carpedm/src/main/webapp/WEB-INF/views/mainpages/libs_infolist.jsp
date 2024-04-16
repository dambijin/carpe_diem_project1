<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>도서관 안내</title>
<!-- <link href="/carpedm/css/layout.css" rel="stylesheet"> -->
<style>
section {
	width: 90%;
	margin-left: 10%;
}

.library-guide {
	display: flex;
	gap: 20px;
	padding: 20px;
}

.library-image {
	width: 400px;
	height: 300px;
	object_fit: contain;
}

.library-text {
	display: flex;
	flex-direction: column;
	justify-content: center;
}

.library-text h1 {
	font-weight: bold;
	margin-bottom: 10px;
	text-shadow: 0px 0px black, 0px 1px black, 1px 0px black, 0px 0px black;
	color: rgba(199, 156, 200, 1.0);
	font-size: 30px;
}

h3 {
	color: rgba(239, 168, 176, 1.0);
	margin-bottom: 0;
}
</style>

</head>

<body>
	<section>
		<div class="s_section">
			<div class="left_section">
				<c:forEach var="item" items="${list}">
					<button type='button' class='sub_but'
						onclick="location.href='libs_infolist?lb=${item.lb_id}'">${item.lb_name}</button>
					<br>
				</c:forEach>
			</div>
			<div class="right_section">
				<div class="library-guide">
					<c:set var="lb_id" value="${param.lb}" scope="page" />
					<c:if test="${lb_id == 0 || empty lb_id}">
						<c:set var="lb_id" value="1" scope="page" />
					</c:if>
					<img class="library-image" src="${list[lb_id].lb_imgurl}"
						alt="이미지 오류">
					<div class="library-text">
						<h1 class="lib_name" style="margin-bottom: 0;">${list[lb_id].lb_name}</h1>

						<h3>이용시간</h3>
						<p>
							<c:out value="${list[lb_id].lb_opentime}" escapeXml="false" />
						</p>

						<h3>위치</h3>
						<p>
							<c:out value="${list[lb_id].lb_address}" escapeXml="false" />
						</p>

						<h3>연락처</h3>
						<p>
							<c:out value="${list[lb_id].lb_tel}" escapeXml="false" />
						</p>

						<h3>특이사항</h3>
						<p>
							<% pageContext.setAttribute("LF", "\n"); %>			
							<c:out value="${fn:replace(list[lb_id].lb_content,LF, '<br>')}"
								escapeXml="false" />
						</p>
						<p class="text_detail"></p>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>

</html>