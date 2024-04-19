<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>예약내역 popup</title>
    <link href="/carpedm_old/css/layout.css" rel="stylesheet">
</head>

<!-- function 스크립트 -->
<script src="
/carpedm_old/js/admin_library.js"></script>
<script>

    window.addEventListener("load", function () {
        bind();
    });

    function bind() {
    	
    	<%
		ArrayList<Map<String, String>> data_list = (ArrayList<Map<String, String>>) request.getAttribute("reser_list");
		%>
		
        // 전체선택 이벤트
        document.querySelector("#select_all").addEventListener("click", function (event) {
            let list_check = document.querySelectorAll(".checkbox");

            for (let i = 0; i < list_check.length; i++) {
                list_check[i].checked = event.target.checked;
            }       // 데이터가 늘어나도 체크박스 적용됨
        })

        //폐기버튼 알림창 및 폐기 클릭 시 remove()
        // 연체해제버튼 클릭했을 때
        // 체크된 애들 querySelectorAll을 모두 가져와 변수에 담음
        document.getElementById('button_cancle').addEventListener('click', function () {
            let list_checked = document.querySelectorAll(".checkbox:checked");

            if (list_checked.length == 0) {
                alert("폐기할 항목을 선택해주세요.");
            } else if (confirm("선택한 항목을 폐기하시겠습니까?")) {
                for (let i = 0; i < list_checked.length; i++) {
                    // 체크된 걸 for문으로 찾고 체크박스의 부모(td)의 부모(tr)을 remove
                    list_checked[i].parentNode.parentNode.remove();
                }
                alert("폐기되었습니다.");
            } else {
                alert("선택된 항목이 없습니다.");
            }
        });

    };

 	// 닫기
    function closePopup() {
        window.open('', '_self', '');
        window.close();
    }

</script>

<style>
    header .nav .book_in {
        background-color: rgba(168, 156, 200, 0.6);
        color: #000000;
        height: 45px;
        line-height: 40px;
        display: inline-block;
        border-radius: 5px;
        width: 150px;
        font-size: 20px;
        margin-top: 10px;
    }

    /* section */
    section {
        font-family: "Wanted Sans Variable";
    }

    .add_table {
        border-collapse: collapse;
    }

    .add_table th {
        background-color: rgba(163, 163, 163, 0.6);
    }

    .add_table tr {
        background-color: #fff;
    }

    .add_table tr .bookname {
        width: 500px;
    }

    .add_table th,
    .add_table td {
        border: 1px solid black;
        height: 40px;
        text-align: center;
    }

    /*  등록 취소 버튼 (맨 아래) */
    .input {
        border: 1px;
        width: 20%;
        margin: auto;
        text-align: center;
        margin-top: 20px;
    }

    .input .button {
        font-family: "Wanted Sans Variable";
        font-size: 18px;
        background-color: rgba(71, 125, 231, 0.973);
        border: 0;
        width: 80px;
        height: 30px;
        border-radius: 5px;
        color: white;
    }

    .input .button:hover {
        background-color: rgba(205, 155, 225, 0.6);
    }
</style>

<body>
    <!-- header -->
    <header></header>

    <!-- section -->
    <section>
        <div>
            <h2 align="center">예약목록</h2>
        </div>

        <div>
			<table class="add_table" border="0" width="900px" align="center"
				cellpadding="5" cellspacing="1" bgcolor="cccccc"
				id="todo_booktable">
				<thead>
					<tr id="page1_tr">
							<th style="cursor:pointer;" onclick="sortTable(0,true)">번호</th>
							<th style="cursor:pointer;" onclick="sortTable(1,false)">책제목</th>
							<th style="cursor:pointer;" onclick="sortTable(2,false)">저자</th>
							<th style="cursor:pointer;" onclick="sortTable(3,false)">출판사</th>
							<th style="cursor:pointer;" onclick="sortTable(4,true)">신청일자</th>
							<th style="cursor:pointer;" onclick="sortTable(5,true)">대출가능일</th>
							<th style="cursor:pointer;" onclick="sortTable(6,false)">예약상태</th>							
							<th style="cursor:pointer;" onclick="sortTable(7,false)">소장기관</th>
							<th>취소 <input type="checkbox" id="selectAll">
							</th>
						</tr>
				</thead>
				<tbody>
					<c:if test="${not empty list }">
						<c:forEach var="item" items="${list}" varStatus="loop">
						    <c:set var="resState" value="${item.r_resstate}" />
						    <c:set var="resStateString" value="" />
						    <c:choose>
						        <c:when test="${resState eq '0'}">
						            <c:set var="resStateString" value="예약중" />
						        </c:when>
						        <c:when test="${resState eq '1'}">
						            <c:set var="resStateString" value="취소" />
						        </c:when>
						        <c:when test="${resState eq '2'}">
						            <c:set var="resStateString" value="대출완료" />
						        </c:when>
						        <c:otherwise>
						            <c:set var="resStateString" value="알 수 없음" />
						        </c:otherwise>
						    </c:choose>
						
						    <tr class="tr">
						        <td>${loop.index + 1}</td>
						        <td>${item.b_title}</td>
						        <td>${item.b_author}</td>
						        <td>${item.b_publisher}</td>
						        <td>${fn:substring(item.r_resdate, 0, 10)}</td>
						        <td>${fn:substring(item.r_resdate, 0, 10)}</td>
						        <td>${resStateString}</td>
						        <td>${item.lb_name}</td>
						        <td><input type="checkbox" class="checkbox" /></td>
						    </tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
    </section>

    <!-- 등록 취소 -->
    <div class="input">
        <button type="button" value="취소" class="button" id="button_cancle" onclick="closeOverduePopup()">취소</button>
        <input type="reset" value="닫기" class="button" onclick="closePopup()">
    </div>

</body>

</html>