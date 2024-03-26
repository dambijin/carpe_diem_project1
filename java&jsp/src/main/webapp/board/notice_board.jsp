<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>공지사항</title>
<link href="/carpedm/css/layout.css" rel="stylesheet">
<script>
window.onload = function() {
	<%        HttpSession getSession = request.getSession();
    String managerChk = (String) getSession.getAttribute("m_managerchk");%>
    // 서버에서 받은 M_MANAGERCHK 값
    var mManagerChk = "<%=managerChk%>";
		//     console.log(mManagerChk);
		var wbut = document.querySelector("#writebut");
		// M_MANAGERCHK 값이 "Y"인 경우 버튼을 표시, 그 외의 경우에는 버튼을 숨김
		if (mManagerChk == "Y") {
			wbut.style.display = "block";
		} else {
			wbut.style.display = "none";
		}

	};

	window.addEventListener("load", function() {

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
	});

	// 	검색 버튼 클릭
	function search_box() {
		let textbox = document.getElementById("searchbox");
		if (textbox.value == "") {
			alert("내용을 입력해주세요");
			document.querySelector('#searchbox').focus();
		} else {
			alert(textbox.value + "검색했습니다");
			window.location.href = '/carpedm/notice_board?search='
					+ encodeURIComponent(textbox.value) + '&n_search='
					+ document.querySelector("#searchselect").value;
		}

	};

	// 	function select() {
	// 		let search = [ "제목", "제목+내용","관할도서관" ];
	// 		let search_box = document.querySelector("#searchselect");

	// 		for (let i = 0; i < search.length; i++) {
	// 			search_box.innerHTML += "<option>" + search[i] + "</option>";
	// 		}
	// 	};
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

/* 테이블 */
.board_notice .board table {
	border-collapse: collapse;
	width: 100%;
	margin-top: 10px;
}

/* 테이블 도서관 */
.board_sub .lb {
	width: 86px;
}

/* 테이블 제목 td */
.board_notice .board_sub .sub {
	width: 50%;
}

/* 작성자 */
.board_sub .writer {
	width: 60px;
}

/* 테이블 등록일 td */
.board_notice .board_sub .day {
	width: 100px;
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

/* 공지사항 제목 */
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
/* 제목 호버시 밑줄 */
#title_st:hover {
	text-decoration: underline;
}

#select {
	text-align: right;
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
				<div class="notice_subject">공지사항</div>
				<div class="board_notice">

					<div class="board">
						<div id="select" name="search">
							<select class="change_handwriting" id="searchselect"
								name="n_search">
								<option>제목</option>
								<option>제목+내용</option>
								<option>도서관</option>
							</select> <input type="text" class="change_handwriting search_input"
								id="searchbox" name="s_box"> <input type="button"
								name="s_box" class="change_handwriting request search_button"
								value="검색" onclick="search_box();">
						</div>
						<form method="post" action="notice_board">
							<table class="board_sub">
								<tr>
									<th class="board_subject">순번</th>
									<th class="board_subject lb">도서관</th>
									<th class="board_subject sub">제목</th>
									<th class="board_subject writer">작성자</th>
									<th class="board_subject day">등록일</th>
									<th class="board_subject">조회</th>
								</tr>

								<%
								List<Map<String, String>> list = (List<Map<String, String>>) request.getAttribute("list");

								for (int i = 0; i < list.size(); i++) {
									Map map = (Map) list.get(i);
									//System.out.println(map.toString());
									//System.out.println(list.get(i).get("num"));
								%>
								<tr>
									<td><%=list.get(i).get("N_ID")%></td>
									<td name="lb_name"><%=list.get(i).get("LB_NAME")%></td>
									<td id="title_st"><a
										href="notice_detail?N_ID=<%=list.get(i).get("N_ID")%>"
										class="table_a"><%=list.get(i).get("N_TITLE")%> <input
											type="hidden" name="title"
											value="<%=list.get(i).get("N_ID")%>"></a></td>
									<td>
<!-- 									이름 바꾸기 -->
									<%String name = list.get(i).get("M_NAME");										
										String rename = name.substring(0, 1) + "**"; %>
										<%=rename%></td>
									<td><%=list.get(i).get("N_DATE").substring(0, 10)%></td>
									<td><%=list.get(i).get("N_VIEWCOUNT")%></td>
								</tr>
								<%
								}
								%>
							</table>
						</form>
					</div>
					<div class="paging_writing">

						<div class="writing" id="writebut">
							<button class="change_handwriting request"
								onclick="location.href='notice_write';">글쓰기</button>
							<!-- <button class="btn">임시버튼</button> -->
						</div>

					</div>
					<div id="paging">
						<%
						// 서블릿에서 불러온 페이징 정보
						int total_count = (int)request.getAttribute("allcount");// 임시로 설정한 값
					int perPage = Integer.parseInt((String) request.getAttribute("perPage"));
					int current_page = Integer.parseInt((String) request.getAttribute("page"));
						int total_pages = total_count > 0 ? (int) Math.ceil((double) total_count / perPage) : 1;

						// 표시할 페이지의 범위 계산
						int start_page = Math.max(current_page - 2, 1);
						int end_page = Math.min(start_page + 4, total_pages);
						start_page = Math.max(1, end_page - 4);
						%>

						<div class="total_count">
							전체 : 총&nbsp;<%=total_count%>&nbsp;건
						</div>

						<div class="paging">
							<%
							if (current_page > 1) {
							%>
							<a href="?page=<%=current_page - 1%>&perPage=<%=perPage%>"
								class="pre">◀</a>
							<%
							}
							%>
							<%
							for (int i = start_page; i <= end_page; i++) {
							%>
							<a href="?page=<%=i%>&perPage=<%=perPage%>"
								class="<%=i == current_page ? "num active" : "num"%>"><%=i%></a>
							<%
							}
							%>
							<%
							if (current_page < total_pages) {
							%>
							<a href="?page=<%=current_page + 1%>&perPage=<%=perPage%>"
								class="next">▶</a>
							<%
							}
							%>
						</div>
						<div class="total">
							<strong><%=current_page%></strong>페이지 / 총 <strong><%=total_pages%></strong>페이지
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