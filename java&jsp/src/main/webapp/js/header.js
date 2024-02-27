document.querySelector('header').innerHTML = `
    <div class="blank_space"><a href="/carpedm/admin/admin_member_list.jsp">관리자페이지</a></div>

    <div class="headerbox" onclick="location.href='/carpedm/mainpages/main.jsp';">
        <img class="logo" width="30px"
            src="/carpedm/resource/logo.png">
        도서관리시스템
    </div>
    <div class="sign_up_in">
        <div class="sign_in">
            <a href="/carpedm/sign/sign_in.jsp">
                로그인&nbsp;
            </a>
        </div>
        <div class="sign_up">
            <a href="/carpedm/sign/sign_up.jsp">
                회원가입
            </a>
        </div>
    </div>


    <div class="nav">
        <hr class="custom-hr">
        <div class="flex-header dropdown">
        도서관 서비스
            <div class="dropdown-content">
                <a href="/carpedm/mainpages/book_search.jsp">자료검색</a>
            </div>
        </div>
        <div class="flex-header dropdown">도서관 안내
        <div class="dropdown-content">
            <a href="/carpedm/libs_infolist">이용안내</a>
            </div>
        </div>
        <div class="flex-header dropdown">정보광장
            <div class="dropdown-content">
                    <a href="/carpedm/board/notice_board.jsp">공지사항</a>
                    <a href="/carpedm/board/QnA_board.jsp">Q&A</a>
                    <a href="/carpedm/board/wishbook_add.jsp">희망도서신청</a>
            </div>
        </div>
        <div class="flex-header dropdown">My Page
            <div class="dropdown-content">
                <a href="/carpedm/mypage/mypage_loan_status.jsp">대출현황</a>
                <a href="/carpedm/mypage/mypage_loan_history.jsp">대출내역</a>
                <a href="/carpedm/mypage/mypage_reservation_list.jsp">예약</a>
                <a href="/carpedm/mypage/mypage_wishbook_list.jsp">희망도서<br>신청목록</a>
            </div>
        </div>
        <hr class="custom-hr">
    </div>
`;