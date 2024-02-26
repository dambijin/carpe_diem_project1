<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인 페이지</title>
    <link href="./css/layout.css" rel="stylesheet">
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="./js/main.js"></script>

    <style>
        /* 바디사이즈 */
        .bodysize {
            text-align: center;
            font-family: "Wanted Sans Variable";
        }

        /* 배너 */
        nav .banner {
            width: 70%;
            margin-bottom: 10px;
        }

        /* 섹션 */
        .library_information_content {
            width: 75%;
            margin: auto;
        }

        /* 공지사항+신착도서 */
        .announcement_library_information {
            width: 69%;
            display: inline-block;
        }

        /* 공지사항 */
        .announcement {
            width: 100%;
            font-size: 20px;
            font-weight: bold;
            border: 2px solid rgba(168, 156, 200, 0.6);
            border-bottom: 0px;
            background-color: rgba(168, 156, 200, 0.6);

        }

        /* 공지사항테이블 */
        .announcement_table {
            font-size: 16px;
            font-weight: normal;
            margin-top: 10px;
            width: 100%;
            border-collapse: collapse;
        }

        .announcement_table .ann_title {
            cursor: pointer;
        }

        /* 공지사항 td */
        .announcement_library_information .announcement_table td {
            border-bottom: 2px solid rgba(168, 156, 200, 0.6);
        }

        /* 신착도서 */
        .announcement_library_information .newbook {
            height: 320px;
            width: 100%;
            background-color: rgba(140, 201, 240, 0.6);
            border: 2px solid rgba(140, 201, 240, 0.6);
            border-bottom: 0px;
            font-size: 20px;
            font-weight: bold;
        }

        /* 신착도서 테이블 */
        .newbook_table {
            margin-top: 10px;
            font-size: 15px;
            font-weight: normal;
        }

        .newbook_table td {
            border-right: 2px solid rgba(140, 201, 240, 0.6);
        }

        /* 신착도서 div */
        .newbook_table .newbook_div {
            margin-top: 8px;
            cursor: pointer;
        }

        /* 도서관별이용정보 */
        .library_information_content .library_information {
            background-color: rgba(239, 168, 176, 0.6);
            display: inline-block;
            width: 30%;
            height: 450px;
            vertical-align: top;
            font-size: 22px;
            padding-top: 8px;
            font-weight: bold;
        }

        /* 이용시간, 휴관일 */
        .library_information .calendar {
            text-align: center;
            margin: auto;
            font-weight: normal;
            margin-top: 10px;
            font-size: 18px;
        }


        .library_information_content .announcement_library_information .announcement .ann_title {
            width: 80%;
        }

        .library_information_content .announcement_library_information .announcement,
        .library_information_content .announcement_library_information .newbook table,
        .library_information table {
            width: 100%;
        }

        .announcement .ann_day {
            text-align: right;
        }

        .announcement_library_information .newbook_img {
            width: 200px;
        }

        .bodysize select,
        .library_information select {
            font-family: "Wanted Sans Variable";
            font-size: 18px;
        }

        .library_infor .gubun {
            background-color: rgba(168, 156, 200, 0.6);
            height: 2px;
            border: none;
            width: 90%;
        }

        /* 검색창관련 */
        .search_box {
            width: 80%;
            margin: auto;
        }

        .search_fieldset {
            margin: auto;
            display: flex;
            align-items: center;
            padding: 10px;
            margin-inline-start: 0px;
            border: 0px solid #ccc;
            justify-content: center;
        }

        .search_fieldset legend {
            margin-right: 20px;
        }

        .search_fieldset .result {
            flex: 1;
            display: flex;
            align-items: center;
        }

        .search_fieldset .search {
            flex: 4;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .search_fieldset .search .input {
            flex: 4;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .search_fieldset .search .input select {
            flex: 1;
            margin-right: 10px;
            height: 40px;
            font-size: 18px;
            font-family: 'Wanted Sans Variable';
        }

        .search_fieldset .search .input input {
            flex: 3;
            padding: 5px;
            border: 1px solid #ccc;
            height: 35px;
            font-family: 'Wanted Sans Variable';
            font-size: 18px;
        }

        /* 자료검색 */
        .search_fieldset .search .btn-search {
            flex: 1;
            padding: 5px 10px;
            background-color: rgba(168, 156, 200, 1.0);
            color: #eee;
            border: none;
            cursor: pointer;
            height: 45px;
            font-family: 'Wanted Sans Variable';
            font-size: 18px;
        }
    </style>
</head>

<body>
    <header></header>
    <div class="bodysize">
        <div class="search_box">
            <fieldset class="search_fieldset">
                <!-- <legend>통합검색</legend> -->
                <div class="search">
                    <div class="input">
                        <strong>
                            <select name="item" id="libsear">

                            </select>
                        </strong>
                        <input type="text" name="word" autocomplete="off" id="searchWord" style="ime-mode:active"
                            placeholder="검색어를 적어주세요">
                        <!-- input type="hidden" name="item" value="title" id="item" -->
                    </div>
                    <input type="button" class="btn btn-search" value="도서검색" id="libsearch" onclick="search()">
            </fieldset>
        </div>
        <br>
        <nav>
            <img class="banner" src="../resource/banner.png">
        </nav>

        <section class="library_information_content">
            <div class="announcement_library_information">
                <div class="announcement">
                    공지사항
                    <table class="announcement_table" id="announcement_table">
                        <!-- 자바스크립트로 가져오기 -->
                    </table>
                </div>

                <div class="newbook">
                    신착도서
                    <table class="newbook_table">
                        <tr id="nb">
                            <!-- 자바스크립트로 가져오기 -->
                        </tr>
                    </table>
                </div>
            </div>

            <div class="library_information">
                이용 정보
                <select id="libs_info">

                </select>
                <div id="libs_time" class="calendar">

                </div>
                <div class="calendar">

                    <div class="text_detail">

                    </div>
                </div>

            </div>
        </section>
    </div>
    <!-- 헤더를 덮어씌우는 자바스크립트 -->
    <script src="./js/header.js"></script>
</body>


</html>