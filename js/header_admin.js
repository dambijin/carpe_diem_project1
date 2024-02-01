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
        <div class="flex-header" onclick="location.href='admin_member_list.html';">회원목록</div>
        <div class="flex-header" onclick="location.href='admin_book_list.html';">재고</div>
        <div class="flex-header" onclick="location.href='admin_wishbook_list.html';">희망도서목록</div>
        <hr class="custom-hr">
    </div>
`;
