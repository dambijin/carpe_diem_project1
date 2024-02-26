<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>책 이름</title>
    <link href="../css/layout.css" rel="stylesheet">

    <style>
        section {
            width: 80%;
            margin: auto;
            /* font-family: 'KNUTRUTHTTF'; */
            font-family: 'Wanted Sans Variable';
        }

        /* 상세정보관련 */
        .view {
            display: flex;
            justify-content: center;
            border: 1px solid black;
            padding: 20px;
            width: 90%;
            margin: auto;
        }

        .view dl {
            width: 95%;
            display: flex;
        }

        .view dt {
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        .view dd {
            flex: 2;
            display: flex;
            flex-direction: column;
            padding-left: 20px;
            border-left: 1px solid black;
        }

        .view .ico {
            font-weight: bold;
            margin-bottom: 0px;
        }

        .view ul {
            list-style: none;
            padding: 0;
            margin-bottom: 0px;
        }

        .view ul li {
            margin-bottom: 5px;
            border-bottom: 1px dotted #888;
        }

        .view ul li .fb {
            font-weight: bold;
            color: #007bff;
        }

        .view ul li a {
            color: #007bff;
            text-decoration: none;
        }

        /* 소장정보 */
        .table {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
        }

        .table h3 {
            font-size: 1.2em;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .table table {
            width: 100%;
            border-collapse: collapse;
        }

        .table table thead th {
            background-color: #f8f8f8;
            padding: 10px;
            border: 1px solid #ccc;
            text-align: left;
        }

        .table table tbody td {
            padding: 10px;
            border: 1px solid #ccc;
            vertical-align: center;
        }

        .table table tbody td a {
            color: #007bff;
            text-decoration: none;
        }

        .table table tbody .reservation_success {
            color: blue;
            text-decoration: underline;
        }

        .table table tbody .reservation_success:hover {
            background-color: rgba(199, 156, 200, 0.6);
            color: black;
        }

        .table table tbody ._fail {
            color: red;
        }
        .table table tbody ._success {
            color: blue;
        }
        .table .info h3 {
            margin-top: 20px;
        }

        .table .info .content_text {
            color: #888;
            font-size: 0.9em;
        }
    </style>
</head>

<script>
    window.addEventListener("load", function () {

        // 현재 페이지의 URL
        let url = window.location.href;

        // URL의 쿼리 문자열을 파싱
        let params = new URLSearchParams(url.split('?')[1]);

        let title = decodeURI(params.get('title'));
        let topic = decodeURI(params.get('topic'));
        let author = decodeURI(params.get('author'));
        let publisher = decodeURI(params.get('publisher'));
        let year = decodeURI(params.get('year'));
        let callNumber = decodeURI(params.get('callNumber'));
        let registerNumber = decodeURI(params.get('registerNumber'));
        let library = decodeURI(params.get('library'));
        let loan_state = params.get('loan_state') == 'true';
        let reservation_state = params.get('reservation_state') == 'true';

        //가져온값에 없어서 일단 임시로...나중에 db를 붙이면 해결될 듯
        let isbn = "8955501609";
        let pages = "554p";
        let detail_text= "상세내용 주저리주저리"

        //타이틀변경
        document.title = title;

        //메인정보
        let html = `
        <dl>
            <dt>
                <em class="label">
                    <img src="https://cdn.discordapp.com/attachments/1200354574037434461/1201422289519005766/reading.png"
                        alt="KDC : 005.73" width="50px" />
                </em>
            </dt>
            <dd>
                <div class="ico ico-bk"><span>${title}</span></div>
                <ul>
                    <li class="label_no"><strong>ㆍ주제</strong>&nbsp;&nbsp;&nbsp;&nbsp;${topic}</li>
                    <li><strong>ㆍ저자사항</strong>&nbsp;&nbsp;&nbsp;&nbsp;${author}</li>
                    <li><strong>ㆍ발행사항</strong>&nbsp;&nbsp;&nbsp;&nbsp;${publisher}, ${year}</li>
                    <li><strong>ㆍ페이지</strong>&nbsp;&nbsp;&nbsp;&nbsp;${pages}</li>
                    <li><strong>ㆍISBN</strong>&nbsp;&nbsp;&nbsp;&nbsp;${isbn}</li>
                    <li><strong>ㆍ주제어/키워드</strong>&nbsp;&nbsp;&nbsp;&nbsp;${topic}</li>
                    <li><strong>ㆍ소장기관</strong>&nbsp;&nbsp;&nbsp;&nbsp;${library}</li>
                </ul>
            </dd>
        </dl>
    `;

        // 생성한 HTML을 DOM에 삽입
        document.querySelector(".view").innerHTML += html;

        let loan_stat = " class=\"_fail\">대출불가<";
        let reservation_stat = " class=\"_fail\">예약불가<";
        if (loan_state) {
            loan_stat = " class=\"_success\">대출가능<"
        }

        if (reservation_state) {
            reservation_stat = " class=\"reservation_success\" onclick=\"reservation('" + registerNumber + "')\">예약가능<";
        }
        //소장정보
        html = `
        <tr>
            <td>${registerNumber}</td>
            <td></td>
            <td>
                <strong>${callNumber}</strong><br>
                <strong>${isbn}</strong>
            </td>
            <td>
                <strong${loan_stat}/strong><br>
            </td>
            <td>-</td>
            <td>
                <strong${reservation_stat}/strong><br>
            </td>
        </tr>
    `;
        document.querySelector(".responsive").querySelector("tbody").innerHTML += html;

        document.querySelector(".content_text").innerHTML += detail_text;
    });
    // 생성한 HTML을 DOM에 삽입



    function selboxAllChecked(id) {
        var allCheck = document.getElementById(id);
        var selbox = document.getElementsByClassName("selbox")[0];
        var checkboxes = selbox.querySelectorAll('input[type="checkbox"]');

        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = allCheck.checked;
        }
    }
    //예약기능
    function reservation(RN) {
        alert(RN + " 예약되었습니다.");
    }
</script>

<body>
    <header></header>
    <section>
        <!-- 여기부터 본문작성해주세요 -->
        <!-- 책 상세페이지 -->
        <div id="searchDetailInfo">
            <div class="view">
                <!-- 책 내용 자바스크립트로 가져오기 -->
            </div>
            <div class="table">
                <h3>소장정보</h3>
                <table class="responsive">
                    <colgroup>
                        <col width="">
                        <col width="">
                        <col width="">
                        <col width="">
                        <col width="">
                        <col width="">
                        <col width="">
                        <col width="">
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col">등록번호</th>
                            <th scope="col">낱권정보</th>
                            <th scope="col">청구기호 / ISBN</th>
                            <th scope="col">자료상태</th>
                            <th scope="col">반납예정일</th>
                            <th scope="col">예약</th>
                        </tr>
                    </thead>
                    <!-- 소장내용 자바스크립트로 가져오기 -->
                    <tbody>
                    </tbody>
                </table>

                <div class="info">
                    <h3>상세정보</h3>
                    <div>
                        <div class="content_text"><!-- 책 상세정보 자바스크립트로 가져오기 --></div>
                    </div>
                </div>
            </div>
        </div>

    </section>
</body>

</html>