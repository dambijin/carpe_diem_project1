<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%-- <%@ page import="carpedm.DBConn"%> --%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>관리자페이지(회원목록)</title>
	<link href="/carpedm/css/layout.css" rel="stylesheet">
</head>

<!-- function 스크립트 -->
<script defer src="/carpedm/js/admin_library.js"></script>
<script>

	// 연체상태 popup창
	function openOverduePopup() {
		window.open
			("admin/admin_book_overdue.jsp", "팝업", "width=1000, height=700, left=100, top=100");
	}
	// 연체해제 누르면 확인 후 닫기
	function closeOverduePopup() {
		alert("연체해제 되었습니다");
		window.close();
	}
	// 예약목록 popup창
	function openReservation() {
		window.open
		("admin/admin_reservation_list.jsp", "팝업", "width=1000, height=700, left=100, top=100");
	}
	// 대출내역 popup창
	function openLoan() {
		window.open
		("admin/admin_loan.jsp", "팝업", "width=1000, height=700, left=100, top=100");
	}
	
	//  윈도우가 로드됐을 때 bind함수 작동
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

		// 검색옵션 기본세팅
		// select 옵션 가져와서 변수에담고
		// html 변수에 배열의 값을 추가해서 for문 돌림
		let search_opt_list = [ "전체", "회원번호", "이름", "회원ID", "생년월일", "전화번호", "주소", "도서관ID", "연체상태"];

		for (let i = 0; i < search_opt_list.length; i++) {
			let search_opt = document.querySelector("#search_option");
			let html = '';
			html += search_opt_list[i];

			let opt = document.createElement("option");//<option></option>
			opt.innerHTML = html;//<option>serach_opt_list[i]</option>

			search_opt.append(opt);
		}
		
		// 검색 textbox를 가져와서 inputTodo 변수에 담고
		// 값이 null이 아니면
		// textbox의 엔터키를 눌렀을 때 
		// 밑에 함수가 있어서 검색됨
		// 도서검색 버튼 클릭
		var textbox = document.getElementById("input_todo");
		 /* enterkey함수 이벤트 */
		textbox.addEventListener("keydown", enterkey);
		if (textbox.value == "") {
			document.querySelector('#input_todo').focus();		
		} else {
			alert(textbox.value + "을 검색했습니다");
			window.location.href = '/carpedm/admin_member_list?search=' + encodeURIComponent(textbox.value);
		}
		
	};

	// 엔터눌렀을 때 search() 실행
	function enterkey() {
		if (window.event.keyCode == 13) {
			// 엔터키가 눌렸을 때 실행하는 반응
			search();
		}
	}
	// 검색기능
	// 검색 함수
	function search() {
	    // 검색어 입력란과 검색 옵션의 DOM 요소 가져오기
	    var textbox = document.getElementById("input_todo");
	    var searchOption = document.getElementById("search_option").value;
	
	    // AJAX 요청을 서블릿으로 전송
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function () {
	        // 서블릿 응답이 도착하면 실행
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // 서블릿에서 받은 JSON 데이터를 파싱
	            var responseData = JSON.parse(xhr.responseText);
	            // 테이블을 새 데이터로 업데이트하는 함수 호출
	            updateTable(responseData);
	        }
	    };	
	
	    // 검색어와 검색 옵션을 포함한 URL 생성
	    var url = "/carpedm/admin_member_list?search=" + encodeURIComponent(textbox.value) + "&searchOption=" + encodeURIComponent(searchOption);
	
	    // AJAX 요청 열기 및 전송
	    xhr.open("GET", url, true);
	    xhr.send();
	}
	
	// 테이블 업데이트 함수
	function updateTable(data) {
	    // 테이블의 tbody 요소 가져오기
	    var memberListBody = document.getElementById("memberListBody");
	    memberListBody.innerHTML = ""; // 기존 테이블 행 삭제
	
	 // 새로운 데이터를 순회하며 테이블 행 추가
	    for (var i = 0; i < data.length; i++) { 
	        var tr=document.createElement("tr"); 
	        
	        // 새로운 행을 테이블에 추가
	        memberListBody.appendChild(tr); 
	        
	        // Add the necessary cells and data to the new row 
	        var cells=[ 
	        	(i +1),data[i].m_pid, data[i].m_name, data[i].m_id, data[i].m_birthday, data[i].m_tel, data[i].m_address, data[i].lb_id, 
	        	'<div class="overdue_name" onclick="openOverduePopup()">3일</div>' , 
	        	'<input type="button" value="조회" onclick="getReservationInfo(\'' + data[i].m_id + ' \')">',
	        	'<input type="button" value="조회" onclick="loans(\'' + data[i].m_id + '\')">',
	        	'<input type="button" value="수정"onclick="location.href=\'/carpedm/admin_member_chginfo?m_pid=' + data[i].m_pid + '\'">'
	        ];

	        // Add cells to the row
	        for (var j = 0; j < cells.length; j++) { 
	            var td=document.createElement("td"); td.innerHTML=cells[j];
	            tr.appendChild(td); 
	        } 
	    } 
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
		alert(count + "개씩 보기로 변경되었습니다.");
	}
	

	// 	// 검색 및 페이징 기능을 위한 함수들 추가
	//     let currentPage = 1;
	//     let itemsPerPage = 10;

	//     function changeViewCount(count) {
	//         itemsPerPage = parseInt(count);
	//         currentPage = 1;
	//         // 실제로는 서버에 해당 페이지의 데이터를 요청하고, 그 결과를 받아와서 표시하는 로직이 들어갑니다.
	//         // 여기서는 간단히 현재 페이지를 알림창으로 표시하는 예시를 보여줍니다.
	//         alert(`한 페이지에 표시할 개수: ${itemsPerPage}`);
	//     }

	//     function search() {
	//         // 검색 기능 구현
	//         let searchOption = document.getElementById("search_option").value;
	//         let searchTextbox = document.getElementById("input_todo").value;
	//         // 실제로는 서버에 검색 요청을 보내고, 그 결과를 받아와서 표시하는 로직이 들어갑니다.
	//         // 여기서는 간단히 검색어를 알림창으로 표시하는 예시를 보여줍니다.
	//         alert(`검색 옵션: ${searchOption}, 검색어: ${searchTextbox}`);
	//     }

	//     function changePage(offset) {
	//         // 페이징 기능 구현
	//         currentPage += offset;
	//         // 실제로는 서버에 해당 페이지의 데이터를 요청하고, 그 결과를 받아와서 표시하는 로직이 들어갑니다.
	//         // 여기서는 간단히 현재 페이지를 알림창으로 표시하는 예시를 보여줍니다.
	//         alert(`현재 페이지: ${currentPage}`);
	//     }
	
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

	.search .range {
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
	.member_table td,
	.member_table th {
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
	.member_table tr td input {
	    font-family: "Wanted Sans Variable";
	    font-size: 14px;
	    background-color: #4CAF50; /* 버튼 색상 변경 */
	    color: white;
	    width: 50px;
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

	/* 이름 링크 */
/* 	.member_name { */
/* 		color: blue; */
/* 		font-family: bold; */
/* 		text-decoration: underline; */
/* 		font-weight: bold; */
/* 		cursor: pointer; */
/* 	} */

	/* 연체상태 링크 */
	.overdue_name {
		color: blue;
		font-family: bold;
		text-decoration: underline;
		font-weight: bold;
		cursor: pointer;
	}

	h1 {
		font-family: "Wanted Sans Variable";
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
	<div class="search">
		<select id="viewCount" class="view_count" onchange="changeViewCount(this.value)">
			<option value="10">10개씩</option>
			<option value="20">20개씩</option>
			<option value="30">30개씩</option>
		</select>


		<select class="range" id="search_option">
			<!-- 자바스크립트로 가져오기 -->
		</select>

		<input type="text" name="search" class="textbox" id="input_todo">
		<button type=button class="button" onclick="search()">검색</button>
	</div>



	<!-- table 보드 -->
	<div class="table_div">
		<form method="get" action="/carpedm/admin_member_list" >
		<table class="member_table" id="memberListTable">
				<thead>
					<tr id="memberListTable_tr">
						<th width="80px">순번</th>
						<th width="80px">회원번호</th>
						<th width="100">이름</th>
						<th width="100">회원ID</th>
						<th width="130px">생년월일</th>
						<th width="150px">전화번호</th>
						<th width="200">주소</th>
						<th width="80px">도서관ID</th>
						<th width="80px">연체상태</th>
						<th width="100">예약목록</th>
						<th width="100">대출내역</th>
						<th width="100">정보수정</th>
					</tr>
				</thead>
				<tbody id="memberListBody">
	                <!-- 동적으로 추가될 테이블 내용 -->
	
					<%
					ArrayList<Map<String, String>> data_list = (ArrayList<Map<String, String>>) request.getAttribute("member_list");
					%>
	
					<%
					for (int i = 0; i < data_list.size(); i++) {
					%>
					<tr>				
						<td><%=i+1 %></td>
						<td class="member_no"><%=data_list.get(i).get("m_pid")%></td>
						<td><div class="member_name"><%=data_list.get(i).get("m_name")%></div></td>
						<td><%=data_list.get(i).get("m_id")%></td>
						<td><%=data_list.get(i).get("m_birthday")%></td>
						<td><%=data_list.get(i).get("m_tel")%></td>
						<td><%=data_list.get(i).get("m_address")%></td>
						<td><%=data_list.get(i).get("lb_id")%></td>
						<td><div class="overdue_name" onclick="openOverduePopup()">3일</div></td>
						<td><input type="button" value="조회" onclick="openReservation()"></td>
<%-- 						getReservationInfo('<%=data_list.get(i).get("m_id")%>') --%>
						<td><input type="button" value="조회" onclick="openLoan()"></td>
						<td><input type="button" value="수정"
							onclick="location.href='/carpedm/admin_member_chginfo?m_pid=<%=data_list.get(i).get("m_pid")%>';"></td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
	</form>
		
	</div>

	<!-- 쪽이동 -->
	<div class="paging nextpage">
        <a href="#" class="pre underline_remove" onclick="changePage(-1)">◀</a>
        <span id="currentPage" class="underline_remove">1</span>
        <a href="#" class="num underline_remove" onclick="changePage(1)">2</a>
        <a href="#" class="num underline_remove" onclick="changePage(2)">3</a>
        <a href="#" class="num underline_remove" onclick="changePage(3)">4</a>
        <a href="#" class="num underline_remove" onclick="changePage(4)">5</a>
        <a href="#" class="next underline_remove" onclick="changePage(1)">▶</a>
    </div>


	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header_admin.js"></script>
</body>

</html>