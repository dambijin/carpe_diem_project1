<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="header-container">
	<div class="blank_space" id="blank_space">
		<!-- 		<a href="/carpedm/admin_member_list">관리자페이지</a> -->
	</div>
	<div class="headerbox" onclick="location.href='/carpedm/main';">
		<img class="logo" width="30px"
			src="/carpedm/resources/resource/logo.png"> 도서관리시스템
	</div>
	<div class="sign_up_in" id="sign_up_in">
		<!-- 		<div class="sign_in"> -->
		<!-- 			<a href="/carpedm/sign_in">로그인&nbsp;</a> -->
		<!-- 		</div> -->
		<!-- 		<div class="sign_up"> -->
		<!-- 			<a href="/carpedm/sign_up">회원가입&nbsp;</a> -->
		<!-- 		</div> -->
	</div>
		<!-- 	<div class="sign_up_in"> -->
		<!-- 		<div class="sign_out"> -->
		<!-- 			<a href="/carpedm/logout">로그아웃&nbsp;</a> -->
		<!-- 		</div> -->
		<!-- 	</div> -->
	<div class="nav">
		<hr class="custom-hr">
		<div class="flex-header"
			onclick="location.href='/carpedm/admin_member_list';">회원목록</div>
		<div class="flex-header"
			onclick="location.href='/carpedm/admin_book_list';">재고</div>
		<div class="flex-header"
			onclick="location.href='/carpedm/admin_wishbook_list';">희망도서목록</div>
		<hr class="custom-hr">
	</div>
</div>


<script>
	function isLoggedIn() {
		// 예를 들어, 쿠키에 'lc'이라는 키의 값이 있는지 확인합니다.
		return document.cookie.includes('lc=');
		// return true;
	}

// 	function mchk() {
// 		// 예를 들어, 쿠키에 'login'이라는 이름의 값이 있는지 확인합니다.
// 		return document.cookie.includes('lc=9');
// 		// return true;
// 	}

	window.addEventListener("load",	function() {
						if (isLoggedIn()) {
							document.getElementById('sign_up_in').innerHTML = '<div class="sign_out"><a href="/carpedm/logout">로그아웃&nbsp;</a></div>';
// 							if (mchk()) {
// 								document.getElementById('blank_space').innerHTML = '<a href="/carpedm/admin_member_list">관리자페이지</a>'
// 							}
						} else {
							document.getElementById('sign_up_in').innerHTML = '<div class="sign_in"><a href="/carpedm/sign_in">로그인&nbsp;</a></div><div class="sign_up"><a href="/carpedm/sign_up"> &nbsp;회원가입&nbsp;</a></div>';
						}
					})

	//     let mchkSection = '';
	//     let loginSection = '';
	//     function generateHeader() {
	//         let headerContainer = document.getElementById('header-container');
	//         if (isLoggedIn()) {
	//             loginSection = `
	//                 <div class="sign_up_in">
	//                     <div class="sign_up">
	//                         <a href="/carpedm/logout">로그아웃&nbsp;</a>
	//                     </div>
	//                 </div>`;

	//             if (mchk()) {
	//                 mchkSection = '<div class="blank_space"><a href="/carpedm/admin_member_list">관리자페이지</a></div>';
	//             }
	//         } else {
	//             loginSection = `
	//                 <div class="sign_up_in">
	//                     <div class="sign_in">
	//                         <a href="/carpedm/sign_in">로그인&nbsp;</a>
	//                     </div>
	//                     <div class="sign_up">
	//                         <a href="/carpedm/sign_up">회원가입</a>
	//                     </div>
	//                 </div>`;
	//         }

	//     }

	//     // 페이지 로드 시 헤더 생성
	//     window.onload = generateHeader;
</script>

