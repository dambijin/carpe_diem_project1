// todo 이벤트
// 대출회원 추가버튼 가져오기
window.addEventListener("load", function () {
    bind();
});

	function bind() {
		let add = document.querySelector("#member_add");

		add.addEventListener('click', function () { // 대출회원 추가 클릭이벤트

		// 테이블을 todlist 에 담아둠
		let todolist = document.querySelector("#todo_membertable");

		let member_html = '';

		// html += '</tr>';
		// 추가한다
		member_html +=		'<td>10</td>';
		member_html +=		'<td>임우혁</td>';
		member_html +=		'<td>2001-05-24</td>';
		member_html +=		'<td>010-1234-5678</td>';
		member_html +=		'<td>청주</td>';
		member_html +=		'<td>';
		member_html +=			'<a href="" onclick="overdue_popup()">3일</a>';
		member_html +=		'</td>';
		member_html +=		'<td><input type="button" value="조회" onclick=\'alert("예약목록 조회")\'></td>';
		member_html +=		'<td><input type="button" value="조회" onclick=\'alert("대출내역 조회")\'></td>';
		member_html +=		'<td><input type="button" value="수정" onclick="location.href=\'admin_member_chginfo.html\';"></td>';
		// html +=	'</tr>'

		let tr = document.createElement("tr"); // <tr></tr>
			tr.innerHTML = member_html;

		todolist.append(tr);
		})
	};

// 10개씩 누를 때 change 이벤트 : 변동이 있을 때 발생
function changeViewCount(count) {
	alert(count + "개씩 보기로 변경되었습니다.");
}

// 예약목록 조회 이벤트
function reservation_check() {
	alert("예약내역조회");
}

// 대출내역 조회 이벤트
function loan_check() {
	alert("대출내역조회");
}


// 검색기능
function search() {
    var textbox = document.getElementById("input_todo");
    if (textbox != null){
        alert(textbox.value + " ");
    }
}

// 엔터 이벤트
function enterkey() {
	if (window.event.keyCode == 13) {
 
		// 엔터키가 눌렸을 때 실행하는 반응
		search();
   }
}

var inputTodo = document.getElementById("input_todo");
if (inputTodo != null) {
    inputTodo.addEventListener("keydown", enterkey);
}

// 이름 클릭 시 
function member_list() {
	alert("회원 정보")
}