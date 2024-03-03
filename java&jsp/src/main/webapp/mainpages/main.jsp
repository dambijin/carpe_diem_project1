<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>메인 페이지</title>
<link href="/carpedm/css/layout.css" rel="stylesheet">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
/* 바디사이즈 */
.bodysize {
	text-align: center;
	font-family: "Wanted Sans Variable";
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
	text-align: center;
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
	height: 400px;
	width: 100%;
	background-color: rgba(140, 201, 240, 0.6);
	border: 2px solid rgba(140, 201, 240, 0.6);
	border-bottom: 0px;
	font-size: 20px;
	font-weight: bold;
	text-align: center;
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
	text-align: center;
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
	text-align: center;
}

/*슬라이드배너 테스트*/
/* 배너 */
nav .banner {
	width: 100%; /* 이미지의 너비를 조절합니다. */
	height: 350px; /* 이미지의 높이를 조절합니다. */
	object-fit: contain;
	/* 이미지의 비율을 유지하면서 너비와 높이에 맞게 이미지를 조절합니다. */
	margin-bottom: 10px;
	object-fit: contain;
}

nav .outer {
	/* 	height: 490px; */
	margin: 0 auto;
	overflow-x: hidden;
	text-align: center;
	vertical-align: center;
}

nav .inner-list {
	display: flex;
	transition: .3s ease-out;
	height: 100%;
}

nav .inner {
	padding: 0 16px;
}

nav .inner .inner-list img {
	object_fit: contain;
}
/* 배너버튼 */
.button-list {
	position: absolute;
	top: 50%;
	width: 100%;
	pointer-events: none;
}

.button-list button {
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	width: 50px;
	height: 50px;
	border-radius: 50%;
	border: none;
	color: #fff;
	background-color: rgba(0, 0, 0, 0.5);
	cursor: pointer;
	transition: background-color 0.3s ease;
	pointer-events: all;
}

.button-list button:hover {
	background-color: rgba(0, 0, 0, 0.8);
}

.button-left {
	left: 15px;
}

.button-right {
	right: 15px;
}
</style>

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
  		silde_bannersetting();
	   	chg_text_detail(0);

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

	
	//슬라이드배너 생성
	function silde_bannersetting()
	{
		/*div사이즈 동적으로 구하기*/
		const outer = document.querySelector('.outer');
		const innerList = document.querySelector('.inner-list');
		const inners = document.querySelectorAll('.inner');
		let currentIndex = 0; // 현재 슬라이드 화면 인덱스
		
	    // 슬라이드배너 크기 설정 함수
	    function setSize() {
	        inners.forEach((inner) => {
	            inner.style.width = `${"${outer.clientWidth}px"}`;
	        })
	        innerList.style.width = `${"${outer.clientWidth * inners.length}px"}`;
	    }

	    setSize();  // 처음 로딩시에도 크기 설정

	    // 창 크기 변경시 슬라이드배너 크기 재설정
	    window.addEventListener('resize', () => {
	        setSize();
	    });
		/*
		  버튼에 이벤트 등록하기
		*/
		const buttonLeft = document.querySelector('.button-left');
		const buttonRight = document.querySelector('.button-right');
		
		buttonLeft.addEventListener('click', () => {
		  currentIndex--;
		  currentIndex = currentIndex < 0 ? 0 : currentIndex; // index값이 0보다 작아질 경우 0으로 변경
		  innerList.style.marginLeft = `-${"${outer.clientWidth * currentIndex}px"}`; // index만큼 margin을 주어 옆으로 밀기
		  clearInterval(interval); // 기존 동작되던 interval 제거
		  interval = getInterval(); // 새로운 interval 등록
		});
		
		buttonRight.addEventListener('click', () => {
		  currentIndex++;
		  currentIndex = currentIndex >= inners.length ? inners.length - 1 : currentIndex; // index값이 inner의 총 개수보다 많아질 경우 마지막 인덱스값으로 변경
		  innerList.style.marginLeft = `-${"${outer.clientWidth * currentIndex}px"}`; // index만큼 margin을 주어 옆으로 밀기
		  clearInterval(interval); // 기존 동작되던 interval 제거
		  interval = getInterval(); // 새로운 interval 등록
		});
		
		/*
		  주기적으로 화면 넘기기
		*/
		const getInterval = () => {
		  return setInterval(() => {
		    currentIndex++;
		    currentIndex = currentIndex >= inners.length ? 0 : currentIndex;
		    innerList.style.marginLeft = `-${"${outer.clientWidth * currentIndex}px"}`;
		  }, 2000);
		}
		
		let interval = getInterval(); // interval 등록
	}
	
	
	// 도서검색 버튼 클릭
	function search() {
		let textbox = document.getElementById("searchWord");
		let selectbox = document.getElementById("libsear");
		window.location.href = '/carpedm/book_search?search=' + encodeURIComponent(textbox.value)+ '&item=' + selectbox.value;		
	};
	
    function openBookDetail(b_id) {
        window.open('book_detail?id='+b_id ,"", "width=900,height=600");
    }

	let library_list_js = [];
	function chg_text_detail(sel) {
	    let content = "<h2>"+library_list_js[sel].lb_name+"</h2>";
	    content += "<h3>이용시간</h3>"+library_list_js[sel].lb_opentime+"<br><br>";
	    content += "<h3>주소</h3>"+library_list_js[sel].lb_address+"<br><br>";
	    content += "<h3>전화번호</h3>"+library_list_js[sel].lb_tel+"<br><br>";
	    document.querySelector('.text_detail').innerHTML = content;
	};
	
	<%		  // 세션에서 현재 아이디값 가져오기
	HttpSession getSession = request.getSession();
	String m_pid = (String) getSession.getAttribute("m_pid");
	%>
</script>
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
								<option>전체</option>
								<option>제목</option>
								<option>저자</option>
								<option>출판사</option>
								<option>키워드</option>
						</select>
						</strong> <input type="text" name="searchWord" autocomplete="off"
							id="searchWord" style="ime-mode: active" placeholder="검색어를 적어주세요">
						<!-- input type="hidden" name="item" value="title" id="item" -->
					</div>
					<input type="button" class="btn btn-search" value="도서검색"
						id="libsearch" onclick="search();">
				</div>
			</fieldset>
		</div>
		<br>
		<nav>
			<div class="outer">
				<div class="inner-list">
					<div class="inner">
						<img class="banner" src="/carpedm/resource/banner3.png">
					</div>
					<div class="inner">
						<img class="banner" src="/carpedm/resource/banner2.png">
					</div>
					<div class="inner">
						<img class="banner" src="/carpedm/resource/banner3.png">
					</div>
				</div>
				<div class="button-list">
					<button class="button-left">←</button>
					<button class="button-right">→</button>
				</div>
			</div>
		</nav>
	</div>

	<section class="library_information_content">
		<div class="announcement_library_information">
			<div class="announcement">
				공지사항
				<table class="announcement_table" id="announcement_table">
					<%
					ArrayList<Map<String, String>> notice_list = (ArrayList<Map<String, String>>) request.getAttribute("notice_list");

					for (int i = 0; i < notice_list.size(); i++) {
					%>
					<tr>
						<td class="ann_id"><%=notice_list.get(i).get("N_ID")%></td>
						<td class="ann_title"><a
							href="notice_detail?N_ID=<%=notice_list.get(i).get("N_ID")%>"><%=notice_list.get(i).get("N_TITLE")%></a>
						</td>
						<td class="ann_day"><%=notice_list.get(i).get("N_DATE").substring(0, 10)%></td>
					</tr>
					<%
					if(i>=4){break;}
					}
					%>
				</table>
			</div>

			<div class="newbook">
				신착도서
				<table class="newbook_table">
					<tr id="nb">
						<%
						ArrayList<Map<String, String>> book_list = (ArrayList<Map<String, String>>) request.getAttribute("book_list");

						for (int i = 0; i < 3; i++) {
						%>
						<td>
							<div class="newbook_div"
								onclick="openBookDetail('<%=book_list.get(i).get("B_ID")%>')">
								<img class="newbook_img"
									src="<%=book_list.get(i).get("B_IMGURL")%>">
							</div>
							<div class="newbook_title"><%=book_list.get(i).get("B_TITLE")%></div>
							<div class="newbook_author"><%=book_list.get(i).get("B_AUTHOR")%></div>
						</td>
						<%
						}
						%>
					</tr>
				</table>
			</div>
		</div>

		<div class="library_information">
			이용 정보 <select id="libs_info"
				onchange="chg_text_detail(this.selectedIndex);">
				<%
				ArrayList<Map<String, String>> library_list = (ArrayList<Map<String, String>>) request.getAttribute("library_list");

				for (int i = 0; i < library_list.size(); i++) {
				%>
				<option><%=library_list.get(i).get("LB_NAME")%></option>
				<%
				}
				%>
			</select>
			<div id="libs_time" class="calendar"></div>
			<div class="calendar">
				<div class="text_detail">
					<%-- 					<%= library_list.get(0).get("LB_CONTENT") %> --%>
				</div>
				<script>
					library_list_js = [
					<%for (int i = 0; i < library_list.size(); i++) {%>
					    {
					        lb_name: '<%=library_list.get(i).get("LB_NAME")%>',
					        lb_address: '<%=library_list.get(i).get("LB_ADDRESS")%>',
					        lb_opentime: '<%=library_list.get(i).get("LB_OPENTIME")%>',
					        lb_tel: '<%=library_list.get(i).get("LB_TEL")%>',
					        lb_content: '<%=library_list.get(i).get("LB_CONTENT").replace("\n", "<br>").replace("\"", "\\\"").replace("\r", "\\r")%>'
					    }<%=(i < library_list.size() - 1) ? "," : ""%>
					<%}%>
					];
					</script>
			</div>
		</div>


	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header.js"></script>
</body>


</html>