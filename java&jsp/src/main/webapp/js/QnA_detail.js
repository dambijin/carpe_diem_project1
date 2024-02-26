window.addEventListener("load", function () {
    // 답글
    let completion = document.querySelector(".completion");
    // 답글 클릭시
    completion.addEventListener('click', function () {
        // div안에
        document.querySelector(".answer_detail").style.display = "block";

    });

    libsinfolist();
    answer();


    // 게시물 첨부파일 다운로드
    document.querySelector("#subject_file").innerHTML = '<a href="/path/to/your/file.pdf" download>첨부파일 다운로드</a>';

    // 게시물삭제버튼
    let QnA_delete = document.querySelector("#QnA_delete");
    // 삭제버튼 클릭시
    QnA_delete.addEventListener('click', function () {
        alert("삭제되었습니다.");
        location.href = 'QnA_board.html';
    });

    // 게시물삭제버튼
    let answer_delete = document.querySelector("#answer_delete");
    // 삭제버튼 클릭시
    answer_delete.addEventListener('click', function () {
        alert("삭제되었습니다.");
        location.href = 'QnA_detail.html';
    });




    // 파일첨부
    document.querySelector("#upload_file").addEventListener("change", function (changeEvent) {
        // 이벤트 객체의 'target' 속성에서 'files' 배열을 가져옵니다. 
        // 'files' 배열에는 사용자가 선택한 파일의 정보가 들어 있습니다.
        // 여기서는 첫 번째 파일만 사용하므로 'files[0]'을 선택합니다.
        var imgFile = changeEvent.target.files[0];

        // 파일 경로를 표시할 요소를 선택합니다.
        var fileName = document.getElementById("file_route");

        // 선택한 요소의 값에 파일 이름을 설정합니다.
        // 이렇게 하면 사용자가 선택한 파일의 이름이 웹 페이지에 표시됩니다.
        fileName.value = imgFile.name;
    });

    //등록일에 현재날짜 넣기
    var today = new Date(); // 현재 날짜와 시간을 가져옴
    var dd = String(today.getDate()).padStart(2, '0'); // 일자를 2자리 문자열로 변환
    var mm = String(today.getMonth() + 1).padStart(2, '0'); // 월을 2자리 문자열로 변환 (JavaScript의 월은 0부터 시작하므로 1을 더함)
    var yyyy = today.getFullYear(); // 연도를 가져옴

    today = yyyy + '-' + mm + '-' + dd; // 'YYYY-MM-DD' 형식의 문자열로 변환
    document.querySelector('.redate').innerHTML = today; // <input> 요소의 value 속성에 설정


    // 유효성검사
    // 등록 버튼
    let registration = document.querySelector("#registration");
    // 등록버튼 클릭시
    registration.addEventListener('click', function () {
        let textarea = document.querySelector('#awtext').value;
        if (textarea == "") {
            alert("내용을 입력해주세요.");
            document.querySelector('#awtext').focus();
        } else {
            alert("등록되었습니다.");
        }
    });

});

function answer() {
    // QnA 제목
    let title = document.querySelector('#qna_title').innerText;
    // 답글 제목
    let answer_title = document.querySelector('#answer_title');

    let html = '';
    html += 'Re : ';
    html += title;

    answer_title.value = html;
};



function libsinfolist() {
    // 도서관 select
    let libs_list = ["천안도서관", "두정도서관", "아우내도서관"];
    let libs_list_box = document.querySelector("#libs_info");

    for (let i = 0; i < libs_list.length; i++) {
        libs_list_box.innerHTML += "<option>" + libs_list[i] + "</option>";
    }
};

