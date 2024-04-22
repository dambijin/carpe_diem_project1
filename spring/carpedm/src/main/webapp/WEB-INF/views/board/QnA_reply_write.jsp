<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>QnA상세페이지</title>
<link href="/carpedm/resources/css/layout.css" rel="stylesheet">
<script>

	window.addEventListener("load", function() {
		// 게시물 첨부파일 다운로드
		//        document.querySelector("#subject_file").innerHTML = '<a href="/path/to/your/file.pdf" download>첨부파일 다운로드</a>';

		// 파일첨부
		document.querySelector("#upload_file").addEventListener("change", function(changeEvent) {
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
		// 등록 버튼
		let registration = document.querySelector("#registration");
		// 등록버튼 클릭시
		registration.addEventListener('click', function() {
			let textarea = document.querySelector('#awtext').value;
			let title = document.querySelector('#answer_title').value;
			if (textarea == "") {
				event.preventDefault();
				alert("내용을 입력해주세요.");
				textarea.focus();
			} else if(title == ""){
				event.preventDefault();
				alert("제목을 입력해주세요.");
				title.focus();
			} else {
				alert("등록되었습니다.");
			}
		});

	});
	
</script>


<style>
/* 글쓴 내용 테이블의 제목, 작성자, 등록일, 조회, 도서관 ,첨부 */
.QnA_detail .subject {
	width: 100px;
	background-color: rgba(168, 156, 200, 0.6);
	text-align: center;
}

.QnA_detail {
	text-align: right;
	width: 100%;
}

/* QnA 테이블 */
.QnA_detail table {
	border-collapse: collapse;
	text-align: left;
	width: 100%;
}

.QnA_detail td {
	border: 1px solid rgba(59, 57, 63, 0.445);
}

/* 제목 오른쪽 td */
.QnA_detail .subject_title {
	width: 800px;
}

/* 글내용 */
.QnA_detail .content {
	height: 300px;
	vertical-align: top;
}

.QnA_detail .detail_hr {
	border: 1px solid rgba(168, 156, 200, 0.6);
	width: 100%;
	text-align: left;
	margin-left: 0px;
}

/* 조회 오른쪽 td */
.inquiry {
	width: 50px;
}

/* 수정삭제버튼 */
.notice_but {
	margin-top: 10px;
	font-family: "Wanted Sans Variable";
	width: 80px;
	background-color: rgba(155, 178, 225, 0.6);
	font-size: 18px;
	border: 0;
	border-radius: 5px;
	cursor: pointer;
}

.notice_but:hover {
	background-color: rgba(205, 155, 225, 0.6);
}

.writer {
	width: 180px;
}

/* 파일첨부 */
.QnA_detail #file_route {
	font-family: "Wanted Sans Variable";
	width: 300px;
}

.QnA_detail #file_upload {
	font-family: "Wanted Sans Variable";
	background-color: rgba(155, 178, 225, 0.6);
	font-size: 15px;
	border: 0;
	border-radius: 5px;
	padding: 1px;
	cursor: pointer;
}

.QnA_detail #file_upload:hover {
	background-color: rgba(205, 155, 225, 0.6);
}

/* 답글 제목 td, 답글 작성자 td,답글 도서관 td */
#libs_info, .answer_write {
	font-family: "Wanted Sans Variable";
	font-size: 15px;
}

/* 답글 글 내용 */
.answer_textarea {
	width: 99%;
	height: 99%;
	vertical-align: top;
}

/* 답글 작성자 오른쪽 td */
.answer_admin {
	width: 93%;
}

/* 답글 제목 인풋 */
.answer_subject {
	width: 99%;
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
			<div class="right_section" id="rs">
				<form method="post" action="QnA_reply_write">
				    <c:set var="login_pid" value="${loginpid}" />
				    <c:set var="qna_notice" value="${qna_notice}" />
				    <c:set var="library" value="${library}" />
				
				    <div class="QnA_detail answer_detail">
				        <table>
				            <tr>
				                <td class="subject">제목</td>
				                <td colspan="5" class="subject_title">
				                    <input type="text" class="answer_write answer_subject" id="answer_title" name="n_title"
				                           value="${qna_notice[0].n_title}">
				                    <input type="hidden" name="n_opt" value="${qna_notice[0].n_opt}">
				                    <input type="hidden" name="n_parent_id" value="${qna_notice[0].n_id}">
				                </td>
				            </tr>
				            <tr>
				                <td class="subject">작성자</td>
				                <td class="writer" colspan="5">${loginpid[0].m_name}
				                <input type="hidden" name="m_pid" value="${loginpid[0].m_pid}">
				                </td>
				            </tr>
				            <tr>
				                <td class="subject">도서관</td>
				                <td colspan="5">
				                    <select id="libs_info" name="lb_id">
				                        <option selected value="${qna_notice[0].lb_id}">${qna_notice[0].lb_name}</option>
				                        <c:forEach var="lib" items="${library}">
				                            <option value="${lib.lb_id}">${lib.lb_name}</option>
				                        </c:forEach>
				                    </select>
				                </td>
				            </tr>
<!-- 				            <tr> -->
<!-- 				                <td class="subject">첨부</td> -->
<!-- 				                <td colspan="5"> -->
<!-- 				                    <input type="text" id="file_route" disabled="disabled" value=""> -->
<!-- 				                    <label for="upload_file" class="btn" id="file_upload">파일첨부</label> -->
<!-- 				                    <input type="file" id="upload_file" name="n_file" -->
<!-- 				                           style="position: absolute; clip: rect(0, 0, 0, 0);"> -->
<!-- 				                </td> -->
<!-- 				            </tr> -->
				            <tr>
				                <td class="content" colspan="6">
				                    <textarea type="textarea" class="answer_write answer_textarea" placeholder="답글을 입력해주세요"
				                              id="awtext" name="n_content"></textarea>
				                </td>
				            </tr>
				        </table>
				        <button type="submit" class="notice_but" id="registration">등록</button>
				    </div>
				</form>

			</div>
		</div>
	</section>

</body>

</html>