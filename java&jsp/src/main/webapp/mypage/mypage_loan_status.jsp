<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><!DOCTYPE html>
    <%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="carpedm.DBConn"%>
<%@ page import="carpedm.MypageDBConn"%>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지 대출 현황</title>
    <link href="../css/layout.css" rel="stylesheet">
    <link href="../css/mypage.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous">
        </script>
    <script>
        window.addEventListener("load", function () {
            bind();

        })

        function bind() {
            let button1 = document.getElementById('chginfo');
            let table = document.querySelector("#page1")
            
            // 내정보 
            let myInfo = `
                    <strong>내정보</strong><br>
                    이름 : 홍길동<br>
                    번호 : 010-0000-0000<br>
                    주소 : 천안시 아산동 병천읍<br>
                    회원번호 : --<br>
                    대출가능여부 : --`;
            let info1 = document.querySelector(".info1")
            info1.innerHTML = myInfo;

            // 정보수정 클릭시 페이지 이동
            button1.addEventListener('click', function () {
                window.open('mypage_chginfo.jsp', '_self')

            });

            // 임시 게시판 생성
             let data = <%=DBConn.getSelectQueryAll("select l_id,b_title,b_author,b_publisher,l_loandate,l_returnrealdate from loan INNER join book on loan.b_id = book.b_id where loan.l_returnrealdate is null")%>
            for (let i = 0; i <= 10; i++) {
                let html = '';
                html += '      <td>' + (i+1) + '</td>';
                html += '      <td><a href="../mainpages/book_detail.jsp" class="bookname">'+ data[i] +'</a></td>';
                html += '      <td></td>';
                html += '     <td>아몰랑</td>';
                html += '     <td>2024.01.20</td>';
                html += '     <td>정상</td>';
                html += '       <td>중앙</td>';
                html += '  <td><button type="button" class="extension">연장</button></td>';


                let tr = document.createElement("tr");
               
                tr.classList.add("tr")
                tr.innerHTML = html;
                console.log(data)

                // 연장버튼 클릭 이벤트
                tr.querySelector(".extension").addEventListener("click", function () {
                    alert("연장되었습니다.")
                })

                table.append(tr);
            }

            // 출력 개수
            // let case_list = ["10개", "20개", "30개"]
            // for (let i = 0; i < case_list.length; i++) {
            //     let html = "";
            //     let result_email_list = document.querySelector("#case")

            //     html += case_list[i];

            //     let opt = document.createElement("option");
            //     opt.innerHTML = html;

            //     result_email_list.append(opt)

            // }
            // 도서관 분류
            let library_list = ["두정도서관", "천안도서관", "아우내도서관"]
            for (let i = 0; i < library_list.length; i++) {
                let html = "";
                let result_library_list = document.querySelector("#library")

                html += library_list[i];

                let opt = document.createElement("option");
                opt.innerHTML = html;

                result_library_list.append(opt)

            }
        };

    </script>

    <style>

    </style>
</head>

<body>
    <header></header>
    <section>
        <!-- 여기부터 본문작성해주세요 -->
        <div class="s_section2">
            <div class="left_section">
                <a href="mypage_loan_status.jsp"><button type="button" class="sub_but">대출 현황</button></a><br>
                <a href="mypage_loan_history.jsp"><button type="button" class="sub_but">대출 내역</button></a><br>
                <a href="mypage_reservation_list.jsp"><button type="button" class="sub_but">예약</button></a>
                <a href="mypage_wishbook_list.jsp"><button type="button" class="sub_but">희망도서<br>신청목록</button></a>

            </div>
            <div class="right_section">
                <div class="notice_subject">
                    마이페이지 대출 현황
                </div>
                <div>
                    <!-- 내정보 -->
                    <div class="div1">
                        <table class="div1_table">
                           
                            <tr>
                              
                                <td class="info1">

                                </td>

                                <td> <button type="button" id="chginfo">정보수정</button></td>
                            </tr>


                        </table>
                        <!-- 분류 -->
                        <div>
                           
                            </div>
                            <div id="select2">
                                <div>
                                   <select id="library">
                                            <option disabled selected> - 도서관 전체</option>

                                        </select>


                                </div>
                            </div>
                        </div>
                        <!-- 보드 -->
                        <table id="page1">
                            <tr id="page1_tr">
                                <th>번호</th>
                                <th>책이름</th>
                                <th>저자</th>
                                <th>출판사</th>
                                <th>대출일/반납예정일</th>
                                <th>대출상태</th>
                                <th>소장기관</th>
                                <th>반납연기</th>
                            </tr>

                        </table>
                    </div>

                </div>
            </div>
        </div>

    </section>
    <!-- 헤더를 덮어씌우는 자바스크립트 -->
    <script src="../js/header.js"></script>
</body>


</html>