function isLoggedIn() {
	// 예를 들어, 쿠키에 'lc'이라는 키의 값이 있는지 확인합니다.
	return document.cookie.includes('lc=');
	// return true;
}

function mchk() {
	// 예를 들어, 쿠키에 'login'이라는 이름의 값이 있는지 확인합니다.
	return document.cookie.includes('lc=9');
	// return true;
}

let loginSection;
let mchkSection = '<div class="blank_space"></div>';

if (isLoggedIn()) {
	loginSection = `
	<div class="sign_up_in">
		<div class="sign_up">
			<a href="/carpedm_old/logout">
				로그아웃&nbsp;
			</a>
		</div>
	</div>`;

	if (mchk()) {
		mchkSection = '<div class="blank_space"><a href="/carpedm_old/admin_member_list">관리자페이지</a></div>';
	}
}
else {
	loginSection = `
	<div class="sign_up_in">
		<div class="sign_in">
			<a href="/carpedm_old/sign_in">
				로그인&nbsp;
			</a>
		</div>
		<div class="sign_up">
			<a href="/carpedm_old/sign_up">
				회원가입
			</a>
		</div>
	</div>`;
}

document.querySelector('header').innerHTML = `
	${mchkSection}
	<div class="headerbox" onclick="location.href='/carpedm_old/main';">
		<img class="logo" width="30px" src="/carpedm_old/resource/logo.png">
		도서관리시스템
	</div>
	${loginSection}
	<div class="nav">
		<hr class="custom-hr">
		<div class="flex-header dropdown">
		도서관 서비스
			<div class="dropdown-content">
				<a href="/carpedm_old/book_search">자료검색</a>
			</div>
		</div>
		<div class="flex-header dropdown">도서관 안내
		<div class="dropdown-content">
			<a href="/carpedm_old/libs_infolist">이용안내</a>
			</div>
		</div>
		<div class="flex-header dropdown">정보광장
			<div class="dropdown-content">
					<a href="/carpedm_old/notice_board">공지사항</a>
					<a href="/carpedm_old/QnA_board">Q&A</a>
					<a href="/carpedm_old/wishbook_add">희망도서신청</a>
			</div>
		</div>
		<div class="flex-header dropdown">My Page
			<div class="dropdown-content">
				<a href="/carpedm_old/mypage_loan_status">대출현황</a>
				<a href="/carpedm_old/mypage_loan_history">대출내역</a>
				<a href="/carpedm_old/mypage_reservation_list">예약</a>
				<a href="/carpedm_old/mypage_wishbook_list">희망도서<br>신청목록</a>
			</div>
		</div>
		<hr class="custom-hr">
	</div>
`;

