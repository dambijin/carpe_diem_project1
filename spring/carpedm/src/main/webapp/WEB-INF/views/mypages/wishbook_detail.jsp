<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>

<%@ page import="java.util.Map"%>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>도서 관리 시스템</title>
<!-- <link href="./css/layout.css" rel="stylesheet"> -->
<script>
        window.addEventListener("load", function () {
            bind();
        })

        function bind() {
            
            // 정보 임시 저장
//             let a = document.querySelector("#a").innerHTML = "두정도서관";
//             let b = document.querySelector("#b").innerHTML = " 100일만에 배우는 자바스크립트";
//             let c = document.querySelector("#c").innerHTML = "김자바";
//             let d = document.querySelector("#d").innerHTML = "2016년";
//             let e = document.querySelector("#e").innerHTML = "123456789";
//             let f = document.querySelector("#f").innerHTML = "제가 너무 읽고 싶은 책이었어요";
//             let g = document.querySelector("#g").innerHTML = "휴먼출판사";
//             let h = document.querySelector("#h").innerHTML = " 010-1234-1234";
           
            // 취소신청 확인 알림 후 창닫기 이벤트 
//         function cancle(w_id)
<%--     	<%// 세션에서 현재 아이디값 가져오기 --%>
// 		HttpSession getSession = request.getSession();
<%-- 		String login_m_pid = (String) getSession.getAttribute("m_pid");%> --%>
//         {
//         	 let url = 'wishbook_detail';
<%-- 	     	    let data = 'w_id=' + encodeURIComponent(w_id)+'&m_pid=' + encodeURIComponent(<%=login_m_pid%>); --%>
// 	     		//dopost로 보내기위한 코드
// 	     	    fetch(url, {
// 	     	      method: 'POST',
// 	     	      headers: {
// 	     	        'Content-Type': 'application/x-www-form-urlencoded',
// 	     	      },
// 	     	      body: data,
// 	     	    })
// 	     	    .then(response => response.json())
// 	     	    .then(data => {
// // 	     	    	console.log(data);
// 	         	  // 서버에서 전달한 결과 메시지에 따라 분기처리
// 	         	  if (data.message === 'success') {
// 	         	    alert(' 취소 신청 되었습니다.');
// 	         	    window.close();  // fetch가 완료된 후에 search 함수를 실행
// 	         	  }
// 	         	  else if (data.message === 'fail') {
// 	         	    alert('비로그인상태입니다. 로그인해주세요.');
// 	         	    window.location.href = "/carpedm_old/sign_in";
// 	         	  } else {
// 	         	    	alert('알 수 없는 오류가 발생하였습니다.');
// 	         	  }	
// 	     	    })
// 	     	    .catch((error) => console.error('Error:', error));
//         }

		<%HttpSession getSession = request.getSession();
String login_m_pid = (String) getSession.getAttribute("m_pid");%>
		
		function cancle(w_id) {
		    let xhr = new XMLHttpRequest();
		    
		    xhr.open("post", "wishbook_detail", true);
		    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		     
		    let param = "w_id=" + encodeURIComponent(w_id)+'&m_pid=' + encodeURIComponent(<%=login_m_pid%>)
		    console.log("param : " + param);
		    
		    xhr.send(param);
		    
		    xhr.onload = function() {
		        let data = xhr.responseText;
		        console.log("data : " + data);
		        	alert("취소 신청 완료");
		        	window.close();
		        	window.opener.location.reload();
		        
		    };
		 }
		
		document.querySelector("#cancle").addEventListener("click", () => {
			let ww_id = document.querySelector("#cancle").getAttribute("data-id");
			cancle(ww_id);
			
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
			<c:forEach var="item" items="${list}">
				<tr>
					<td class="substance">희망소장처</td>
					<td>${item.lb_name}</td>
				</tr>
				<tr>
					<td class="substance">자료명</td>
					<td>${item.w_title}</td>
				</tr>
				<tr>
					<td class="substance">저자</td>
					<td>${item.w_author}</td>
				</tr>
				<tr>
					<td class="substance">발행연도</td>
					<td>${item.w_pubyear}</td>
				</tr>
				<tr>
					<td class="substance">ISBN번호<br> ISSN번호
					</td>
					<td>${item.w_isbn}</td>
				</tr>
				<tr>
					<td class="substance">신청사유</td>
					<td class="because">${item.w_content}</td>
				</tr>
				<tr>
					<td class="substance">출판사</td>
					<td>${item.w_publisher}</td>
				</tr>
				<tr>
					<td class="substance">휴대폰번호</td>
					<td>${item.w_tel}</td>
				</tr>
			</c:forEach>

		</table>
		<button type="button" class="button_revocation but" id="cancle"
			data-id="${w_id}">취소신청</button>
		<button type="button" class="button_x but" id="close">닫기</button>
	</section>
	<script>
   // 닫기 버튼 이벤트
   			let cancle = document.querySelector("#cancle");
            let closes = document.querySelector("#close");
           
            closes.addEventListener("click", function () {
                window.close();
            })
        </script>
</body>

</html>