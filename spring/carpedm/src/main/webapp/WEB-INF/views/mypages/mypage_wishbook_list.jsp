<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>

<%@ page import="java.util.Map"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지 희망도서 신청목록</title>
<link href="/carpedm/resources/css/layout.css" rel="stylesheet">
<link href="/carpedm/resources/css/mypage.css" rel="stylesheet">
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
            
            //기존에 선택되어있는 표기개수를 재설정하기 위해 추가
        let case_value_opts = document.getElementById("case").options;
        for(let i = 0; i< case_value_opts.length;i++)
        {   
            if(case_value_opts[i].value == '<%=request.getAttribute("perPage")%>') {
            	case_value_opts.selectedIndex = i;
                break;
            }
        }
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

           
            // 정보수정 창으로 이동
            button1.addEventListener('click', function () {
                window.open('mypage_chginfo', '_self')

            });

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

 				// 선택항목 삭제
                document.querySelector("#cancle").addEventListener("click", function () {
                    let list_checked = document.querySelectorAll(".checkbox:checked");
                    let ids = [];
                    for (let i = 0; i < list_checked.length; i++) {
                        let row = list_checked[i].closest("tr");
                        let id = row.querySelector('input[type="hidden"]').value;
                        ids.push(id);
                        console.log(ids)
                    }
                    
                    let xhr = new XMLHttpRequest();
                    xhr.open("POST", "/carpedm/mypage_wishbook_list", true);
                    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === XMLHttpRequest.DONE) {
                            if (xhr.status === 200) {
                                // 요청이 성공적으로 완료됨
                                console.log("서버 응답:", xhr.responseText);
                                alert("취소가 완료 되었습니다.")
                                window.location.href = "/carpedm/mypage_wishbook_list";
                            } else {
                                // 요청이 실패함
                                console.error("서버 응답 오류:", xhr.status);
                            }
                        }
                    };
                    // ids 배열을 query string으로 변환합니다.
                    let queryString = "ids=" + encodeURIComponent(JSON.stringify(ids));

                    xhr.send(queryString);
                });

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
            let url = "/carpedm/wishbook_detail?w_id=" + w_id;
            window.open(url, "_blank", options);
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

    	    window.location.href = '/carpedm/mypage_wishbook_list?'
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
				<div class="notice_subject">마이페이지 희망도서 신청목록</div>
				<div>
    <!-- 내정보 -->
    <div class="div1">
        <table class="div1_table">
            <tr>
                <td class="info1">
                    <c:set var="myInfo" value="${requestScope.myInfo}" />
<!--                     <strong>내 정보</strong><br> -->
                    이름 : ${myInfo.m_name}<br>
                    번호 : ${myInfo.m_tel}<br>
                    주소 : ${myInfo.m_address}<br>
                    <c:choose>
                        <c:when test="${diff eq null or diff le 0}">
                            대출 가능 여부 : 대출 가능
                        </c:when>
                        <c:otherwise>
                            대출 가능 여부 : ${diff} 일
                        </c:otherwise>
                    </c:choose>
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
                    <form method="get" action="mypage_wishbook_list">
                        <input type="text" name="search">
                        <button>검색</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- 보드 -->
    </div>
    <table id="page1">
        <tr id="page1_tr">
            <th style="cursor:pointer;" onclick="sortTable(0,true)">번호</th>
            <th style="cursor:pointer;" onclick="sortTable(1,false)">희망소장처</th>
            <th style="cursor:pointer;" onclick="sortTable(2,false)">자료명</th>
            <th style="cursor:pointer;" onclick="sortTable(3,false)">저자</th>
            <!-- <th style="cursor:pointer;" onclick="sortTable(4,true)">발행년도</th> -->
            <!-- <th style="cursor:pointer;" onclick="sortTable(5,false)">신청사유</th> -->
            <th style="cursor:pointer;" onclick="sortTable(4,false)">출판사</th>
            <th style="cursor:pointer;" onclick="sortTable(5,false)">처리상태</th>
            <th>취소<input type="checkbox" id="selectAll"></th>
        </tr>

        <c:forEach var="item" items="${list}" varStatus="loop">
            <c:set var="state" value="${item.w_state}" />
            <c:set var="resStateString" value="" />
            <c:choose>
                <c:when test="${state eq '0'}">
                    <c:set var="resStateString" value="진행중" />
                </c:when>
                <c:when test="${state eq '1'}">
                    <c:set var="resStateString" value="완료" />
                </c:when>
                <c:when test="${state eq '2'}">
                    <c:set var="resStateString" value="취소" />
                </c:when>
                <c:when test="${state eq '3'}">
                    <c:set var="resStateString" value="반려" />
                </c:when>
                <c:otherwise>
                    <c:set var="resStateString" value="알 수 없음" />
                </c:otherwise>
            </c:choose>

            <tr class="tr">
                <td>${loop.index + 1}<input type="hidden" value="${item.w_id}"></td>
                <td>${item.lb_name}</td>
                <td>
                    <a href="javacsript:void(0);" onclick="popup('${item.w_id}')">
                        ${item.w_title}
                    </a>
                </td>
                <td>${item.w_author}</td>
                <!-- <td>${item.w_pubyear}</td> -->
                <!-- <td>${item.w_content}</td> -->
                <td>${item.w_publisher}</td>
                <td>${resStateString}</td>
                <td><input type="checkbox" class="checkbox"></td>
            </tr>
        </c:forEach>
    </table>
    <div id="button_cancle">
        <button id="cancle">취소</button>
    </div>
</div>

<%--
				<div id="paging">
					<%
					// 서블릿에서 불러온 페이징 정보
					int total_count = (int)request.getAttribute("totalViewCount");// 임시로 설정한 값
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
	</section>
 --%>

</body>

</html>