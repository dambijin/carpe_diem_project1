<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>

<%@ page import="java.util.Map"%>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지 대출 내역</title>
<link href="/carpedm/resources/css/layout.css" rel="stylesheet">
<link href="/carpedm/resources/css/mypage.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous">
        </script>
<script>

        window.addEventListener("load", function () {
            bind();
        });
        
        function bind() {
            // 정보수정 버튼
            let button1 = document.querySelector('#chginfo');
            //  보드
            let table = document.querySelector("#page1")
            
             //기존에 선택되어있는 표기개수를 재설정하기 위해 추가
        let case_value_opts = document.getElementById("case").options;
        for(let i = 0; i< case_value_opts.length;i++)
        {   
            if(case_value_opts[i].value == '${perPage}') {
            	case_value_opts.selectedIndex = i;
                break;
            }
        }
            
            
            // 내정보 
           
//             let myInfo = `
//                     <strong>내정보</strong><br>
//                     이름 :  홍길동<br>
//                     번호 : 010-0000-0000<br>
//                     주소 : 천안시 아산동 병천읍<br>
//                     회원번호 : --<br>
//                     대출가능여부 : --`;
//             let info1 = document.querySelector(".info1")
//             info1.innerHTML = myInfo;
           


            // 정보수정 창으로 이동
            button1.addEventListener('click', function () {
                window.open('mypage_chginfo', '_self')

            });
//             // 임시 보드 내용 추가
<%--             let data = <%=DBConn.getSelectQueryAll("select l_id, b_title, b_author, b_publisher, l_loandate, l_returnrealdate from loan inner join book on loan.b_id = book.b_id")%> --%>
//             for (let i = 0; i < 10; i++) {            	            	
//                 let html = '';
//                 html += '<tr class="tr">';
//                 html += '      <td>' + (i+1) + '</td>';
//                 html += '      <td>' + data[(i*6)] + '</td>';
//                 html += '      <td><a href="../mainpages/book_detail.jsp" class="bookname">' + data[(i*6)+1] + '</a></td>';
//                 html += '      <td>'+ data[(i*6)+2] + '</td>';
//                 html += '     <td>' + data[(i*6)+3] +'</td>';
//                 html += '     <td>'+ data[(i*6)+4]+'</td>';
//                 html += '     <td>'+data[(i*6)+5]+'</td>';
//                 html += '       <td>중앙</td>';
//                 html += '</tr>';

//                 table.innerHTML += html;
            	
              
//             }  console.log(data)
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
<%--             let libs_list = <%=DBConn.getlibraryNameAll()%>; --%>
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
		function redirectPage()
        {
			let perPage = document.getElementById("case").value;  
//     	    let currentPage = document.querySelector('#paging .paging a.num.active').textContent;

    	    window.location.href = '/carpedm/mypage_loan_history?'
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
}
</style>
</head>

<body>
	<header></header>
	<section>
		<!-- 여기부터 본문작성해주세요 -->
		<div class="s_section2">
			<div class="left_section">
			<a href="/carpedm/mypage_loan_status">
				<button type="button" class="sub_but">대출 현황</button></a><br> 
				<a href="/carpedm/mypage_loan_history">
				<button type="button"class="sub_but">대출 내역</button></a><br> 
				<a href="/carpedm/mypage_reservation_list">
				<button type="button" class="sub_but">예약 목록</button></a> 
				<a href="/carpedm/mypage_wishbook_list">
				<button type="button" class="sub_but">희망도서<br>신청목록</button></a>
				<a href="/carpedm/mypage_bookcart">
				<button type="button" class="sub_but">장바구니</button></a>

			</div>
			<div class="right_section">
				<div class="notice_subject">마이페이지 대출 내역</div>
				<div>
					<!-- 내정보 -->

					<div class="div1">
						<table class="div1_table">

							<tr>

								<td class="info1"><c:set var="myInfo"
										value="${requestScope.myInfo}" /> <!--                     <strong>내 정보</strong><br> -->
									이름 : ${myInfo.m_name}<br> 번호 : ${myInfo.m_tel}<br> 주소
									: ${myInfo.m_address}<br> <c:choose>
										<c:when test="${diff eq null or diff le 0}">
                            대출 가능 여부 : 대출 가능
                        </c:when>
										<c:otherwise>
                            대출 가능 여부 : ${diff} 일
                        </c:otherwise>
									</c:choose></td>
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
										<option value="10">10개</option>
										<option value="20">20개</option>
										<option value="30">30개</option>
										<option value="40">40개</option>
										<option value="50">50개</option>
									</select>


								</div>

							</div>
							<div id="select1">
								<div>
									<form method="get" action="mypage_loan_history">
										<input type="text" name="search" placeholder="책 제목을 입력하세요">
										<button>검색</button>
									</form>
								</div>
							</div>
						</div>
						<!-- 보드 -->
						<table id="page1">
							<tr id="page1_tr">
								<th style="cursor: pointer;" onclick="sortTable(0,true)">번호</th>
								<th style="cursor: pointer;" onclick="sortTable(1,false)">자료명/등록번호</th>
								<th style="cursor: pointer;" onclick="sortTable(2,false)">저자</th>
								<th style="cursor: pointer;" onclick="sortTable(3,true)">대출일</th>
								<th style="cursor: pointer;" onclick="sortTable(4,true)">실반납일</th>
								<th style="cursor: pointer;" onclick="sortTable(5,false)">소장기관</th>
							</tr>
							<c:forEach var="item" items="${list}" varStatus="loop">
								<tr class="tr">
									<td>${loop.index + 1}</td>
									<td>${item.b_title}</td>
									<td>${item.b_author}</td>
									<td>${fn:substring(item.l_loandate, 0, 10)}</td>
									<td>${item.l_returnrealdate != null ? fn:substring(item.l_returnrealdate, 0, 10) : ""}</td>
									<td>${item.lb_name}</td>
								</tr>
							</c:forEach>
						</table>
						<div id="paging">
					<%-- 서블릿에서 불러온 페이징 정보 --%>
					<c:set var="total_count" value="${loan_count}" />
					<c:set var="perPage" value="${perPage}" />
					<c:set var="current_page" value="${page}" />

					<%-- 표시할 페이지의 범위 계산 --%>
					<c:set var="total_pages" value="${total_pages}" />
					<c:set var="start_page" value="${start_page}" />
					<c:set var="end_page" value="${end_page}" />

					<div class="total_count">
						전체 : 총&nbsp;
						<c:out value="${total_count}" />
						&nbsp;권
					</div>
					<div class="total">
						<strong><c:out value="${current_page}" /></strong>페이지 / 총 <strong><c:out
								value="${total_pages}" /></strong>페이지
					</div>
					<div class="paging">
						<c:if test="${current_page > 1}">
							<a href="?page=${current_page - 1}&perPage=${perPage}"
								 class="pre">◀</a>
						</c:if>
						<c:forEach var="i" begin="${start_page}" end="${end_page}">
							<a href="?page=${i}&perPage=${perPage}" 
								class="${i == current_page ? 'num active' : 'num'}">${i}</a>
						</c:forEach>
						<c:if test="${current_page < total_pages}">
							<a href="?page=${current_page + 1}&perPage=${perPage}"
								 class="next">▶</a>
						</c:if>
					</div>
				</div>
					
			<%--			<div id="paging">
							<%
							// 서블릿에서 불러온 페이징 정보
							int total_count = (int) request.getAttribute("totalViewCount");// 임시로 설정한 값
							int perPage = Integer.parseInt((String) request.getAttribute("perPage"));
							int current_page = Integer.parseInt((String) request.getAttribute("page"));
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
								<a href="?page=<%=current_page - 1%>&perPage=<%=perPage%>"
									class="pre">◀</a>
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
								<a href="?page=<%=current_page + 1%>&perPage=<%=perPage%>"
									class="next">▶</a>
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
		</div>--%>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->

</body>

</html>