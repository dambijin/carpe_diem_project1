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
<title>마이페이지 예약 목록</title>
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
            let table = document.getElementById('page1');
           
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
//             // 임시 보드 작성
<%--             let data = <%=DBConn.getSelectQueryAll("select b_title, b_author,b_publisher, r_resdate from reservation inner join book on reservation.b_id = book.b_id")%>     --%>
//             for(let i=0; i < data.length/4; i++){
//                 let html = '';

//                 html += '           <td>' + (i+1) + '</td>';
//                 html += '      <td><a href="../mainpages/book_detail.jsp" class="bookname">'+ data[(i*4)] +'</a></td>';
//                 html += '           <td>'+ data[(i*4)+1] +'</td>';
//                 html += '          <td>'+ data[(i*4)+2] +'</td>';
//                 html += '          <td>'+ data[(i*4)+3] +'</td>';
//                 html += '          <td></td>';
//                 html += '         <td>중앙</td>';
//                 html += '       <td><input type="checkbox" class="checkbox"></td>';


//                 let tr = document.createElement("tr");
//                 tr.classList.add("tr")
//                 tr.innerHTML = html;


                // 체크박스 전체선택 중 항목 체크해제시 전체선택 체크박스 해제
               console.log(document.querySelector(".checkbox"));
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
                
                 // 출력 개수
//             let case_list = ["10개", "20개", "30개"]
//             for (let i = 0; i < case_list.length; i++) {
//                 let html = "";
//                 let result_email_list = document.querySelector("#case")

//                 html += case_list[i];

//                 let opt = document.createElement("option");
//                 opt.innerHTML = html;

//                 result_email_list.append(opt)

             
//             }
            
           
             // 도서관 분류
<%--              let libs_list = <%=DBConn.getlibraryNameAll()%>; --%>
//             for (let i = 0; i < libs_list.length; i++) {
//                 let html = "";
//                 let result_library_list = document.querySelector("#library")

//                 html += libs_list[i];

//                 let opt = document.createElement("option");
//                 opt.innerHTML = html;

//                 result_library_list.append(opt)

            
// }
        }
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
		function redirectPage()
        {
			let perPage = document.getElementById("case").value;  
//     	    let currentPage = document.querySelector('#paging .paging a.num.active').textContent;

    	    window.location.href = '/carpedm/mypage_reservation_list?'
    	    + '&page=' + "1"
    	    + '&perPage=' + perPage;
        }

    </script>
<style>
/* 페이지 */
#paging {
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-top: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
	height: 50px;
}

#paging .total {
	font-weight: bold;
}

#paging .paging {
	display: flex;
}

#paging .paging a, #paging .paging strong {
	margin: 0 5px;
	padding: 5px 10px;
	border-radius: 5px;
	text-decoration: none;
	color: #333;
}

#paging .paging a {
	background-color: #f8f8f8;
}

#paging .paging a.num.active {
    color: blue;
    font-size: 20px;
    font-style: bold;
}

#paging .paging strong {
	background-color: #007bff;
	color: #fff;
</style>
</head>

<body>
	<header></header>
	<section>
		<!-- 여기부터 본문작성해주세요 -->
		<div class="s_section2">
			<div class="left_section">
				<a href="/carpedm/mypage_loan_status"><button
						type="button" class="sub_but">대출 현황</button></a><br> <a
					href="/carpedm/mypage_loan_history"><button
						type="button" class="sub_but">대출 내역</button></a><br> <a
					href="/carpedm/mypage_reservation_list"><button
						type="button" class="sub_but">예약</button></a> <a
					href="/carpedm/mypage_wishbook_list"><button
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

								<td class="info1">
									<%
									ArrayList<Map<String, String>> myInfo = (ArrayList<Map<String, String>>) request.getAttribute("myInfo");
									System.out.println(myInfo.size());
									%><Strong>내정보</Strong><br> 
									이름 : <%=myInfo.get(0).get("M_NAME")%><br>
									번호 : <%=myInfo.get(0).get("M_TEL")%><br> 
									주소 : <%=myInfo.get(0).get("M_ADDRESS")%><br>
									<% String loanstate_text = "대출가능";
								if(myInfo.get(0).get("M_LOANSTATE") != null && !myInfo.get(0).get("M_LOANSTATE").equals("0"))
								{
									loanstate_text = myInfo.get(0).get("M_LOANSTATE")+"일 연체상태";
								}
								%>
								대출가능여부 : <%=loanstate_text%></td>
								<td>
									<button type="button" id="chginfo">정보수정</button>
								</td>
							</tr>


						</table>
						<!-- 분류 -->
						<div>
							<div id="select">
								<div>
									<select id="case" onchange="redirectPage()">
										<option value=10>10개</option>
										<option value=20>20개</option>
										<option value=30>30개</option>
										<option value=40>40개</option>
										<option value=50>50개</option>
									</select>
								</div>
							</div>
							<div id="select1">
								<div>
									


								</div>
							</div>
						</div>
						<!-- 보드 -->
					</div>
					<table id="page1">
						<tr id="page1_tr">
							<th style="cursor:pointer;" onclick="sortTable(0,true)">번호</th>
							<th style="cursor:pointer;" onclick="sortTable(1,false)">책제목</th>
							<th style="cursor:pointer;" onclick="sortTable(2,false)">저자</th>
							<th style="cursor:pointer;" onclick="sortTable(3,false)">출판사</th>
							<th style="cursor:pointer;" onclick="sortTable(4,true)">신청일자</th>
							<th style="cursor:pointer;" onclick="sortTable(5,true)">대출가능일</th>
							<th style="cursor:pointer;" onclick="sortTable(6,false)">대출상태</th>							
							<th style="cursor:pointer;" onclick="sortTable(7,false)">소장기관</th>
							<th>취소 <input type="checkbox" id="selectAll">
							</th>
						</tr>

						<%
						ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) request.getAttribute("list");
						System.out.println(list.size());

						for (int i = 0; i < list.size(); i++) {
							 String resState = list.get(i).get("r_resstate");
							 String resStateString;
						%>
						<tr class="tr">
							<td><%=i + 1%></td>
							<td><%=list.get(i).get("b_title")%></td>
							<td><%=list.get(i).get("b_author")%></td>
							<td><%=list.get(i).get("b_publisher")%></td>
							<td><%=list.get(i).get("r_resdate").substring(0,10)%></td>
							<td><%=list.get(i).get("r_resdate").substring(0,10)%></td>
							<%    switch(resState) {
					        case "0":
					            resStateString = "예약중";
					            break;
					        case "1":
					            resStateString = "취소";
					            break;
					        case "2":
					            resStateString = "대출완료";
					            break;
					        default:
					            resStateString = "알 수 없음";
					            break; }
					    %>
							<td><%=resStateString%></td>
							
							<td><%=list.get(i).get("lb_name")%></td>

							<td><input type="checkbox" class="checkbox"></td>
						</tr>
						<%
						}
						%>

					</table>
				</div>
				<div id="button_cancle">
					<button id="cancle">취소</button>
				</div>
				<div id="paging">
					<%
					// 서블릿에서 불러온 페이징 정보
					int total_count = 5;// 임시로 설정한 값
					int perPage = 10;
					int current_page = 1;
				    int total_pages = total_count > 0 ? (int) Math.ceil((double) total_count / perPage) : 1;

					// 표시할 페이지의 범위 계산
					int start_page = Math.max(current_page - 2, 1);
					int end_page = Math.min(start_page + 4, total_pages);
					start_page = Math.max(1, end_page - 4);
					%>

					<div class="total_count">
						전체 : 총&nbsp;<%=total_count%>&nbsp;권
					</div>

					<div class="paging">
						<%
						if (current_page > 1) {
						%>
						<a href="?page=<%=current_page - 1%>&perPage=<%=perPage%>" class="pre">◀</a>
						<%
						}
						%>
						<%
						for (int i = start_page; i <= end_page; i++) {
						%>
						<a href="?page=<%=i%>&perPage=<%=perPage%>"
							class="<%=i == current_page ? "num active" : "num"%>"><%=i%></a>
						<%
						}
						%>
						<%
						if (current_page < total_pages) {
						%>
						<a href="?page=<%=current_page + 1%>&perPage=<%=perPage%>" class="next">▶</a>
						<%
						}
						%>
					</div>
					<div class="total">
						<strong><%=current_page%></strong>페이지 / 총 <strong><%=total_pages%></strong>페이지
					</div>
				</div>
			</div>
		</div>
		</div>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header.js"></script>
</body>

</html>