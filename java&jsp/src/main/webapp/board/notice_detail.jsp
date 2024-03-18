<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>

<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>

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
	<%HttpSession getSession = request.getSession();
	String login_m_pid = (String) getSession.getAttribute("m_pid");
	List<Map<String, String>> member = (List<Map<String, String>>) request.getAttribute("member");
	%>
	
	var login_mpid = "<%=login_m_pid%>"; 
	var mpid = "<%=member.get(0).get("M_PID")%>"; 
    // 서버에서 받은 M_MANAGERCHK 값
    var mManagerChk = "<%=request.getAttribute("manager")%>"; 
//     console.log(mManagerChk);
    var upbut = document.querySelector("#notice_update"); // 수정버튼
    var debut = document.querySelector("#notice_delete"); // 삭제버튼
    // M_MANAGERCHK 값이 "Y"인 경우 버튼을 표시, 그 외의 경우에는 버튼을 숨김
    if (mManagerChk == "Y") {
    	debut.style.display = "inline-block";
    } else {
        debut.style.display = "none";
    }
    
    if (login_mpid == mpid){
    	upbut.style.display = "inline-block";
    }else {
    	upbut.style.display = "none";
    }

    
//  삭제버튼
    document.querySelector("#notice_delete").addEventListener('click', function() {
    	alert("삭제했습니다.");
    });
};
    



</script>
</head>

<body>
	<header></header>
	<%
	List<Map<String, String>> result_list = (List<Map<String, String>>) request.getAttribute("notice");
	List<Map<String, String>> library = (List<Map<String, String>>) request.getAttribute("library_list");
	List<Map<String, String>> update = (List<Map<String, String>>) request.getAttribute("update");
	 Map<String, String> map = new HashMap<String, String>();
%>
							
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
							<input type="hidden" name="notice_id" id="n_id" value="<%=result_list.get(0).get("N_ID")%>">
							<%=result_list.get(0).get("N_TITLE")%></td>
						</tr>
						<tr>
							<td class="subject">작성자</td>
							<td id="subject_writer">
							<%String name = member.get(0).get("M_NAME");										
							String rename = name.substring(0, 1) + "**"; %>
							<%=rename%></td>
							<td class="subject">등록일</td>
							<td id="subject_date"><%=result_list.get(0).get("N_DATE").substring(0,10)%></td>
							<td class="subject">조회</td>
							<td id="subject_view"><%=update.get(0).get("N_VIEWCOUNT")%></td>
						</tr>
						<tr>
							<td class="subject">도서관</td>
							<td colspan="5" id="subject_lib"><%=library.get(0).get("LB_NAME")%></td>
						</tr>
						<tr>
							<td class="subject">첨부</td>
							<td colspan="5" id="subject_file"><%
							if (result_list.get(0).get("N_FILE") == null){
								out.print(" ");
							} else {
								out.print(result_list.get(0).get("N_FILE"));
								}%></td>
						</tr>
						<tr>
							<td class="content" colspan="6" id="subject_cont"><%=result_list.get(0).get("N_CONTENT")%></td>
						</tr>
					</table>
					<form method="get" action="notice_delete">
					<button type="button" id="notice_update" onclick="location.href='notice_update?N_ID=<%=result_list.get(0).get("N_ID")%>';">수정</button>
					<button type="submit" id="notice_delete" onclick="location.href='notice_delete?N_ID=<%=result_list.get(0).get("N_ID")%>';">삭제</button>
					</form>
				</div>
			</div>
		</div>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header.js"></script>
</body>

</html>