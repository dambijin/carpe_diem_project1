<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="carpedm.DBConn"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>메인 페이지</title>
<link href="../css/layout.css" rel="stylesheet">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	// 이용정보 달력
	$(function() {
		$("#datepicker").datepicker(
				{
					nextText : '다음달',
					prevText : '이전달'
					// 요일 표기 바꾸기
					,
					dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ]
					// 영어month 한글로 바꾸기
					,
					monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월',
							'8월', '9월', '10월', '11월', '12월' ]
					// 월 년 으로 표기되던거 년 월로 바꾸기
					,
					showMonthAfterYear : true
					// 앞 뒤 월의 날짜 표기
					,
					showOtherMonths : true
					// year년으로 붙이기
					,
					yearSuffix : "년"
				});

	});

	window.addEventListener("load", function() {
		announcement();
		libsinfolist();
// 		chg_text_detail(0);

		// 도서검색 버튼 엔터이벤트
		let textbox = document.getElementById("searchWord");
		// Enter 키 이벤트 리스너 추가
		textbox.addEventListener("keyup", function(event) {
			// keyCode 13은 Enter 키를 나타냅니다
			if (event.keyCode === 13) {
				event.preventDefault(); // 기본 동작인 폼 제출 방지
				search(); // 검색 함수 호출
			}
		});
	});

	// 도서검색 버튼 클릭
	function search() {
		let textbox = document.getElementById("searchWord");
		if (textbox.value == "") {
			alert("내용을 입력해주세요");
			document.querySelector('#searchWord').focus();
		} else {
			alert(textbox.value + "을 검색했습니다");
			window.location.href = 'book_search.html';
		}

	};

	function chg_text_detail(sel) {
		<% 
			ArrayList<String> result_list2 = DBConn.getSelectQueryAll("select * from library");
		%>
	
		let data = "<%=result_list2.get(0).toString() %>";
		data = data.replace(/\n/g, '<br>'); // 텍스트 파일 내의 줄바꿈('\n')을 HTML의 줄바꿈('<br>')으로 변환합니다.
		data = data.replace('이용시간', '<b>이용시간</b>');
		data = data.replace('휴관일', '<b>휴관일</b>');

		console.log(data);
		// 변환된 데이터를 웹 페이지에 추가
		document.querySelector('.text_detail').innerHTML = data;

	};

	function announcement() {
		// 공지사항
		for (let i = 0; i < 5; i++) {
			let announ = document.querySelector("#announcement_table")

			let html = '';
			html += '<td class="ann_title">';
			html += '독서토론대회' + (i + 1);
			html += '</td>';
			html += '<td class="ann_day">';
			html += '2023-01-06';
			html += '</td>';

			let tr = document.createElement("tr");
			tr.innerHTML = html;

			tr.querySelector(".ann_title").addEventListener("click",
					function() {
						location.href = "notice_detail.html";
					})
			announ.append(tr);
			// 만약에 5개의 공지사항이 모두 있을 경우 맨아래 tr 필요
		}

		// 신착도서
		// tr 안에
		for (let i = 0; i < 3; i++) {
			let newbook = document.querySelector("#nb")

			let html = '';

			html += '<div class="newbook_div">';
			html += '<img class="newbook_img" src="../resource/logo.png">';
			html += '</div>';
			html += '<div class="newbook_title">(자바개발자도 쉽고 즐겁게 배우는) 테스팅 이야기</div>';
			html += '<div class="newbook_author">저자 : 이상민</div>';

			let td = document.createElement("td");
			td.innerHTML = html;

			td
					.querySelector(".newbook_div")
					.addEventListener(
							"click",
							function() {
								let href_text = "book_detail.html?title=(%EC%9E%90%EB%B0%94%EA%B0%9C%EB%B0%9C%EC%9E%90%EB%8F%84%20%EC%89%BD%EA%B3%A0%20%EC%A6%90%EA%B2%81%EA%B2%8C%20%EB%B0%B0%EC%9A%B0%EB%8A%94)%20%ED%85%8C%EC%8A%A4%ED%8C%85%20%EC%9D%B4%EC%95%BC%EA%B8%B0&topic=%EA%B0%9C%EB%B0%9C&author=%EC%9D%B4%EC%83%81%EB%AF%BC%20%EC%A7%80%EC%9D%8C&publisher=%ED%95%9C%EB%B9%9B%EB%AF%B8%EB%94%94%EC%96%B4&year=2009&callNumber=005.115-%EC%9D%B4%EC%83%81%EB%AF%BC&registerNumber=NG0000002167&library=%EC%84%B1%EB%82%A8%EB%A9%B4%EC%9E%91%EC%9D%80%EB%8F%84%EC%84%9C%EA%B4%80&loan_state=false&reservation_state=true"
								window.open(href_text, '_blank',
										'width=900,height=600');
							})

			newbook.append(td);
		}
	};

	function libsinfolist() {		
		<% 
			ArrayList<String> result_list = DBConn.getlibraryAll();
		%>
		
		// 도서관 select
// 		let libs_list = [ "천안도서관", "두정도서관", "아우내도서관" ];
		let libs_list = <%=result_list%>;
		let libs_list_box = document.querySelector("#libs_info");

		for (let i = 0; i < libs_list.length; i++) {
			console.log(libs_list[i]);
			libs_list_box.innerHTML += "<option>" + libs_list[i] + "</option>";
		}

		// 자료검색 select
		let libs_search = [ "전체", "제목", "저자", "발행처", "키워드" ];
		let libs_searchbox = document.querySelector("#libsear");

		for (let i = 0; i < libs_search.length; i++) {
			libs_searchbox.innerHTML += "<option>" + libs_search[i]
					+ "</option>";
		}

		// 이용정보 select 변경될때
		let select = document.querySelector("#libs_info");
		select.addEventListener("change", function() {
			let sel = select.value;
			chg_text_detail(sel);

		});
	};
</script>

<style>
/* 바디사이즈 */
.bodysize {
	text-align: center;
	font-family: "Wanted Sans Variable";
}

/* 배너 */
nav .banner {
	width: 70%;
	margin-bottom: 10px;
}

/* 섹션 */
.library_information_content {
	width: 75%;
	margin: auto;
}

/* 공지사항+신착도서 */
.announcement_library_information {
	width: 69%;
	display: inline-block;
}

/* 공지사항 */
.announcement {
	width: 100%;
	font-size: 20px;
	font-weight: bold;
	border: 2px solid rgba(168, 156, 200, 0.6);
	border-bottom: 0px;
	background-color: rgba(168, 156, 200, 0.6);
}

/* 공지사항테이블 */
.announcement_table {
	font-size: 16px;
	font-weight: normal;
	margin-top: 10px;
	width: 100%;
	border-collapse: collapse;
}

.announcement_table .ann_title {
	cursor: pointer;
}

/* 공지사항 td */
.announcement_library_information .announcement_table td {
	border-bottom: 2px solid rgba(168, 156, 200, 0.6);
}

/* 신착도서 */
.announcement_library_information .newbook {
	height: 320px;
	width: 100%;
	background-color: rgba(140, 201, 240, 0.6);
	border: 2px solid rgba(140, 201, 240, 0.6);
	border-bottom: 0px;
	font-size: 20px;
	font-weight: bold;
}

/* 신착도서 테이블 */
.newbook_table {
	margin-top: 10px;
	font-size: 15px;
	font-weight: normal;
}

.newbook_table td {
	border-right: 2px solid rgba(140, 201, 240, 0.6);
}

/* 신착도서 div */
.newbook_table .newbook_div {
	margin-top: 8px;
	cursor: pointer;
}

/* 도서관별이용정보 */
.library_information_content .library_information {
	background-color: rgba(239, 168, 176, 0.6);
	display: inline-block;
	width: 30%;
	height: 450px;
	vertical-align: top;
	font-size: 22px;
	padding-top: 8px;
	font-weight: bold;
}

/* 이용시간, 휴관일 */
.library_information .calendar {
	text-align: center;
	margin: auto;
	font-weight: normal;
	margin-top: 10px;
	font-size: 18px;
}

.library_information_content .announcement_library_information .announcement .ann_title
	{
	width: 80%;
}

.library_information_content .announcement_library_information .announcement,
	.library_information_content .announcement_library_information .newbook table,
	.library_information table {
	width: 100%;
}

.announcement .ann_day {
	text-align: right;
}

.announcement_library_information .newbook_img {
	width: 200px;
}

.bodysize select, .library_information select {
	font-family: "Wanted Sans Variable";
	font-size: 18px;
}

.library_infor .gubun {
	background-color: rgba(168, 156, 200, 0.6);
	height: 2px;
	border: none;
	width: 90%;
}

/* 검색창관련 */
.search_box {
	width: 80%;
	margin: auto;
}

.search_fieldset {
	margin: auto;
	display: flex;
	align-items: center;
	padding: 10px;
	margin-inline-start: 0px;
	border: 0px solid #ccc;
	justify-content: center;
}

.search_fieldset legend {
	margin-right: 20px;
}

.search_fieldset .result {
	flex: 1;
	display: flex;
	align-items: center;
}

.search_fieldset .search {
	flex: 4;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.search_fieldset .search .input {
	flex: 4;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.search_fieldset .search .input select {
	flex: 1;
	margin-right: 10px;
	height: 40px;
	font-size: 18px;
	font-family: 'Wanted Sans Variable';
}

.search_fieldset .search .input input {
	flex: 3;
	padding: 5px;
	border: 1px solid #ccc;
	height: 35px;
	font-family: 'Wanted Sans Variable';
	font-size: 18px;
}

/* 자료검색 */
.search_fieldset .search .btn-search {
	flex: 1;
	padding: 5px 10px;
	background-color: rgba(168, 156, 200, 1.0);
	color: #eee;
	border: none;
	cursor: pointer;
	height: 45px;
	font-family: 'Wanted Sans Variable';
	font-size: 18px;
}
</style>
</head>

<body>
	<header></header>
	<div class="bodysize">
		<div class="search_box">
			<fieldset class="search_fieldset">
				<!-- <legend>통합검색</legend> -->
				<div class="search">
					<div class="input">
						<strong> <select name="item" id="libsear">

						</select>
						</strong> <input type="text" name="word" autocomplete="off" id="searchWord"
							style="ime-mode: active" placeholder="검색어를 적어주세요">
						<!-- input type="hidden" name="item" value="title" id="item" -->
					</div>
					<input type="button" class="btn btn-search" value="도서검색"
						id="libsearch" onclick="search()">
			</fieldset>
		</div>
		<br>
		<nav>
			<img class="banner" src="../resource/banner.png">
		</nav>

		<section class="library_information_content">
			<div class="announcement_library_information">
				<div class="announcement">
					공지사항
					<table class="announcement_table" id="announcement_table">
						<!-- 자바스크립트로 가져오기 -->
					</table>
				</div>

				<div class="newbook">
					신착도서
					<table class="newbook_table">
						<tr id="nb">
							<!-- 자바스크립트로 가져오기 -->
						</tr>
					</table>
				</div>
			</div>

			<div class="library_information">
				이용 정보 <select id="libs_info">

				</select>
				<div id="libs_time" class="calendar"></div>
				<div class="calendar">

					<div class="text_detail"></div>
				</div>

			</div>
		</section>
	</div>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="../js/header.js"></script>
</body>


</html>