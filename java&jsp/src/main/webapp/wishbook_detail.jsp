<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>도서 관리 시스템</title>
    <link href="./css/layout.css" rel="stylesheet">
    <script>
        window.addEventListener("load", function () {
            bind();
        })

        function bind() {
            
            // 정보 임시 저장
            let a = document.querySelector("#a").innerHTML = "두정도서관";
            let b = document.querySelector("#b").innerHTML = " 100일만에 배우는 자바스크립트";
            let c = document.querySelector("#c").innerHTML = "김자바";
            let d = document.querySelector("#d").innerHTML = "2016년";
            let e = document.querySelector("#e").innerHTML = "123456789";
            let f = document.querySelector("#f").innerHTML = "제가 너무 읽고 싶은 책이었어요";
            let g = document.querySelector("#g").innerHTML = "휴먼출판사";
            let h = document.querySelector("#h").innerHTML = " 010-1234-1234";
            let cancle = document.querySelector("#cancle")
            let close = document.querySelector("#close")
           
            // 취소신청 확인 알림 후 창닫기 이벤트
            cancle.addEventListener("click", function () {
                let result = confirm("취소하시겠습니까?")
                if (result === true) {
                    alert("취소되었습니다.")
                    window.close(); 
                } else {      
                }
            })

            // 닫기 버튼 이벤트
            close.addEventListener("click", function () {
                window.close();

            })
        }

    </script>
    <style>
        /* 반응형 웹 %로 제공 */
        .substance {
            background-color: rgba(168, 156, 200, 0.6);
            height: 50px;
        }

        .title {
            font-family: 'KNUTRUTHTTF';
            font-size: 30px;
        }

        .hope table {
            width: 70%;
            border-collapse: collapse;
            margin: auto;

        }

        .hope td {
            font-family: "Wanted Sans Variable";
            border: 2px solid black;

        }

        .book_of_hope {
            text-align: center;

        }

        /* 버튼공용 */
        .hope .but {
            margin-top: 15px;
            font-family: "Wanted Sans Variable";
            width: 100px;
            background-color: rgba(155, 178, 225, 0.6);
            font-size: 18px;
            border: 0;
            border-radius: 5px;
            height: 30px;
        }

        .hope .but:hover {
            background-color: rgba(205, 155, 225, 0.6);
        }

        .hope .because {
            height: 200px;
        }
    </style>
</head>


<body class="book_of_hope">
    <h2 class="title">신청한 희망도서</h2>
    <section class="hope">
        <table>
            <tr>
                <td class="substance">
                    희망소장처
                </td>
                <td id="a"></td>

            </tr>
            <tr>
                <td class="substance">
                    자료명
                </td>
                <td id="b">

                </td>
            </tr>
            <tr>
                <td class="substance">
                    저자
                </td>
                <td id="c">

                </td>
            </tr>
            <tr>
                <td class="substance">
                    발행연도
                </td>
                <td id="d">

                </td>
            </tr>
            <tr>
                <td class="substance">
                    ISBN번호<br>
                    ISSN번호
                </td>
                <td id="e">

                </td>
            </tr>
            <tr>
                <td class="substance">
                    신청사유
                </td>
                <td class="because" id="f">

                </td>
            </tr>
            <tr>
                <td class="substance">
                    출판사
                </td>
                <td id="g">

                </td>
            </tr>
            <tr>
                <td class="substance">
                    휴대폰번호
                </td>
                <td id="h">

                </td>
            </tr>
        </table>
        <button type="button" class="button_revocation but" id="cancle">취소신청</button>
        <button type="button" class="button_x but" id="close">닫기</button>
    </section>

</body>

</html>