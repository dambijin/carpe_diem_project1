<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>이메일로 아이디 찾기</title>

<style>
.email_find {
	width: 80%;
	text-align: center;
	margin: auto;
}

.abc {
	text-align: center;
}

.email_find .main {
	font-family: "Wanted Sans Variable";
	font-size: 30px;
	margin-bottom: 15px;
}

.email_find .search {
	border: 2px solid rgba(168, 156, 200, 0.5);
	border-radius: 20px;
	width: 600px;
	margin: auto;
	height: 220px;
	text-align: center;
	font-family: "Wanted Sans Variable";
}

.search_title {
	background-color: rgba(155, 178, 225, 0.6);
	color: #000000;
	line-height: 40px;
	display: inline-block;
	border-radius: 5px;
	width: 160px;
	font-size: 18px;
	margin-top: 10px;
	text-align: center;
	font-family: "Wanted Sans Variable";
	font-weight: bold;
}

.search_text {
	width: 70%;
	text-align: center;
	margin: auto;
	margin-top: 20px;
}

.search_text tr .sub {
	text-align: center;
	background-color: rgba(168, 156, 200, 0.6);
	width: 35%;
}

.search_text tr th {
	background-color: rgba(168, 156, 200, 0.6);
	text-align: center;
}

input {
	font-family: "Wanted Sans Variable";
}

.search_text .text_name {
	width: 95%; /* 변경된 부분 */
	height: 25px;
	font-size: 15px;
	font-family: "Wanted Sans Variable";
}

.search_text #text_email1, .search_text #text_email2 {
	width: 70px;
	height: 25px;
	font-size: 15px;
}

.search_text tr .sub {
	text-align: center;
	width: 30%;
	background-color: rgba(168, 156, 200, 0.6);
}

.search_text th {
	background-color: rgb(212, 212, 212);
}

.btnSearch {
	border: 1px;
	width: 10%;
	margin: auto;
	text-align: center;
	margin-top: 20px;
}

.btnSearch #button {
	font-family: "Wanted Sans Variable";
	font-size: 19px;
	background-color: rgba(155, 178, 225, 0.6);
	width: 100px;
	height: 30px;
	border: 0;
	border-radius: 5px;
	margin-bottom: 15px;
}

.btnSearch #button:hover {
	background-color: rgba(205, 155, 225, 0.6);
}

/* 이메일로찾기, 휴대폰번호로찾기 div */
.email_find .search .search_title:hover {
	background-color: rgba(140, 201, 240, 0.6);
}

/* 이메일 select */
#email_domain {
	height: 32px;
	font-family: "Wanted Sans Variable";
}
</style>
<script>

	window.onload = function() {

		email();

		let button = document.getElementById('button');

		// 이메일 선택시 따라붙기
		function email() {
			let email_domain = document.querySelector("#email_domain");
			let email2 = document.querySelector("#text_email2");
			let name = document.querySelector("#name");

			email_domain.addEventListener("change", function() {
				let selectedIndex = email_domain.selectedIndex;
				email2.value = email_domain.options[email_domain.selectedIndex].value; //동기화
				if (email_domain.selectedIndex > 0) //직접입력 차단
				{
					email2.readOnly = true;
				} else //직접입력 허용
				{
					email2.value = "";
					email2.readOnly = false;
				}
			});
		}

		button.addEventListener('click', function() {
			let username = document.querySelector('.text_name').value;
			let useremail1 = document.querySelector('#text_email1').value;
			let useremail2 = document.querySelector('#text_email2').value;

			if (username == "") {
				alert("이름을 입력해주세요.");
				document.querySelector('.text_name').focus();
				return;
			} else if (useremail1 == "") {
				alert("이메일를 입력해주세요.");
				document.querySelector('#text_email1').focus();
				return;
			} else if (useremail2 == "") {
				alert("이메일주소를 입력해주세요.");
				document.querySelector('#text_email2').focus();
			} else {
// 				console.log("이름:", username);
// 				console.log("이메일:", useremail1 + "@" + useremail2);
				  // 조회
				  let useremail = useremail1 + "@" + useremail2;
                fetch('/carpedm/find_id_email', {
                    method: 'POST',
                    headers: {
                    	'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: `username=${"${encodeURIComponent(username)}"}&useremail=${"${encodeURIComponent(useremail)}"}`
                })
                .then(response => response.json())
                .then(data => {
                    if(data.message === "fail") { // 'equal' 메소드 대신 '===' 연산자 사용, 구문 수정
                        alert("아이디찾기에 실패했습니다.");
                    } else {
                        alert("해당 아이디는 "+data.message+"입니다."); // 올바른 닫는 괄호 위치
                    }
                });
			}

			
			let textbox1 = document.getElementById("text_email1");
			// Enter 키 이벤트 리스너 추가
			textbox1.addEventListener("keydown", function(event) {
				// keyCode 13은 Enter 키를 나타냅니다
				if (event.keyCode === 13) {
					button.click();
				}
			});

			// 도서검색 버튼 엔터이벤트
			let textbox2 = document.getElementById("text_email2");
			// Enter 키 이벤트 리스너 추가
			textbox2.addEventListener("keydown", function(event) {
				// keyCode 13은 Enter 키를 나타냅니다
				if (event.keyCode === 13) {

					button.click();

				}
			});

		})
	};
</script>
</head>

<body>
	<header></header>
	<section>
		<div class="email_find">
			<div class="main">아이디 찾기</div>
			<div class="search">
				<div class='search_title' onclick="location.href='find_id_email'"
					style="cursor: pointer">이메일로 찾기</div>
				<div class="search_title" onclick="location.href='find_id_tel'"
					style="cursor: pointer">전화번호로 찾기</div>
				<table class="search_text" cellpadding="5" cellspacing="1">
					<tr>
						<td class="sub">이름</td>
						<td><input type="text" class="text_name"
							placeholder="이름을 입력해주세요." autofocus></td>
					</tr>
					<tr>
						<td class="sub">이메일</td>
						<td><input type="text" id="text_email1"
							placeholder="이메일을 입력해주세요."> @ <input type="text"
							id="text_email2" placeholder="직접입력"> 
							<select	id="email_domain" value="직접입력">
								<c:forEach var="email" items="${emailList}">
									<option value="${email}">${email}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<div class="btnSearch">
					<input type="button" name="enter" value="확인" id="button">
				</div>
			</div>
		</div>
	</section>
</body>

</html>