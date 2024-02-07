// 이용정보 달력
$(function () {
    $("#datepicker").datepicker({
        nextText: '다음달'
        , prevText: '이전달'
        // 요일 표기 바꾸기
        , dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
        // 영어month 한글로 바꾸기
        , monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
        // 월 년 으로 표기되던거 년 월로 바꾸기
        , showMonthAfterYear: true
        // 앞 뒤 월의 날짜 표기
        , showOtherMonths: true
        // year년으로 붙이기
        , yearSuffix: "년"
    });

});

window.addEventListener("load", function () {
    announcement();
    libsinfolist();
    chg_text_detail("천안도서관");

    // 도서검색 버튼 엔터이벤트
    let textbox = document.getElementById("searchWord");
    // Enter 키 이벤트 리스너 추가
    textbox.addEventListener("keyup", function (event) {
        // keyCode 13은 Enter 키를 나타냅니다
        if (event.keyCode === 13) {
            event.preventDefault(); // 기본 동작인 폼 제출 방지
            search(); // 검색 함수 호출
        }
    });
});

// 도서검색 버튼 클릭
function search() {
    let textbox = document.getElementById("searchWord");
    if (textbox.value == "") {
        alert("내용을 입력해주세요");
        document.querySelector('#searchWord').focus();
    } else {
        alert(textbox.value + "을 검색했습니다");
        window.location.href = 'book_search.html';
    }

};

function chg_text_detail(sel) {
    // 'fetch' 함수를 사용하여 특정 URL에서 데이터를 가져옴.
    fetch('메인페이지 이용정보/' + sel + '.txt').then(function (response) {
        // 'response.text()'는 응답 본문을 텍스트로 읽은 후 반환
        // 'response.text()'로부터 반환, 'data'는 텍스트 파일의 내용     
        return response.text();
    }).then(function (data) {
        data = data.replace(/\n/g, '<br>');   // 텍스트 파일 내의 줄바꿈('\n')을 HTML의 줄바꿈('<br>')으로 변환합니다.
        data = data.replace('이용시간', '<b>이용시간</b>');
        data = data.replace('휴관일', '<b>휴관일</b>');
        // 변환된 데이터를 웹 페이지에 추가
        document.querySelector('.text_detail').innerHTML = data;
        // 도서관 이름을 웹 페이지의 특정 요소에 추가합니다.
    }).catch(function (error) {
        // 오류가 발생 시 콘솔에 출력
        console.error('Error:', error);
    });
};

function announcement() {
    // 공지사항
    // 임시추가버튼을 클릭했을때
    let completion = document.querySelector("#completion");

    // 클릭하면
    completion.addEventListener('click', function () {
        //테이블안에
        let announ = document.querySelector("#announcement_table")

        let html = '';
        html += '<td class="ann_title">';
        html += '독서토론대회';
        html += '</td>';
        html += '<td class="ann_day">';
        html += '2023-01-06';
        html += '</td>';


        let tr = document.createElement("tr");
        tr.innerHTML = html;

        announ.append(tr);
        // 만약에 5개의 공지사항이 모두 있을 경우 맨아래 tr 필요
    });

    // 신착도서
    // 임시추가버튼
    let newbut = document.querySelector("#newbook_but");

    // 클릭하면
    newbut.addEventListener('click', function () {
        // tr 안에
        let newbook = document.querySelector("#nb")

        let html = '';

        html += '<div>';
        html += '<img class="newbook_img" src="https://cdn.discordapp.com/attachments/1200354574037434461/1201422289519005766/reading.png?ex=65c9c2b3&is=65b74db3&hm=e5dca025187ad91185fa40db48b0a231f86ad9a3d7e890102ebe60fcf09dce5b&">';
        html += '</div>';
        html += '<div class="newbook_div">자바스크립트</div>';
        html += '<div class="newbook_div">저자 : 김땡땡</div>';

        let td = document.createElement("td");
        td.innerHTML = html;

        newbook.append(td);
    });
};



function libsinfolist() {
    // 도서관 select
    let libs_list = ["천안도서관", "두정도서관", "아우내도서관"];
    let libs_list_box = document.querySelector("#libs_info");

    for (let i = 0; i < libs_list.length; i++) {
        libs_list_box.innerHTML += "<option>" + libs_list[i] + "</option>";
    }


    // 자료검색 select
    let libs_search = ["전체", "제목", "저자", "발행처", "키워드"];
    let libs_searchbox = document.querySelector("#libsear");

    for (let i = 0; i < libs_search.length; i++) {
        libs_searchbox.innerHTML += "<option>" + libs_search[i] + "</option>";
    }


    // 이용정보 select 변경될때
    let select = document.querySelector("#libs_info");
    select.addEventListener("change", function () {
        let sel = select.value;
        chg_text_detail(sel);

    });
};









