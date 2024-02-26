<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>관리자페이지(회원목록)</title>
	<link href="../css/layout.css" rel="stylesheet">
</head>

<!-- function 스크립트 -->
<script defer src="../js/admin_library.js"></script>
<script>
	// 연체상태 popup창
	function overdue_popup() {
		window.open
			("admin_book_overdue.html", "팝업", "width=1000, height=700, left=100, top=100");
	}
	// 연체해제 누르면 확인 후 닫기
	function overdue_close() {
		alert("연체해제 되었습니다");
		window.close();
	}
	//  윈도우가 로드됐을 때 bind함수 작동
	window.addEventListener("load", function () {
		bind();
	});

	function bind() {

		// 테이블을 가져와서 todolist변수에 담아둠
		let todolist = document.querySelector("#page1");

		// 임시 게시판 생성
		for (let i = 1; i <= 10; i++) {
			let html = '';

			html += '<td class="member_no">' + i + '</td>';
			html += '<td><div class="member_name">임우혁</div></td>';
			html += '<td>2001-05-24</td>';
			html += '<td>010-1234-5678</td>';
			html += '<td>청주</td>';
			html += '<td><div class="overdue_name" onclick="overdue_popup()">3일</div></td>';
			html += '<td><input type="button" value="조회" onclick=\'alert("예약목록 조회")\'></td>';
			html += '<td><input type="button" value="조회" onclick=\'alert("대출내역 조회")\'></td>';
			html += '<td><input type="button" value="수정" onclick="location.href=\'admin_member_chginfo.html\';"></td>';

			// 새로운 tr 추가
			let tr = document.createElement("tr"); // <tr></tr>
			tr.innerHTML = html;

			//이름에 클릭이벤트
			// tr 엘리먼트 내에서 member_name을 찾아 이벤트 리스너 추가
			// tr 엘리먼트 내에서 member_no 찾아 그 내용(innerHTML)을 변수에 담음
			tr.querySelector(".member_name").addEventListener("click", function () {
				let member_no = tr.querySelector(".member_no").innerHTML;
				alert("회원번호 : " + member_no);
			})

			// 테이블의 tr 엘리먼트를 추가
			todolist.append(tr);
		}

		// 검색 textbox를 가져와서 inputTodo 변수에 담고
		// 값이 null이 아니면
		// textbox의 엔터키를 눌렀을 때 
		// 밑에 함수가 있어서 검색됨
		var inputTodo = document.getElementById("input_todo");
		if (inputTodo != null) {
			inputTodo.addEventListener("keydown", enterkey); /* enterkey함수 이벤트 */
		}

		//검색옵션 기본세팅
		// select 옵션 가져와서 변수에담고
		// html 변수에 배열의 값을 추가해서 for문 돌림
		let search_opt_list = ["회원번호", "이름", "생년월일", "전화번호", "주소"];

		for (let i = 0; i < search_opt_list.length; i++) {
			let search_opt = document.querySelector("#search_option");
			let html = '';
			html += search_opt_list[i];

			let opt = document.createElement("option");//<option></option>
			opt.innerHTML = html;//<option>serach_opt_list[i]</option>

			search_opt.append(opt);
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
	// 텍스트박스가 null이 아니면 textbox 입력값을 alert띄움
	function search() {
		var textbox = document.getElementById("input_todo");
		if (textbox != null) {
			alert(textbox.value + " ");
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

	// 주석해놔서 지금 실행 안됨
	// 검색 옵션과 검색 텍스트박스에 입력된 값을 처리하는 함수
	function handleSearchOption() {
		// 검색 옵션과 검색 텍스트박스의 DOM 요소 가져오기
		var searchOption = document.getElementById("search_option").value;
		var searchTextbox = document.getElementById("input_todo");
		
		// 선택된 옵션에 따라 다르게 동작
		switch (searchOption) {
			case "회원번호":
				alert("회원번호: " + searchTextbox.value);
				break;
			case "이름":
				alert("이름: " + searchTextbox.value);
				break;
			case "연체상태":
				alert("연체상태: " + searchTextbox.value);
				break;
			default:
				// 기타 옵션의 경우 아무 동작도 수행하지 않음
				break;
		}
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

	/* 테이블 안에 input */
	.member_table tr td input {
		font-family: "Wanted Sans Variable";
		font-size: 16px;
		background-color: rgba(71, 125, 231, 0.973);
		color: white;
		width: 50px;
		height: 20px;
		border: 0;
		border-radius: 5px;
		cursor: pointer;
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
	.member_name {
		color: blue;
		font-family: bold;
		text-decoration: underline;
		font-weight: bold;
		cursor: pointer;
	}

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
		<!-- <select class="view_count" onchange="changeViewCount(this.value)"> -->
		<select class="view_count">
			<option value="10">10개씩</option>
			<option value="20">20개씩</option>
			<option value="30">30개씩</option>
		</select>

		<!-- <select class="range" id="search_option" onchange="handleSearchOption()"> -->
		<select class="range" id="search_option">
			<!-- 자바스크립트로 가져오기 -->
		</select>

		<input type="text" name="search" class="textbox" id="input_todo">
		<button type=button class="button" onclick="search()">검색</button>
	</div>



	<!-- table 보드 -->
	<div class="table_div">
		<table class="member_table" id="page1">
			<tr id="page1_tr">
				<th width="100">회원번호</th>
				<th width="100">이름</th>
				<th width="100">생년월일</th>
				<th width="100">전화번호</th>
				<th width="100">주소</th>
				<th width="100">연체상태</th>
				<th width="100">예약목록</th>
				<th width="100">대출내역</th>
				<th width="100">정보수정</th>
			</tr>

		</table>
	</div>

	<!-- 쪽이동 -->
	<div class="paging nextpage">
		<a href="" class="pre underline_remove">◀</a>
		<strong class="underline_remove">1</strong>
		<a href="" class="num underline_remove">2</a>
		<a href="" class="num underline_remove">3</a>
		<a href="" class="num underline_remove">4</a>
		<a href="" class="num underline_remove">5</a>
		<a href="" class="next underline_remove">▶</a>
	</div>


	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="../js/header_admin.js"></script>
</body>

</html>