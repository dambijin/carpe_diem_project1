<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호찾기</title>
<!--     <link href="/carpedm/css/layout.css" rel="stylesheet"> -->
</head>
<script>
	function btn(sample) {
		alert(sample)
	}
	window.addEventListener("load", function() {
		email();

	})
	// 이메일 선택시 따라붙기
	function email() {
		let email_domain = document.querySelector("#email_domain")
		let email2 = document.querySelector("#text_email2")
		let name = document.querySelector("#name")

		email_domain.addEventListener(
						"change",
						function() {
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
						})
	}

	window.onload = function() {
		let button1 = document.getElementById('complete1')

		button1.addEventListener('click',function() {
							let userid = document.querySelector('#id').value;
							let username = document.querySelector('#name').value;
							let useremail1 = document
									.querySelector('#text_email1').value;
							let useremail2 = document
									.querySelector('#text_email2').value;
// 							let userCertification = document
// 									.querySelector('#Certification').value;

							if (userid == "") {
								alert("아이디를 입력해주세요.");
								document.querySelector('#id').focus();
							} else if (username == "") {
								alert("이름을 입력해주세요.");
								document.querySelector('#name').focus();
							} else if (useremail1 == "") {
								alert("이메일을 입력해주세요.");
								document.querySelector('#text_email1').focus();
							} else if (useremail2 == "") {
								alert("이메일주소를 입력해주세요.");
								document.querySelector('#text_email2').focus();
								/*  } else if (userCertification == "") {
								    alert("인증번호를 입력해주세요.")
								    document.querySelector('#Certification').focus(); */
							} else {
								// 조회
								let useremail = useremail1 + "@" + useremail2;
				                fetch('/carpedm/find_pw', {
				                    method: 'POST',
				                    headers: {
				                    	'Content-Type': 'application/x-www-form-urlencoded'
				                    },
				                    body: `userid=${"${encodeURIComponent(userid)}"}&username=${"${encodeURIComponent(username)}"}&useremail=${"${encodeURIComponent(useremail)}"}`
				                })
				                .then(response => response.json())
				                .then(data => {
				                    if(data.message === "fail") { // 'equal' 메소드 대신 '===' 연산자 사용, 구문 수정
				                        alert("비밀번호찾기에 실패했습니다.");
				                    } else {
				                        alert("해당 비밀번호는 "+data.message+"입니다."); // 올바른 닫는 괄호 위치
				                        location.href="/carpedm/main";
				                    }
				                });

							}
						})
// 		let textbox1 = document.getElementById("Certification");
// 		// Enter 키 이벤트 리스너 추가
// 		textbox1.addEventListener("keydown", function(event) {
// 			// keyCode 13은 Enter 키를 나타냅니다
// 			if (event.keyCode === 13) {
// 				button.click();
// 			}
// 		});
	}
</script>
<style>
    /* 헤더 아래 내용 */
    .section_find_pw {
        width: 80%;
        text-align: center;
        margin: auto;
    }

    /* 비밀번호 찾기 제목 */
    .section_find_pw .find_pw {
        font-family: "Wanted Sans Variable";
        font-size: 30px;
        margin-bottom: 15px;
    }

    /* 테이블 div */
    .section_find_pw .idt {
        border: 2px solid rgba(168, 156, 200, 0.5);
        border-radius: 20px;
        width: 600px;
        margin: auto;
        text-align: center;
        font-family: "Wanted Sans Variable";
    }

    /* 테이블 */
    .section_find_pw .idt table {
        width: 80%;
        margin: auto;
        margin-top: 15px;
    }

    /* 테이블 왼쪽 정렬 */
    .section_find_pw .idt tr {
        text-align: left;
    }

    /* 아이디, 이름, 비밀번호, 인증번호 td */
    .section_find_pw .idt .sub {
        background-color: rgba(168, 156, 200, 0.6);
        text-align: center;
        width: 20%;
    }

    /* 테이블 tr */
    .section_find_pw .idt tr {
        height: 30px;
    }

    /* 테이블 input */
    .section_find_pw .idt input,
    .section_find_pw .idt select {
        height: 25px;
        font-size: 15px;
        font-family: "Wanted Sans Variable";
    }

    /* 이메일 input */
    .section_find_pw .idt .email {
        width: 20%;
    }


    /* 확인버튼 */
    .complete_but .complete {
        font-family: "Wanted Sans Variable";
        font-size: 19px;
        background-color: rgba(155, 178, 225, 0.6);
        width: 100px;
        height: 30px;
        border: 0;
        border-radius: 5px;
        margin-top: 15px;
        margin-bottom: 15px;
    }

    .complete_but .complete:hover {
        background-color: rgba(205, 155, 225, 0.6);
    }
</style>

<body>
    <!-- header -->
    <header></header>

    <!-- section -->
    <section>
        <div class="section_find_pw">
            <div class="find_pw">
                비밀번호찾기
            </div>
            <div class="idt">
                <table>
                    <tr>
                        <td class="sub">아이디</td>
                        <td>
                            <input type="text" name="userid" id="id" placeholder="아이디를 입력해주세요." autofocus>
                        </td>
                    </tr>
                    <tr>
                        <td class="sub">이름</td>
                        <td>
                            <input type="text" name="name" id="name" placeholder="이름을 입력해주세요.">
                        </td>
                    </tr>
                    <tr>
                        <td class="sub">이메일</td>
                        <td>
                            <input type="text" placeholder="이메일을 입력해주세요." class="email" id="text_email1"> @
                            <input type="text" class="email" id="text_email2" placeholder="직접입력">
	  						<select	id="email_domain" value="직접입력">
								<c:forEach var="email" items="${emailList}">
									<option value="${email}">${email}</option>
								</c:forEach>
							</select>
<!--                             <input type="button" value="인증번호 발송" onclick="javascript:btn('인증번호가 발송되었습니다.' )" -->
<!--                             class="verification_code" id="send"> -->
                        </td>
                    </tr>
                    <tr>
<!--                         <td class="sub">인증번호</td> -->
<!--                         <td> -->
<!--                             <input type="text" id="Certification" placeholder="인증번호를 입력해주세요."> -->
<!--                         </td> -->
                    </tr>
                </table>
                <div class="complete_but">
                    <button type="button" class="complete" id="complete1">확인</button>
                </div>

            </div>

        </div>
    </section>

    <!-- 확인 -->


    <!-- 헤더를 덮어씌우는 자바스크립트 -->
<!--     <script src="/carpedm_old/js/header.js"></script> -->
</body>

</html>