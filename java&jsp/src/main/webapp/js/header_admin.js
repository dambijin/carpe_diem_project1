function isLoggedIn() {
    // 예를 들어, 쿠키에 'login'이라는 이름의 값이 있는지 확인합니다.
    return document.cookie.includes('lc=');
// return true;
}
let loginSection;
if(isLoggedIn()) {
	loginSection = `
	<div class="sign_up_in">
		<div class="sign_up">
			<a href="/carpedm/logout">
				로그아웃&nbsp;
			</a>
		</div>
	</div>`;
}
else {
	loginSection = `
	<div class="sign_up_in">
		<div class="sign_in">
			<a href="/carpedm/sign_in">
				로그인&nbsp;
			</a>
		</div>
		<div class="sign_up">
			<a href="/carpedm/sign_up">
				회원가입
			</a>
		</div>
	</div>`;
}

document.querySelector('header').innerHTML = `
    <div class="blank_space"> </div>

    <div class="headerbox" onclick="location.href='/carpedm/main';">
    <img class="logo" width="30px"
    src="/carpedm/resource/logo.png">
        도서관리시스템
    </div>
	${loginSection}


    <div class="nav">
        <hr class="custom-hr">
        <div class="flex-header" onclick="location.href='/carpedm/admin_member_list';">회원목록</div>
        <div class="flex-header" onclick="location.href='/carpedm/admin_book_list';">재고</div>
        <div class="flex-header" onclick="location.href='/carpedm/admin_wishbook_list';">희망도서목록</div>
        <hr class="custom-hr">
    </div>
`;
