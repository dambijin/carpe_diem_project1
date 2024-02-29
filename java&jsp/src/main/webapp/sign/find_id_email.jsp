<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <link href="/carpedm/css/layout.css" rel="stylesheet">
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
        
        .search_text #text_email1,
        .search_text #text_email2 {
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

        window.onload = function () {
        	
        	email();
        	
            let button = document.getElementById('button');
            
         // 이메일 선택시 따라붙기
            function email() {
                let email1 = document.querySelector("#email_domain");
                let email2 = document.querySelector("#text_email2");
                let name = document.querySelector("#name");

                // 이메일 리스트 생성 및 옵션 추가
                let email_list = ["직접입력", "naver.com", "daum.net", "google.com"];
                for (let i = 0; i < email_list.length; i++) {
                    let html = email_list[i];
                    let opt = document.createElement("option");
                    opt.innerHTML = html;
                    email1.append(opt);
                }

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
                });
            }
            

            button.addEventListener('click', function () {
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
                	console.log("이름:", username);
                    console.log("이메일:", useremail1 + "@" + useremail2);
                    
                <%		
                List<Map<String, String>> nameemail_list = (List<Map<String, String>>) request.getAttribute("nameemail_list");
                %>
				
                let found = false;
                <% for (Map<String, String> nameemail : nameemail_list) { %> // JSP 코드의 Java 코드 영역을 그대로 가져옵니다.
                    if ("<%= nameemail.get("name") %>" === username && "<%= nameemail.get("email") %>" === (useremail1 + "@" + useremail2)) { // 이름과 이메일이 일치하는 경우
                        console.log("일치하는 아이디를 찾았습니다.");
                        alert("해당 정보의 아이디는 " + "<%= nameemail.get("id") %>" + "입니다"); // 해당 아이디를 알려주는 알림창 표시
                        found = true;
                        // 원하는 작업을 수행하거나 다른 페이지로 이동할 수 있습니다.
                        onclick(location.href='find_pw')
                    }
                <% } %>

                if (!found) {
                    console.log("일치하는 정보를 찾을 수 없습니다."); // 일치하는 정보가 없는 경우 알림창 표시
                	alert("일치하는 정보를 찾을 수 없습니다.");
                }
            
                }
            
            
            let textbox1 = document.getElementById("text_email1");
            // Enter 키 이벤트 리스너 추가
            textbox1.addEventListener("keydown", function (event) {
                // keyCode 13은 Enter 키를 나타냅니다
                if (event.keyCode === 13) {
                    
                    button.click();
                    
                }
            });

            // 도서검색 버튼 엔터이벤트
            let textbox2 = document.getElementById("text_email2");
            // Enter 키 이벤트 리스너 추가
            textbox2.addEventListener("keydown", function (event) {
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
                <div class='search_title' onclick="location.href='find_id_email'" style="cursor:pointer">
                    이메일로 찾기
                </div>
                <div class="search_title" onclick="location.href='find_id_tel'" style="cursor:pointer">
                    전화번호로 찾기
                </div>
                <table class="search_text" cellpadding="5" cellspacing="1">
                    <tr>
                        <td class="sub">이름</td>
                        <td><input type="text" class="text_name" placeholder="이름을 입력해주세요." autofocus></td>
                    </tr>
                    <tr>
                        <td class="sub">이메일</td>
                        <td><input type="text" id="text_email1" placeholder="이메일을 입력해주세요."> @
                            <input type="text" id="text_email2" placeholder="직접입력">
                            <select id="email_domain" value="직접입력">
                            </select>
                        </td>
                    </tr>
                </table>
				

                <div class="btnSearch">
                    <input type="button" name="enter" value="확인" id="button">
                </div>
            </div>

    </section>
    <!-- 헤더를 덮어씌우는 자바스크립트 -->
    <script src="/carpedm/js/header.js"></script>
</body>

</html>