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
<title>마이페이지 대출 현황</title>
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
            let table = document.querySelector("#page1")
            
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

            // 정보수정 클릭시 페이지 이동
            button1.addEventListener('click', function () {
                window.open('/carpedm/mypage_chginfo', '_self')

            });

//             // 임시 게시판 생성
<%--              let data = <%=DBConn.getSelectQueryAll("select l_id,b_title,b_author,b_publisher,l_loandate,l_returnrealdate from loan INNER join book on loan.b_id = book.b_id where loan.l_returnrealdate is null")%> --%>
//             for (let i = 0; i < data.length/6; i++) {
//                 let html = '';
//                 html += '      <td>' + (i+1) + '</td>';
//                 html += '      <td>' + data[(i*5)] + '</td>';
//                 html += '      <td><a href="../mainpages/book_detail.jsp" class="bookname">'+ data[(i*5)+1] +'</a></td>';
//                 html += '      <td>'+ data[(i*5)+2] +'</td>';
//                 html += '     <td>'+ data[(i*5)+3] +'</td>';
//                 html += '     <td>'+ data[(i*5)+4] +'</td>';
//                 html += '     <td>'+ data[(i*5)+5] +'</td>';
//                 html += '       <td>중앙</td>';
//                 html += '  <td><button type="button" class="extension">연장</button></td>';


//                 let tr = document.createElement("tr");
               
//                 tr.classList.add("tr")
//                 tr.innerHTML = html;
//                 console.log(data)

//                 // 연장버튼 클릭 이벤트
//                 tr.querySelector(".extension").addEventListener("click", function () {
//                     alert("연장되었습니다.")
//                 })

//                 table.append(tr);
//             }

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
//             // 도서관 분류
<%--            let libs_list = <%=%>; --%>
//             for (let i = 0; i < libs_list.length; i++) {
//                 let html = "";
//                 let result_library_list = document.querySelector("#library")

//                 html += libs_list[i];

//                 let opt = document.createElement("option");
//                 opt.innerHTML = html;

//                 result_library_list.append(opt)

//             }
        };
        //true일때 숫자, false일때 문자 테이블정렬함수
		function sortTable(n, isNumeric) {
		    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
		    table = document.getElementById("page1");
		    switching = true;
		    dir = "asc";
		
		    while (switching) {
		        switching = false;
		        rows = table.getElementsByTagName("tr");
		
		        for (i = 1; i < (rows.length - 1); i++) {
		            shouldSwitch = false;
		            x = rows[i].getElementsByTagName("td")[n];
		            y = rows[i + 1].getElementsByTagName("td")[n];
		
		            var xContent = isNumeric ? Number(x.textContent.trim()) : x.textContent.trim();
		            var yContent = isNumeric ? Number(y.textContent.trim()) : y.textContent.trim();
		
		            if (dir == "asc") {
		                if (xContent > yContent) {
		                    shouldSwitch = true;
		                    break;
		                }
		            } else if (dir == "desc") {
		                if (xContent < yContent) {
		                    shouldSwitch = true;
		                    break;
		                }
		            }
		        }
		        if (shouldSwitch) {
		            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		            switching = true;
		            switchcount++;
		        } else {
		            if (switchcount == 0 && dir == "asc") {
		                dir = "desc";
		                switching = true;
		            }
		        }
		    }
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
				<a href="/carpedm/mypage_loan_status"><button type="button"
						class="sub_but">대출 현황</button></a><br> <a
					href="/carpedm/mypage_loan_history"><button type="button"
						class="sub_but">대출 내역</button></a><br> <a
					href="/carpedm/mypage_reservation_list"><button type="button"
						class="sub_but">예약</button></a> <a
					href="/carpedm/mypage_wishbook_list"><button type="button"
						class="sub_but">
						희망도서<br>신청목록
					</button></a>

			</div>
			<div class="right_section">
				<div class="notice_subject">마이페이지 대출 현황</div>
				<div>
					<!-- 내정보 -->
					<div class="div1">
						<table class="div1_table">
							<tr>
								<td class="info1">
									<%
									ArrayList<Map<String, String>> myInfo = (ArrayList<Map<String, String>>) request.getAttribute("myInfo");
									System.out.println(myInfo.size());
									%><Strong>내정보</Strong><br> 이름 : <%=myInfo.get(0).get("M_NAME")%><br>
									번호 : <%=myInfo.get(0).get("M_TEL")%><br> 주소 : <%=myInfo.get(0).get("M_ADDRESS")%><br>
									<%
									String loanstate_text = "대출가능";
									if (myInfo.get(0).get("M_LOANSTATE") != null && !myInfo.get(0).get("M_LOANSTATE").equals("0")) {
										loanstate_text = myInfo.get(0).get("M_LOANSTATE") + "일 연체상태";
									}
									%> 대출가능여부 : <%=loanstate_text%>
								<td>
									<button type="button" id="chginfo">정보수정</button>
								</td>
							</tr>


						</table>
						<!-- 분류 -->
						<div></div>
						<div id="select2">
							<div></div>
						</div>
					</div>
					<!-- 보드 -->
					<table id="page1">
						<tr id="page1_tr">
							<th style="cursor: pointer;" onclick="sortTable(0,true)">번호</th>
							<!-- 							<th style="cursor:pointer;" onclick="sortTable(0,true)">관리번호</th> -->
							<th style="cursor: pointer;" onclick="sortTable(1,false)">책이름</th>
							<th style="cursor: pointer;" onclick="sortTable(2,false)">저자</th>
							<th style="cursor: pointer;" onclick="sortTable(3,false)">출판사</th>
							<th style="cursor: pointer;" onclick="sortTable(4,true)">대출일</th>
							<th style="cursor: pointer;" onclick="sortTable(5,true)">반납예정일</th>
							<th style="cursor: pointer;" onclick="sortTable(6,false)">소장기관</th>
							<th>반납연기</th>
						</tr>
						<%
						ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) request.getAttribute("list");
						System.out.println(list.size());

						for (int i = 0; i < list.size(); i++) {
						%>
						<tr class="tr">
							<td><%=i + 1%></td>
							<%-- 							<td><%=list.get(i).get("l_id") %></td> --%>
							<td><%=list.get(i).get("b_title")%></td>
							<td><%=list.get(i).get("b_author")%></td>
							<td><%=list.get(i).get("b_publisher")%></td>
							<td><%=list.get(i).get("l_loandate").substring(0, 10)%></td>
							<td><%=list.get(i).get("l_returnrealdate") != null ? list.get(i).get("l_returnrealdate").substring(0, 10) : ""%></td>
							<td><%=list.get(i).get("lb_name")%></td>
							<td><button>연장</button></td>

						</tr>
						<%
						}
						%>

					</table>
				</div>

			</div>
		</div>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header.js"></script>
</body>


</html>