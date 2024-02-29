<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>

<%@ page import="java.util.Map"%>


<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지 희망도서 신청목록</title>
<link href="/carpedm/css/layout.css" rel="stylesheet">
<link href="/carpedm/css/mypage.css" rel="stylesheet">
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
            let table = document.getElementById('page1')

            // 내정보 
//             let myInfo = `
//                     <strong>내정보</strong><br>
//                     이름 : 홍길동<br>
//                     번호 : 010-0000-0000<br>
//                     주소 : 천안시 아산동 병천읍<br>
//                     회원번호 : --<br>
//                     대출가능여부 : --`;
//             let info1 = document.querySelector(".info1")
//             info1.innerHTML = myInfo;

            // 취소버튼 클릭시 팝업 알림
            button3.addEventListener('click', function () {
                alert('취소 완')

            });
            // 정보수정 창으로 이동
            button1.addEventListener('click', function () {
                window.open('http://localhost:8080/carpedm/mypage_chginfo', '_self')

            });

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
<%--            let libs_list = <%=DBConn.getlibraryNameAll()%>; --%>
//             for (let i = 0; i < libs_list.length; i++) {
//                 let html = "";
//                 let result_library_list = document.querySelector("#library")

//                 html += libs_list[i];

//                 let opt = document.createElement("option");
//                 opt.innerHTML = html;

//                 result_library_list.append(opt)

//             }



            // 임시 보드 작성
<%--             let data = <%=DBConn.getSelectQueryAll("select w_title, w_author, w_pubyear, w_isbn, w_content, w_publisher, w_tel, w_date from wishlist;")%> --%>
//             for (let i = 1; i <= 10; i++) {
//                 let html = '';
				
//                 html += '            <td>' + i ;
//                 html += '            <td>천안</td>';
//                 html += '           <td><a href="" onclick=popup() class="bookname">'+ data[i] +'</a></td>';
//                 html += '           <td>'+ data[i] +'</td>';
//                 html += '          <td>1998</td>';
//                 html += '          <td>?</td>';
//                 html += '          <td>정상</td>';
//                 html += '         <td>중앙</td>';
//                 html += '        <td>010-0000-0000</td>';
//                 html += '      <td>2024.03.03</td>';
//                 html += '      <td>정상</td>';
//                 html += '       <td><input type="checkbox" class="checkbox"></td>';


//                 let tr = document.createElement("tr");
//                 tr.classList.add("tr")
//                 tr.innerHTML = html;

                // 체크박스 전체선택 중 항목 체크해제시 전체선택 체크박스 해제
                document.querySelector(".checkbox")
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
//                 table.append(tr)
            
        
        };
        function popup(w_id) {
            let width = 600;
            let height = 800;
            let left = (window.innerWidth - width) / 2;
            let top = (window.innerHeight - height) / 2;
            let options = "width=" + width + ",height=" + height + ",left=" + left + ",top=" + top;
            let url = "http://localhost:8080/carpedm/wishbook_detail?w_id=" + w_id;
            window.open(url, "_blank", options);
        }
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
				<a href="http://localhost:8080/carpedm/mypage_loan_status"><button
						type="button" class="sub_but">대출 현황</button></a><br> <a
					href="http://localhost:8080/carpedm/mypage_loan_history"><button
						type="button" class="sub_but">대출 내역</button></a><br> <a
					href="http://localhost:8080/carpedm/mypage_reservation_list"><button
						type="button" class="sub_but">예약</button></a> <a
					href="http://localhost:8080/carpedm/mypage_wishbook_list"><button
						type="button" class="sub_but">
						희망도서<br>신청목록
					</button></a>
			</div>
			<div class="right_section">
				<div class="notice_subject">마이페이지 희망도서 신청목록</div>
				<div>

					<!-- 내정보 -->
					<div class="div1">
						<table class="div1_table">
							<tr>
								<td class="info1">
									<% ArrayList<Map<String,String>> myInfo = (ArrayList<Map<String,String>>)request.getAttribute("myInfo"); 
							System.out.println(myInfo.size());
							
							%><Strong>내정보</Strong><br> 이름 : <%=myInfo.get(0).get("M_NAME") %><br>
									번호 : <%=myInfo.get(0).get("M_TEL") %><br> 주소 : <%=myInfo.get(0).get("M_ADDRESS") %><br>
									회원번호 : <%=myInfo.get(0).get("M_PID") %><br> <% String loanstate_text = "대출가능";
								if(myInfo.get(0).get("M_LOANSTATE") != null && !myInfo.get(0).get("M_LOANSTATE").equals("0"))
								{
									loanstate_text = "대출불가";
								}
								%> 대출가능여부 : <%=loanstate_text%>
								</td>
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
										<% ArrayList<Map<String,String>> library = (ArrayList<Map<String,String>>)request.getAttribute("library"); 
							System.out.println(myInfo.size());
								for(int i=0; i < library.size(); i++){
							%>
										<option><%=library.get(i).get("LB_NAME") %></option>
										<%} %>
									</select>
								</div>
							</div>
						</div>

						<!-- 보드 -->
					</div>
					<table id="page1">
						<tr id="page1_tr">
							<th>번호</th>
							<th>희망소장처</th>
							<th>자료명</th>
							<th>저자</th>
							<th>발행년도</th>

							<th>신청사유</th>
							<th>출판사</th>

							<th>처리상태</th>
							<th>취소 <input type="checkbox" id="selectAll"></th>
						</tr>

						<% ArrayList<Map<String,String>> list = (ArrayList<Map<String,String>>)request.getAttribute("list"); 
							System.out.println(list.size());
							
							for(int i = 0; i< list.size(); i++)
                         {%>
						<tr class="tr">
							<td><%=i+1 %></td>
							<td><%=list.get(i).get("lb_name") %></td>
							<td><a href=""
								onclick="popup('<%=list.get(i).get("w_id") %>')"> <%=list.get(i).get("w_title") %>
							</a></td>
							<td><%=list.get(i).get("w_author") %></td>
							<td><%=list.get(i).get("w_pubyear") %></td>

							<td><%=list.get(i).get("w_content") %></td>
							<td><%=list.get(i).get("w_publisher") %></td>

							<td>정상</td>
							<td><input type="checkbox" class="checkbox"></td>
						</tr>

						<%}
							
                         %>

					</table>
				</div>
				<div id="button_cancle">
					<button id="cancle">취소</button>
				</div>
			</div>
		</div>
	</section>

	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header.js"></script>
</body>

</html>