<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>


<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>공지사항 상세페이지</title>
<link href="../css/layout.css" rel="stylesheet">

<style>
/* 글쓴 내용 테이블의 제목, 작성자, 등록일, 조회, 도서관 ,첨부 */
.notice_detail {
	text-align: right;
	width: 800px;
}

.notice_detail .subject {
	width: 100px;
	background-color: rgba(168, 156, 200, 0.6);
	text-align: center;
}

/* notice 테이블 */
.notice_detail table {
	border-collapse: collapse;
	text-align: left;
}

.notice_detail td {
	border: 1px solid rgba(59, 57, 63, 0.445);
}

/* 제목 오른쪽 td */
.notice_detail #subject_title {
	width: 800px;
}

/* 글내용 */
.notice_detail .content {
	height: 300px;
	vertical-align: top;
}

.notice_detail .detail_hr {
	border: 1px solid rgba(168, 156, 200, 0.6);
}

/* 수정삭제버튼 */
.notice_detail button {
	margin-top: 10px;
	font-family: "Wanted Sans Variable";
	width: 80px;
	background-color: rgba(155, 178, 225, 0.6);
	font-size: 18px;
	border: 0;
	border-radius: 5px;
	cursor: pointer;
}

.notice_detail button:hover {
	background-color: rgba(205, 155, 225, 0.6);
}
</style>
<script>
	window
			.addEventListener(
					"load",
					function() {
						// 등록 버튼
						document.querySelector("#notice_update")
								.addEventListener('click', function() {
									location.href = "notice_update.jsp";
								});

						document.querySelector("#notice_delete")
								.addEventListener('click', function() {
									alert("삭제했습니다.");
									location.href = "notice_board.jsp";
								});

						document.querySelector("#subject_title").innerHTML = "tttt";
						document.querySelector("#subject_writer").innerHTML = "wwww";
						document.querySelector("#subject_date").innerHTML = "2024-02-05";
						document.querySelector("#subject_view").innerHTML = "10000000000";
						document.querySelector("#subject_lib").innerHTML = "llll";
						document.querySelector("#subject_file").innerHTML = '<a href="/path/to/your/file.pdf" download>첨부파일 다운로드</a>';
						document.querySelector("#subject_cont").innerHTML = "cccc";
					})
</script>
</head>

<body>
	<header></header>
	
	<section>
		<div class="s_section">
			<div class="left_section">
				<button type="button" class="sub_but"
					onclick="location.href='notice_board.jsp';">공지사항</button>
				<br>
				<button type="button" class="sub_but"
					onclick="location.href='QnA_board.jsp';">Q&A</button>
				<br>
				<button type="button" class="sub_but"
					onclick="location.href='wishbook_add.jsp';">희망도서신청</button>
			</div>

			<div class="right_section">
				<div class="notice_detail">
					<table>
						<tr>
							<td class="subject">제목</td>
							<td colspan="5" id="subject_title"></td>
						</tr>
						<tr>
							<td class="subject">작성자</td>
							<td id="subject_writer"></td>
							<td class="subject">등록일</td>
							<td id="subject_date"></td>
							<td class="subject">조회</td>
							<td id="subject_view"></td>
						</tr>
						<tr>
							<td class="subject">도서관</td>
							<td colspan="5" id="subject_lib"></td>
						</tr>
						<tr>
							<td class="subject">첨부</td>
							<td colspan="5" id="subject_file"></td>
						</tr>
						<tr>
							<td class="content" colspan="6" id="subject_cont"></td>
						</tr>
					</table>
					<button type="button" id="notice_update">수정</button>
					<button type="button" id="notice_delete">삭제</button>
				</div>
			</div>
		</div>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="../js/header.js"></script>
</body>

</html>