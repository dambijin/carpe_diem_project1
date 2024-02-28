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
	width: 100px;
	height: 100px;
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

#paging .paging strong {
	background-color: #007bff;
	color: #fff;
}
</style>
</head>

<script>
    window.addEventListener("load", function () {

        //필터 기본세팅
//         let libs_list = ["아우내도서관", "성거도서관", "두정도서관", "도솔도서관"];
//         도서관세팅
<%-- 		let libs_list = <%=DBConn.getlibraryNameAll()%>; --%>
//         for (let i = 0; i < libs_list.length; i++) {
//             let libs_chk_list = document.querySelector("#_multiChk1");
//             let html = '';
//             html += '<input class="chk" type="checkbox">&nbsp;&nbsp;' + libs_list[i];

//             let li = document.createElement("li"); // <li></li>
//             li.innerHTML = html;

//             li.querySelector(".chk")
//                 .addEventListener("click", function (event) {
//                     let allCount = document.querySelectorAll(".chk").length;
//                     let checkedCount = document.querySelectorAll(".chk:checked").length;
//                     if (allCount == checkedCount) {
//                         document.querySelector("#multiChk1").checked = true;
//                     } else {
//                         document.querySelector("#multiChk1").checked = false;
//                     }
//                 })

//             libs_chk_list.append(li);
//         }
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

        // 결과옵션 기본세팅
        let result_filter_list = ["10", "20", "30", "40", "50"];

        for (let i = 0; i < result_filter_list.length; i++) {
            let result_filter1 = document.querySelector("#result_filter1");
            let html = '';
            html += result_filter_list[i] + "개씩";

            let opt = document.createElement("option");
            opt.value = result_filter_list[i];  // value 속성 설정

            opt.innerHTML = html;

            result_filter1.append(opt);
        }

        // 결과옵션2 기본세팅
        let result_filter_list2 = ["제목 오름차순", "제목 내림차순", "발행년 오름차순", "발행년 내림차순"];

        for (let i = 0; i < result_filter_list2.length; i++) {
            let result_filter1 = document.querySelector("#result_filter2");
            let html = '';
            html += result_filter_list2[i];

            let opt = document.createElement("option");
            opt.innerHTML = html;

            result_filter2.append(opt);
        }
        
        paging();
    })

    //모두 체크기능
    function selboxAllChecked(id) {
        var allCheck = document.getElementById(id);
        // var selbox = document.getElementsByClassName("selbox")[0];
        // var checkboxes = selbox.querySelectorAll('.selbox input[type="checkbox"]');
        var checkboxes = document.querySelectorAll('.selbox input[type="checkbox"]');

        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = allCheck.checked;
        }
    }

    function openBookDetail(b_id) {
        // book 객체의 모든 속성을 쿼리 문자열로 변환
//         let book = JSON.parse(decodeURIComponent(bookString));
//         var queryString = Object.keys(book).map(function (key) {
//             return encodeURIComponent(key) + '=' + encodeURIComponent(book[key]);
//         }).join('&');

//         // 쿼리 문자열을 URL에 추가하여 새 창을 열기
        window.open('book_detail?id='+b_id ,"", "width=900,height=600");
    }

    //검색결과 총 개수 표기 및 페이징관련
    function paging() {
        let total_count = 521;  //임시

        let result_filter1 = document.querySelector("#result_filter1");
        let view_count = result_filter1.options[result_filter1.selectedIndex].value;

        document.querySelector(".total_count").innerHTML = "전체 : 총&nbsp;" + total_count + "&nbsp권";
        html = `
                    <div class="total"><strong>1</strong>페이지 / 총 <strong>${Math.ceil(total_count / view_count)}</strong>페이지</div>
                        <div class="paging">
                            <a href="" class="pre">
                                ◀</a>

                            <strong>1</strong>

                            <a href="" class="num">2</a>

                            <a href="" class="num">3</a>

                            <a href="" class="num">4</a>

                            <a href="" class="num">5</a>


                            <a href="" class="next">▶</a>
                        </div>        
`
        document.querySelector("#paging").innerHTML = html;
    }

    //예약기능
    function reservation(RN) {
        alert(RN + " 예약되었습니다.");
    }

	function search() {
		let textbox = document.getElementById("searchWord");
		let selectbox = document.getElementById("search_opt_list");
		if (textbox.value == "") {
			alert("내용을 입력해주세요");
			document.querySelector('#searchWord').focus();
		} else {
			alert(textbox.value + "을 검색했습니다");
			window.location.href = '/carpedm/book_search?search=' + encodeURIComponent(textbox.value)+ '&item=' + selectbox.value;
		}
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
							<li><input class="chk" type="checkbox"
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
						<span class="result"> <input type="checkbox" id="reSearch"
							name="reSearch" value="1" title="결과내 검색"> <label
							for="reSearch">&nbsp;&nbsp;결과 내 검색</label>
						</span>
						<div class="search">
							<div class="input">
								<strong> <select name="item" id="search_opt_list">
										<option>전체</option>
										<option>제목</option>
										<option>저자</option>
										<option>출판사</option>
										<option>키워드</option>
								</select>
								</strong> <input type="text" name="word" autocomplete="off"
									id="searchWord" style="ime-mode: active"
									placeholder="검색어를 적어주세요">
								<!-- input type="hidden" name="item" value="title" id="item" -->
							</div>
							<input type="submit" class="btn btn-search" value="도서검색">
						</div>
					</fieldset>
				</div>
			</div>
			<div class="result_filter_div">
				<div class="blank_space total_count">전체 수 :</div>
				<div class="result_text">검색결과</div>
				<div class="result_filter_all">
					<select class="result_filter" id="result_filter1"
						onchange="paging()">
						<!-- 자바스크립트로 검색결과옵션가져오기 -->
					</select> &nbsp; <select class="result_filter" id="result_filter2">
						<!-- 자바스크립트로 검색결과옵션2가져오기 -->
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
						<li class="label_no"><strong>주제 : </strong> <%=book.get("B_KYWD")%></li>
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
			<!-- 자바스크립트로 페이지 가져오기 및 갱신 -->
		</div>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header.js"></script>
</body>

</html>