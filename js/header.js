document.querySelector('header').innerHTML = `
    <div class="blank_space"> </div>

    <div class="headerbox">
        <img class="logo" width="30px"
            src="https://cdn.discordapp.com/attachments/1200354574037434461/1201422289519005766/reading.png">
        도서관리시스템
    </div>
    <div class="sign_up_in">
        <div class="sign_in">
            로그인&nbsp;
        </div>
        <div class="sign_up">
            회원가입
        </div>
    </div>


    <div class="nav">
        <hr class="custom-hr">
        <div class="flex-header dropdown">
        도서관 서비스
            <div class="dropdown-content">
                <a href="#">자료검색</a>
            </div>
        </div>
        <div class="flex-header dropdown">도서관 안내
        <div class="dropdown-content">
            <a href="#">이용안내</a>
            </div>
        </div>
        <div class="flex-header dropdown">정보광장
            <div class="dropdown-content">
                    <a href="#">공지사항</a>
                    <a href="#">Q&A</a>
                    <a href="#">희망도서신청</a>
            </div>
        </div>
        <div class="flex-header dropdown">My Page
            <div class="dropdown-content">
                <a href="#">대출현황</a>
                <a href="#">대출내역</a>
                <a href="#">예약</a>
                <a href="#">희망도서<br>신청목록</a>
            </div>
        </div>
        <hr class="custom-hr">
    </div>
`;