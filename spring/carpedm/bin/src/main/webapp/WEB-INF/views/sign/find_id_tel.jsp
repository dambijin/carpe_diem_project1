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
    <title>전화번호로 아이디 찾기</title>
    <link href="/carpedm_old/css/layout.css" rel="stylesheet">
    <style>
        /* 헤더 가운데 정렬 */
        .tel_find {
            width: 80%;
            text-align: center;
            margin: auto;
        }

        /*  아이디 찾기 제목 */
        .tel_find .main {
            font-family: "Wanted Sans Variable";
            font-size: 30px;
            margin-bottom: 15px;
        }

        /* 테이블 div */
        .tel_find .search {
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

        /* 테이블 */
        .search_text {
            width: 70%;
            text-align: center;
            margin: auto;
            margin-top: 20px;
        }

        /* 테이블 아이디 전화번호 td */
        .search_text tr .sub {
            text-align: center;
            background-color: rgba(168, 156, 200, 0.6);
            width: 30%;

        }

        .search_text tr th {
            background-color: rgba(168, 156, 200, 0.6);
            text-align: center;
        }

        .search_text #text_tel,
        .search_text #text_name {
            height: 25px;
            width: 95%;
            font-size: 15px;
            font-family: "Wanted Sans Variable";
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

        .tel_find {
            width: 80%;
            margin: auto;
        }

        .btnSearch #button:hover {
            background-color: rgba(205, 155, 225, 0.6);
        }

        /* 이메일로찾기, 휴대폰번호로찾기 div */
        .tel_find .search {
            display: inline-block;
            text-align: center;
            width: 65%;
        }

        .tel_find .search .search_title:hover {
            background-color: rgba(140, 201, 240, 0.6);
        }
    </style>
    <script>
        window.onload = function () {
            let button = document.getElementById('button');

            button.addEventListener('click', function () {
                let username = document.querySelector('#text_name').value;
                let usertel = document.querySelector('#text_tel').value;

                if (username == "") {
                    alert("이름을 입력해주세요.");
                    document.querySelector('#text_name').focus();
                    return;
                } else if (usertel == "") {
                    alert("전화번호를 입력해주세요.");
                    document.querySelector('#text_tel').focus();
                    return;
                } else {
                	console.log("이름:", username);
                    console.log("전화번호:", usertel);


                // 아이디 찾는 기능 추가
            };
            
            let found = false;
                <c:forEach var="nametel" items="${nametel_list}">
                    if ("<c:out value="${nametel.name}" />" === username && "<c:out value="${nametel.tel}" />" === usertel) {
                        console.log("일치하는 아이디를 찾았습니다.");
                        alert("해당 정보의 아이디는 <c:out value="${nametel.id}" />입니다");
                        found = true;
                        // 원하는 작업을 수행하거나 다른 페이지로 이동할 수 있습니다.
                        location.href='find_pw';
                    }
                </c:forEach>

            if (!found) {
                console.log("일치하는 정보를 찾을 수 없습니다."); // 일치하는 정보가 없는 경우 알림창 표시
            	alert("일치하는 정보를 찾을 수 없습니다.");
            }
            });
        
            
        
            let textbox1 = document.getElementById("text_tel");
            // Enter 키 이벤트 리스너 추가
            textbox1.addEventListener("keydown", function (event) {
                // keyCode 13은 Enter 키를 나타냅니다
                if (event.keyCode === 13) {

                    button.click();

                }
            })
        })
            };

    </script>
</head>

<body>
    <header></header>
    <section>
        <div class="tel_find">
            <div class="main">아이디 찾기</div>
            <div class="search">
                <div class="search_title" onclick="location.href='find_id_email'" style="cursor:pointer">
                    이메일로 찾기
                </div>
                <div class="search_title" onclick="location.href='find_id_tel'" style="cursor:pointer">
                    전화번호로 찾기
                </div>
                <table class="search_text" cellpadding="5" cellspacing="1">
                    <tr>
                        <td width="30%" class="sub">이름</td>
                        <td width="*"><input type="text" id="text_name" placeholder="이름을 입력해주세요." autofocus></td>
                    </tr>
                    <tr>
                        <td class="sub">전화번호</td>
                        <td><input type="text" id="text_tel" placeholder="전화번호를 입력해주세요."></td>
                    </tr>
                </table>
                <div class="btnSearch">
                    <input type="button" name="enter" value="확인" id="button">
                </div>
            </div>
        </div>
    </section>
    <!-- 헤더를 덮어씌우는 자바스크립트 -->
    <script src="/carpedm_old/js/header.js"></script>
</body>

</html>