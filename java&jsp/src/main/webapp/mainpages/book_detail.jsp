<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
<link href="/carpedm_old/css/layout.css" rel="stylesheet">
<style>
section {
	width: 830px;
	margin: auto;
	/* font-family: 'KNUTRUTHTTF'; */
	font-family: 'Wanted Sans Variable';
	margin-top: 20px;
}

/* 상세정보관련 */
.view {
	display: flex;
	justify-content: center;
	border: 1px solid black;
	padding: 20px;
	width: 90%;
	margin: auto;
}

.view dl {
	width: 100%;
	display: flex;
}

.view dd {
	flex: 1;
	display: flex;
	flex-direction: column;
	padding-left: 20px;
	border-left: 1px solid black;
}

.view .ico {
	font-weight: bold;
	margin-bottom: 0px;
}

.view ul {
	list-style: none;
	padding: 0;
	margin-bottom: 0px;
}

.view ul li {
	margin-bottom: 5px;
	border-bottom: 1px dotted #888;
}

.view ul li .fb {
	font-weight: bold;
	color: #007bff;
}

.view ul li a {
	color: #007bff;
	text-decoration: none;
}

.view .label img {
	width: 180px;
	height: 220px;
	object_fit: contain;
}

/* 소장정보 */
.table {
	width: 100%;
	padding: 10px;
	box-sizing: border-box;
}

.table h3 {
	font-size: 1.2em;
	font-weight: bold;
	margin-bottom: 10px;
}

.table table {
	width: 100%;
	border-collapse: collapse;
}

.table table thead th {
	background-color: #f8f8f8;
	padding: 10px;
	border: 1px solid #ccc;
	text-align: left;
}

.table table tbody td {
	padding: 10px;
	border: 1px solid #ccc;
	vertical-align: center;
}

.table table tbody td a {
	color: #007bff;
	text-decoration: none;
}

.table table tbody .reservation_success {
	color: blue;
	text-decoration: underline;
}

.table table tbody .reservation_success:hover {
	background-color: rgba(199, 156, 200, 0.6);
	color: black;
}

.table table tbody ._fail {
	color: red;
}

.table table tbody ._success {
	color: blue;
}

.table .info h3 {
	margin-top: 20px;
}

.table .info .content_text {
	color: #888;
	font-size: 0.9em;
}

/* 추천도서 */
.recommendbook {
	height: 490px;
	width: 100%;
	background-color: rgba(220, 220, 220);
/*  	border: 2px solid black;  */
	font-size: 20px;
	font-weight: bold;
	text-align: center;
	font-family: "Wanted Sans Variable";
	margin-top:10px;
	padding-top:10px;
}

/* 추천도서 테이블 */
.recommendbook_table {
	margin-top: 10px;
	font-size: 15px;
	font-weight: normal;
}

.recommendbook_table td {
	border-right: 2px solid black;
	width: 300px;
}

/* 신착도서 div */
.recommendbook_table .recommendbook_div {
	margin-top: 8px;
	cursor: pointer;
	width: 100%;
}

.recommendbook_table img {
	width: 250px;
	height: 355px;
	object_fit: contain;
}
</style>
</head>
<%
ArrayList<Map<String, String>> bookdetail_list = (ArrayList<Map<String, String>>) request
		.getAttribute("bookdetail_list");
%>
<script>
	window.addEventListener("load",function() {
		document.title ='<%=bookdetail_list.get(0).get("B_TITLE")%>';
	});
	
	function selboxAllChecked(id) {
		var allCheck = document.getElementById(id);
		var selbox = document.getElementsByClassName("selbox")[0];
		var checkboxes = selbox.querySelectorAll('input[type="checkbox"]');

		for (var i = 0; i < checkboxes.length; i++) {
			checkboxes[i].checked = allCheck.checked;
		}
	}
	//예약기능
	function reservation(b_id) {
			<%// 세션에서 현재 아이디값 가져오기
		HttpSession getSession = request.getSession();
		String login_m_pid = (String) getSession.getAttribute("m_pid");%>
// 	    alert(b_id + " 예약되었습니다.");
	    let url = '/carpedm_old/book_search';
	    let data = 'b_id=' + encodeURIComponent(b_id)+'&m_pid=' + encodeURIComponent(<%=login_m_pid%>);
		//dopost로 보내기위한 코드
	    fetch(url, {
	      method: 'POST',
	      headers: {
	        'Content-Type': 'application/x-www-form-urlencoded',
	      },
	      body: data,
	    })
	    .then(response => response.json())
	    .then(data => {
 	    	console.log(data);
    	  // 서버에서 전달한 결과 메시지에 따라 분기처리
    	  if (data.message === 'success') {
    	    alert(' 예약되었습니다.');
    	    location.reload();  // fetch가 완료된 후에
    	  } else if (data.message === 'fail') {
    	    alert('비로그인상태입니다. 로그인해주세요.');
    	    window.location.href = "/carpedm_old/sign_in";
    	  } else {
    	    alert('알 수 없는 오류가 발생하였습니다.');
    	  }	      
	    })
	    .catch((error) => console.error('Error:', error));   
	}
</script>

<body>
	<header></header>
	<section>
		<!-- 여기부터 본문작성해주세요 -->
		<!-- 책 상세페이지 -->
		<div id="searchDetailInfo">
			<div class="view">
				<dl>
					<em class="label"> <img
						src="<%=bookdetail_list.get(0).get("B_IMGURL")%>" alt="사진불러오기 실패" />
					</em>
					<dd>
						<div class="ico ico-bk">
							<span><%=bookdetail_list.get(0).get("B_TITLE")%></span>
						</div>
						<ul>
							<li class="label_no"><strong>ㆍ키워드</strong>&nbsp;&nbsp;&nbsp;&nbsp;<%=bookdetail_list.get(0).get("B_KYWD")%></li>
							<li><strong>ㆍ저자</strong>&nbsp;&nbsp;&nbsp;&nbsp;<%=bookdetail_list.get(0).get("B_AUTHOR")%></li>
							<li><strong>ㆍ발행년도</strong>&nbsp;&nbsp;&nbsp;&nbsp;<%=bookdetail_list.get(0).get("B_PUBYEAR")%></li>
							<li><strong>ㆍ출판사</strong>&nbsp;&nbsp;&nbsp;&nbsp;<%=bookdetail_list.get(0).get("B_PUBLISHER")%></li>
							<li><strong>ㆍISBN</strong>&nbsp;&nbsp;&nbsp;&nbsp;<%=bookdetail_list.get(0).get("B_ISBN")%></li>
							<li><strong>ㆍ장르</strong>&nbsp;&nbsp;&nbsp;&nbsp;<%=bookdetail_list.get(0).get("BG_NAME")%></li>
							<li><strong>ㆍ소장기관</strong>&nbsp;&nbsp;&nbsp;&nbsp;<%=bookdetail_list.get(0).get("LB_NAME")%></li>
						</ul>
					</dd>
				</dl>
			</div>
			<div class="table">
				<h3>소장정보</h3>
				<table class="responsive">
					<colgroup>
						<col width="">
						<col width="">
						<col width="">
						<col width="">
						<col width="">
						<col width="">
						<col width="">
						<col width="">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">등록번호</th>
							<!-- 							<th scope="col">낱권정보</th> -->
							<th scope="col">ISBN</th>
							<th scope="col">자료상태</th>
							<th scope="col">반납예정일</th>
							<th scope="col">예약</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><%=bookdetail_list.get(0).get("B_ID")%></td>
							<!-- 							<td></td> -->
							<td><strong><%=bookdetail_list.get(0).get("B_ISBN")%></strong></td>
							<td><strong
								class="<%=bookdetail_list.get(0).get("B_LOANSTATE").equals("Y") ? "_success" : "_fail"%>">
									<%=bookdetail_list.get(0).get("B_LOANSTATE").equals("Y") ? "대출가능" : "대출불가"%>
							</strong><br></td>
							<td><%=bookdetail_list.get(0).get("L_RETURNDATE")==null ? "-" : bookdetail_list.get(0).get("L_RETURNDATE").substring(0,10)%></td>
							<td><strong
								class="<%=bookdetail_list.get(0).get("B_RESSTATE").equals("Y") ? "reservation_success" : "_fail"%>"
								<%if (bookdetail_list.get(0).get("B_RESSTATE").equals("Y")) {%>
								onclick="reservation('<%=bookdetail_list.get(0).get("B_ID")%>')"
								<%}%>> <%=bookdetail_list.get(0).get("B_RESSTATE").equals("Y") ? "예약가능" : "예약불가"%>
							</strong><br></td>
						</tr>
					</tbody>
				</table>

				<div class="info">
					<h3>특이사항</h3>
					<div>
						<div class="content_text">특이사항 없음</div>
					</div>
				</div>
			</div>
		</div>
		<hr>
		<div class="recommendbook">
			관련추천도서
			<table class="recommendbook_table">
				<tr id="rcb">
					<%
					ArrayList<Map<String, String>> bookrecommend_list = (ArrayList<Map<String, String>>) request
							.getAttribute("bookrecommend_list");

					for (int i = 0; i < bookrecommend_list.size(); i++) {
						String tdStyle = "";
						if (i == bookrecommend_list.size()-1) {
							tdStyle = "border-right: 0px;"; // 마지막에 오른쪽 줄 없애기
						}
					%>
					<td style="<%=tdStyle%>" onclick="window.open('<%=bookrecommend_list.get(i).get("b_src")%>','', 'width=1200,height=700');">
						<div class="recommendbook_div">
							<img class="recommendbook_img"
								src="<%=bookrecommend_list.get(i).get("b_img")%>">
						</div>
						<div class="recommendbook_title"><%=bookrecommend_list.get(i).get("b_title")%></div>
						<hr>
						<div class="recommendbook_author"><%=bookrecommend_list.get(i).get("b_auth")%></div>
					</td>
					<%
					}
					%>
				</tr>
			</table>
		</div>
	</section>
</body>

</html>