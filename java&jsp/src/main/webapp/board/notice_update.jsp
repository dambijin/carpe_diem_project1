<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
	
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>공지사항 수정</title>
<link href="/carpedm/css/layout.css" rel="stylesheet">
<style>
/* section */
.notice_update section {
	margin: auto;
	/* font-family: 'KNUTRUTHTTF'; */
	font-family: 'Wanted Sans Variable';
}

/* 테이블 div */
.notice_table {
	width: 100%;
	margin: 20px auto;
	border-collapse: collapse;
}

/* 테이블 */
.notice_table .table_table {
	width: 100%;
	text-align: center;
}

/* 테이블 왼쪽 */
.notice_table .table_table .sub {
	background-color: rgba(168, 156, 200, 0.6);
	text-align: center;
	font-size: 18px;
	width: 30%;
}

/* 테이블 오른쪽 */
.notice_table .table_table .text {
	text-align: left;
	font-size: 18px;
	width: 69%;
}

/* 테이블 input */
.notice_table .table_table .text input {
	font-size: 18px;
	height: 25px;
	width: 100%;
	font-family: 'Wanted Sans Variable';
}

/* 글쓰는 내용들 */
.td1 textarea {
	width: 100%;
	height: 300px;
	font-family: 'Wanted Sans Variable';
	font-size: 18px;
}

/* 수정버튼 div */
.div_button {
	width: 100%;
	text-align: right;
	margin-top: 10px;
}

/* 파일첨부 */
.notice_table .table_table #file_route {
	font-family: "Wanted Sans Variable";
	width: 80%;
}

.notice_table .table_table #file_upload {
	font-family: "Wanted Sans Variable";
	background-color: rgba(155, 178, 225, 0.6);
	font-size: 18px;
	padding: 4px;
}

.notice_table .table_table #file_upload {
	font-family: "Wanted Sans Variable";
	background-color: rgba(155, 178, 225, 0.6);
	font-size: 18px;
	border: 0;
	border-radius: 5px;
	padding: 5px;
	cursor: pointer;
}

.right_section #file_upload:hover {
	background-color: rgba(205, 155, 225, 0.6);
}

.div_button .but {
	font-family: "Wanted Sans Variable";
	width: 80px;
	background-color: rgba(155, 178, 225, 0.6);
	font-size: 18px;
	border: 0;
	border-radius: 5px;
	cursor: pointer;
}

/* 등록버튼 마우스 오버 */
.right_section button:hover {
	background-color: rgba(205, 155, 225, 0.6);
}

.input_phone {
	width: 100px;
}

.notice_update_name {
	font-size: 25px;
	font-weight: bold;
	margin-bottom: 10px;
	text-align: center;
}

#library {
	font-family: "Wanted Sans Variable";
	font-size: 18px;
}
</style>

<script>

	window
			.addEventListener(
					"load",
					function() {

<%-- 						let libs_list = ["<%=library.get(0).get("LB_NAME")%>", "두정도서관", "아우내도서관" ]; --%>
// 						let libs_list_box = document.querySelector("#library");

// 						for (let i = 0; i < libs_list.length; i++) {
// 							libs_list_box.innerHTML += "<option>"
// 									+ libs_list[i] + "</option>";
// 						}

// 						내용 임의로 채워넣기
// 						document.querySelector("#notice_write_title").value = "제에목입니다.";
// 						document.querySelector("#writer").innerHTML = "";
// 						document.querySelector(".td1 textarea").innerHTML = "내애용입니다";

						// 등록 버튼
// 						document.querySelector("#completion").addEventListener('click',function() {
// 											//제목
// 											let title = document
// 													.querySelector("#notice_write_title");
// 											//작성자
// 											let writer = document
// 													.querySelector("#writer");
// 											//소속도서관
// 											let lib = document
// 													.querySelector("#library");
// 											let lib_text = lib.options[lib.selectedIndex].value;
// 											//첨부파일
// 											let attach = document
// 													.querySelector("#file_route");
// 											//내용
// 											let cont_text = document
// 													.querySelector(".td1 textarea");

// 											if (title.value != ""
// 													&& writer.innerHTML != ""
// 													&& lib_text != ""
// 													&& cont_text.value != "") {
// 												alert("등록이 완료되었습니다.");
// 												location.href = "notice_board";
// 											} else {
// 												alert("첨부파일을 제외한 모든 내용을 작성해주세요.");
// 											}
// 										});

						document
								.querySelector("#upload_file")
								.addEventListener(
										"change",
										function(changeEvent) {
											// 이벤트 객체의 'target' 속성에서 'files' 배열을 가져옵니다. 
											// 'files' 배열에는 사용자가 선택한 파일의 정보가 들어 있습니다.
											// 여기서는 첫 번째 파일만 사용하므로 'files[0]'을 선택합니다.
											var imgFile = changeEvent.target.files[0];

											// 파일 경로를 표시할 요소를 선택합니다.
											var fileName = document
													.getElementById("file_route");

											// 선택한 요소의 값에 파일 이름을 설정합니다.
											// 이렇게 하면 사용자가 선택한 파일의 이름이 웹 페이지에 표시됩니다.
											fileName.value = imgFile.name;
										});
					})
</script>
</head>

<body>
<%
	List<Map<String, String>> result_list = (List<Map<String, String>>) request.getAttribute("notice");
	List<Map<String, String>> member = (List<Map<String, String>>) request.getAttribute("member");
	Map<String, String> map = new HashMap<String, String>();
	%>
	<header></header>
	<div class="notice_update">
		<section>
		
			<!-- 여기부터 본문작성해주세요 -->
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
					<div class="notice_update_name">공지사항 수정</div>
					<form method="post" action="notice_update">
					<div class="notice_table">
						<table class="table_table">
							<tr>
								<td class="sub">제목<input type="hidden" name="n_id" value="<%=result_list.get(0).get("N_ID")%>"></td>
								<td class="text"><input type="text" id="notice_write_title" value="<%=result_list.get(0).get("N_TITLE")%>" name="notice_subject"></td>
							</tr>
							<tr>
								<td class="sub">작성자</td>
								<td class="text" id="writer">
									<%String name = member.get(0).get("M_NAME");										
									String rename = name.substring(0, 1) + "**"; %>
									<%=rename%>
								</td>
							</tr>
							<tr>
								<td class="sub">소속도서관</td>
								<td class="text"><select class="textbox" id="library" name="library">
								<%	ArrayList<Map<String, String>> library_list = (ArrayList<Map<String, String>>) request.getAttribute("library_list");
								ArrayList<Map<String, String>> library_id = (ArrayList<Map<String, String>>) request.getAttribute("library_id");
								%>
								<option selected value="<%=library_list.get(0).get("LB_ID")%>"><%=library_list.get(0).get("LB_NAME")%></option>
				<%for (int i = 0; i < library_id.size(); i++) {	%>
    				<option value="<%=library_id.get(i).get("LB_ID")%>"><%= library_id.get(i).get("LB_NAME") %></option>
				<% } %>
								</select></td>
							</tr>
							<tr>
								<td class="sub">첨부파일</td>
								<td class="text">
								<input type="text" id="file_route"
									disabled="disabled" value="" name="n_file"> <label for="upload_file"
									id="file_upload">파일첨부</label> <input type="file"
									id="upload_file" 
									style="position: absolute; clip: rect(0, 0, 0, 0);"></td>
							</tr>
						</table>

					</div>
					<div class="td1">
						<textarea name="n_textarea"><%=result_list.get(0).get("N_CONTENT")%></textarea>
					</div>

					<div class="div_buttonAll">
						<div class="div_button">
							<button type="submit" class="but" id="completion">수정</button>
						</div>
					</div>
				</form>
				</div>
				
			</div>
			
		</section>
	</div>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->

	<script src="/carpedm/js/header.js"></script>
</body>

</html>