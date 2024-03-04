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
            width: 30%;
            text-align: center;
        }

        .sign_up_form tr {
            text-align: left;
            width: 70%;
            border: 1px solid grey;
        }

        .sign_up_form input {
            width: 100%;
            height: 23px;
        }

        .sign_up_form table {
            margin: auto;
            width: 40%;
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
            width: 25%;
        }

        /* 개인정보동의에 있는 체크박스 */
        .sign_up_form .chk {
            width: 15px;
            height: 15px;

        }

        /* 테이블 오른쪽의 텍스트상자 */
        .sign_up_form .textbox {
            font-family: "Wanted Sans Variable";
            font-size: 18px;
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
        <from method="post" action="/sign_up">
        <table>
            <tr>
                <td class="r_border">아이디</td>
                <td><input type="text" name="id" placeholder="아이디를 입력(6~20자)" class="textbox" id="id_text"></td>
            </tr>
            <tr>
                <td class="r_border">비밀번호</td>
                <td><input type="password" name="pw" placeholder="비밀번호를 입력(8~20자)" class="textbox"
                        id="pw_text">
                </td>
            </tr>
            <tr>
                <td class="r_border">비밀번호 확인</td>
                <td><input type="password" name="pw_check" placeholder="비밀번호 재입력" class="textbox" id="pw_check_text">
                </td>
            </tr>
            <tr>
                <td class="r_border">이름</td>
                <td><input type="text" name="name" placeholder="이름" class="textbox" id="name_text"></td>
            </tr>
            <tr>
                <td class="r_border">생년월일</td>
                <td><input type="number" name="birthday" placeholder="생년월일 8자리" class="textbox" id="birthday_text"></td>
            </tr>
            <tr>
                <!-- 숫자만 입력할 수 있게 설정하기 -->
                <td class="r_border">휴대폰번호</td>
                <td> <input type="tel" name="tel" placeholder="-없이 입력해주세요" class="textbox" id="tel_text"> </td>
            </tr>
            <tr>
                <td class="r_border">이메일</td>
                <td><input type="text" name="email" placeholder="이메일" class="email textbox" id="email1_text"> @
                    <input type="text" name="email" placeholder="직접입력" class="email textbox" id="email2_text">
                    <select id="email_domain" value="직접입력">

                    </select>
                </td>
            </tr>
            <tr>
                <td class="r_border">주소</td>
                <td><input type="text" name="address" placeholder="우편번호" class="zip_code" id="add_text">
                    <button id="place_button" onclick="sample6_execDaumPostcode()">
                        주소 찾기
                    </button>
                    <input type="text" name="address" placeholder="기본주소" class="textbox" id="add1_text">
                    <input type="text" name="address" placeholder="나머지주소" class="textbox" id="add2_text">
                </td>
            </tr>
            <tr>
                <td class="r_border">개인정보동의</td>
                <td class="chk">
                    <input type="checkbox" class="chk" id="chk">[필수]개인정보동의
                </td>
            </tr>
        </table>
        <br>
        <!-- 회원가입완료 -->
        <input type="button" class="sub" id="button" value="회원가입">
        </from>
    </section>
    <!-- 헤더를 덮어씌우는 자바스크립트 -->
    <script src="/carpedm/js/header.js"></script>
</body>


</html>