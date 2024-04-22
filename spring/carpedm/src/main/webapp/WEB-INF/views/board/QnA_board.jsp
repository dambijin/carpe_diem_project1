<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>QnA 게시판</title>
<link href="/carpedm/css/layout.css" rel="stylesheet">
<script>
	window.addEventListener("load", function() {

		var userChk = document.cookie.includes('lc=');

		//     console.log(mManagerChk);
		var wbut = document.querySelector(".writing");
		if (userChk) {
			wbut.style.display = "block";
		} else {
			wbut.style.display = "none";
		}
		// 도서검색 버튼 엔터이벤트
		let textbox = document.getElementById("searchbox");
		// Enter 키 이벤트 리스너 추가
		textbox.addEventListener("keyup", function(event) {
			// keyCode 13은 Enter 키를 나타냅니다
			if (event.keyCode === 13) {
				event.preventDefault(); // 기본 동작인 폼 제출 방지
				search_box(); // 검색 함수 호출
			}
		});
		
		
// 		<c:set var="login_m_pid" value="${login_id}" />
// // 		로그인한 아이디
//         const login_nid = "${login_m_pid[0].m_pid}";
//         const managerchk = "${login_m_pid[0].m_managerchk}";
//         const hrefValue = document.querySelector("#title_st").getAttribute("href");
        
// 	document.querySelector("#title_st").addEventListener('click', function () {
// 			// value 값 int니까 이렇게 적는게 맞는가?
// 			if(document.querySelector("#close_id").value == 2){
// 				if(login_nid==nid || managerchk=="Y"){
// //					href가 사용가능
// 				window.location.href = hinid;
// 				} else {
// // 				href가 사용불가능
// 				alert("작성자의 로그인이 필요합니다.");
// 				}
// 			}
// 		});
	});

	// 	검색 버튼 클릭
	function search_box() {
		let textbox = document.getElementById("searchbox");
		if (textbox.value == "") {
			alert("내용을 입력해주세요");
			document.querySelector('#searchbox').focus();
		} else {
			alert(textbox.value + "검색했습니다");
			window.location.href = '/carpedm/QnA_board?search='+ encodeURIComponent(textbox.value) + '&n_search='+ document.querySelector("#searchselect").value;
		}
	};
</script>
<style>
/* 헤더 아래 */
.board_notice {
	width: 100%;
	text-align: center;
	margin: auto;
	font-family: "Wanted Sans Variable";
}

/* 테이블, 순번, 글쓰기 */
.board_notice .board {
	text-align: center;
	margin: auto;
	font-family: "Wanted Sans Variable";
}

.table_title{
	text-align: left;
}

/* 테이블 */
.board_notice .board table {
	border-collapse: collapse;
	width: 100%;
	margin-top: 10px;
}

/* 테이블 제목 td */
.board_notice .board_sub .sub {
	width: 50%;
}

/* 테이블 등록일 td */
.board_notice .board_sub .day {
	width: 100px;
}
/* 분류(공개, 비공개) */
.board_sub .open {
	width: 50px;
}

/* 글씨체 변경 */
.paging_writing .writing .change_handwriting, .board_notice .search .change_handwriting,
	.board_notice .board .change_handwriting {
	font-family: "Wanted Sans Variable";
	font-size: 16px;
}

/* 출력건수,제목, 검색(테이블 바로 위에) */
.board_notice .search {
	text-align: center;
	margin-top: 20px;
}

/* 테이블 tr */
.board_notice .board_sub tr {
	height: 30px;
}

/* 테이블 th td */
.board_notice td, .board_notice th {
	border: 1px solid black;
}

/* 페이지 쪽수 */
.board_notice .paging {
	margin: 10px 5px;
	padding: 5px 10px;
	border-radius: 5px;
	text-decoration: none;
	color: #333;
}

/* 검색, 글쓰기 버튼 */
.board_notice .request {
	font-family: "Wanted Sans Variable";
	width: 70px;
	background-color: rgba(155, 178, 225, 0.6);
	font-size: 18px;
	border: 0;
	border-radius: 5px;
	cursor: pointer;
}

.board_notice .search .request {
	width: 10%;
}

/* 검색, 글쓰기 버튼 마우스 오버 */
.paging_writing .writing .request:hover, .board_notice .search .request:hover
	{
	background-color: rgba(205, 155, 225, 0.6);
}

.paging_writing {
	text-align: center;
	margin: auto;
}

/* 글쓰기버튼 div*/
.board_notice .writing {
	text-align: right;
	margin: 10px auto;
	width: 100%;
}

/* 순번,분류,제목,관할도서관,등록일,조회 tr */
.board_subject {
	background-color: rgba(168, 156, 200, 0.6);
}

/* 순번 전체(밑줄 지우기) */
.paging_writing .paging .underline_remove {
	text-decoration: none;
	font-size: 20px;
}

/* QnA 제목 */
.right_section .notice_subject {
	font-size: 30px;
	font-weight: bold;
	text-align: center;
	margin-bottom: 15px;
}

/* 테이블 a href*/
.board_notice .board_sub .table_a {
	text-decoration: none;
	font-size: 16px;
	color: black;
}

#select {
	text-align: right;
}

/* 제목 호버시 밑줄 */
#title_st:hover {
	text-decoration: underline;
}

/* 페이지 */
#paging {
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-top: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
	height: 50px;
}

#paging .total {
	font-weight: bold;
}

#paging .paging {
	display: flex;
}

#paging .paging a, #paging .paging strong {
	margin: 0 5px;
	padding: 5px 10px;
	border-radius: 5px;
	text-decoration: none;
	color: #333;
}

#paging .paging a {
	background-color: #f8f8f8;
}

#paging .paging a.num.active {
	color: blue;
	font-size: 20px;
	font-style: bold;
}

#paging .paging strong {
	background-color: #007bff;
	color: #fff;
}
</style>
</head>

<body>
	<header></header>
	<section>
		<div class="s_section">
			<div class="left_section">
				<button type="button" class="sub_but"
					onclick="location.href='notice_board';">공지사항</button>
				<br>
				<button type="button" class="sub_but"
					onclick="location.href='QnA_board';">Q&A</button>
				<br>
				<button type="button" class="sub_but"
					onclick="location.href='wishbook_add';">희망도서신청</button>
			</div>
			<div class="right_section">
				<div class="notice_subject">Q & A</div>
				<div class="board_notice">

					<div class="board">
						<div id="select" name="search">
							<select class="change_handwriting" id="searchselect"
								name="n_search">
								<option>제목</option>
								<option>제목+내용</option>
								<option>내용</option>
							</select> <input type="text" class="change_handwriting search_input"
								id="searchbox"> <input type="button"
								class="change_handwriting request search_button" value="검색"
								onclick="search_box()">
						</div>
						<form method="post" action="QnA_board">
						    <table class="board_sub">
						        <thead>
						            <tr>
						                <th class="board_subject">순번</th>
						                <th class="board_subject open">분류</th>
						                <th class="board_subject sub">제목</th>
						                <th class="board_subject">작성자</th>
						                <th class="board_subject day">등록일</th>
						                <th class="board_subject">조회</th>
						            </tr>
						        </thead>
						        <tbody>
						            <c:forEach var="item" items="${list}">
						                <tr>
						                    <td id="nid_value">${item.n_id}</td>
						                    <td name="close_tx">
						                        <input type="hidden" id="close_id" value="${item.n_opt}" name="n_opt">
						                        <c:choose>
						                            <c:when test="${item.n_opt == '1'}">공개</c:when>
						                            <c:otherwise>비공개</c:otherwise>
						                        </c:choose>
						                    </td>
						                    <c:set var="lv" value="${item.lv}" />
						                    <c:set var="pad" value="${lv * 10}" />
						                    <td class="table_title" style="padding-left: ${pad}px;">
						                        <a id="title_st" href="QnA_detail?N_ID=${item.n_id}" class="table_a" name="a_tag">
						                            <c:if test="${item.n_parent_id != 0 && item.n_parent_id != null}">└</c:if>
						                            ${item.n_title}
						                        </a>
						                    </td>
						                    <td>
						                        <c:set var="name" value="${item.m_name}" />
						                        <c:if test="${name == null}">
						                            <c:set var="name" value=" " />
						                        </c:if>
						                        <c:set var="rename" value="${fn:substring(name, 0, 1)}**" />
						                        ${rename}
						                        <input type="hidden" name="m_writer_id" value="${item.m_pid}">
						                    </td>
						                    <td>${fn:substring(item.n_date, 0, 10)}</td>
						                    <td>${item.n_viewcount}</td>
						                </tr>
						            </c:forEach>
						        </tbody>
						    </table>
						</form>

					</div>
					<div class="paging_writing">
						<div class="writing">
							<button class="change_handwriting request"
								onclick="location.href='QnA_write';">글쓰기</button>
						</div>
						<div id="paging">
							<%-- 서블릿에서 불러온 페이징 정보 --%>
							<c:set var="total_count" value="${allcount}" />
							<c:set var="perPage" value="${perPage}" />
							<c:set var="current_page" value="${page}" />
		
							<%-- 표시할 페이지의 범위 계산 --%>
							<c:set var="total_pages" value="${total_pages}" />
							<c:set var="start_page" value="${start_page}" />
							<c:set var="end_page" value="${end_page}" />
		
							<div class="total_count">
								전체 : 총&nbsp;
								<c:out value="${total_count}" />
								&nbsp;개
							</div>
							<div class="total">
								<strong><c:out value="${current_page}" /></strong>페이지 / 총 <strong><c:out
										value="${total_pages}" /></strong>페이지
							</div>
							<div class="paging">
								<c:if test="${current_page > 1}">
									<a href="?page=${current_page - 1}&perPage=${perPage}"
										 class="pre">◀</a>
								</c:if>
								<c:forEach var="i" begin="${start_page}" end="${end_page}">
									<a href="?page=${i}&perPage=${perPage}" 
										class="${i == current_page ? 'num active' : 'num'}">${i}</a>
								</c:forEach>
								<c:if test="${current_page < total_pages}">
									<a href="?page=${current_page + 1}&perPage=${perPage}"
										 class="next">▶</a>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header.js"></script>
</body>

</html>