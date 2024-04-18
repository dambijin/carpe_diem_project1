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
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link href="/carpedm_old/css/layout.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        /* 헤더 아래 모두 */
        .log_in {
            text-align: center;
            width: 80%;
            margin: auto;
            font-family: "Wanted Sans Variable";

        }

        /* 로그인 제목 */
        .log_in .subject {
            font-family: "Wanted Sans Variable";
            font-size: 30px;
            margin-bottom: 15px;
        }

        /* 테이블 */
        .log_in_table table {
            width: 80%;
            text-align: left;
            margin: auto;
            margin-top: 20px;
        }

        /* 테이블 tr */
        .log_in_table tr {
            height: 30px;
        }

        /* 테이블 아이디 비밀번호 td */
        .log_in_table .sub {
            background-color: rgba(168, 156, 200, 0.6);
            text-align: center;
        }


        /* 로그인 제목 아래 내용 */
        .log_in_table {
            border: 2px solid rgba(168, 156, 200, 0.5);
            border-radius: 20px;
            width: 400px;
            margin: auto;
            text-align: center;
        }

        /* 아이디저장 자동로그인 체크 div */
        .log_in_table .check_sub {
            margin-top: 20px;
            margin-bottom: 15px;
            font-size: 18px;
        }

        /* 아이디 비밀번호 입력칸(텍스트박스) */
        .log_in_table .textbox1,
        .log_in_table .textbox2 {
            height: 25px;
            font-size: 15px;
        }

        /* 아이디 비밀번호 input 텍스트 .log_in_table .textbox
        로그인 버튼 .log_but .log_button
        아이디찾기, 비밀번호찾기, 회원가입 버튼 .find_button .but
        글씨체 변경
        */
        .log_in_table .textbox1,
        .log_in_table .textbox2,
        .log_but #log_button,
        .find_button .but {
            font-family: "Wanted Sans Variable";

        }

        /* 아이디찾기, 비밀번호찾기, 회원가입 버튼 */
        .find_button .but {
            background-color: rgba(140, 201, 240, 0.6);
            font-size: 17px;
            border: 0;
            border-radius: 5px;
        }

        .find_button .but:hover {
            background-color: rgba(168, 156, 200, 0.6);

        }

        /* 로그인버튼 */
        .log_but #log_button {
            background-color: rgba(155, 178, 225, 0.6);
            font-size: 19px;
            border: 0;
            border-radius: 5px;
            width: 100px;
        }

        .log_but #log_button:hover {
            background-color: rgba(205, 155, 225, 0.6);
        }


        /* 로그인 버튼 div */
        .log_in_table .log_but {
            margin-bottom: 15px;
        }

        /* 아이디찾기, 비밀번호찾기, 회원가입 div */
        .log_in_table .find_button {
            margin-bottom: 10px;
        }
    </style>

   
    <script>
    window.onload = function() {
        let button = document.getElementById('log_button');
        button.addEventListener('click', function() {
            let userid = document.querySelector('.textbox1').value.trim();
            let userpw = document.querySelector('.textbox2').value.trim();

                if(userid == "") {
                    alert("아이디를 입력해주세요.");
                    document.querySelector('.textbox1').focus();
                } else if(userpw == "") {
                    alert("비밀번호를 입력해주세요.");
                    document.querySelector('.textbox2').focus();
                 }else {
                    // 로그인 요청 보내기
                    fetch('/carpedm_old/sign_in', {
                        method: 'POST',
                        headers: {
                        	'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: `userid=${"${encodeURIComponent(userid)}"}&userpw=${"${encodeURIComponent(userpw)}"}`
                    })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
//                             alert("로그인 성공");
                            location.href = 'main'; // 메인 페이지로 이동
                        } else {
                            alert("아이디 또는 비밀번호가 잘못되었습니다.");
                        }
                    });
                }
                //else {
//                     console.log("아이디:", userid);
//                     console.log("비밀번호:", userpw);
<%--                     <%		 --%>
//                     List<Map<String, String>> idpw_list = (List<Map<String, String>>) request.getAttribute("idpw_list");
<%--                     %> --%>

//                     let found = false;
<%--                     <% for (Map<String, String> idpw : idpw_list) { %> --%>
<%--                         if ("<%= idpw.get("id") %>" === userid && "<%= idpw.get("pw") %>" === userpw) { --%>
//                             console.log("로그인 성공");
//                             alert("로그인 성공")
//                             found = true;
//                             onclick(location.href='main')
                        
//                         }
                            
<%--                     <% } %> --%>

//                     if (!found) {
//                     	console.log("아이디 또는 비밀번호가 잘못되었습니다.")
//                         alert("아이디 또는 비밀번호가 잘못되었습니다.");
                    	
//                     }
//                     // 로그인 처리 또는 다른 작업 수행
//                     // onclick(location.href='../mainpages/main.jsp')
//                 }
        

        });        
        let textbox1 = document.getElementById("text_id");
        // Enter 키 이벤트 리스너 추가
        textbox1.addEventListener("keydown", function (event) {
            // keyCode 13은 Enter 키를 나타냅니다
            if (event.keyCode === 13) {
                
                button.click();
                
            }
        });
        let textbox2 = document.getElementById("text_pass");
        // Enter 키 이벤트 리스너 추가
        textbox2.addEventListener("keydown", function (event) {
            // keyCode 13은 Enter 키를 나타냅니다
            if (event.keyCode === 13) {
                
                button.click();
                
            }
        });
        $(document).ready(function(){
            // 페이지 로드 시 쿠키에 저장된 아이디 값을 가져와서 입력칸에 표시
            var key = getCookie("key");
            $("#text_id").val(key); 
            
            // 저장된 아이디가 있을 경우 체크박스를 체크 상태로 변경
            if($("#text_id").val() != ""){ 
                $("#checkId").prop("checked", true); 
            }
            
            // 아이디 저장하기 체크박스의 상태 변경 이벤트 리스너
            $("#checkId").change(function(){ 
                if($("#checkId").is(":checked")){
                    // 체크됐을 때 아이디를 쿠키에 저장
                    setCookie("key", $("#text_id").val(), 7);
                } else {
                    // 체크 해제됐을 때 쿠키 삭제
                    deleteCookie("key");
                }
            });
            
            // 아이디 입력칸에 키를 입력할 때마다 쿠키에 저장 (아이디 저장하기 체크된 상태일 때만)
            $("#text_id").keyup(function(){
                if($("#checkId").is(":checked")){
                    setCookie("key", $("#text_id").val(), 7);
                }
            });
        });
    
        // 쿠키 저장 함수
        function setCookie(cookieName, value, exdays) {
            var exdate = new Date();
            exdate.setDate(exdate.getDate() + exdays);
            var cookieValue = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
            document.cookie = cookieName + "=" + cookieValue;
        }
    
        // 쿠키 삭제 함수
        function deleteCookie(cookieName) {
            var expireDate = new Date();
            expireDate.setDate(expireDate.getDate() - 1);
            document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
        }
         
        // 쿠키 가져오는 함수
        function getCookie(cookieName) {
            cookieName = cookieName + '=';
            var cookieData = document.cookie;
            var start = cookieData.indexOf(cookieName);
            var cookieValue = '';
            if (start != -1) {
                start += cookieName.length;
                var end = cookieData.indexOf(';', start);
                if (end == -1)
                    end = cookieData.length;
                cookieValue = cookieData.substring(start, end);
            }
            return unescape(cookieValue);
        }
        
    };
    </script>
</head>



<body>
    <header></header>
    <section>
        <div class="log_in">
            <div class="subject">
                로그인
            </div>
            <div class="log_in_table">
                <table>
                    <tr>
                        <td class="sub">아이디</td>
                        <td><input type="text" class="textbox1" id="text_id" placeholder=" 아이디" value="${empty id ? '' : id}"></td>
                    </tr>
                    <tr>
                        <td class="sub">비밀번호</td>
                        <td><input type="password" class="textbox2" id="text_pass" placeholder=" 비밀번호" value="${empty pw ? '' : pw}"></td>
                    </tr>
                </table>
                <div class="check_sub">
                    <input type="checkbox" id="checkId">아이디 저장
                    <input type="checkbox">자동 로그인
                </div>
                <div class="log_but"><button type="button" id="log_button">로그인</button></div>
                <div class="find_button">
                    <button type="button" class="find_button but" onclick="location.href='find_id_email';">아이디 찾기</button>
                    <button type="button" class="find_password but" onclick="location.href='find_pw';">비밀번호 찾기</button>
                    <button type="button" class="sign_up but" onclick="location.href='sign_up';">회원가입</button>

                </div>
            </div>
        </div>
    </section>
    <!-- 헤더를 덮어씌우는 자바스크립트 -->
    <script src="/carpedm_old/js/header.js"></script>
</body>

</html>