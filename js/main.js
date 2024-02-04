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
})


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

window.addEventListener("load", function () {
    libsinfolist();
});

function libsinfolist() {
    let select = document.querySelector("#libs_info");
    let libstime = document.getElementById("libs_time");

    select.addEventListener("change", function () {
        let sel = select.value;

        if (sel === "천안도서관") {
            let html = '';
            html += '이용시간<br>';
            html += '- 평일 : 오전 9시 ~ 오후 9시 <br>';
            html += '- 토요일 : 오전 9시 ~ 오후 7시 <br>';
            html += '- 공휴일 : 휴무 <br> <br>';
            libstime.innerHTML = html;
        } else if (sel === "두정도서관") {
            let html = '';
            html += '이용시간 <br>';
            html += '- 평일 : 오전 9시 ~ 오후 9시 <br>';
            html += '- 토요일 : 오전 9시 ~ 오후 8시 <br>';
            html += '- 공휴일 : 휴무 <br> <br>';
            libstime.innerHTML = html;
        } else if (sel === "아우내도서관") {
            let html = '';
            html += '이용시간<br>';
            html += '- 평일 : 오전 9시 ~ 오후 9시 <br>';
            html += '- 토요일 : 오전 10시 ~ 오후 6시 <br>';
            html += '- 공휴일 : 휴무 <br> <br>';
            libstime.innerHTML = html;
        } else {
            libstime.textContent = "";
        }

    });
};

