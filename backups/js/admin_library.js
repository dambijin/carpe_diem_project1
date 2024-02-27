
// 닫기
function closePopup() {
    window.open('', '_self', '');
    window.close();
}
// 도서폐기 전체선택
function fn_allcheck() {
    let allcheck= document.myform.allcheck.checked; //true or false
	
	let len = document.myform.check.length;  //개수
	for(let i=0; i<len; i++) {
		document.myform.check[i].checked = allcheck;
    }
}

