document.querySelector('header').innerHTML = `
    <div class="blank_space"><a href="admin_member_list.jsp">관리자페이지</a></div>

    <div class="headerbox" onclick="location.href='main.jsp';">
        <img class="logo" width="30px"
            src="./resource/logo.png">
        도서관리시스템
    </div>
    <div class="sign_up_in">
        <div class="sign_in">
            <a href="sign_in.html">
                로그인&nbsp;
            </a>
        </div>
        <div class="sign_up">
            <a href="sign_up.html">
                회원가입
            </a>
        </div>
    </div>


    <div class="nav">
        <hr class="custom-hr">
        <div class="flex-header dropdown">
        도서관 서비스
            <div class="dropdown-content">
                <a href="book_search.html">자료검색</a>
            </div>
        </div>
        <div class="flex-header dropdown">도서관 안내
        <div class="dropdown-content">
            <a href="libs_infolist.html">이용안내</a>
            </div>
        </div>
        <div class="flex-header dropdown">정보광장
            <div class="dropdown-content">
                    <a href="notice_board.html">공지사항</a>
                    <a href="QnA_board.html">Q&A</a>
                    <a href="wishbook_add.html">희망도서신청</a>
            </div>
        </div>
        <div class="flex-header dropdown">My Page
            <div class="dropdown-content">
                <a href="mypage_loan_status.html">대출현황</a>
                <a href="mypage_loan_history.html">대출내역</a>
                <a href="mypage_reservation_list.html">예약</a>
                <a href="mypage_wishbook_list.html">희망도서<br>신청목록</a>
            </div>
        </div>
        <hr class="custom-hr">
    </div>
`;