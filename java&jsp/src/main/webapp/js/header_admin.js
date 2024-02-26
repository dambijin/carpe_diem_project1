document.querySelector('header').innerHTML = `
    <div class="blank_space"> </div>

    <div class="headerbox" onclick="location.href='main.html';">
    <img class="logo" width="30px"
    src="../resource/logo.png">
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
        <div class="flex-header" onclick="location.href='admin_member_list.html';">회원목록</div>
        <div class="flex-header" onclick="location.href='admin_book_list.html';">재고</div>
        <div class="flex-header" onclick="location.href='admin_wishbook_list.html';">희망도서목록</div>
        <hr class="custom-hr">
    </div>
`;
