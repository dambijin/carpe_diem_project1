<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%-- <%@ page import="carpedm.DBConn"%> --%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>관리자페이지(회원목록)</title>
<link href=" /carpedm/resources/css/layout.css" rel="stylesheet">
</head>
<!-- function 스크립트 -->
<script defer src="/carpedm/resources/js/admin_library.js"></script>
<script>
	// 윈도우가 로드됐을 때 bind함수 작동
	window.addEventListener("load", function () {
		bind();
	});

	function bind() {
		// 회원목록 가져옴		
<%-- 		let data_list = <%=getSelectQueryAll("select m_pid, m_name, m_id, m_birthday, m_tel, m_address, lb_id from member")%> --%>

// 		console.log(data_list.length);
// 		for (let i = 0; i < data_list.length; i=i+7) {
// 			let todolist = document.querySelector("#memberListTable");		
// 			let html = '';
			
// 			회원목록 순번 1부터 만들기
// 			html += '<td>';
// 			html += data_list[i];		
// 			html += '</td>';
			
// 			생년월일 부분은 시간까지 출력되어서 변수에 담고
// 			let birthdate = data_list[i + 3];
// 			// substring으로 10번째까지만 추출한 뒤 변수에 담아서 datePart 출력함
// 			let datePart = birthdate.substring(0, 10);

			
// 			html += '<td class="member_no">' + data_list[i] + '</td>';
// 			html += '<td><div class="member_name">';
// 			html += data_list[i + 1];
// 			html += '</div></td>';
// 			html += '<td>';
// 			html += data_list[i + 2];
// 			html += '</td>';
// 			html += '<td>';
// 			html += datePart;
// 			html += '</td>';
// 			html += '<td>';
// 			html += data_list[i + 4];
// 			html += '</td>';
// 			html += '<td>';
// 			html += data_list[i + 5];
// 			html += '</td>';
// 			html += '<td>';
// 			html += data_list[i + 6];
// 			html += '</td>';
// 			html += '<td><div class="overdue_name" onclick="openOverduePopup()">3일</div></td>';
// 			html += '<td><input type="button" value="조회" onclick=\'alert("예약목록 조회")\'></td>';
// 			html += '<td><input type="button" value="조회" onclick=\'alert("대출내역 조회")\'></td>';
// 			html += '<td><input type="button" value="수정" onclick="location.href=\'admin_member_chginfo.jsp\';"></td>';

// 			// 새로운 tr 추가
// 			let tr = document.createElement("tr"); // <tr></tr>
// 			tr.innerHTML = html;

// 			이름에 클릭이벤트
// 			tr 엘리먼트 내에서 member_name을 찾아 이벤트 리스너 추가
// 			tr 엘리먼트 내에서 member_no 찾아 그 내용(innerHTML)을 변수에 담음
// 					tr.querySelector(".member_name")
// 					.addEventListener(
// 							"click",
// 							function() {
// 								let member_no = tr.querySelector(".member_no").innerHTML;
// 								alert("회원번호 : " + member_no);
// 							})

// 			// 테이블의 tr 엘리먼트를 추가
// 			todolist.append(tr);
// 		}

		let m_names = document.querySelectorAll("#m_name")
		console.log("m_names : "+ m_names)
		for(let i=0; i<m_names.length; i++){
			m_names[i].addEventListener("click", (event) => {
				// event.target.parentNode : 부모 즉,<td>를 뜻함
				event.target.parentNode.querySelector("form").submit();
			})
		}
		
		document.querySelector("#m_pid").addEventListener("click", function() {
			// form id 값 잡아옴 			
			let frm = document.querySelector("#frm");
			frm.querySelector("[name=orderColumn]").value = "m_pid";
			
			let orderType = frm.querySelector("[name=orderType]");
			// 없다가 클릭하면
			// desc > asc > 없음 순으로 변경
			console.log(orderType.value);
			
			if(orderType.value == ""){
				orderType.value = 'desc';
			} else if(orderType.value == "desc") {
				orderType.value = 'asc';
			} else if(orderType.value == "asc") {
				orderType.value = '';
				// 차라리 orderColumn을 지우는 방법도 있다
// 				frm.querySelector("[name=orderColumn]").value = '';
				frm.querySelector("[name=orderType]").value = "desc";
			}
			
			
			frm.submit();
		})
		
	}
	
	
	  
	// 엔터눌렀을 때 search() 실행
	function enterkey() {
		if (window.event.keyCode == 13) {
			// 엔터키가 눌렸을 때 실행하는 반응
			search();
		}
	}

	// 연체상태 popup창
	function openOverduePopup(m_pid) {
		window.open
		("/carpedm/admin_book_overdue?m_pid="+m_pid, "팝업", "width=1050, height=700, left=100, top=100");
	}
	
	// 예약목록 popup창
	function openReservation(m_pid) {
		window.open
		("/carpedm/admin_reservation_list?m_pid="+m_pid, "팝업", "width=1000, height=700, left=100, top=100");
	}
	// 대출내역 popup창
	function openLoan(m_pid) {
		window.open
		("/carpedm/admin_loan?m_pid="+m_pid, "팝업", "width=1000, height=700, left=100, top=100");
	}
	
	// 정보수정 popup창
	function openChinfo(m_pid) {
		window.open
		("/carpedm/admin_member_chginfo?m_pid="+m_pid, "팝업", "width=1000, height=700, left=100, top=100");
	}
	
	// 예약목록 조회 이벤트
	function reservation_check() {
		alert("예약내역조회");
	}

	// 대출내역 조회 이벤트
	function loan_check() {
		alert("대출내역조회");
	}
	// 10개씩 누를 때 change 이벤트 : 변동이 있을 때 발생
	function changeViewCount(count) {
// 		alert(count + "개씩 보기로 변경되었습니다.");
	}
	
	// 예약목록 조회 클릭 시 동작하는 함수
    function getReservationInfo(memberId) {
    	// 회원 ID를 알림창에 표시
        alert("예약목록 조회 - 회원 ID: " + memberId);
    }
	
    function loans(memberId) {
    	// 회원 ID를 알림창에 표시
        alert("대출내역 조회 - 회원 ID: " + memberId);
    }
    
</script>


<style>
.flex-header {
	cursor: pointer;
}

header .nav .member_list {
	background-color: rgba(168, 156, 200, 0.6);
	color: #000000;
	height: 45px;
	line-height: 40px;
	display: inline-block;
	border-radius: 5px;
	width: 150px;
	font-size: 20px;
	margin-top: 10px;
}

/* 검색창 */
.search {
	width: 80%;
	margin: auto;
	text-align: center;
	margin-bottom: 10px;
}

.search .type {
	width: 90px;
	height: 30px;
	font-family: "Wanted Sans Variable";
	font-size: 17px;
}

.search .textbox {
	width: 350px;
	height: 20px;
	padding: 2px;
	font-family: "Wanted Sans Variable";
	font-size: 17px;
}

.search .button {
	font-family: "Wanted Sans Variable";
	font-size: 18px;
	background-color: rgb(36, 116, 190);
	color: white;
	width: 50px;
	height: 27px;
	border: 0;
	border-radius: 5px;
	cursor: pointer;
}

/* 테이블 div */
.table_div {
	width: 80%;
	margin: auto;
}

/* 테이블 */
.member_table {
	width: 100%;
	border-collapse: collapse;
	font-family: "Wanted Sans Variable";
}

/* 테이블 td th */
.member_table td, .member_table th {
	border: 1px solid #000000;
	background-color: #fff;
	text-align: center;
	height: 30px;
}

/* 테이블 th */
.member_table th {
	height: 35px;
	background-color: rgba(163, 163, 163, 0.6);
}

/* 예약목록, 대출내역, 정보수정 버튼 스타일 */
.member_table input {
	font-family: "Wanted Sans Variable";
	font-size: 14px;
	background-color: #4CAF50; /* 버튼 색상 변경 */
	color: white;
	width: 60px;
	height: 20px;
	border: 0;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s ease; /* hover 효과를 위한 전환 효과 */
}

/* 예약목록, 대출내역, 정보수정 버튼에 hover 효과 */
.member_table tr td input[type="button"]:hover {
	background-color: #45a049; /* hover 효과 색상 변경 */
}

/* 쪽이동 */
.nextpage {
	font-family: "Wanted Sans Variable";
	width: 80%;
	margin: auto;
	text-align: center;
	margin-top: 30px;
}

/* 쪽이동 a 태그*/
.underline_remove {
	text-decoration: none;
	font-size: 20px;
	color: black;
}

.nextpage input {
	height: 30px;
}

/* 연체상태 td 글씨 */
.table_div .member_table a {
	font-family: "Wanted Sans Variable";
	color: blue;
	font-weight: bold;
}

/* 출력 개수 */
.search .view_count {
	font-family: "Wanted Sans Variable";
	font-size: 16px;
	height: 30px;
}
/* 연체상태 링크 */
.overdue_name {
	color: blue;
	text-decoration: underline;
	font-weight: bold;
	cursor: pointer;
}

h1 {
	font-family: "Wanted Sans Variable";
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

<body>

	<!-- header-->
	<header></header>

	<!-- 회원목록 -->
	<div>
		<h1 align="center">회원목록</h1>
	</div>

	<!-- 검색창   -->
	<form id="frm" method="get" action="admin_member_list">
		<div class="search">
			<select id="viewCount" class="view_count"
				onchange="changeViewCount(this.value)">
				<option value="10">10개씩</option>
				<option value="20">20개씩</option>
				<option value="30">30개씩</option>
			</select>
			<select class="type" name="type">
				<option value="1" ${type ==1 ? "selected" : "" }>전체</option>
				<option value="2" ${type ==2 ? "selected='selected'" : "" }>회원번호</option>
				<option value="3" ${type ==3 ? "selected='selected'" : "" }>이름</option>
				<option value="4" ${type ==4 ? "selected='selected'" : "" }>회원ID</option>
			</select> 
			<input type="text" name="keyword" class="textbox" id="input_todo"
				value="" ${keyword }>
			<input type=submit class="button" value="검색" onclick="search()" >
		</div>
	 		<!-- 정렬용 필드 --> 
		<input type="hidden" name="orderColumn" value="${orderColumn }">
		<input type="hidden" name="orderType" value="${orderType }">
	</form>


	<!-- table 보드 -->
	<form method="get" action="admin_member_list">
		<div class="table_div">
			<table class="member_table">
				<thead>
					<tr>
<!-- 						<th>순번</th> -->
						<th id="m_pid" width="80px">
						<c:if test="${orderColumn == 'm_pid' }">
							<c:if test="${orderType == 'desc' }">
								<strong style="cursor:pointer;">회원번호 ↓</strong>
							</c:if>
							<c:if test="${orderType != 'desc' }">
								<strong style="cursor:pointer;">회원번호 ↑</strong>
							</c:if>
						</c:if>
						<c:if test="${orderColumn != 'm_pid' }">
							<span style="cursor:pointer;">회원번호</span>
						</c:if>
						</th>
						<th width="100px" id="m_name">이름</th>
						<th>회원ID</th>
						<th width="100px">생년월일</th>
						<th>전화번호</th>
						<th>주소</th>
						<th>연체상태</th>
						<th>예약목록</th>
						<th>대출내역</th>
						<th>정보수정</th>
					</tr>
				</thead>
				<tbody id="memberListBody">
					<!-- empty: 비어있다/ 사이즈가0이거나 NULL일 때	 -->
					<c:if test="${not empty member_list }">
						<c:forEach var="dto" items="${member_list }" varStatus="status">
							<tr>
<%-- 								<td>${status.index + 1}</td> --%>
								<td class="member_no">${dto.m_pid}</td>
								<td><div class="member_name">${dto.m_name}</div></td>
								<td>${dto.m_id}</td>
								<td>${dto.m_birthday}</td>
								<td>${dto.m_tel}</td>
								<td>${dto.m_address}</td>
								<td>
						            <div class="overdue_name" onclick="openOverduePopup('${dto.m_pid}')">
						            	${dto.m_limitdate_text}
						            </div>
						        </td>
								<td><input type="button" value="예약조회" onclick="openReservation('${dto.m_pid}')"></td>
								<td><input type="button" value="대출조회" onclick="openLoan('${dto.m_pid}')"></td>
<%-- 								<td><input type="button" value="정보수정" onclick="openChinfo('${dto.m_pid}')"></td> --%>
								<td><input type="button" value="정보수정" onclick="location.href='/carpedm/admin_member_chginfo?m_pid=${dto.m_pid}'"></td>
							</tr>
						</c:forEach>
					</c:if>
					<!-- 비어있는 리스트라면 -->
					<c:if test="${empty member_list}">
						<tr>
							<td colspan="11">조회할 내용이 없습니다</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</form>
		<div id="paging">
		<%-- 서블릿에서 불러온 페이징 정보 --%>
		<c:set var="total_count" value="${allcount}" />
		<c:set var="perPage" value="${perPage}" />
		<c:set var="current_page" value="${page}" />

		<%-- 표시할 페이지의 범위 계산 --%>
		<c:set var="total_pages" value="${total_pages}" />
		<c:set var="start_page" value="${start_page}" />
		<c:set var="end_page" value="${end_page}" />

		<div class="total_count">
			전체 : 총&nbsp;
			<c:out value="${total_count}" />
			&nbsp;명
		</div>
		<div class="total">
			<strong><c:out value="${current_page}" /></strong>페이지 / 총 <strong><c:out
					value="${total_pages}" /></strong>페이지
		</div>
		<div class="paging">
			<c:if test="${current_page > 1}">
				<a href="?page=${current_page - 1}&perPage=${perPage}" class="pre">◀</a>
			</c:if>
			<c:forEach var="i" begin="${start_page}" end="${end_page}">
				<a href="?page=${i}&perPage=${perPage}" class="${i == current_page ? 'num active' : 'num'}">${i}</a>
			</c:forEach>
			<c:if test="${current_page < total_pages}">
				<a href="?page=${current_page + 1}&perPage=${perPage}" class="next">▶</a>
			</c:if>
		</div>
	</div>
<!-- 	<div id="paging"> -->
<%-- 		<% --%>
<!-- //  		// 서블릿에서 불러온 페이징 정보 -->
<!-- //  		int total_count = (int) request.getAttribute("allcount");// 임시로 설정한 값 -->
<!-- //  		int perPage = Integer.parseInt((String) request.getAttribute("perPage")); -->
<!-- //  		int current_page = Integer.parseInt((String) request.getAttribute("page")); -->
<!-- // 		int total_pages = total_count > 0 ? (int) Math.ceil((double) total_count / perPage) : 1; -->

<!-- //  		// 표시할 페이지의 범위 계산 -->
<!-- //  		int start_page = Math.max(current_page - 2, 1); -->
<!-- //  		int end_page = Math.min(start_page + 4, total_pages); -->
<!-- //  		start_page = Math.max(1, end_page - 4); -->
<%--  		%> --%>

<!-- 		<div class="total_count"> -->
<%-- 			전체 : 총&nbsp;<%=total_count%>&nbsp;명 --%>
<!-- 		</div> -->

<!-- 		<div class="paging"> -->
<%-- 			<% --%>
<!-- // 	 			if (current_page > 1) { -->
<%--  			%> --%>
<%-- 			<a href="?page=<%=current_page - 1%>&perPage=<%=perPage%>" --%>
<!-- 				class="pre">◀</a> -->
<%-- 			<% --%>
<!-- //  			} -->
<%--  			%> --%>
<%-- 			<% --%>
<!-- //  			for (int i = start_page; i <= end_page; i++) { -->
<%--  			%> --%>
<%-- 			<a href="?page=<%=i%>&perPage=<%=perPage%>" --%>
<%-- 				class="<%=i == current_page ? "num active" : "num"%>"><%=i%></a> --%>
<%-- 			<% --%>
<!-- //  				} -->
<%-- 			%> --%>
<%-- 			<% --%>
<!-- // 			if (current_page < total_pages) { -->
<%--  			%>  --%>
<%-- 			<a href="?page=<%=current_page + 1%>&perPage=<%=perPage%>" --%>
<!-- 				class="next">▶</a> -->
<%-- 			<% --%>
<!-- // 			}  -->
<%-- 			%> --%>
<!-- 		</div> -->
<!-- 		<div class="total"> -->
<%-- 			<strong><%=current_page%></strong>페이지 / 총 <strong><%=total_pages%></strong>페이지 --%>
<!-- 		</div> -->
<!-- 	</div> -->

	<!-- 쪽이동 -->

</body>

</html>