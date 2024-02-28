<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>도서관 안내</title>
<link href="/carpedm/css/layout.css" rel="stylesheet">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style>
.library-guide {
	display: flex;
	gap: 20px;
	padding: 20px;
}

.library-image {
	width: 300px;
	height: 300px;
}

.library-text {
	display: flex;
	flex-direction: column;
	justify-content: center;
}

.library-text h1 {
	font-weight: bold;
	margin-bottom: 10px;
	text-shadow: 0px 0px black, 0px 1px black, 1px 0px black, 0px 0px black;
	color: rgba(199, 156, 200, 1.0);
	font-size: 30px;
}

h3 {
	color: rgba(239, 168, 176, 1.0);
	margin-bottom: 0;
}
</style>
<script>
        window.onload = function () {
            // 이후 list를 사용한 코드 작성(폐기처분)
//             let left_section = document.querySelector(".left_section");
<%--     		let libs_list = JSON.parse('<%= request.getAttribute("list") %>');  // JSON 문자열을 JavaScript 객체로 변환 --%>
//     		console.log(libs_list);
//             for (let i = 0; i < libs_list.length; i++) {
//                 let lib_text = libs_list[i].lb_name;
//                 let plushtml = "";
//                 plushtml += "<button type='button' class='sub_but' onclick='chg_text_detail(\"" + i + "\")'>" + lib_text + "</button><br>";
                
// //                 left_section.innerHTML += plushtml;
//             }

//             if (libs_list.length > 0) {
//                 chg_text_detail(0);                
//             }
//             else {
//                 console.log("가져올 파일이 없습니다.");
//             }
        };

        function chg_text_detail(libidx) {//폐기처분
        	
        	
<%--         	let data_list =<%=DBConn.getSelectQueryAll("select lb_name,lb_content from library")%>; --%>
<%-- 			let libs_list = JSON.parse('<%=request.getAttribute("list")%>');  // JSON 문자열을 JavaScript 객체로 변환 --%>
//     		let data = "";
//             data = data+'<h3>이용시간</h3><br>'+libs_list[libidx].lb_openTime;
//             data = data+'<h3>위치</h3><br>'+libs_list[libidx].lb_address;
//             data = data+'<h3>연락처</h3><br>'+libs_list[libidx].lb_tel;
//             data = data+'<h3>특이사항</h3><br>'+libs_list[libidx].lb_content;
//             data = data.replace(/\n/g, '<br>');   // 텍스트 파일 내의 줄바꿈('\n')을 HTML의 줄바꿈('<br>')으로 변환합니다.
//             // 변환된 데이터를 웹 페이지에 추가
//             document.getElementsByClassName('text_detail')[0].innerHTML = data;
//             // 도서관 이름을 웹 페이지의 특정 요소에 추가합니다.
//             var h1 = document.getElementsByClassName('lib_name')[0];
//             h1.innerHTML = libs_list[libidx].lb_name;
//             //이미지
//             document.querySelector(".library-image").setAttribute("src",libs_list[libidx].lb_imgUrl);
//             document.querySelector(".library-image").setAttribute("src","/carpedm/resource/logo.png");
        };
    </script>
</head>

<body>
	<header></header>
	<section>
		<!-- 여기부터 본문작성해주세요 -->
		<div class="s_section">
			<div class="left_section">
				<%
				
				ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) request.getAttribute("list");
				for (int i = 0; i < list.size(); i++) {
				%>
				<button type='button' class='sub_but'
					onclick="location.href='libs_infolist?lb=<%=i%>'"><%=list.get(i).get("lb_name")%></button>
				<br>
				<%
				}
				%>
			</div>
			<div class="right_section">
				<div class="library-guide">
				<%int lb_id = Integer.parseInt((String)request.getAttribute("lb")) ; %>
					<img class="library-image" src="<%=list.get(lb_id).get("lb_imgUrl")%>" alt="이미지 오류"
						class="library-image">
					<div class="library-text">
						<h1 class="lib_name" style="margin-bottom: 0;"><%=list.get(lb_id).get("lb_name")%></h1>

						<h3>이용시간</h3>
						<p><%=list.get(lb_id).get("lb_openTime").replace("\n", "<br>")%></p>

						<h3>위치</h3>
						<p><%=list.get(lb_id).get("lb_address").replace("\n", "<br>")%></p>

						<h3>연락처</h3>
						<p><%=list.get(lb_id).get("lb_tel").replace("\n", "<br>")%></p>

						<h3>특이사항</h3>
						<p><%=list.get(lb_id).get("lb_content").replace("\n", "<br>")%></p>
						<p class="text_detail"></p>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- 헤더를 덮어씌우는 자바스크립트 -->
	<script src="/carpedm/js/header.js"></script>
</body>

</html>