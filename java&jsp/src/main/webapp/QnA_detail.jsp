<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QnA상세페이지</title>
    <link href="../css/layout.css" rel="stylesheet">
    <script src="../js/QnA_detail.js"></script>
    
    <style>
        /* 글쓴 내용 테이블의 제목, 작성자, 등록일, 조회, 도서관 ,첨부 */
        .QnA_detail .subject {
            width: 100px;
            background-color: rgba(168, 156, 200, 0.6);
            text-align: center;
        }

        .QnA_detail {
            text-align: right;
            width: 900px;
        }

        /* QnA 테이블 */
        .QnA_detail table {
            border-collapse: collapse;
            text-align: left;
        }

        .QnA_detail td {
            border: 1px solid rgba(59, 57, 63, 0.445);

        }

        /* 제목 오른쪽 td */
        .QnA_detail .subject_title {
            width: 800px;
        }

        /* 글내용 */
        .QnA_detail .content {
            height: 300px;
            vertical-align: top;
        }

        .QnA_detail .detail_hr {
            border: 1px solid rgba(168, 156, 200, 0.6);
            width: 900px;
            text-align: left;
            margin-left: 0px;
        }

        /* 조회 오른쪽 td */
        .inquiry {
            width: 50px;
        }

        /* 수정삭제버튼 */
        .notice_but {
            margin-top: 10px;
            font-family: "Wanted Sans Variable";
            width: 80px;
            background-color: rgba(155, 178, 225, 0.6);
            font-size: 18px;
            border: 0;
            border-radius: 5px;
            cursor: pointer;
        }

        .notice_but:hover {
            background-color: rgba(205, 155, 225, 0.6);
        }

        .writer {
            width: 180px;
        }

        /* 파일첨부 */
        .QnA_detail #file_route {
            font-family: "Wanted Sans Variable";
            width: 300px;
        }

        .QnA_detail #file_upload {
            font-family: "Wanted Sans Variable";
            background-color: rgba(155, 178, 225, 0.6);
            font-size: 15px;
            border: 0;
            border-radius: 5px;
            padding: 1px;
            cursor: pointer;
        }

        .QnA_detail #file_upload:hover {
            background-color: rgba(205, 155, 225, 0.6);
        }

        /* 답글 제목 td, 답글 작성자 td,답글 도서관 td */
        #libs_info,
        .answer_write {
            font-family: "Wanted Sans Variable";
            font-size: 15px;
        }

        /* 답글 글 내용 */
        .answer_textarea {
            width: 99%;
            height: 99%;
            vertical-align: top;
        }

        /* 답글 작성자 오른쪽 td */
        .answer_admin {
            width: 93%;
        }

        /* 답글 제목 인풋 */
        .answer_subject {
            width: 99%;
        }

        .answer_detail {
            display: none;
        }
    </style>
</head>

<body>
    <header></header>
    <section>
        <div class="s_section">
            <div class="left_section">
                <button type="button" class="sub_but" onclick="location.href='notice_board.html';">공지사항</button><br>
                <button type="button" class="sub_but" onclick="location.href='QnA_board.html';">Q&A</button><br>
                <button type="button" class="sub_but" onclick="location.href='wishbook_add.html';">희망도서신청</button>

            </div>
            <div class="right_section" id="rs">
                <div class="QnA_detail">
                    <table>
                        <tr>
                            <td class="subject">제목</td>
                            <td colspan="5" class="subject_title" id="qna_title">희망도서 신청 관련 문의</td>
                        </tr>
                        <tr>
                            <td class="subject">작성자</td>
                            <td class="writer">김**</td>
                            <td class="subject">등록일</td>
                            <td>2024-01-30</td>
                            <td class="subject">조회</td>
                            <td class="inquiry">46</td>
                        </tr>
                        <tr>
                            <td class="subject">도서관</td>
                            <td colspan="5">천안도서관</td>
                        </tr>
                        <tr>
                            <td class="subject">첨부</td>
                            <td colspan="5" id="subject_file"></td>
                        </tr>
                        <tr>
                            <td class="content" colspan="6">글 내용입니다.</td>
                        </tr>
                    </table>
                    <button type="button" class="notice_but completion">답글</button>
                    <button type="button" class="notice_but" onclick="location.href='QnA_update.html';">수정</button>
                    <button type="button" class="notice_but" id="QnA_delete">삭제</button>

                    <hr class="detail_hr">
                </div>

                <div class="QnA_detail answer_detail">
                    <table>
                        <tr>
                            <td class="subject">제목</td>
                            <td colspan="5" class="subject_title"><input type="text" class="answer_write answer_subject"
                                    id="answer_title"></td>
                        </tr>
                        <tr>
                            <td class="subject ">작성자</td>
                            <td class="writer"><input type="text" class="answer_write answer_admin" value="admin"></td>
                            <td class="subject">등록일</td>
                            <td class="redate"></td>
                            <td class="subject inquiry">조회</td>
                            <td class="inquiry">0</td>
                        </tr>
                        <tr>
                            <td class="subject">도서관</td>
                            <td colspan="5">
                                <select id="libs_info">
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="subject">첨부</td>
                            <td colspan="5"><input type="text" id="file_route" disabled="disabled" value="">
                                <label for="upload_file" class="btn" id="file_upload">파일첨부</label>
                                <input type="file" id="upload_file" required=true
                                    style="position:absolute; clip:rect(0, 0, 0, 0);">
                            </td>
                        </tr>
                        <tr>
                            <td class="content" colspan="6">
                                <textarea type="textarea" class="answer_write answer_textarea" placeholder="답글을 입력해주세요"
                                    id="awtext"></textarea>
                            </td>
                        </tr>
                    </table>
                    <!-- <button type="button" class="notice_but" onclick="location.href='QnA_update.html';">수정</button> -->
                    <button type="button" class="notice_but" id="registration">등록</button>
                    <button type="button" class="notice_but" id="answer_delete">삭제</button>
                </div>
            </div>
        </div>
    </section>
    <!-- 헤더를 덮어씌우는 자바스크립트 -->
    <script src="../js/header.js"></script>

</body>

</html>