window.addEventListener("load", function () {
    QnAdetail();
})

function QnAdetail() {
    // 임시버튼
    let completion = document.querySelector("#completion");

    // 임시버튼 클릭시
    completion.addEventListener('click', function () {
        // div안에
        let reply = document.querySelector("#rs")

        let html = '';
        html += '<hr class="detail_hr"></hr>';
        html += '<table>';
        html += '<tr>';
        html += '<td class="subject">제목</td>';
        html += '<td colspan="5" class="subject_title">RE : 희망도서 신청 관련 문의</td>';
        html += '</tr>';
        html += '<tr>';
        html += '<td class="subject">작성자</td>';
        html += '<td>김**</td>';
        html += '<td class="subject">등록일</td>';
        html += '<td>2024-01-30</td>';
        html += '<td class="subject">조회</td>';
        html += '<td>46</td>';
        html += '</tr>';
        html += '<tr>';
        html += '<td class="subject">도서관</td>';
        html += '<td colspan="5">천안도서관</td>';
        html += '</tr>';
        html += '<tr>';
        html += '<td class="subject">첨부</td>';
        html += '<td colspan="5">첨부파일</td>';
        html += '</tr>';
        html += '<tr>';
        html += '<td class="content" colspan="6">글 내용입니다.</td>';
        html += '</tr>';
        html += '</table>';
        html += '<button type="button" class="notice_but">답글</button>';
        html += '<button type="button" class="notice_but" onclick="location.href=\'QnA_update.html\';">수정</button>';
        html += '<button type="button" class="notice_but">삭제</button>';


        let div = document.createElement("div");
        div.classList.add("QnA_detail");
        div.innerHTML = html;

        reply.append(div);


    });
};