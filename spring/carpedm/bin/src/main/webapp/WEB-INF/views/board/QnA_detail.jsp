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
<link href="/carpedm_old/css/layout.css" rel="stylesheet">
<script>
window.onload = function() {
    // 변수 설정
    var login_mpid = '${sessionScope.m_pid}';
    var mpid = '${notice[0].M_PID}';
    var mgChk = '${sessionScope.m_managerchk}';
    var qnabut = document.querySelector("#qna_but"); // 수정
    var debut = document.querySelector("#delet_but"); // 삭제
    var mgbut = document.querySelector("#detail_but"); // 답글

    // 로그인한 아이디와 게시물 작성자가 일치할 때 수정 버튼 표시
    if (login_mpid === mpid) {
        qnabut.style.display = "inline-block";
    } else {
        var n_opt = '${notice[0].N_OPT}';
        if (n_opt == 2 && mgChk !== "Y") {
            location.href = "QnA_board";
            alert("비공개 글입니다.");
        }
        qnabut.style.display = "none";
    }

    // 로그인한 아이디와 게시물 작성자가 일치하거나 관리자일 때 삭제 버튼 표시
    if (login_mpid === mpid || mgChk === "Y") {
        debut.style.display = "inline-block";
    } else {
        debut.style.display = "none";
    }

    // 관리자인 경우 답글 버튼 표시
    if (mgChk === "Y") {
        mgbut.style.display = "inline-block";
    } else {
        mgbut.style.display = "none";
    }
};


	window.addEventListener("load", function() {
		// 답글
		let completion = document.querySelector(".completion");
		// 답글 클릭시
		completion.addEventListener('click', function() {
			// div안에
			document.querySelector(".answer_detail").style.display = "block";
		});

		libsinfolist();
		answer();

		// 게시물 첨부파일 다운로드
		//        document.querySelector("#subject_file").innerHTML = '<a href="/path/to/your/file.pdf" download>첨부파일 다운로드</a>';

		// 게시물삭제버튼
		let QnA_delete = document.querySelector("#QnAdelete");
		// 삭제버튼 클릭시
		QnA_delete.addEventListener('click', function() {
			alert("삭제되었습니다.");
		});

		// 게시물삭제버튼
		let answer_delete = document.querySelector("#answer_delete");
		// 삭제버튼 클릭시
		answer_delete.addEventListener('click', function() {
			alert("삭제되었습니다.");
			location.href = 'QnA_detail';
		});

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
			if (textarea == "") {
				alert("내용을 입력해주세요.");
				document.querySelector('#awtext').focus();
			} else {
				alert("등록되었습니다.");
			}
		});

	});

	function answer() {
		// QnA 제목
		let title = document.querySelector('#qna_title').innerText;
		// 답글 제목
		let answer_title = document.querySelector('#answer_title');

		let html = '';
		html += 'Re : ';
		html += title;

		answer_title.value = html;
	};

	function libsinfolist() {
		// 도서관 select
		let libs_list = [ "천안도서관", "두정도서관", "아우내도서관" ];
		let libs_list_box = document.querySelector("#libs_info");

		for (let i = 0; i < libs_list.length; i++) {
			libs_list_box.innerHTML += "<option>" + libs_list[i] + "</option>";
		}
	};
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

.answer_detail {
	display: none;
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
				<div class="QnA_detail">
				    <c:set var="select" value="${qnanotice}" />
				    <table>
				        <tr>
				            <td class="subject">제목</td>
				            <td colspan="5" class="subject_title" id="qna_title">
				                ${select[0].n_title}
				            </td>
				        </tr>
				        <tr>
				            <td class="subject">작성자</td>
				            <td class="writer">
				                <c:set var="name" value="${select[0].m_name}" />
				                <c:set var="rename" value="${fn:substring(name, 0, 1)}**" />
				                ${rename}
				                <input type="hidden" value="${select[0].n_opt}" name="NOPT" />
				                <input type="hidden" value="${select[0].m_pid}" name="mpid" />
				            </td>
				            <td class="subject">등록일</td>
				            <td>
				                ${fn:substring(select[0].n_date, 0, 10)}
				            </td>
				            <td class="subject">조회</td>
				            <td class="inquiry">${select[0].n_viewcount}</td>
				        </tr>
				        <tr>
				            <td class="subject">도서관</td>
				            <td colspan="5">${select[0].lb_name}</td>
				        </tr>
				        <tr>
				            <td class="subject">첨부</td>
				            <td colspan="5" id="subject_file">
				                <c:choose>
				                    <c:when test="${empty select[0].n_file}">
				                        <c:out value=" " />
				                    </c:when>
				                    <c:otherwise>
				                        ${select[0].n_file}
				                    </c:otherwise>
				                </c:choose>
				            </td>
				        </tr>
				        <tr>
				            <td class="content" colspan="6">
				            	<c:set var="newline" value="${newline}" />
				                ${fn:replace(select[0].n_content, newline, "<br>")}
				            </td>
				        </tr>
				    </table>
				
				    <div id="qna_but">
				        <button type="button" class="notice_but" id="QnAupdate"
				                onclick="location.href='QnA_update?N_ID=${select[0].n_id}'">수정
				        </button>
				    </div>
				
				    <div id="detail_but">
				        <button type="button" class="notice_but completion reply"
				                id="writebut"
				                onclick="location.href='QnA_reply_write?N_ID=${select[0].n_id}'">답글
				        </button>
				    </div>
				    <div id="delet_but">
				        <form method="get" action="QnA_delete">
				            <button type="submit" class="notice_but" id="QnAdelete"
				                    onclick="location.href='QnA_delete?N_ID=${select[0].n_id}'">삭제
				            </button>
				        </form>
				    </div>
				</div>

			</div>
		</div>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm_old/js/header.js"></script>

</body>

</html>