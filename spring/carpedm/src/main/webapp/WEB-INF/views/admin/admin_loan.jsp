<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
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
<title>대출내역 popup</title>
<link href="/carpedm/resources/css/layout.css" rel="stylesheet">
</head>

<!-- function 스크립트 -->
<script src="/carpedm/resources/js/admin_library.js"></script>
<script>
	window.addEventListener("load", function() {
		bind();
	});

	function bind() {
<%-- <%ArrayList<Map<String, String>> data_list = (ArrayList<Map<String, String>>) request.getAttribute("loan");%> --%>

		// 전체선택 이벤트
		document.querySelector("#select_all").addEventListener("click",
				function(event) {
					let list_check = document.querySelectorAll(".checkbox");

					for (let i = 0; i < list_check.length; i++) {
						list_check[i].checked = event.target.checked;
					} // 데이터가 늘어나도 체크박스 적용됨
				})

		//폐기버튼 알림창 및 폐기 클릭 시 remove()
		// 연체해제버튼 클릭했을 때
		// 체크된 애들 querySelectorAll을 모두 가져와 변수에 담음
		document.getElementById('button_cancle').addEventListener(
				'click',
				function() {
					let list_checked = document
							.querySelectorAll(".checkbox:checked");

					if (list_checked.length == 0) {
						alert("폐기할 항목을 선택해주세요.");
					} else if (confirm("선택한 항목을 폐기하시겠습니까?")) {
						for (let i = 0; i < list_checked.length; i++) {
							// 체크된 걸 for문으로 찾고 체크박스의 부모(td)의 부모(tr)을 remove
							list_checked[i].parentNode.parentNode.remove();
						}
						alert("폐기되었습니다.");
					} else {
						alert("선택된 항목이 없습니다.");
					}
				});

	};

	// 닫기
	function closePopup() {
		window.open('', '_self', '');
		window.close();
	}
</script>

<style>
header .nav .book_in {
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

/* section */
section {
	font-family: "Wanted Sans Variable";
}

.add_table {
	border-collapse: collapse;
}

.add_table th {
	background-color: rgba(163, 163, 163, 0.6);
}

.add_table tr {
	background-color: #fff;
}

.add_table tr .bookname {
	width: 500px;
}

.add_table th, .add_table td {
	border: 1px solid black;
	height: 40px;
	text-align: center;
}

/*  등록 취소 버튼 (맨 아래) */
.input {
	border: 1px;
	width: 20%;
	margin: auto;
	text-align: center;
	margin-top: 20px;
}

.input .button {
	font-family: "Wanted Sans Variable";
	font-size: 18px;
	background-color: rgba(71, 125, 231, 0.973);
	border: 0;
	width: 80px;
	height: 30px;
	border-radius: 5px;
	color: white;
}

.input .button:hover {
	background-color: rgba(205, 155, 225, 0.6);
}
</style>

<body>
	<!-- header -->
	<header></header>

	<!-- section -->
	<section>
		<div>
			<h2 align="center">대출내역</h2>
		</div>

		<div>
			<table class="add_table" border="0" width="900px" align="center"
				cellpadding="5" cellspacing="1" bgcolor="cccccc" id="todo_booktable">
				<thead>
					<tr>
						<th>회원번호</th>
						<th>책제목</th>
						<th>저자</th>
						<th>소장기관</th>
						<th>대출일자</th>
						<th>반납일자</th>
						<th>연체일자</th>
						<th>연체상태</th>

					</tr>
				</thead>
				<tbody>
				    <c:forEach var="item" items="${list}">
				        <tr>
				        
				            <td>${item.m_pid}</td>
				            <td>${item.b_title}</td>
				            <td>${item.b_author}</td>
				            <td>${item.lb_name}</td>
				            <td>${fn:substring(item.l_loandate, 0, 10)}</td>
				            <td>${item.l_returnrealdate != null ? fn:substring(item.l_returnrealdate, 0, 10) : ""}</td>
				            <td>${item.b_resstate}</td>
				            <td>${item.b_loanstate}</td>
				        </tr>
				    </c:forEach>
				</tbody>
				
			</table>
		</div>
	</section>

	<!-- 등록 취소 -->
	<div class="input">
		<!--         <button type="button" value="연체해제" class="button" id="button_cancle" onclick="closeOverduePopup()">연체해제</button> -->
		<input type="reset" value="닫기" class="button" onclick="closePopup()">
	</div>



</body>

</html>