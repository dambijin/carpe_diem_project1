<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>관리자페이지 책등록 popup</title>
<link href="/carpedm/resources/css/layout.css" rel="stylesheet">
</head>

<!-- function 스크립트 -->
<script src="/carpedm/resources/js/admin_library.js"></script>
<script>
    window.addEventListener("load", function () {
        //등록일자에 현재날짜 넣기
//         var today = new Date(); // 현재 날짜와 시간을 가져옴
//         var dd = String(today.getDate()).padStart(2, '0'); // 일자를 2자리 문자열로 변환
//         var mm = String(today.getMonth() + 1).padStart(2, '0'); // 월을 2자리 문자열로 변환 (JavaScript의 월은 0부터 시작하므로 1을 더함)
//         var yyyy = today.getFullYear(); // 연도를 가져옴

//         today = yyyy + '-' + mm + '-' + dd; // 'YYYY-MM-DD' 형식의 문자열로 변환
//         document.querySelector('input[name="regi_date"]').value = today; // <input> 요소의 value 속성에 설정

        //isbn가져오기 버튼리스너
        document.querySelector("#isbn_import").addEventListener("click", function () {
            let isbn = document.querySelector('input[name="b_isbn"]').value;
            if (isbn.trim() == "") {
            	  alert("값을 입력해주세요.");
            }
            else{
           	    let data = 'isbn=' + encodeURIComponent(isbn);
           		//dopost로 보내기위한 코드
           	    fetch('/carpedm/isbn_import', {
           	      method: 'POST',
           	      headers: {
           	        'Content-Type': 'application/x-www-form-urlencoded',
           	      },
           	      body: data,
           	    })
           	    .then(response => response.json())
           	    .then(data => {
           	    	console.log(data);
	           	    document.querySelector('input[name="b_title"]').value = data.title;
	                document.querySelector('input[name="b_author"]').value = data.author;
	                document.querySelector('input[name="b_pubyear"]').value = data.b_date;
	                document.querySelector('input[name="b_publisher"]').value = data.publisher;
           	    })
           	    .catch((error) => console.error('Error:', error));   
            }
        });
        
        //qr_isbn가져오기 버튼리스너
        document.querySelector("#qr_isbn").addEventListener("click", function () {
           	    let data = 'isbn=' + encodeURIComponent(isbn);
           		//dopost로 보내기위한 코드
           	    fetch('/carpedm/qr_isbn', {
           	      method: 'POST',
           	      headers: {
           	        'Content-Type': 'application/x-www-form-urlencoded',
           	      },
           	      body: data,
           	    })
           	    .then(response => response.json())
           	    .then(data => {
           	    	console.log(data);
	           	  
           	    })
           	    .catch((error) => console.error('Error:', error));           
        });
    });
    
    
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

/* 책등록 글씨 */
.add_subject {
	font-family: 'Wanted Sans Variable';
}

/* 테이블 */
section .add_table {
	font-family: 'Wanted Sans Variable';
	border-collapse: collapse;
	margin: auto;
	width: 60%;
}

.add_table th {
	background-color: rgba(168, 156, 200, 0.6);
}

.add_table tr {
	background-color: #fff;
}

.add_table tr .bookname {
	width: 500px;
}

.add_table th, .add_table td {
	border: 1px solid black;
	height: 50px;
	padding: 3px;
}

/* 테이블 안에 인풋 */
.add_table input, .add_table select {
	font-family: "Wanted Sans Variable";
	font-size: 18px;
}

/*  등록 취소 버튼 (맨 아래) */
.input {
	border: 1px;
	width: 20%;
	margin: auto;
	text-align: center;
	margin-top: 20px;
}

/* 버튼 */
.input .button {
	font-family: "Wanted Sans Variable";
	font-size: 18px;
	background-color: rgba(155, 178, 225, 0.6);
	border: 0;
	width: 70px;
	height: 30px;
	border-radius: 5px;
	cursor: pointer;
}

.input .button:hover {
	background-color: rgba(205, 155, 225, 0.6);
}

/* 인풋 넘버 위아래 방향키 삭제 */
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
	-webkit-appearance: none;
	margin: 0;
}

/* 가져오기 버튼 */
.upload {
	font-family: "Wanted Sans Variable";
	font-size: 16px;
	background-color: rgba(155, 178, 225, 0.6);
	border: 0;
	width: 75px;
	height: 30px;
	border-radius: 5px;
	cursor: pointer;
}
.upload2 {
	font-family: "Wanted Sans Variable";
	font-size: 16px;
	background-color: rgba(155, 178, 225, 0.6);
	border: 0;
	width: 100px;
	height: 30px;
	border-radius: 5px;
	cursor: pointer;
}
.upload:hover {
	background-color: rgba(205, 155, 225, 0.6);
}
</style>

<body>
	<!-- header -->
	<header></header>

	<!-- section -->
	<form method="post" action="admin_book_add">
		<section>
			<div class="add_subject">
				<h2 align="center">책등록</h2>
			</div>
			<div>
				<table class="add_table">
					<tr>
						<th>ISBN</th>
						<td>
							<input type="number" name="b_isbn">
							<input type="button" value="QR가져오기" id="qr_isbn" class="upload2">
							<input type="button" value="가져오기" id="isbn_import" class="upload">
						</td>
					</tr>
					<tr>
						<th width="20%">책이름</th>
						<td width="80%"><input type="text" name="b_title"
							class="bookname" autofocus></td>
					</tr>
					<tr>
						<th>저자</th>
						<td><input type="text" name="b_author"></td>
					</tr>
					<tr>
						<th>출판사</th>
						<td><input type="text" name="b_publisher"></td>
					</tr>
					<tr>
						<th>발행년도</th>
						<td><input type="text" name="b_pubyear"></td>
					</tr>
<!-- 					<tr> -->
<!-- 						<th>도서ID</th> -->
<!-- 						<td><input type="number" name="regi_number"></td> -->
<!-- 					</tr> -->
					<tr>
						<th>키워드</th>
						<td><input type="text" name="b_kywd"></td>
					</tr>
					<tr>
						<th>이미지</th>
						<td><input type="text" name="b_imgurl"></td>
					</tr>
					<tr>
						<th>소장기관</th>
						<td>
						<select id="libs_info" name="lb_id">
							<c:forEach var="list" items="${list}">
							    <option value="${list.lb_id}">${list.lb_name}</option>
							</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<th>도서 장르</th>
						<td>
						<select id="book_genre" name="bg_id">
							<c:forEach var="genre" items="${genre}">
							    <option value="${genre.bg_id}">${genre.bg_name}</option>
							</c:forEach>
						</select>
						</td>
					</tr>
	<!-- 				<tr> -->
	<!-- 					<th>등록일자</th> -->
	<!-- 					<td><input type="b_date" name="regi_date"></td> -->
	<!-- 				</tr> -->
	
				</table>
			</div>
			<!-- 등록 취소 -->
			<div class="input">
				<input type="submit" value="책 등록" class="button" onclick="bookAdd()">
				<input type="reset" value="닫기" class="button" onclick="closePopup()">
			</div>
		</section>
	</form>

	
</body>

</html>