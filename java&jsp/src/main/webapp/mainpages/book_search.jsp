<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Arrays"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>자료검색</title>
<link href="/carpedm/css/layout.css" rel="stylesheet">


<style>
section {
	width: 80%;
	margin: auto;
	/* font-family: 'KNUTRUTHTTF'; */
	font-family: 'Wanted Sans Variable';
}

/* 필터관련 */
.selbox {
	border: 1px solid #000000;
	background-color: #e0e0e0;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	margin-inline-start: 0px;
}

.selbox legend {
	text-align: center;
	font-weight: bold;
}

.selbox dl {
	margin-top: 0px;
	margin-bottom: 0px;
}

.selbox dd {
	margin-inline-start: 0px;
}

.selbox dl dd ul {
	list-style: none;
	padding: 5px;
	padding-left: 0px;
	display: flex;
	flex-wrap: wrap;
	border-top: 1px solid #000000;
	margin-top: 10px;
}

.selbox dl dd ul li {
	padding: 5px;
	display: inline-block;
}

.selbox label.all {
	cursor: pointer;
	font-size: 14px;
	font-weight: bold;
}

.selbox label.all input {
	margin-right: 3px;
	/* 모든 브라우저를 지원하기 위해 -webkit- 및 -ms- 접두사를 사용 */
	-webkit-transform: scale(1.5);
	-ms-transform: scale(1.5);
	transform: scale(1.5);
}

.selbox dl dd ul li input[type="checkbox"] {
	margin-right: 1px;
	/* 모든 브라우저를 지원하기 위해 -webkit- 및 -ms- 접두사를 사용 */
	-webkit-transform: scale(1.5);
	-ms-transform: scale(1.5);
	transform: scale(1.5);
}

/* 검색창관련 */
.search_fieldset {
	display: flex;
	align-items: center;
	padding: 10px;
	margin-inline-start: 0px;
	border: 0px solid #ccc;
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
	margin-left: 5px;
}

.result input[type="checkbox"] {
	/* 모든 브라우저를 지원하기 위해 -webkit- 및 -ms- 접두사를 사용 */
	-webkit-transform: scale(1.5);
	-ms-transform: scale(1.5);
	transform: scale(1.5);
}

.result label {
	font-size: 20px;
	font-family: 'Wanted Sans Variable';
}

.result_filter_div {
	height: 50px;
	margin-top: 10px;
	margin-bottom: 5px;
}

.result_filter_div .blank_space {
	display: inline-block;
	width: 25%;
}

.result_filter_div .result_text {
	display: inline-block;
	text-align: center;
	width: 49%;
	font-size: 40px;
	font-family: 'Wanted Sans Variable';
}

.result_filter_div .result_filter_all {
	display: inline-block;
	text-align: right;
	width: 25%;
	/* height: 100%; */
	vertical-align: middle;
}

.result_filter_div .result_filter_all .result_filter {
	height: 35px;
	font-size: 18px;
	font-family: 'Wanted Sans Variable';
}

/* 검색결과 책 리스트 */
#result_booklist {
	display: flex;
	flex-direction: column;
	width: 100%;
}

#result_booklist dl {
	border: 1px solid #ccc;
	border-radius: 5px;
	padding: 20px;
	display: flex;
	margin-bottom: 2px;
}

#result_booklist dt {
	flex: 1;
	display: flex;
	justify-content: center;
	align-items: center;
	padding-right: 20px;
	border-right: 1px solid #ccc;
}

#result_booklist dd {
	flex: 3;
	padding-left: 20px;
}

#result_booklist dd .ico {
	font-weight: bold;
	margin-bottom: 10px;
}

#result_booklist dd ul {
	list-style: none;
	padding: 0;
	margin-bottom: 10px;
}

#result_booklist dd ul li {
	margin-bottom: 5px;
}

#result_booklist dd ol {
	list-style: none;
	padding: 0;
	display: flex;
}

#result_booklist dd ol li {
	padding: 5px 10px;
	background-color: #e0e0e0;
	color: black;
	font-weight: bold;
	border-radius: 5px;
	margin-right: 10px;
}

#result_booklist dd ol .reservation_success {
	color: blue;
	cursor: pointer;
	background-color: rgba(199, 156, 200, 0.6);
}

#result_booklist dd ol ._fail {
	color: red;
}

#result_booklist dd ol .loan_success {
	color: blue;
}

#result_booklist dt img {
	width: 180px;
	height: 200px;
	object_fit: contain;
}

/* 페이지 */
#paging {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 10px;
	border-top: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
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
/* 인기검색어 */
.pop_searchkeyword {
	width: 100%; /* 필요에 따라 조정 */
	max-width: 300px; /* 최대 너비 설정, 필요에 따라 조정 */
	margin: 0 auto; /* 가운데 정렬 */
	padding: 5px; /* 내부 여백 */
	padding-left:30px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
	border-radius: 8px; /* 모서리 둥글게 */
	position: fixed; /* 뷰포트에 상대적으로 고정 */
	right: 20px; /* 오른쪽에서 20px 떨어진 위치 */
	bottom: 70px; /* 아래에서 20px 떨어진 위치 */
	background-color: rgba(255, 255, 255, 0.9); /* 배경 색상 및 투명도 설정 */
	z-index: 1000; /* 다른 요소 위에 렌더링 되도록 z-index 설정 */
	box-sizing: border-box; /* padding을 포함한 박스 모델 */
	height:500px;
	max-height: 70px; /* 초기 최대 높이 설정 */
	overflow: hidden; /* 내용이 넘칠 경우 숨김 처리 */
	transition: max-height 0.5s ease; /* 최대 높이 변경시 부드러운 전환 효과 */
	border: 1px solid black;
}


.pop_searchkeyword ul {
	list-style: none; /* 기본 리스트 스타일 제거 */
	margin: 0;
	padding: 0;
}

.pop_searchkeyword li {
	border-bottom: 1px solid #eee; /* 하단 경계선 */
	padding: 10px 0; /* 상하 여백 */
	display: flex; /* Flexbox 레이아웃 사용 */
	align-items: center; /* 세로 중앙 정렬 */
}

.pop_searchkeyword li:last-child {
	border-bottom: none; /* 마지막 항목의 하단 경계선 제거 */
}

.pop_searchkeyword em {
	font-style: normal; /* 기울임꼴 제거 */
	font-weight: bold; /* 글자 두껍게 */
	color: #007bff; /* 글자색 */
	margin-right: 10px; /* 오른쪽 여백 */
}

.pop_searchkeyword span {
	flex-grow: 1; /* 남은 공간 채우기 */
}

.pop_searchkeyword a {
	text-decoration: none;
	color: black;
}

.pop_searchkeyword:hover {
	max-height: 500px; /* 마우스 오버시 충분히 큰 값으로 변경 */
}
</style>
</head>


<script>
    window.addEventListener("load", function () {

        //도서관체크박스 처리
    document.querySelectorAll(".chk").forEach(chk => {
        chk.addEventListener("click", function (event) {
            let allCount = document.querySelectorAll(".chk").length;
            let checkedCount = document.querySelectorAll(".chk:checked").length;
            if (allCount == checkedCount) {
                document.querySelector("#multiChk1").checked = true;
            } else {
                document.querySelector("#multiChk1").checked = false;
            }
        });
    });

        // 도서검색 버튼
        // Enter 키 이벤트 리스너 추가
        document.querySelector(".btn-search").addEventListener("click", function () {
            search();
        });
        // Enter 키 이벤트 리스너 추가
        document.getElementById("searchWord").addEventListener("keyup", function (event) {
            // keyCode 13은 Enter 키를 나타냅니다
            if (event.keyCode === 13) {
                search();
            }
        });

        //리다이렉트로 받아온 값을 재설정(ajax쓸껄...)
        //키워드 유지
        document.getElementById("searchWord").value = "<%=request.getAttribute("searchWord")%>";
        
        //옵션값 유지
        let search_opt_list_values = document.getElementById("search_opt_list").options;
        for(let i = 0; i< search_opt_list_values.length;i++){   
            if(search_opt_list_values[i].value == '<%=request.getAttribute("item")%>') {
            	search_opt_list_values.selectedIndex = i;
                break;
            }
        }
        //옵션값 유지
        let result_filter1_values = document.getElementById("result_filter1").options;
        for(let i = 0; i< result_filter1_values.length;i++){   
            if(result_filter1_values[i].value == '<%=request.getAttribute("perPage")%>') {
            	result_filter1_values.selectedIndex = i;
                break;
            }
        }

        //옵션값 유지
        let result_filter2_values = document.getElementById("result_filter2").options;
        for(let i = 0; i< result_filter2_values.length;i++){   
            if(result_filter2_values[i].value == '<%=request.getAttribute("okywd")%>') {
            	result_filter2_values.selectedIndex = i;
                break;
            }
        }
        
        // 서버에서 전달받은 libraryIds 값
		<%String[] libraryIds = (String[]) request.getAttribute("libraryIds");%>
    	 let libraryIds = <%=Arrays.toString(libraryIds)%>; // libraryIds 값을 자바스크립트 변수로 변환
    	 libraryIds = libraryIds.map(String); // 모든 원소를 문자열로 변환
        let libs_filter_values = document.querySelectorAll('#_multiChk1 input[type="checkbox"]');
    	 	 
        for(let i = 0; i < libs_filter_values.length;i++)
        {
            if(libraryIds.includes(libs_filter_values[i].value)) {//포함되어있으면 체크!
                libs_filter_values[i].click();
            }            
        }

    })

    //모두 체크기능
    function selboxAllChecked(id) {
        let allCheck = document.getElementById(id);
        // var selbox = document.getElementsByClassName("selbox")[0];
        // var checkboxes = selbox.querySelectorAll('.selbox input[type="checkbox"]');
        let checkboxes = document.querySelectorAll('.selbox input[type="checkbox"]');

        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = allCheck.checked;
        }
    }

    function openBookDetail(b_id) {
        // 쿼리 문자열을 URL에 추가하여 새 창을 열기
        window.open("book_detail?id="+ b_id + "&si_id=<%=request.getAttribute("si_id")%>" ,"", "width=900,height=600");
    }


    //예약기능
	function reservation(b_id) {
    	//현재 세션에서 아이디 값 가져오기
		 var login_m_pid = "<%=(String) request.getSession().getAttribute("m_pid")%>";
// 	    alert(b_id + " 예약되었습니다.");
	    let url = '/carpedm/book_search';
	    let data = 'b_id=' + encodeURIComponent(b_id)+'&m_pid=' + encodeURIComponent(login_m_pid);
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
// 	    	console.log(data);
    	  // 서버에서 전달한 결과 메시지에 따라 분기처리
    	  if (data.message === 'success') {
    	    alert(' 예약되었습니다.');
    	    search();  // fetch가 완료된 후에 search 함수를 실행
    	  } else if (data.message === 'fail') {
    	    alert('비로그인상태입니다. 로그인해주세요.');
    	    window.location.href = "/carpedm/sign_in";
    	  } else {
    	    alert('알 수 없는 오류가 발생하였습니다.');
    	  }	      
	    })
	    .catch((error) => console.error('Error:', error));   
	}

	function search() {
	    let textbox = document.getElementById("searchWord");
	    let selectbox = document.getElementById("search_opt_list");
	    let rf_box1 = document.getElementById("result_filter1");
	    let rf_box2 = document.getElementById("result_filter2");

	    let checkboxes = document.querySelectorAll('input[name="libraryIds"]:checked');
	    let libraryIdsParam = '';
	    for (let i = 0; i < checkboxes.length; i++) {
	        libraryIdsParam += '&libraryIds='+ checkboxes[i].value;
	    }
// 	    let currentPage = document.querySelector('#paging .paging a.num.active').textContent;
	    let currentPage = "1";

	    window.location.href = '/carpedm/book_search?search=' + encodeURIComponent(textbox.value)
	    + '&item=' + selectbox.value
	    + '&page=' + currentPage
	    + '&perPage=' + rf_box1.value
	    + '&okywd=' + rf_box2.value
	    + libraryIdsParam;
	};
	
	function search2(currentPage) {
	    let textbox = document.getElementById("searchWord");
	    let selectbox = document.getElementById("search_opt_list");
	    let rf_box1 = document.getElementById("result_filter1");
	    let rf_box2 = document.getElementById("result_filter2");

	    let checkboxes = document.querySelectorAll('input[name="libraryIds"]:checked');
	    let libraryIdsParam = '';
	    for (let i = 0; i < checkboxes.length; i++) {
	        libraryIdsParam += '&libraryIds='+ checkboxes[i].value;
	    }

	    window.location.href = '/carpedm/book_search?search=' + encodeURIComponent(textbox.value)
	    + '&item=' + selectbox.value
	    + '&page=' + currentPage
	    + '&perPage=' + rf_box1.value
	    + '&okywd=' + rf_box2.value
	    + libraryIdsParam;
	};
</script>

<body>
	<header></header>
	<section>
		<!-- 여기부터 본문작성해주세요 -->
		<div id="headSearchForm">
			<fieldset class="selbox">
				<legend>도서관 필터</legend>
				<dl>
					<dd>
						<label class="all none"><input id="multiChk1"
							type="checkbox" name="multiChk1" value="1" title="전체선택"
							onclick="selboxAllChecked(this.id);"> 전체선택</label>
						<ul id="_multiChk1">
							<%
							ArrayList<Map<String, String>> libs_list = (ArrayList<Map<String, String>>) request.getAttribute("library_list");
							for (int i = 0; i < libs_list.size(); i++) {
							%>
							<li><input class="chk" type="checkbox" name="libraryIds"
								value="<%=libs_list.get(i).get("LB_ID")%>"> &nbsp;&nbsp;<%=libs_list.get(i).get("LB_NAME")%>
							</li>
							<%
							}
							%>
						</ul>
					</dd>
				</dl>
			</fieldset>
			<div class="search_div">
				<div class="allbox">
					<fieldset class="search_fieldset">
						<!-- <legend>통합검색</legend> -->
						<span class="result" style="display: none;"> <input
							type="checkbox" id="reSearch" name="reSearch" value="1"
							title="결과내 검색"> <label for="reSearch">&nbsp;&nbsp;결과
								내 검색</label>
						</span>
						<div class="search">
							<div class="input">
								<strong> <select name="item" id="search_opt_list">
										<option>전체</option>
										<option>제목</option>
										<option>저자</option>
										<option>출판사</option>
										<option>키워드</option>
										<option>ISBN</option>
								</select>
								</strong> <input type="text" name="word" autocomplete="off"
									id="searchWord" style="ime-mode: active"
									placeholder="검색어를 적어주세요">
								<!-- input type="hidden" name="item" value="title" id="item" -->
							</div>
							<input type="button" class="btn btn-search" value="도서검색">
						</div>
					</fieldset>
				</div>
			</div>
			<div class="pop_searchkeyword">
				<h3>인기 검색어 TOP 10</h3>
				<ul>
					<%
					ArrayList<Map<String, String>> pop_search_list = (ArrayList<Map<String, String>>) request
							.getAttribute("pop_search_list");
					for (int i = 0; i < pop_search_list.size(); i++) {
					%>
					<li><em><%=(i + 1)%></em> <span><a href="/carpedm/book_search?search=<%=pop_search_list.get(i).get("SI_KEYWORD")%>"><%=pop_search_list.get(i).get("SI_KEYWORD")%></a></span>
					</li>
					<%
					}
					%>
				</ul>
			</div>
			<div class="result_filter_div">
				<div class="blank_space total_count">
					전체 : 총&nbsp;<%=(String) request.getAttribute("book_count")%>&nbsp;권
				</div>
				<div class="result_text">검색결과</div>
				<div class="result_filter_all">
					<select class="result_filter" id="result_filter1"
						onchange="search()">
						<option value=10>10개씩</option>
						<option value=20>20개씩</option>
						<option value=30>30개씩</option>
						<option value=40>40개씩</option>
						<option value=50>50개씩</option>
					</select> &nbsp; <select class="result_filter" id="result_filter2"
						onchange="search()">
						<option>제목 오름차순</option>
						<option>제목 내림차순</option>
						<option>키워드 오름차순</option>
						<option>키워드 내림차순</option>
						<option>저자 오름차순</option>
						<option>저자 내림차순</option>
						<option>발행년도 오름차순</option>
						<option>발행년도 내림차순</option>
						<option>소장기관 오름차순</option>
						<option>소장기관 내림차순</option>
					</select>
				</div>
			</div>
		</div>

		<!-- 책 리스트 -->
		<div id="result_booklist">
			<%
			ArrayList<Map<String, String>> books = (ArrayList<Map<String, String>>) request.getAttribute("book_list");
			for (int i = 0; i < books.size(); i++) {
				Map<String, String> book = books.get(i);
				String loan_stat = "<li class=\"_fail\">대출불가</li>";
				String reservation_stat = "<li class=\"_fail\">예약불가</li>";
				if ("Y".equals(book.get("B_LOANSTATE"))) {
					loan_stat = "<li class=\"loan_success\">대출가능</li>";
				}
				if ("Y".equals(book.get("B_RESSTATE"))) {
					reservation_stat = "<li class=\"reservation_success\" onclick=\"reservation('" + book.get("B_ID")
					+ "')\">예약가능</li>";
				}
			%>
			<dl>
				<dt>
					<em class="label label-bk"><img
						src="<%=book.get("B_IMGURL")%>" alt="사진불러오기실패"></em>
				</dt>
				<dd>
					<div class="ico ico-bk">
						<a href="javascript:void(0);"
							onclick="openBookDetail('<%=book.get("B_ID")%>')"><%=book.get("B_TITLE")%></a>
					</div>
					<ul>
						<li class="label_no"><strong>키워드 : </strong> <%=book.get("B_KYWD")%></li>
						<li><strong>저자 : </strong> <%=book.get("B_AUTHOR")%> <em>|</em>
							<strong>발행처 : </strong> <%=book.get("B_PUBLISHER")%></li>
						<li><strong>발행년 : </strong> <%=book.get("B_PUBYEAR")%> <em>|</em>
							<strong>ISBN : </strong> <%=book.get("B_ISBN")%></li>
						<li class="so"><strong>소장기관 : </strong> <span class="blue"><%=book.get("LB_NAME")%></span>
						</li>
					</ul>
					<ol>
						<%=loan_stat%>
						<%=reservation_stat%>
					</ol>
				</dd>
			</dl>
			<%
			}
			%>
		</div>


		<div id="paging">
			<%
			// 서블릿에서 불러온 페이징 정보
			int total_count = Integer.parseInt((String) request.getAttribute("book_count"));// 임시로 설정한 값
			int perPage = Integer.parseInt((String) request.getAttribute("perPage"));
			int current_page = Integer.parseInt((String) request.getAttribute("page"));
			int total_pages = total_count > 0 ? (int) Math.ceil((double) total_count / perPage) : 1;

			// 표시할 페이지의 범위 계산
			int start_page = Math.max(current_page - 2, 1);
			int end_page = Math.min(start_page + 4, total_pages);
			start_page = Math.max(1, end_page - 4);
			%>

			<div class="total_count">
				전체 : 총&nbsp;<%=total_count%>&nbsp;권
			</div>
			<div class="total">
				<strong><%=current_page%></strong>페이지 / 총 <strong><%=total_pages%></strong>페이지
			</div>
			<div class="paging">
				<%
				if (current_page > 1) {
				%>
				<a href="javascript:void(0);"
					onclick="search2(<%=current_page - 1%>)" class="pre">◀</a>
				<%
				}
				%>
				<%
				for (int i = start_page; i <= end_page; i++) {
				%>
				<a href="javascript:void(0);" onclick="search2(<%=i%>)"
					class="<%=i == current_page ? "num active" : "num"%>"><%=i%></a>
				<%
				}
				%>
				<%
				if (current_page < total_pages) {
				%>
				<a href="javascript:void(0);"
					onclick="search2(<%=current_page + 1%>)" class="next">▶</a>
				<%
				}
				%>
			</div>
		</div>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header.js"></script>
</body>

</html>