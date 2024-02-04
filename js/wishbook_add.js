window.addEventListener("load", function () {
    request();
})


function request() {

    // 신청 버튼
    let reqbut = document.querySelector("#request_but");

    reqbut.addEventListener('click', function () {
        // select (천안도서관, 두정도서관, 아우내도서관)
        let lib = document.querySelector("#library");
        // 자료명 input
        let mrial = document.querySelector("#material");
        // 저자
        let writer = document.querySelector("#writer");
        // 발행연도
        let year = document.querySelector("#year");
        // ISBN번호 ISSN번호
        let is = document.querySelector("#is");
        // 사유
        let reason = document.querySelector("#reason");
        // 출판사
        let publisher = document.querySelector("#publisher");
        // 신청자이름
        let user = document.querySelector("#user_name");
        // 전화번호
        let handphone = document.querySelector("#handphone");

        alert(lib.value + "\n" + mrial.value + "\n" + writer.value + "\n" + year.value + "\n" + is.value + "\n" + reason.value + "\n" + publisher.value + "\n" + user.value + "\n" + handphone.value + "\n신청이 완료되었습니다.");

    });
};