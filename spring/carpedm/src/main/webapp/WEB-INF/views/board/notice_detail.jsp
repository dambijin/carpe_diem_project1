<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>

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


<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>공지사항 상세페이지</title>
<link href="/carpedm/css/layout.css" rel="stylesheet">

<style>
/* 글쓴 내용 테이블의 제목, 작성자, 등록일, 조회, 도서관 ,첨부 */
.notice_detail {
	text-align: right;
	width: 100%;
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
	width: 100%;
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
window.onload = function() {
	<%-- HttpSession 객체에서 속성 가져오기 --%>
	<c:set var="login_m_pid" value="${sessionScope.m_pid}" />
	<c:set var="managerChk" value="${sessionScope.m_managerchk}" />
	
	<%-- request 객체에서 notice 목록 가져오기 --%>
	<c:set var="notice" value="${notice}" />
		// 세션에서 로그인된 사용자의 m_pid를 가져옵니다.
	    var login_mpid = "${sessionScope.m_pid}";

	    // `notice` 목록의 첫 번째 항목에서 M_PID 값을 가져옵니다.
	    var mpid = "${notice[0].m_pid}";

	    // 세션에서 M_MANAGERCHK 값을 가져옵니다.
	    var mManagerChk = "${sessionScope.m_managerchk}";

	    // JavaScript에서 DOM 요소를 선택합니다.
	    var upbut = document.querySelector("#notice_update"); // 수정버튼
	    var debut = document.querySelector("#notice_delete"); // 삭제버튼


	    // 로그인된 사용자의 m_pid와 notice 목록의 첫 번째 항목의 M_PID가 일치하는지 확인합니다.
	    if (login_mpid === mpid) {
	        upbut.style.display = "inline-block";
	        debut.style.display = "inline-block";
	    } else {
	        upbut.style.display = "none";
	        debut.style.display = "none";
	    }

	    // 삭제 버튼에 클릭 이벤트를 추가합니다.
	    debut.addEventListener('click', function() {
	        alert("삭제했습니다.");
	    });
};
</script>
</head>

<body>
	<header></header>
<%-- 	<% --%>
<!-- Map<String, String> map = new HashMap<String, String>(); -->
<%-- %> --%>
							
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
				<div class="notice_detail">
				    <table>
				        <tr>
				            <td class="subject">제목</td>
				            <td colspan="5" id="subject_title">
				                <!-- EL을 사용하여 notice의 첫 번째 항목의 N_ID를 가져옴 -->
				                <input type="hidden" name="notice_id" id="n_id" value="${notice[0].n_id}">
				                <!-- EL을 사용하여 notice의 첫 번째 항목의 N_TITLE를 가져옴 -->
				                ${notice[0].n_title}
				            </td>
				        </tr>
				        <tr>
				            <td class="subject">작성자</td>
				            <td id="subject_writer">
				                <!-- EL을 사용하여 notice의 첫 번째 항목의 M_NAME을 가져옴 -->
				                <!-- 첫 글자와 **를 결합하여 이름을 표시 -->
				                <c:set var="name" value="${notice[0].m_name}" />
				                <c:set var="rename" value="${name.substring(0, 1)}**" />
				                ${rename}
				            </td>
				            <td class="subject">등록일</td>
				            <td id="subject_date">
				                <!-- EL을 사용하여 notice의 첫 번째 항목의 N_DATE를 가져와서 날짜 부분을 추출 -->
				                ${fn:substring(notice[0].n_date, 0, 10)}
				            </td>
				            <td class="subject">조회</td>
				            <td id="subject_view">
				                <!-- EL을 사용하여 notice의 첫 번째 항목의 N_VIEWCOUNT를 가져옴 -->
				                ${notice[0].n_viewcount}
				            </td>
				        </tr>
				        <tr>
				            <td class="subject">도서관</td>
				            <td colspan="5" id="subject_lib">
				                <!-- EL을 사용하여 notice의 첫 번째 항목의 LB_NAME을 가져옴 -->
				                ${notice[0].lb_name}
				            </td>
				        </tr>
				        <tr>
				            <td class="subject">첨부</td>
				            <td colspan="5" id="subject_file">
				                <!-- EL을 사용하여 notice의 첫 번째 항목의 N_FILE을 가져와서 존재 여부 확인 -->
				                <c:choose>
				                    <c:when test="${not empty notice[0].n_file}">
				                        ${notice[0].n_file}
				                    </c:when>
				                    <c:otherwise>
				                        <!-- 첨부 파일이 없는 경우 공백 표시 -->
				                        &nbsp;
				                    </c:otherwise>
				                </c:choose>
				            </td>
				        </tr>
				        <tr>
						    <td class="content" colspan="6" id="subject_cont">
						    	<% pageContext.setAttribute("newline", "\n"); %>
						  		${fn:replace(notice[0].n_content, newline, "<br>")}
							</td>
						</tr>
				    </table>
				        <!-- EL을 사용하여 수정 버튼의 URL에 N_ID를 추가 -->
				        <button type="button" id="notice_update" onclick="location.href='notice_update?N_ID=${notice[0].n_id}';">수정</button>
				        <!-- EL을 사용하여 삭제 버튼의 URL에 N_ID를 추가 -->
				        <button type="button" id="notice_delete" onclick="location.href='notice_delete?N_ID=${notice[0].n_id}&n_mpid=${notice[0].m_pid}';">삭제</button>
				</div>

			</div>
		</div>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header.js"></script>
</body>

</html>