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
<title>마이페이지 예약 목록</title>
<link href="../css/layout.css" rel="stylesheet">
<link href="../css/mypage.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous">
        </script>
<script>
        window.addEventListener("load", function () {
            bind();
        })




        function bind() {
            let button1 = document.getElementById('chginfo');
            let button3 = document.getElementById('cancle');
            let table = document.getElementById('page1');
           
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


            // 취소버튼 클릭시 팝업 알림
            button3.addEventListener('click', function () {
                alert('취소 완')

            });
            // 정보수정 창으로 이동
            button1.addEventListener('click', function () {
                window.open('mypage_chginfo.jsp', '_self')

            });
            // 임시 보드 작성
            let data = <%=DBConn.getSelectQueryAll("select b_title, b_author,b_publisher, r_resdate from reservation inner join book on reservation.b_id = book.b_id")%>    
            for(let i=0; i < data.length/4; i++){
                let html = '';

                html += '           <td>' + (i+1) + '</td>';
                html += '      <td><a href="../mainpages/book_detail.jsp" class="bookname">'+ data[(i*4)] +'</a></td>';
                html += '           <td>'+ data[(i*4)+1] +'</td>';
                html += '          <td>'+ data[(i*4)+2] +'</td>';
                html += '          <td>'+ data[(i*4)+3] +'</td>';
                html += '          <td></td>';
                html += '         <td>중앙</td>';
                html += '       <td><input type="checkbox" class="checkbox"></td>';


                let tr = document.createElement("tr");
                tr.classList.add("tr")
                tr.innerHTML = html;
                console.log(data)

                // 체크박스 전체선택 중 항목 체크해제시 전체선택 체크박스 해제
                tr.querySelector(".checkbox")
                    .addEventListener("click", function (event) {

                        if (!event.target.checked) {

                            document.querySelector("#selectAll").checked = false;
                        } else {

                            let allCount = document.querySelectorAll(".checkbox").length;
                            let checkedCount = document.querySelectorAll(".checkbox:checked").length;
                            if (allCount == checkedCount) {
                                document.querySelector("#selectAll").checked = true;
                            } else {
                                document.querySelector("#selectAll").checked = false;
                            }
                        }
                    })


                // 전체선택 체크
                document.querySelector("#selectAll").addEventListener("click", function (event) {

                    let list_check = document.querySelectorAll(".checkbox")

                    if (event.target.checked) {
                        for (let i = 0; i < list_check.length; i++) {
                            list_check[i].checked = true;
                        }
                    } else {
                        for (let i = 0; i < list_check.length; i++) {
                            list_check[i].checked = false;
                        }
                    }

                });

                // 선택항목 삭제
                document.querySelector("#cancle").addEventListener("click", function () {
                    let list_checked = document.querySelectorAll(".checkbox:checked")
                    for (let i = 0; i < list_checked.length; i++) {
                        list_checked[i].parentNode.parentNode.remove();
                    }
                })
                table.append(tr)
            }
            
            // 출력 개수
            let case_list = ["10개", "20개", "30개"]
            for (let i = 0; i < case_list.length; i++) {
                let html = "";
                let result_email_list = document.querySelector("#case")

                html += case_list[i];

                let opt = document.createElement("option");
                opt.innerHTML = html;

                result_email_list.append(opt)

            }
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
				<a href="mypage_loan_status.jsp"><button type="button"
						class="sub_but">대출 현황</button></a><br> <a
					href="mypage_loan_history.jsp"><button type="button"
						class="sub_but">대출 내역</button></a><br> <a
					href="mypage_reservation_list.jsp"><button type="button"
						class="sub_but">예약</button></a> <a href="mypage_wishbook_list.jsp"><button
						type="button" class="sub_but">
						희망도서<br>신청목록
					</button></a>
			</div>
			<div class="right_section">
				<div class="notice_subject">마이페이지 예약 목록</div>
				<div>
					<!-- 내정보 -->
					<div class="div1">
						<table class="div1_table">

							<tr>

								<td class="info1"></td>
								<td>
									<button type="button" id="chginfo">정보수정</button>
								</td>
							</tr>


						</table>
						<!-- 분류 -->
						<div>
							<div id="select">
								<div>

									<select id="case">
										<option disabled selected>출력 건수</option>


									</select>
								</div>
							</div>
							<div id="select1">
								<div>
									<select id="library">
										<option disabled selected>- 도서관 전체</option>

									</select>


								</div>
							</div>
						</div>
						<!-- 보드 -->
					</div>
					<table id="page1">
						<tr id="page1_tr">
							<th>번호</th>
							<th>책제목</th>
							<th>저자</th>
							<th>출판사</th>
							<th>신청일자/반납예정일</th>
							<th>반납일/예약가능일</th>
							<th>소장기관</th>
							<th>취소 <input type="checkbox" id="selectAll">
							</th>
						</tr>

					</table>
				</div>
				<div id="button_cancle">
					<button id="cancle">취소</button>
				</div>
				<div class="paging">
					<a href="" class="pre underline_remove">◀</a> <strong
						class="underline_remove">1</strong> <a href=""
						class="num underline_remove">2</a> <a href=""
						class="num underline_remove">3</a> <a href=""
						class="num underline_remove">4</a> <a href=""
						class="num underline_remove">5</a> <a href=""
						class="next underline_remove">▶</a>
				</div>
			</div>
		</div>
		</div>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="../js/header.js"></script>
</body>

</html>