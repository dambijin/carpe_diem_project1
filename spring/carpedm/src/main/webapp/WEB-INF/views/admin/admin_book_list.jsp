<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>관리자페이지(재고)</title>
	<link href=" /carpedm/resources/css/layout.css" rel="stylesheet">
</head>

<!-- function 스크립트 -->
<script defer src="/carpedm/resources/js/admin_library.js"></script>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
<script>
	// admin_book_list 등록 팝업창열기
	function bookadd_popup() {
		window.open
			("/carpedm/admin_book_add", "책 등록", "width=1000, height=800, left=100, top=100");
	}
	// todo 이벤트
	// 책 추가버튼 가져오기
	window.addEventListener("load", function () {
		bind();

	});

	function bind() {
		// 전체선택 체크 해제
		document.querySelector("#select_all").addEventListener("click", function (event) {
			// 클릭된 요소가 check 상태라면
			let list_check = document.querySelectorAll(".checkbox_book")
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
		
		// 각 체크박스에 대한 이벤트 리스너 등록
		let checkboxes = document.querySelectorAll(".checkbox_book");
		for (let i = 0; i < checkboxes.length; i++) {
		    checkboxes[i].addEventListener("click", function (event) {
		        // 만약 현재 클릭된 체크박스가 체크 해제되었다면
		        if (!event.target.checked) {
		            // 전체선택 체크박스도 체크 해제
		            document.querySelector("#select_all").checked = false;
		        } else {
		            // 전체 체크박스 개수와 현재 체크된 체크박스 개수를 세어서 비교
		            let allCount = checkboxes.length;
		            let checkedCount = document.querySelectorAll(".checkbox_book:checked").length;

		            // 만약 모든 체크박스가 체크 되어있다면
		            if (allCount == checkedCount) {
		                // 전체선택 체크박스를 체크
		                document.querySelector("#select_all").checked = true;
		            } else {
		                // 그렇지 않다면 전체선택 체크박스를 체크 해제
		                document.querySelector("#select_all").checked = false;
		            }
		        }
		    });
		}

		// 폐기버튼 알림창 및 폐기
		document.getElementById('button_cancle').addEventListener('click', function () {
		    let list_checked = document.querySelectorAll(".checkbox_book:checked");
		    
		    // 선택된 체크박스가 있는지 확인
		    if (list_checked.length === 0) {
		        alert("폐기할 항목을 선택해주세요.");
		        return;
		    }
		    
		    var b_id = [];
		    
		    for (let i = 0; i < list_checked.length; i++) {
		        let id = list_checked[i].value;
		        b_id.push(id); // 배열에 각 id를 추가합니다.
		    }
		    
		    console.log(b_id);
		    
		    let url = '/carpedm/admin_book_list';
		    let data = 'b_id=' + encodeURIComponent(b_id);
//	 	    console.log(data);
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
//	 	    	console.log(data);
	    	  // 서버에서 전달한 결과 메시지에 따라 분기처리
	    	  if (data.message === 'success') {
	    	    alert('폐기완료');
	    	    window.location.href = "/carpedm/admin_book_list";
	    	  } else if (data.message === 'fail') {
	    	    alert('폐기실패');
	    	  } else {
	    	    alert('알 수 없는 오류가 발생하였습니다.');
	    	  }	      
		    })
		    .catch((error) => console.error('Error:', error));   

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
		let search_opt_list = ["전체", "책이름", "저자", "출판사", "ISBN", "소장기관"];

		for (let i = 0; i < search_opt_list.length; i++) {
			let search_opt = document.querySelector("#search_option");
			let html = '';
			html += search_opt_list[i];

			let opt = document.createElement("option");//<option></option>
			opt.innerHTML = html;//<option>serach_opt_list[i]</option>

			search_opt.append(opt);
		}

		
		let m_names = document.querySelectorAll("#m_name")
		console.log("m_names : "+ m_names)
		for(let i=0; i<m_names.length; i++){
			m_names[i].addEventListener("click", (event) => {
				// event.target.parentNode : 부모 즉,<td>를 뜻함
				event.target.parentNode.querySelector("form").submit();
			})
		}
		
		document.querySelector("#b_pid").addEventListener("click", function() {
			// form id 값 잡아옴 			
			let frm = document.querySelector("#frm");
			frm.querySelector("[name=orderColumn]").value = "b_pid";
			
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
		
	} // bind() 함수 종료
	

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
// 	function handleSearchOption() {
// 		// 검색 옵션 가져옴
// 		var searchOption = document.getElementById("search_option").value;
// 		// 검색 텍스트박스 입력값을 가져옴
// 		var searchTextbox = document.getElementById("input_todo");

// 		// 선택된 옵션에 따라 다르게 동작
// 		switch (searchOption) {
// 			case "책이름":
// 				alert("책이름: " + searchTextbox.value);
// 				break;
// 			case "청구기호":
// 				alert("청구기호: " + searchTextbox.value);
// 				break;
// 			case "등록번호":
// 				alert("등록번호: " + searchTextbox.value);
// 				break;
// 			default:
// 				// 기타 옵션의 경우 아무 동작도 수행하지 않음
// 				break;
// 		}
// 	};
	
	
	function openBookPopup(b_id){
		window.open
		("/carpedm/book_detail?id="+b_id, "팝업", "width=1000, height=700, left=100, top=100");
	}

</script>

<style>
	.flex-header{
		cursor: pointer;
	}
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
	#todo_booktable {
		margin:auto;
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
		margin-bottom : 20px;
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


	/* 이름 링크 */
	.book_name {
		color: blue;
		font-family: bold;
		text-decoration: underline;
		font-weight: bold;
		cursor: pointer;
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
	/* 페이지 */
#paging {
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-top: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
	height: 50px;
	width:85%;
	margin:auto;
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
				<input type="text" name="keyword" class="textbox" id="input_todo" 
						value="" ${keyword }>
<!-- 					<input type="text" name="search" class="textbox" id="input_todo"> -->
				<input type=submit class="button" value="검색" onclick="search()" >
			</div>
		
		<!-- 정렬용 필드 --> 
		<input type="hidden" name="orderColumn" value="${orderColumn }">
		<input type="hidden" name="orderType" value="${orderType }">

	<!-- table -->
<!-- 	<form method="post" action="admin_book_list"> -->
		<div class="booklist_entire">
			<table id="todo_booktable">
				<thead>
					<tr>
<!-- 					<th width="50px">순번</th> -->
						<th width="*">책번호</th>
						<th width="*">책이름</th>
						<th width="100">저자</th>
						<th width="100">출판사</th>
						<th width="100">ISBN</th>
						<th width="100">소장기관</th>
						<th width="100">등록날짜</th>
						<th width="50px">예약</th>
						<th width="50px">대출상태</th>
						<th width="80px">
							도서폐기
							<input type="checkbox" id="select_all">
						</th>
					</tr>
				</thead>
				<tbody id="memberListBody">                
				<!-- empty: 비어있다/ 사이즈가0이거나 NULL일 때	 -->
				<c:if test="${not empty book_list }">
					<c:forEach var="dto" items="${book_list }" varStatus="status">
						<tr>
							<td>${dto.b_id}<input type="hidden" value="${b_id}"></td>
							<td><div class="book_name" onclick="openBookPopup('${dto.b_id}')">${dto.b_title}</div></td>
							<td>${dto.b_author}</td>
			                <td>${dto.b_publisher}</td>
			                <td>${dto.b_isbn}</td>
			                <td>${dto.lb_name}</td>
<%-- 			                <td>${dto.b_date}</td> --%>
<%-- 			                <td>${dto.b_date.substring(0,10)}</td> --%>
 							<td>${formattedDate}</td>
			                <td>${dto.b_resstate}</td>
			                <td>${dto.b_loanstate}</td>
							<td><input type="checkbox" name="check" class="checkbox_book" value="${not empty dto ? dto.b_id : ''}"></td>
						</tr>
					</c:forEach>
				</c:if>
				<!-- 비어있는 리스트라면 -->
				<c:if test="${empty book_list}">
					<tr>
						<td colspan="11">조회할 내용이 없습니다</td>
					</tr>
				</c:if>
				</tbody>
				</table>
<!-- 				<input type="hidden" style="display: none;" name="b_id" -->
<%-- 							value="<%=data_list.get(0).get("b_id")%>"> --%>
<!-- 				<input type="hidden" style="display: none;" name="b_id" -->
<%--        				value="${not empty book_list ? dto.b_id : ''}"> --%>
<!-- 등록 삭제 -->
		<div class="input1">
			<button type="button" class="button" onclick="bookadd_popup()">등록</button>
			<button type="button" class="button" id="button_cancle" name="delete">폐기</button>
<!-- 			<button type="submit" class="button" id="button_cancle" name="delete">폐기</button> -->
		</div>
		
<!-- 	</form> -->

		

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
			&nbsp;권
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
		</div>
		</div>
		

</body>

</html>