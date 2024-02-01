let bookpopup;

// 회원정보 수정
function chgInfo() {
    alert("수정되었습니다");
    location.href='admin_member_list.html';
}
// admin_book_list 등록 팝업창열기
function bookadd_popup() {
    bookpopup = window.open
    ("admin_book_add.html", "책 등록", "width=1000, height=800, left=100, top=100"); 
}
// admin_book_add 책 등록           미완성
function bookAdd() {
    alert("등록되었습니다");
    window.opener.location.reload();
    window.close();
}
// admin_book_add 닫기          미완성
function close() {
    bookpopup.close();
}
// 도서폐기 전체선택
function fn_allcheck() {
    let allcheck= document.myform.allcheck.checked; //true or false
	
	let len = document.myform.check.length;  //개수
	for(let i=0; i<len; i++) {
		document.myform.check[i].checked = allcheck;
    }
}

// 연체상태 popup창
function overdue_popup() {
    window.open
    ("admin_book_overdue.html", "팝업", "width=1000, height=700, left=100, top=100");
}
// 연체해제 누르면 확인 후 닫기         미완성
function overdue_close() {
    alert("연체해제 되었습니다");
}