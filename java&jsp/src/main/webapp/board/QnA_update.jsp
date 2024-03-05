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
<title>QnA 게시판 글쓰기(수정)</title>
 <link href="/carpedm/css/layout.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous">
        </script>
<script>
        window.addEventListener("load", function () {
        	       	
            document.querySelector("#upload_file").addEventListener("change", function (changeEvent) {
                // 이벤트 객체의 'target' 속성에서 'files' 배열을 가져옵니다. 
                // 'files' 배열에는 사용자가 선택한 파일의 정보가 들어 있습니다.
                // 여기서는 첫 번째 파일만 사용하므로 'files[0]'을 선택합니다.
                var imgFile = changeEvent.target.files[0];

                // 파일 경로를 표시할 요소를 선택합니다.
                var fileName = document.getElementById("file_route");

                // 선택한 요소의 값에 파일 이름을 설정합니다.
                // 이렇게 하면 사용자가 선택한 파일의 이름이 웹 페이지에 표시됩니다.
                fileName.value = imgFile.name;
            });

            // 유효성검사
            let button = document.getElementById('save');

            button.addEventListener('click', function () {
                // 제목
                let subject = document.querySelector('#sject').value;
                // 작성자
                let user_name = document.querySelector('#user_name').value;
                // 휴대폰번호1
                let number1 = document.querySelector('#pnumber1').value;
                // 휴대폰번호2
                let number2 = document.querySelector('#pnumber2').value;
                // 휴대폰번호3
                let number3 = document.querySelector('#pnumber3').value;
                // textarea
                let btext = document.querySelector('#textarea').value;


                if (subject == "") {
                    alert("제목을 입력해주세요.");
                    document.querySelector('#sject').focus();
                } else if (user_name == "") {
                    alert("이름을 작성해주세요");
                    document.querySelector('#user_name').focus();
                } else if (number1 == "") {
                    alert("전화번호를 입력해주세요.");
                    document.querySelector('#pnumber1').focus();
                } else if (number2 == "") {
                    alert("전화번호를 입력해주세요.");
                    document.querySelector('#pnumber2').focus();
                } else if (number3 == "") {
                    alert("전화번호를 입력해주세요.");
                    document.querySelector('#pnumber3').focus();                
                } else {
                    if (btext == "") {
                        alert("내용을 입력해주세요.");
                        document.querySelector('#textarea').focus();
                    } else {
                        alert("등록 되었습니다");
                        location.href = 'QnA_board';
                    }
                }

            });

        });
        
        
    </script>

<style>
/* 글쓰기 */
.table_table {
	width: 100%;
	text-align: center;
	border-collapse: collapse;
}

/* 표 */
tr {
	border-bottom: 1px solid rgba(59, 57, 63, 0.445);
	height: 50px
}

/* 글쓰기 박스 */
textarea {
	width: 100%;
	height: 300px;
	margin-top: 10px;
}

/* 버튼 */
.div_button {
	text-align: right;
	margin-top: 30px;
	
}

/* 버튼 */
.div_buttonAll {
	width: 100%;
	margin: auto;
}

th {
	width: 200px;
	padding: 7px;
}

/* 개인정보 테이블 td */
#div_table #table2 td {
	text-align: left;
	width: 30%;
	padding: 7px;
	text-align: center;
}

/* QnA글쓰기 */
.div_back .sub {
	font-size: 25px;
	font-weight: bold;
	margin-bottom: 10px;
	text-align: center;
	margin-bottom: 20px;
}

/* QnA글쓰기 아래 hr */
.hr_bottom {
	background-color: rgba(168, 156, 200, 0.6);
	height: 2px;
	border: none;
	width: 50%;
	margin-top: 20px;
}

/* 개인정보 배너 */
#head {
	font-size: 25px;
	font-weight: bold;
	margin-bottom: 10px;
	text-align: center;
}

#div_table {
	width: 100%;
	margin: auto;
}

/* 개인정보 표 */
#table2 {
	background-color: white;
	width: 100%;
	border-collapse: collapse;
}

/* 개인정보 표 */
#table_tr {
	background-color: rgba(168, 156, 200, 0.6);
	text-align: center;
}

/* 개인정보 표 */
#table_tr2 {
	background-color: white;
}

/* 인풋박스 높이 지정 */
input {
	height: 18px;
}

/* 버튼 css */
button {
	font-family: "Wanted Sans Variable";
	width: 100px;
	background-color: rgba(155, 178, 225, 0.6);
	font-size: 18px;
	border: 0;
	border-radius: 5px;
	cursor : pointer;
}

/* 버튼 호버 */
button:hover {
	background-color: rgba(205, 155, 225, 0.6);
}

.npub {
	height: 20px;
}

/* 라디오 박스 */
input[type=radio] {
	border: 0px;
	width: 10px;
	height: 10px;
}

/* 공개 비공개 체크박스 */
.private {
	font-size: 17px;
}

/* 폰트 사이즈 지정 */
font {
	font-size: 15px;
}

/* 본문 col그룹 css */
col {
	background: rgba(168, 156, 200, 0.6);
	text-align: center;
	font-size: 18px;
	width: 200px;
}

/* 인풋박스 통합 폰트 지정 */
.input_phone, .inputbox {
	font-size: 18px;
	font-family: "Wanted Sans Variable";
	width: 50%;
}

.inputbox_subject {
	width: 100%;
}

/* 업로드 버튼 css */
.but_upload {
	font-family: "Wanted Sans Variable";
	width: 80px;
	background-color: rgba(155, 178, 225, 0.6);
	font-size: 18px;
	border: 0;
	border-radius: 5px;
	height: 30px;
}

/* 전화번호 인풋 */
.phonenumber {
	width: 70px;
}

/* 전화번호 인풋 화살표 없애기 */
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
	-webkit-appearance: none;
	margin: 0;
}

/* 작성칸 테이블 */
.div_table .table_table td {
	width: 500px;
	text-align: left;
}

/* 첨부파일 */
.div_table .table_table #file_route {
	font-family: "Wanted Sans Variable";
	width: 60%;
}

.div_table .table_table #file_upload {
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
</style>
</head>

<body>
	<%
	List<Map<String, String>> result_list = (List<Map<String, String>>) request.getAttribute("notice");
	List<Map<String, String>> member = (List<Map<String, String>>) request.getAttribute("member");
	List<Map<String, String>> member_tel = (List<Map<String, String>>) request.getAttribute("membertel");
	Map<String, String> map = new HashMap<String, String>();
	%>
	<header> </header>
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
			<form method="post" action="QnA_update">
				<div>
					<!-- 여기부터 본문작성해주세요 -->
					<!-- 상단 배너 -->
					<div class="div_back">
						<div class="sub">
							Q & A 게시물 수정
							<hr class="hr_bottom">
						</div>

					</div>

					<!-- 글쓰기 정보 -->
					<div class="div_table">
						<table class="table_table">
							<colgroup>
								<col>
							</colgroup>
							<thead>
								<tr>
									<th>제목</th>
									<td><input type="text" class="inputbox" placeholder="제목"
										id="sject" name="notice_subject" value="<%=result_list.get(0).get("N_TITLE")%>"></td>
								</tr>
							</thead>
							<tbody>
<!-- 								<tr> -->
<!-- 									<th>작성자</th> -->
<!-- 									<td><input type="text" class="inputbox" -->
<!-- 										placeholder="이름을 작성해주세요." id="user_name" -->
<%-- 										value="<%=member.get(0).get("M_NAME")%>"></td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<th>휴대폰번호</th> -->
<!-- 									<td><input class="input_phone phonenumber" type="number" -->
<!-- 										id="pnumber1" -->
<%-- 										value="<%=member_tel.get(0).get("M_TEL").substring(0, 3)%>"> --%>
<!-- 										- <input class="input_phone phonenumber" type="number" -->
<!-- 										id="pnumber2" -->
<%-- 										value="<%=member_tel.get(0).get("M_TEL").substring(3, 7)%>"> --%>
<!-- 										- <input class="input_phone phonenumber" type="number" -->
<!-- 										id="pnumber3" -->
<%-- 										value="<%=member_tel.get(0).get("M_TEL").substring(7, 11)%>"> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<th>공개</th> -->
<!-- 									<td class="private"> -->
<%-- 										<%if (result_list.get(0).get("N_OPT").equals("1")) {%>  --%>
<!-- 										<input type="radio" name="pub" checked> 공개 <input -->
<%-- 										type="radio" name="pub"> 비공개 <% } else { %>  --%>
<!-- 										<input type="radio" name="pub"> 공개 <input type="radio" -->
<%-- 										name="pub" checked> 비공개 <% } %> --%>

<!-- 									</td> -->
<!-- 								</tr> -->
								<tr>
								<th>소속도서관<input type="hidden" name="n_id" value="<%=result_list.get(0).get("N_ID")%>"></th>
								<td ><select class="inputbox" id="library" name="library">
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
									<th>첨부파일</th>
									<td><input type="text" id="file_route" disabled="disabled"
										value="" name="n_file"> <label for="upload_file" class="btn"
										id="file_upload">파일첨부</label> <input type="file"
										id="upload_file" 
										style="position: absolute; clip: rect(0, 0, 0, 0);"></td>
								</tr>
								
							</tbody>
						</table>
						<!-- 글쓰기 -->
					</div>
					<div class="td1">
						<textarea id="textarea" class="inputbox inputbox_subject"
							placeholder="내용을 입력해주세요." name="n_textarea" ><%=result_list.get(0).get("N_CONTENT")%></textarea>
					</div>
					<!-- 버튼 -->
					<div class="div_buttonAll">
						<div class="div_button">
							<button class="button" type="submit" id="save">수정</button>							
						</div>

					</div>
				</div>
			</form>
			</div>
		</div>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header.js"></script>
</body>

</html>