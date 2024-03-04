<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자페이지(희망도서목록)</title>
    <link href="/carpedm/css/layout.css" rel="stylesheet">
</head>

<!-- function 스크립트 -->
<script src="/carpedm/js/admin_library.js"></script>

<script>
    window.addEventListener("load", function () {
        bind();
    });

    function bind() {
    	// 회원목록 가져옴		
<%-- 		let data_list = <%=getSelectQueryAll("select lb_id, w_title, w_author, w_isbn, w_pubyear, m_pid, w_name, w_pubyear, w_content from wishlist;")%> --%>
		

//         for (let i = 0; i <= data_list.length; i=i+9) {
//         	// 테이블을 가져와서 todolist변수에 담아둠
//         	let todolist = document.querySelector("#page1");
//             let html = '';

//             html += '</tr>';
//             추가한다
//             html += '<td>' + data_list[i] + '</td>';
//             html += '<td>' + data_list[i+1] + '</td>';
//             html += '<td>' + data_list[i+2] + '</td>';
//             html += '<td>' + data_list[i+3] + '</td>';
//             html += '<td>' + data_list[i+4] + '</td>';
//             html += '<td>' + data_list[i+5] + '</td>';
//             html += '<td>' + data_list[i+6] + '</td>';
//             html += '<td>' + data_list[i+7] + '</td>';
//             html += '<td>' + data_list[i+8] + '</td>';
//             html += '<td width="150px"><input type="button" value="완료" class="complete" onclick="complete()"> <input type = "button" value = "반려" class="companion" onclick = "companion()" ></td>';
//             // html +=	'</tr>'

//             let tr = document.createElement("tr"); // <tr></tr>
//             tr.innerHTML = html;

//             todolist.append(tr);
//         }

        //검색옵션 기본세팅
        let search_opt_list = ["회원번호", "책이름", "저자", "출판사", "ISBN", "발행년", "신청날짜", "회원ID"];

        for (let i = 0; i < search_opt_list.length; i++) {
            let search_opt = document.querySelector("#search_option");
            let html = '';
            html += search_opt_list[i];

            let opt = document.createElement("option");//<option></option>
            opt.innerHTML = html;//<option>serach_opt_list[i]</option>

            search_opt.append(opt);
        }

        // 검색에 입력값 받아와 enterkey 작동
        var inputTodo = document.getElementById("input_todo");
        if (inputTodo != null) {
            inputTodo.addEventListener("keydown", enterkey);
        };
        // 엔터 이벤트
        function enterkey() {
            if (window.event.keyCode == 13) {
                search();
            }
        }
    };


    // 검색기능
    function search() {
        var textbox = document.getElementById("input_todo");
        if (textbox != null) {
            alert(textbox.value + " ");
        }
    }


    function complete(w_id) {
    	alert("완료되었습니다 회망도서 ID: " + w_id);
    }


    function companion(w_id) {
    	alert("반려되었습니다 회망도서 ID: " + w_id);
    }

    // 책이름 클릭 시 팝업창 띄우기
    function popup(w_id) {
            let width = 600;
            let height = 800;
            let left = (window.innerWidth - width) / 2;
            let top = (window.innerHeight - height) / 2;
            let options = "width=" + width + ",height=" + height + ",left=" + left + ",top=" + top;
            let url = "/carpedm/wishbook_detail?w_id=" + w_id;
            window.open(url, "_blank", options);
     }

//     var request = new XMLHttpRequest();
// 	function searchFunction(){
// 		request.open("get", "./admin_wishbook_listServlet?input_todo=" + encodeURIComponent(document.getElementById("input_todo").value,true);
// 		request.onreadystatechange = searchProcess;
// 		request.send(null);
// 	}
// 	function searchProcess(){
// 		var table = document.getElementById("ajaxTable");
// 		table.innerHTML = "";
// 		if(request.readyState == 4 && request.status == 200){
// 			var object = eval('(' + request.responseText + ')');
// 			var result = object.result;
// 			for(var i = 0; i< result.length; i++){
// 				var row = table.insertRow(0);
// 				for(var j = 0; j< result[i].length; j++){
// 					cell.innerHTML = result[i][j].value;					
// 				}
// 			}
// 		}
// 	}

</script>

<style>
	.flex-header{
		cursor: pointer;
	}
	
    header .nav .wish_list {
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

    /* 검색창 */
    .search {
        width: 80%;
        margin: auto;
        text-align: center;
        margin-bottom: 10px;
    }

    .search select {
        width: 80px;
        height: 30px;
    }

    .search .textbox {
        width: 350px;
        height: 20px;
        padding: 2px;
        font-family: "Wanted Sans Variable";
        font-size: 17px;
    }

    .search .button {
        font-family: "Wanted Sans Variable";
        font-size: 18px;
        background-color: rgb(36, 116, 190);
        color: white;
        width: 50px;
        height: 27px;
        border: 0;
        border-radius: 5px;
        cursor: pointer;
    }

    /* 테이블 div */
    .table_div {
        width: 80%;
        margin: auto;
    }

    /* 테이블 */
    .wish_table {
        width: 100%;
        border-collapse: collapse;
        font-family: "Wanted Sans Variable";
    }

    /* 테이블 td th */
    .wish_table td,
    .wish_table th {
        border: 1px solid #000000;
        background-color: #fff;
        text-align: center;
        height: 30px;
    }

    /* 테이블 th */
    .wish_table th {
        height: 35px;
        background-color: rgba(163, 163, 163, 0.6);
    }
    
    /* 테이블 안에 input */
    .wish_table tr td input {
        font-family: "Wanted Sans Variable";
        font-size: 16px;
        background-color: rgba(71, 125, 231, 0.973);
        color: white;
        width: 70px;
        height: 20px;
        border: 0;
        border-radius: 5px;
        cursor: pointer;
    }

    /* 완료 반려 버튼 */
    .complete,
    .companion {
        font-family: "Wanted Sans Variable";
        font-size: 18px;
        background-color: rgba(71, 125, 231, 0.973);
        width: 50px;
        height: 30px;
        border: 0;
        border-radius: 5px;
        cursor: pointer;
    }

    /* 쪽이동 */
    .nextpage {
        width: 80%;
        margin: auto;
        text-align: center;
        margin-top: 30px;
    }

    /* 쪽이동 a 태그*/
    .underline_remove {
        text-decoration: none;
        font-size: 20px;
        color: black;
    }

    /* 책이름 팝업 */
    .book_name {
        color: blue;
        font-family: bold;
        text-decoration: underline;
        font-weight: bold;
        cursor: pointer;
    }

    h1 {
        font-family: "Wanted Sans Variable";
    }
</style>

<body>
    <!-- header -->
    <header></header>

    <div>
        <h1 align="center">희망도서목록</h1>
    </div>
    <!-- section   -->
    <section>
        <div class="search">
            <select align="center" class="select" id="search_option">
                <!-- 자바스크립트화 -->
            </select>
            <input type="text" class="textbox" id="input_todo">
            <button type="button" class="button"  onkeyup="enterkey()">검색</button>
<!--  input text 에 들어갈 것    onkeyup="searchFunction()" 
   	  button에 들어 갈 것		 onclick="searchFunction()" -->
        </div>

        <div class="table_div">
            <table class="wish_table" width="1100px" id="page1">
            	<thead>
	                <tr id="page1_tr">
	                    <th width="80px">희망도서ID</th>
	                    <th width="80px">책이름</th>
	                    <th width="80px">저자</th>
	                    <th width="80px">출판사</th>
	                    <th width="100px">회원ID</th>
	                    <th width="80px">소장기관</th>
	                    <th width="100px">신청날짜</th>
	                    <th width="100px">신청사유</th>
	                    <th width="100px">처리상태</th>
	                    <th width="100px">처리</th>
	                </tr>
                </thead>
                <tbody id ="ajaxTable">
					<%
					ArrayList<Map<String, String>> data_list = (ArrayList<Map<String, String>>) request.getAttribute("wishbook_list");
					%>

					<%
					for (int i = 0; i < data_list.size(); i++) {
					%>
					
					<tr>
						<td><%=data_list.get(i).get("w_id")%></td>
						<td><a href="javacsript:void(0);" onclick="popup('<%=data_list.get(i).get("w_id") %>')"><%=data_list.get(i).get("w_title")%></a></td>
						<td><%=data_list.get(i).get("w_author")%></td>
						<td><%=data_list.get(i).get("w_publisher")%></td>
						<td><%=data_list.get(i).get("m_pid")%></td>
						<td><%=data_list.get(i).get("lb_name")%></td>
						<td><%=data_list.get(i).get("w_pubyear")%></td>
						<td><%=data_list.get(i).get("w_content")%></td>
						<td><%=data_list.get(i).get("w_state")%></td>
						<td width="150px">
							<input type="button" value="완료" class="complete" onclick="complete('<%=data_list.get(i).get("w_id")%>')"> 
							<input type="button" value="반려" class="companion" onclick="companion('<%=data_list.get(i).get("w_id")%>')">
						</td>
					</tr>
					<%
					}
					%>
				</tbody>
            </table>
        </div>


    </section>

    <!-- 쪽이동 -->
    <!-- 쪽이동 -->
    <div class="paging nextpage">
        <a href="" class="pre underline_remove">◀</a>
        <strong class="underline_remove">1</strong>
        <a href="" class="num underline_remove">2</a>
        <a href="" class="num underline_remove">3</a>
        <a href="" class="num underline_remove">4</a>
        <a href="" class="num underline_remove">5</a>
        <a href="" class="next underline_remove">▶</a>
    </div>


    <!-- 헤더를 덮어씌우는 자바스크립트 -->
    <script src="/carpedm/js/header_admin.js"></script>
</body>

</html>