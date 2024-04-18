<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>

<%@ page import="java.util.Map"%>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>도서 관리 시스템</title>
<link href="/carpedm_old/css/layout.css" rel="stylesheet">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.10.2.min.js">
	
</script>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>


<script>
	window.addEventListener("load", function() {
		email();

	})
	// 이메일 선택시 따라붙기
	function email() {
		let email1 = document.querySelector("#email_domain1")
		let email2 = document.querySelector("#email_domain")
		let name = document.querySelector("#name")

		email1.addEventListener("change", function() {
			let selectedIndex = email1.selectedIndex;
			email2.value = email1.options[email1.selectedIndex].value; //동기화
			if (email1.selectedIndex > 0) //직접입력 차단
			{
				email2.readOnly = true;
			} else //직접입력 허용
			{
				email2.value = "";
				email2.readOnly = false;
			}
		})

		// 이메일 리스트 스크립트
		let email_list = [ "직접입력", "naver.com", "daum.net", "google.com" ];

		for (let i = 0; i < email_list.length; i++) {
			let result_email_list = document.querySelector("#email_domain1")
			let html = '';
			html += email_list[i];

			let opt = document.createElement("option");
			opt.innerHTML = html;

			result_email_list.append(opt)
		}

		// 본문 내용 스크립트화
		//             name.textContent = "박상민";
		//             birth.textContent = "1995.06.15";
		//             id.textContent = "sdfsdf";

		//    수신동의 체크
		let mod = document.querySelector("#mod")

		// 정보 임의 지정

		//             document.querySelector("#phonenumber").value = "010-0000-0000";
		//             document.querySelector("#email_id").value = "doseo";
		//             document.querySelector("#email_domain").value = "human.com";
		//             document.querySelector("#tel1").value = "041";
		//             document.querySelector("#tel2").value = "331";
		//             document.querySelector("#tel3").value = "1234";
		//             document.querySelector("#sample6_postcode").value = "";
		//             document.querySelector("#sample6_address").value = "";
		//             document.querySelector("#sample6_address2").value = "";
		//             document.getElementsByName("sms")[0].checked = true;
		//             document.getElementsByName("email")[0].checked = true;

		var forms = document.querySelector("#form")
		forms.addEventListener("submit", function(event) {
			event.preventDefault();

		})

		mod.addEventListener(
						"click",
						function() {
							let password = document.querySelector("#password").value
							let password1 = document
									.querySelector("#password1").value
							let smsCheck = document.getElementsByName("sms")
							let phonenumber = document
									.querySelector("#phonenumber").value
							let emailCheck = document
									.getElementsByName("email")
							let selectSms;
							let selectEmail;

							for (let i = 0; i < smsCheck.length; i++) {
								if (smsCheck[i].checked) {
									selectSms = smsCheck[i].value;
								}
								if (emailCheck[i].checked) {
									selectEmail = emailCheck[i].value;
								}
							}

							// 유효성 검사
							if (password == "") {
								alert("비밀번호 입력 안함")
								document.querySelector("#password").focus();

							} else if (password1 == "") {
								alert("비밀번호 확인 입력안함")
								document.querySelector("#password1").focus();

							} else if (phonenumber == "") {
								alert("핸드폰번호 입력안함")
								document.querySelector("#phonenumber").focus();

							} else if (document.querySelector("#email_id").value == "") {
								alert("이메일id 입력안함")
								document.querySelector("#email_id").focus();

							} else if (document.querySelector("#email_domain").value == "") {
								alert("이메일 도메인 입력안함")
								document.querySelector("#email_domain").focus();

							} else if (document
									.querySelector("#sample6_postcode").value == "") {
								alert("우편번호 입력")
								document.querySelector("#sample6_postcode")
										.focus();

							} else if (document
									.querySelector("#sample6_address").value == "") {
								alert("주소 입력")
								document.querySelector("#sample6_address")
										.focus();

							} else if (document
									.querySelector("#sample6_address2").value == "") {
								alert("상세 주소 입력")
								document.querySelector("#sample6_address2")
										.focus();

							} else {
								if (password === password1) {
									alert(name.textContent
											+ "님의 정보를 수정하였습니다."
											+ "\n"
											+ "비밀번호 : "
											+ password
											+ "\n"
											+ "휴대폰 번호 : "
											+ phonenumber
											+ "\n"
											+ "이메일 : "
											+ document
													.querySelector("#email_id").value
											+ "@" + email2.value + "\n"
											+ "sms수신동의여부 : " + selectSms + "\n"
											+ "email수신동의여부 : " + selectEmail
											+ "\n" + "로 변경되었습니다.")

									forms.submit();
									window.location.href = "/carpedm/mypage_loan_status";

								} else {
									document.querySelector("#password").value = "";
									document.querySelector("#password1").value = "";
									alert("비밀번호를 다시 확인해주세요.");
								}
							}
						})

		// 취소버튼 이동
		let cancle = document.querySelector("#cancle")
		cancle.addEventListener("click", function() {
			location.href = "mypage_loan_status";
		})

	}
	// 주소찾기
	function sample6_execDaumPostcode() {
		new daum.Postcode(
				{
					oncomplete : function(data) {
						// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

						// 각 주소의 노출 규칙에 따라 주소를 조합한다.
						// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
						var fullAddr = ''; // 최종 주소 변수
						var extraAddr = ''; // 조합형 주소 변수

						// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
						if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
							fullAddr = data.roadAddress;

						} else { // 사용자가 지번 주소를 선택했을 경우(J)
							fullAddr = data.jibunAddress;
						}

						// 사용자가 선택한 주소가 도로명 타입일때 조합한다.
						if (data.userSelectedType === 'R') {
							//법정동명이 있을 경우 추가한다.
							if (data.bname !== '') {
								extraAddr += data.bname;
							}
							// 건물명이 있을 경우 추가한다.
							if (data.buildingName !== '') {
								extraAddr += (extraAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}
							// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
							fullAddr += (extraAddr !== '' ? ' (' + extraAddr
									+ ')' : '');
						}

						// 우편번호와 주소 정보를 해당 필드에 넣는다.
						document.getElementById('sample6_postcode').value = data.zonecode; //5자리 새우편번호 사용

						document.getElementById('sample6_address').value = fullAddr;

						// 커서를 상세주소 필드로 이동한다.
						document.getElementById('sample6_address2').focus();
					}
				}).open();
	}
</script>
<style>
section {
	font-family: "Wanted Sans Variable";
}

td, th {
	border: 1px solid black;
	padding: 7px;
}

/* 설명창 정렬 */
.text {
	text-align: center;
	padding: 20px;
}

/* 인풋박스 사이즈 */
.input_width {
	width: 100px;
	height: 20px;
}

/* 상단배너 사이즈 */
#head {
	width: 800px;
	text-align: center;
	margin: auto;
}

/* 테이블 사이즈 */
#table {
	width: 800px;
	margin: auto;
	border-collapse: collapse;
}

/* 하단 버튼 위치 조정 */
#button_align {
	text-align: center;
	margin: auto;
	padding: 20px;
}

.place_input_width {
	width: 90%;
	margin: 5px auto;
	height: 20px;
}

/* 하단 버튼 css */
.button {
	font-family: "Wanted Sans Variable";
	width: 100px;
	background-color: rgba(155, 178, 225, 0.6);
	font-size: 18px;
	border: 0;
	border-radius: 5px;
}

/* 주소찾기 버튼 */
#place_button {
	font-family: "Wanted Sans Variable";
	width: 70px;
	background-color: rgba(155, 178, 225, 0.6);
	font-size: 13px;
	border: 0;
	border-radius: 5px;
	margin-bottom: 5px;
}

font {
	font-size: 13px;
}

::placeholder {
	font-family: "Wanted Sans Variable";
}

input {
	font-family: "Wanted Sans Variable";
}
</style>
</head>

<body>
	<header></header>
	<section>
		<!-- 여기부터 본문작성해주세요 -->
		<!-- 상단배너 -->
		<div id="head">
			<h1>정보 수정</h1>
		</div>
		<!-- 수정항목 -->
		<form method="post" action="mypage_chginfo" id="form">
			<div>
				<table id="table">
					<tr>
						<th>항목</th>
						<th class="padding">정보</th>
					</tr>
					<tr>
						<th>이름</th>
						<td class="padding" id="name" name="name">
							<input type="text" name="name" value="${myInfo[0].M_NAME}" /></td>
					</tr>
					<tr>
						<th>생년월일</th>
						<td class="padding" id="birth">${myInfo[0].M_BIRTHDAY.toString().substring(0, 10)}</td>
					</tr>
					<tr>
						<th>아이디</th>
						<td class="padding" id="id">${myInfo[0].M_ID}</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td class="padding"><input type="password" placeholder="비밀번호"
							id="password" name="password"></td>
					</tr>
					<tr>
						<th class="text" colspan="2"><font> 비밀번호를 입력하세요(8~20자로
								숫자, 영문소, 영문대, 특수문자 중 3가지 이상 조합)<br> - 입력가능한 특수문자는 [<font
								color="red"> ! , @ , $ , % , ^ , * , ( , ) </font>] 입니다. (지정된
								특수문자 외는 입력이 불가능합니다.)<br> 비밀번호 수정 시 적어주세요.
						</font></th>
					</tr>
					<tr>
						<th>비밀번호 확인</th>
						<td class="padding"><input type="password"
							placeholder="비밀번호 확인" id="password1" name="password1"></td>
					</tr>
					<tr>
						<th>휴대폰 번호</th>
						<td class="padding"><input type="text" placeholder="휴대폰 번호"
							id="phonenumber" name="phonenumber" value="${myInfo[0].M_TEL}">
							<font> SMS수신 <input type="radio" value="Y" name="sms"
								${myInfo[0].M_EMAIL_AGREE eq "Y" ? "checked" : ""}>예 <input
								type="radio" value="N" name="sms"
								${myInfo[0].M_EMAIL_AGREE eq "N" ? "checked" : ""}>아니오
						</font></td>
					</tr>
					<tr>
						<th class="text" colspan="2"><font> 마케팅 / 홍보를 위하여 귀하의
								개인정보를 이용(SMS,이메일)하는데 동의 하십니까?<br> <font color="red">
									동의 거부 시 대출·반납, 희망도서 정보안내 등 서비스가 제한됩니다. </font>
						</font></th>
					</tr>
					<tr>
						<th>이메일</th>
						<td class="padding"><input class="input_width" type="text"
							placeholder="내용을 입력해주세요." id="email_id" name="email_id"
							value="${fn:split(myInfo[0].M_EMAIL, '@')[0]}"> @ <input
							class="input_width" type="text" placeholder="내용을 입력해주세요."
							id="email_domain" name="email_domain"
							value="${fn:split(myInfo[0].M_EMAIL, '@')[1]}"> <select
							id="email_domain1" value="직접입력"></select> <font> 이메일 수신 <input
								type="radio" value="Y" name="email"
								${myInfo[0].M_EMAIL_AGREE eq "Y" ? "checked" : ""}>예 <input
								type="radio" value="N" name="email"
								${myInfo[0].M_EMAIL_AGREE eq "N" ? "checked" : ""}>아니오
						</font></td>
					</tr>
					<th>주소</th>
					<td><input class="input_width" type="text" placeholder="우편 번호"
						name="sample6_postcode" id="sample6_postcode"
						value="${myInfo[0].M_ADDRESS.split(',')[0]}">
						<button id="place_button"
							onclick="sample6_execDaumPostcode(); return false;">주소
							찾기</button> <br> <input class="place_input_width"
						id="sample6_address" name="sample6_address" type="text"
						placeholder="도로명 주소" value="${myInfo[0].M_ADDRESS.split(',')[1]}"><br>
						<input class="place_input_width" id="sample6_address2"
						name="sample6_address2" type="text" placeholder="상세 주소"
						value="${myInfo[0].M_ADDRESS.split(',')[2]}"></td>
				</table>
			</div>
			<!-- 버튼 -->
			<div id="button_align">
				<button value="submit" class="button" id="mod">수정</button>
				&nbsp;
				<button value="reset" class="button" id="cancle">취소</button>
			</div>
		</form>

	</section>

	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm_old/js/header.js"></script>
</body>

</html>