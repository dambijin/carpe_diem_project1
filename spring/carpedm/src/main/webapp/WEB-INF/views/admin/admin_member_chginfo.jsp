<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.RequestDispatcher"%>
<%@ page import="javax.servlet.ServletException"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>관리자페이지(회원정보수정)</title>
<link href="/carpedm/resources/css/layout.css" rel="stylesheet">
</head>

<!-- function 스크립트 -->
<script src="/carpedm/resources/js/admin_library.js"></script>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	// window load했을 때
	window.addEventListener("load", function() {
		//검색옵션 기본세팅
		let search_opt_list = [ "직접입력", "naver.com", "google.com" ];

		for (let i = 0; i < search_opt_list.length; i++) {
			let search_opt = document.querySelector("#select_email");
			let html = '';
			html += search_opt_list[i];

			let opt = document.createElement("option");//<option></option>
			opt.innerHTML = html;//<option>serach_opt_list[i]</option>

			search_opt.append(opt);
		}

	});

	// 이메일 자동완성
	function selectWebsite() {
		var selectBox = document.getElementById("select_email");
		var emailInput = document.getElementById("emailId2");

		var selectedValue = selectBox.value;

		if (selectedValue === "직접입력") {
			// 직접입력 옵션 선택 시 텍스트 입력 상자를 활성화합니다.
			emailInput.removeAttribute("disabled");
			emailInput.value = ""; // 기존 값 비우기
		} else {
			// 다른 옵션 선택 시 해당 값을 텍스트 입력 상자에 자동 완성합니다.
			emailInput.value = selectedValue;
			emailInput.setAttribute("disabled", "disabled"); // 텍스트 입력 상자 비활성화
		}
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
						document.getElementById('zipcodenum').value = data.zonecode; //5자리 새우편번호 사용

						document.getElementById('adr1').value = fullAddr;

						// 커서를 상세주소 필드로 이동한다.
						document.getElementById('adr2').focus();
					}
				}).open();
	}

	// 수정 버튼 누르면 수정되게하고 memberlist로 이동
	function crystal() {

		window.location.href = "/carpedm_old/admin_member_list";
	}
</script>

<style>
header .nav .member_list {
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

/* 테이블 thead */
.custom-table {
    border: 0;
    margin: auto; /* Align center */
    border-spacing: 5px;
    background-color: #cccccc;
}

/* 회원 정보 수정 글씨 */
.admin_chginfo {
	font-family: "Wanted Sans Variable";
}

/* 테이블 */
.chtable table {
	width: 50%;
	height: 600px;
	border-collapse: collapse;
	font-family: "Wanted Sans Variable";
}

.chtable table tr {
	background-color: #fff;
}

.chtable table tr th, .chtable table tr td {
	border: 1px solid #000000;
	padding: 3px;
}

/* 테이블 인풋 */
.chtable table input {
	font-family: "Wanted Sans Variable";
	font-size: 17px;
}

.chtable table tr td .colspan {
	font-size: 13px;
	color: red;
	font-weight: bold;
}

/* 주소찾기 버튼 */
.chtable table tr td .add {
	font-family: "Wanted Sans Variable";
	font-size: 17px;
	background-color: rgb(36, 116, 190);
	width: 80px;
	height: 30px;
	border: 0;
	border-radius: 5px;
	color: white;
	cursor: pointer;
}

/* 이메일 인풋 이메일 셀렉 */
.chtable table .temail {
	font-family: "Wanted Sans Variable";
	font-size: 17px;
	width: 150px;
}

/* 수정 취소 버튼 div */
.input {
	border: 1px;
	width: 20%;
	margin: auto;
	text-align: center;
	margin-top: 20px;
}

/* 수정 취소 버튼 */
.input .button {
	font-family: "Wanted Sans Variable";
	font-size: 18px;
	background-color: rgba(71, 125, 231, 0.973);
	width: 70px;
	height: 30px;
	border: 0;
	border-radius: 5px;
	color: white;
	cursor: pointer;
}

/* 테이블 th */
.chtable th {
	background-color: rgba(163, 163, 163, 0.6);
}

.home_num {
	width: 50px;
}

/* 우편번호 인풋 */
.chtable table .zipcode {
	width: 100px;
	margin-bottom: 5px;
	margin-right: 5px;
}

/* 기본주소 상세주소 인풋 */
.chtable table .adr {
	width: 90%;
	margin-bottom: 5px;
}

#emailId1, #emailId2 {
	width: 200px;
}

input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
	-webkit-appearance: none;
	margin: 0;
}
</style>


<body>
	<!-- header -->
	<header></header>

	<div>
		<h2 align="center" class="admin_chginfo">회원 정보수정</h2>
	</div>

	<div class="chtable">
		<form method="get" action="admin_member_update">
			<table class="custom-table">
				<thead>
					<c:if test="${not empty list }">
						<c:set var="data_list" value="${list}" />
						<tr>
					        <input type="hidden" style="display: none;" name="m_pid" value="${data_list[0].m_pid}" />
					        <th width="20%" height="40px">항목</th>
					        <th width="80%">정보</th>
					    </tr>
					    <tr>
					        <th height="40px">이름</th>
					        <td><input type="text" id="name" name="name" value="${data_list[0].m_name}" /></td>
					    </tr>
					    <tr>
					        <th height="40px">생년월일</th>
					        <td><input type="date" name="date" id="yymmdd" value="${fn:substring(data_list[0].m_birthday, 0, 10)}" /></td>
					    </tr>
					    <tr>
					        <th height="40px">아이디</th>
					        <td><input type="text" name="id" id="userid" value="${data_list[0].m_id}" /></td>
					    </tr>
					    <tr>
					        <th height="40px">비밀번호</th>
					        <td><input type="password" name="pw" id="password" maxlength="20" placeholder="비밀번호를 입력해주세요." value="${data_list[0].m_pw}" /></td>
					    </tr>
					    <tr>
					        <th height="40px">비밀번호확인</th>
					        <td><input type="password" id="password_check" name="pw_chk" maxlength="16" placeholder="비밀번호를 확인해주세요." /></td>
					    </tr>
					    <tr>
					        <th height="40px">휴대폰번호</th>
					        <td>
					            <input type="number" name="phone_number" id="phone" placeholder="-를 빼고 작성해주세요." value="${data_list[0].m_tel.replace('-', '')}" />
					            SMS수신
					            <input type="radio" name="sms1" checked />예
					            <input type="radio" name="sms1" />아니오
					        </td>
					    </tr>
					    <tr height="100px">
					        <td colspan="2" align="center" height="40px">
					            <div class="colspan">
					                고객님께 물어봐주세요<br /><br />
					                마케팅 / 홍보를 위하여 귀하의 개인정보를 이용(SMS,이메일)하는데 동의 하십니까?<br />
					                동의 거부 시 대출·반납, 희망도서 정보안내 등 서비스가 제한됩니다.
					            </div>
					        </td>
					    </tr>
					    <tr>
					        <th height="40px">이메일</th>
					        <td>
					            <input type="text" name="email1" id="emailId1" placeholder="이메일 입력" value="${data_list[0].m_email.split('@')[0]}" />
					            <span>@</span>
					            <input type="text" name="email2" id="emailId2" placeholder="이메일을 선택하세요." value="${data_list[0].m_email.split('@')[1]}" />
					            <select class="temail" onchange="selectWebsite()" id="select_email">
					                <!-- 자바스크립트화 -->
					            </select>
					            이메일수신
					            <input type="radio" name="sms2" checked />예
					            <input type="radio" name="sms2" />아니오
					        </td>
					    </tr>
					    <tr>
					        <th height="40px">주소</th>
					        <td>
					            <input type="text" class="zipcode" id="zipcodenum" placeholder="우편번호" />
					            <input type="button" value="주소찾기" class="add" onclick="sample6_execDaumPostcode()" /><br />
					            <input type="text" class="adr" name="address1" id="adr1" placeholder="기본주소" value="${data_list[0].m_address}" /><br />
					            <input type="text" class="adr" id="adr2" placeholder="상세주소" />
					        </td>
					    </tr>
					</c:if>
				    <c:set var="data_list" value="${list}" />
				</thead>
			</table>
			<!-- 수정 취소 -->
			<div class="input">
				<!--  이거 구현하기 -->
				<input type="submit" value="수정" class="button"> 
				<input type="reset" value="취소" class="button" 
					onclick="location.href='/carpedm/admin_member_list';">
			</div>
		</form>

	</div>



	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm_old/js/header_admin.js"></script>
</body>

</html>