<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>관리자페이지(재고)</title>
	<link href="../css/layout.css" rel="stylesheet">
</head>

<!-- function 스크립트 -->
<script defer src="../js/admin_library.js"></script>
<script>
	// admin_book_list 등록 팝업창열기
	function bookadd_popup() {
		window.open
			("admin_book_add.html", "책 등록", "width=1000, height=800, left=100, top=100");
	}
	// todo 이벤트
	// 책 추가버튼 가져오기
	window.addEventListener("load", function () {
		bind();
	});

	function bind() {

		// 테이블을 todlist 에 담아둠
		let todolist = document.querySelector("#todo_booktable");

		for (let i = 1; i <= 10; i++) {
			let book_html = '';

			// html += '</tr>';
			// 추가한다
			book_html += '<td>' + i + '</td>';
			book_html += '<td>java초급</td>';
			book_html += '<td>최민수강사님</td>';
			book_html += '<td>2023-05-01</td>';
			book_html += '<td>12215</td>';
			book_html += '<td>천안도서관</td>';
			book_html += '<td>2024-02-02</td>';
			book_html += '<td>예약</td>';
			book_html += '<td>대출중</td>';
			book_html += '<td><input type="checkbox" name="check" class="checkbox"></td>';
			// html +=	'</tr>'

			let tr = document.createElement("tr"); // <tr></tr>
			tr.innerHTML = book_html;

			// 체크박스 전체선택 중 항목 체크해제시 전체선택 체크박스 해제
			tr.querySelector(".checkbox").addEventListener("click", function (event) {
				// 만약 현재 클릭된 체크박스가 체크 해제되었다면
				if (!event.target.checked) {
					// 전체선택 체크박스도 체크 해제
					document.querySelector("#select_all").checked = false;
				} else {
					// 전체 체크박스 개수와 현재 체크된 체크박스 개수를 세어서 비교
					let allCount = document.querySelectorAll(".checkbox").length;
					let checkedCount = document.querySelectorAll(".checkbox:checked").length;

					// 만약 모든 체크박스가 체크 되어있다면
					if (allCount == checkedCount) {
						// 전체선택 체크박스를 체크
						document.querySelector("#select_all").checked = true;
					} else {
						// else 전체선택 체크박스를 체크 해제
						document.querySelector("#select_all").checked = false;
					}
				}
			});

			// todolist에 tr을 추가함
			todolist.append(tr);

		}

		// 전체선택 체크 해제
		document.querySelector("#select_all").addEventListener("click", function (event) {
			// 클릭된 요소가 check 상태라면
			let list_check = document.querySelectorAll(".checkbox")
			console.log(list_check.length);

			// 체크된 것만
			if (event.target.checked) {
				// 모든 체크박스를 체크
				for (let i = 0; i < list_check.length; i++) {
					list_check[i].checked = true;
				}
			} else {
				// 모든 체크박스를 체크 해제
				for (let i = 0; i < list_check.length; i++) {
					list_check[i].checked = false;
				}
			}
		});


		//폐기버튼 알림창 및 폐기 클릭 시 remove()
		document.getElementById('button_cancle').addEventListener('click', function () {
			let list_checked = document.querySelectorAll(".checkbox:checked");


			if (list_checked.length == 0) {
				alert("폐기할 항목을 선택해주세요.");
			} else if (confirm("선택한 항목을 폐기하시겠습니까?")) {
				for (let i = 0; i < list_checked.length; i++) {
					list_checked[i].parentNode.parentNode.remove();
				}
				alert("폐기되었습니다.");
			} else {
				alert("선택된 항목이 없습니다.");
			}
		});

		// 검색에 입력값 받아와 enterkey 작동
		var inputTodo = document.getElementById("input_todo");
		if (inputTodo != null) {
			inputTodo.addEventListener("keydown", enterkey);
		};

		// 엔터 이벤트
		function enterkey() {
			if (window.event.keyCode == 13) {
				// 엔터키가 눌렸을 때 실행하는 반응
				search();
			}
		};

		//검색옵션 기본세팅
		let search_opt_list = ["책이름", "저자", "청구기호", "등록번호", "소장기관"];

		for (let i = 0; i < search_opt_list.length; i++) {
			let search_opt = document.querySelector("#search_option");
			let html = '';
			html += search_opt_list[i];

			let opt = document.createElement("option");//<option></option>
			opt.innerHTML = html;//<option>serach_opt_list[i]</option>

			search_opt.append(opt);
		}


	};


	// 10개씩 누를 때 change 이벤트 : 변동이 있을 때 발생
	// 페이징 구현 정하고 기능
	function changeViewCount(count) {
		alert(count + "개씩 보기로 변경되었습니다.");
	};

	// 검색기능
	function search() {
		var textbox = document.getElementById("input_todo");
		if (textbox != null) {
			alert(textbox.value + " ");
		}
	};


	//나중에 쓸 수 있는 코드	
	// 검색 옵션과 검색 텍스트박스에 입력된 값을 처리하는 함수
	function handleSearchOption() {
		// 검색 옵션 가져옴
		var searchOption = document.getElementById("search_option").value;
		// 검색 텍스트박스 입력값을 가져옴
		var searchTextbox = document.getElementById("input_todo");

		// 선택된 옵션에 따라 다르게 동작
		switch (searchOption) {
			case "책이름":
				alert("책이름: " + searchTextbox.value);
				break;
			case "청구기호":
				alert("청구기호: " + searchTextbox.value);
				break;
			case "등록번호":
				alert("등록번호: " + searchTextbox.value);
				break;
			default:
				// 기타 옵션의 경우 아무 동작도 수행하지 않음
				break;
		}
	}


</script>

<style>
	/* 전체 */
	.booklist_entire {
		font-family: "Wanted Sans Variable";
	}

	header .blank_space .nav .book_inventory {
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

	/*  검색창 div*/
	.search {
		font-family: "Wanted Sans Variable";
		width: 80%;
		margin: auto;
		text-align: center;
		margin-bottom: 10px;
		font-family: "Wanted Sans Variable";

	}

	.search .search_opt_list {
		font-family: "Wanted Sans Variable";
		width: 80px;
		height: 30px;
	}

	.search .view_count {
		width: 80px;
		height: 30px;
	}

	/* 검색창 인풋 */
	.search .textbox {
		width: 350px;
		height: 22px;
		padding: 2px;
		font-family: "Wanted Sans Variable";
		font-size: 17px;
	}

	/* 테이블 */
	.booklist_entire table {
		border-collapse: collapse;
	}

	/* 테이블 tr*/
	.booklist_entire table tr {
		background-color: #fff;
	}

	/* 테이블 th */
	.booklist_entire table th {
		background-color: rgba(163, 163, 163, 0.6);
		border: 1px solid #000000;
		height: 35px;
	}

	/* 테이블 td */
	.booklist_entire td {
		border: 1px solid #000000;
		text-align: center;
	}

	/* select 셀렉트 */
	.booklist_entire select {
		font-family: "Wanted Sans Variable";
		font-size: 17px;
	}

	/* 등록삭제 버튼 */
	.input1 {
		border: 1px;
		width: 20%;
		margin: auto;
		text-align: center;
		margin-top: 20px;
	}

	/* 검색 버튼, 등록 폐기 버튼 */
	.search .button,
	.input1 .button {
		font-family: "Wanted Sans Variable";
		font-size: 18px;
		width: 50px;
		height: 30px;
		border: 0;
		border-radius: 5px;
		cursor: pointer;
		color: white;
	}

	/* 검색 버튼 */
	.search .button {
		font-family: "Wanted Sans Variable";
		background-color: rgb(36, 116, 190);
	}

	/* 등록 폐기 버튼 */
	.input1 .button {
		font-family: "Wanted Sans Variable";
		background-color: rgba(71, 125, 231, 0.973);

	}


	/* 쪽이동 */
	.nextpage {
		border: 1px;
		width: 20%;
		margin: auto;
		text-align: center;
		margin-top: 20px;
	}


	/* 쪽이동 a 태그*/
	.underline_remove {
		text-decoration: none;
		font-size: 20px;
		color: black;
	}

	h1 {
		font-family: "Wanted Sans Variable";
	}
</style>

<body>
	<!-- header -->
	<header></header>

	<div>
		<h1 align="center">책 재고</h1>
	</div>

	<div class="booklist_entire">
		<!-- 검색창   -->
		<div class="search">
			<select class="view_count" onchange="changeViewCount(this.value)">
				<option value="10">10개씩</option>
				<option value="20">20개씩</option>
				<option value="30">30개씩</option>
			</select>
			<!-- <select class="search_opt_list" id="search_option" onchange="handleSearchOption()"> -->
			<select class="search_opt_list" id="search_option">
				<!-- 자바스크립트화 -->
			</select>
			<input type="text" name="search" class="textbox" id="input_todo">
			<button type="button" class="button" id="todo_search" onclick="search()">검색
			</button>
		</div>


		<!-- table -->
		<form name="myform" method="post" action="">
			<div class="detail_tabel">
				<table border="0" width="1200" align="center" cellpadding="5" cellspacing="1" bgcolor="cccccc"
					id="todo_booktable">
					<tr>
						<th width="100">등록번호</th>
						<th width="100">책이름</th>
						<th width="100">저자</th>
						<th width="100">발행년</th>
						<th width="100">청구기호</th>
						<th width="100">소장기관</th>
						<th width="100">등록날짜</th>
						<th width="100">예약</th>
						<th width="100">대출상태</th>
						<th width="100">
							도서폐기
							<input type="checkbox" id="select_all">
						</th>
					</tr>
				</table>
		</form>

		<!-- 등록 삭제 -->
		<div class="input1">
			<button type="button" class="button" onclick="bookadd_popup()">등록</button>
			<button type="button" class="button" id="button_cancle">폐기</button>
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

	</div>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="../js/header_admin.js"></script>

</body>

</html>