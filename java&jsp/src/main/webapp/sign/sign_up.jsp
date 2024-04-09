<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link href="/carpedm/css/layout.css" rel="stylesheet">
    <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <style>
        .sign_up_nav {
            font-family: "Wanted Sans Variable";
            text-align: center;
            font-size: 30px;
        }

        /* section */
        .sign_up_form {
            font-family: "Wanted Sans Variable";
            text-align: center;
        }

        .sign_up_form td {
            width: 100%;
            height: 40px;
        }

        /* 테이블 왼쪽 td  */
        .sign_up_form .r_border {
            background-color: rgba(168, 156, 200, 0.6);
            width:25%;
            text-align: center;
        }

        .sign_up_form tr {
            text-align: left;
            width: 70%;
            border: 1px solid grey;
        }

        .sign_up_form .inputbox {
            width: 100%;
            height: 23px;
            font-size:18px;
            font-family: "Wanted Sans Variable";
        }

        .sign_up_form table {
            margin: auto;
            width: 50%;
        }

        /* 회원가입 버튼 */
        .sign_up_form .sub {
            font-family: "Wanted Sans Variable";
            width: 100px;
            background-color: rgba(155, 178, 225, 0.6);
            font-size: 18px;
            border: 0;
            border-radius: 5px;
        }

        .sign_up_form .sub:hover {
            background-color: rgba(205, 155, 225, 0.6);
        }

        /* 이메일 */
        .sign_up_form .email {
            width: 100px;
            font-family: "Wanted Sans Variable";
            font-size: 18px;
            
        }
        #email_domain {
        	font-family: "Wanted Sans Variable";
        	height: 30px;
        }

        /* 개인정보동의에 있는 체크박스 */
        .sign_up_form .chk {
            width: 15px;
            height: 15px;

        }

        /* 테이블 오른쪽의 텍스트상자 */
        .sign_up_form .textbox {
            margin-bottom: 5px;
        }

        /* 우편번호 인풋상자 */
        .sign_up_form .zip_code {
            font-family: "Wanted Sans Variable";
            font-size: 18px;
            width: 100px;
            margin-bottom: 5px;
        }

        /* 주소찾기버튼 */
        #place_button {
            font-family: "Wanted Sans Variable";
            height: 30px;
            font-size: 18px;
        }
        
        #birthday_text::-webkit-inner-spin-button {
        	-webkit-appearance: none;
        	margin: 0;
        }
       

    </style>
    <script>

        window.onload = function () {
            let button = document.getElementById('button');

            button.addEventListener('click', function () {
                let userid = document.querySelector('#id_text').value;
                let userpw = document.querySelector('#pw_text').value;
                let userpw_check = document.querySelector('#pw_check_text').value;
                let username = document.querySelector('#name_text').value;
                let userbirthday = document.querySelector('#birthday_text').value;
                let usertel = document.querySelector('#tel_text').value;
                let useremail1 = document.querySelector('#email1_text').value;
                let useremail2 = document.querySelector('#email2_text').value;
                let useradd1 = document.querySelector('#add1_text').value;
                let useradd2 = document.querySelector('#add2_text').value;
                let userchk = document.querySelector('#chk').checked;

                if (userid == "") {
                    alert("아이디을 입력해주세요.");
                    document.querySelector('#id_text').focus();
                } else if (userid.length < 6 || userid.length > 20) {
                    alert("아이디는 최소 6자 이상, 최대 20자 이하여야 합니다.");
                    document.querySelector('#id_text').focus();
                } else if (userpw == "") {
                    alert("비밀번호를 입력해주세요.");
                    document.querySelector('#pw_text').focus();
                } else if (userpw.length < 8 || userpw.length > 20) {
                    alert("비밀번호는 최소 8자 이상, 최대 20자 이하여야 합니다.");
                    document.querySelector('#pw_text').focus();
                } else if (userpw_check == "") {
                    alert("비밀번호 확인을 입력해주세요.");
                    document.querySelector('#pw_check_text').focus();
                } else if (userpw !== userpw_check) {
                    alert("비밀번호가 일치하지 않습니다.");
                    document.querySelector('#pw_check_text').focus();
                } else if (username == "") {
                    alert("이름를 입력해주세요.");
                    document.querySelector('#name_text').focus();
                } else if (userbirthday == "") {
                    alert("생년월일을 입력해주세요.");
                    document.querySelector('#birthday_text').focus();
                } else if (usertel == "") {
                    alert("전화번호을 입력해주세요.");
                    document.querySelector('#tel_text').focus();
                } else if (useremail1 == "") {
                    alert("이메일을 입력해주세요.");
                    document.querySelector('#email1_text').focus();
                } else if (useremail2 == "") {
                    alert("이메일주소를 입력해주세요.");
                    document.querySelector('#email2_text').focus();
                } else if (useradd1 == "") {
                    alert("주소을 입력해주세요.");
                    document.querySelector('#add1_text').focus();
                } else if (useradd2 == "") {
                    alert("상세주소을 입력해주세요.");
                    document.querySelector('#add2_text').focus();
                } else if (!userchk) {
                    alert("개인정보에 동의해주세요.");
                } else {
                    alert("회원가입 되었습니다");
                    location.href = 'sign_in';
                    
                }
            });
        };

		
        function sample6_execDaumPostcode() {
            new daum.Postcode({
                oncomplete: function (data) {
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
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }
                        // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                        fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
                    }

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    document.getElementById('add_text').value = data.zonecode; //5자리 새우편번호 사용

                    document.getElementById('add1_text').value = fullAddr;

                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById('add2_text').focus();
                }
            }).open();
        }
        window.addEventListener("load", function () {
            email();

        })
        // 이메일 선택시 따라붙기
        function email() {
            let email1 = document.querySelector("#email_domain")
            let email2 = document.querySelector("#email2_text")
            let name = document.querySelector("#name")



            email1.addEventListener("change", function () {
                let selectedIndex = email1.selectedIndex;
                email2.value = email1.options[email1.selectedIndex].value;  //동기화
                if (email1.selectedIndex > 0) //직접입력 차단
                {
                    email2.readOnly = true;
                }
                else                            //직접입력 허용
                {
                    email2.value = "";
                    email2.readOnly = false;
                }
            })



            // 이메일 리스트 스크립트
            let email_list = ["직접입력", "naver.com", "daum.net", "google.com"];


            for (let i = 0; i < email_list.length; i++) {
                let result_email_list = document.querySelector("#email_domain")
                let html = '';
                html += email_list[i];

                let opt = document.createElement("option");
                opt.innerHTML = html;

                result_email_list.append(opt)
            }
        }
    </script>
</head>

<body>
    <header></header>
    <nav class="sign_up_nav">
        회원가입
    </nav>
    <section class="sign_up_form">
        <!-- 회원가입form -->
        <form method="post" action="sign_up">
        <table>
            <tr>
                <td class="r_border">아이디</td>
                <td><input type="text" class="inputbox" name="id" placeholder="아이디를 입력(6~20자)" class="textbox" id="id_text">
                	<input type="button" value="중복확인"></td>
            </tr>
            <tr>
                <td class="r_border">비밀번호</td>
                <td><input type="password" class="inputbox" name="pw" placeholder="비밀번호를 입력(8~20자)" class="textbox"
                        id="pw_text">
                </td>
            </tr>
            <tr>
                <td class="r_border">비밀번호 확인</td>
                <td><input type="password" class="inputbox" name="pw_check" placeholder="비밀번호 재입력" class="textbox" id="pw_check_text">
                </td>
            </tr>
            <tr>
                <td class="r_border">이름</td>
                <td><input type="text" class="inputbox" name="name" placeholder="이름" class="textbox" id="name_text"></td>
            </tr>
            <tr>
                <td class="r_border">생년월일</td>
                <td><input type="number" class="inputbox" name="birthday" placeholder="생년월일 8자리" class="textbox" id="birthday_text"></td>
            </tr>
            <tr>
                <!-- 숫자만 입력할 수 있게 설정하기 -->
                <td class="r_border">휴대폰번호</td>
                <td> <input type="tel" class="inputbox" name="tel" placeholder="-없이 입력해주세요" class="textbox" id="tel_text"> </td>
            </tr>
            <tr>
                <td class="r_border">이메일</td>
                <td><input type="text" name="email1" placeholder="이메일" class="email textbox" id="email1_text"> @
                    <input type="text" name="email2" placeholder="직접입력" class="email textbox" id="email2_text">
                    <select id="email_domain" value="직접입력" class="email">
                    </select>
                </td>
            </tr>
             <tr>
                <td class="r_border">이메일 동의</td>
                <td> 
                	<input type="radio" class="chk_agree" name="agree" value="Y">동의함
                	<input type="radio" class="chk_agree" name="agree" value="N">동의안함
                </td>
                
            </tr>
            <tr>
                <td class="r_border">주소</td>
                <td><input type="text" name="address1" placeholder="우편번호" class="zip_code" id="add_text">
                    <button id="place_button" onclick="sample6_execDaumPostcode(); return false;" >
                        주소 찾기
                    </button>
                    <input type="text" class="inputbox textbox" name="address2" placeholder="기본주소"  id="add1_text">
                    <input type="text" class="inputbox textbox" name="address3" placeholder="나머지주소"  id="add2_text">
                </td>
            </tr>
            <tr>
					<td class="r_border">개인정보동의</td>
					<td class="chk"><details>
							<summary><input type="checkbox">[필수]개인정보동의</summary>
							<b>1. 수집하는 개인정보 항목</b>
국립중앙도서관은 회원가입, 원활한 고객상담, 각종 서비스 등 기본적인 서비스 제공을 위해 회원가입 시 아래와 같은 개인정보를 수집하고 있습니다.<br>
가. 필수항목: ID, 비밀번호, 성명, E-Mail, 연락처(또는 핸드폰), 생년월일, 주소, 법정대리인 성명(만14세 미만 회원의 경우)<br>

나. 서비스 이용과정에서 아래와 같은 정보들이 자동으로 생성되어 수집될 수 있습니다.
(IP Address, 쿠키, 방문 일시, 서비스 이용 기록, 불량 이용 기록)<br><br>


<b>2. 개인정보의 수집 및 이용 목적</b><br>

가. 서비스 제공에 관한 업무 이행<br>

 - 홈페이지 회원에게 정보 서비스 제공<br>

나. 회원관리<br>

 - 회원제 서비스 이용 및 제한적 본인 확인제에 따른 본인확인, 개인식별, 가입 의사 확인, 추후 법정 대리인 본인확인, 분쟁 조정을 위한 기록보존, 불만 처리 등 민원처리, 고지사항 전달<br><br>


<b>3. 개인정보의 보유 및 이용기간</b><br>

회원 이용자의 개인정보는 개인정보보호법 및 공공기록물법에 따라 마지막 이용일(도서관 방문, 홈페이지 로그인)로부터 5년간 보관되며, 그 이후, 관계법령의 절차에 따라 파기합니다.
다만, 다음과 같은 경우에 개인정보의 처리 및 이용이 중지됩니다.<br>

가. 휴면회원 전환(마지막 이용일로부터 2년간 미사용)시점부터 계정 활성화 또는 파기 시까지<br>

나. 회원 탈퇴 요청 시점부터 개인정보 파기시까지<br><br>


<b>4. 동의 거부권 및 동의 거부에 따른 불이익</b><br>

가입자는 개인정보 수집·이용에 대하여 거부할 수 있는 권리가 있습니다. 단, 이에 대한 동의를 거부할 경우, 회원가입이 불가능합니다.</p>
						</details></td>
				</tr>
        </table>
        <br>
        <!-- 회원가입완료 -->
        <input type="submit" class="sub" id="button" value="회원가입">
        </form>
    </section>
    <!-- 헤더를 덮어씌우는 자바스크립트 -->
    <script src="/carpedm/js/header.js"></script>
</body>


</html>