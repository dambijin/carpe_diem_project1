<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>QnA 게시판</title>
<link href="/carpedm/css/layout.css" rel="stylesheet">
<script>
	window.addEventListener("load", function() {
		select();
		search();

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

	function search_box() {
		let textbox = document.getElementById("searchbox");
		if (textbox.value == "") {
			alert("내용을 입력해주세요");
			document.querySelector('#searchbox').focus();
		} else {
			alert(textbox.value + "검색했습니다");

		}

	};

	//         function search() {
	//             // 검색 input
	//             let input = document.querySelector(".search_input")

	//             // 게시판 테이블
	//             let board_sub = document.querySelector(".board_sub")
	//             let title = "제목입니다";
	//             // 임시버튼
	//             let btn = document.querySelector(".btn");

	//             // 임시버튼을 클릭
	//             /*
	//             btn.addEventListener("click", function () {
	//                 let html = '';
	//                 html += '<td></td>';
	//                 html += '<td></td>';
	//                 html += '<td>'
	//                 html += '<a href="notice_detail.jsp" class="table_a">';
	//                 html += title;
	//                 html += '</a>';
	//                 html += '</td>';
	//                 html += '<td></td>';
	//                 html += '<td></td>';
	//                 html += '<td></td>';

	//                 let tr = document.createElement("tr");
	//                 tr.innerHTML = html;
	//                 board_sub.append(tr);

	//             });*/

	//             for (let i = 1; i <= 10; i++) {
	//                 let html = '';
	//                 html += '<td>' + i + '</td>';
	//                 html += '<td></td>';
	//                 html += '<td>'
	//                 html += '<a href="QnA_detail.jsp" class="table_a">';
	//                 html += title;
	//                 html += '</a>';
	//                 html += '</td>';
	//                 html += '<td></td>';
	//                 html += '<td></td>';
	//                 html += '<td></td>';

	//                 let tr = document.createElement("tr");
	//                 tr.innerHTML = html;
	//                 board_sub.append(tr);
	//             }

	//         };

	function select() {
		let search = [ "제목", "관할도서관" ];
		let search_box = document.querySelector("#searchselect");

		for (let i = 0; i < search.length; i++) {
			search_box.innerHTML += "<option>" + search[i] + "</option>";
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

/* 테이블 */
.board_notice .board table {
	border-collapse: collapse;
	width: 100%;
	margin-top: 10px;
}

/* 테이블 제목 td */
.board_notice .board_sub .sub {
	width: 60%;
}

/* 테이블 등록일 td */
.board_notice .board_sub .day {
	width: 13%;
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
						<div id="select">
							<select class="change_handwriting" id="searchselect">
							</select> <input type="text" class="change_handwriting search_input"
								id="searchbox"> <input type="button"
								class="change_handwriting request search_button" value="검색"
								onclick="search_box()">
						</div>
						<table class="board_sub">
							<tr>
								<th class="board_subject">순번</th>
								<th class="board_subject">분류</th>
								<th class="board_subject sub">제목</th>
								<th class="board_subject">작성자</th>
								<th class="board_subject day">등록일</th>
								<th class="board_subject">조회</th>
							</tr>

							<%
							List<Map<String, String>> list = (List<Map<String, String>>) request.getAttribute("list");
							
							for (int i = 0; i < list.size(); i++) {
								Map map = (Map) list.get(i);
								
								String a= "1";
								
							%>
							<tr>
								<td><%=list.get(i).get("N_ID")%></td>
								<td>
								<% if(list.get(i).get("N_OPT").equals(a)){%>
									공개					
								<%} else{%>		
									비공개
								<%}
									%>								
								</td>
								<td>
								<a href="notice_detail?N_ID=<%=list.get(i).get("N_ID")%>" class="table_a" name="QnA_title" "><%=list.get(i).get("N_TITLE")%></a></td>
								<td><%=list.get(i).get("M_PID")%></td>
								<td><%=list.get(i).get("N_DATE").substring(0, 10)%></td>
								<td><%=list.get(i).get("N_VIEWCOUNT")%></td>
							</tr>
							<%
							}
							%>
						</table>
					</div>
					<div class="paging_writing">

						<div class="writing">
							<button class="change_handwriting request"
								onclick="location.href='QnA_write.jsp';">글쓰기</button>

						</div>

						<div class="paging">
							<a href="" class="pre underline_remove">◀</a> <strong
								class="underline_remove">1</strong> <a href=""
								class="num underline_remove">2</a> <a href=""
								class="num underline_remove">3</a> <a href=""
								class="num underline_remove">4</a> <a href=""
								class="num underline_remove">5</a> <a href=""
								class="next underline_remove">▶</a>
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