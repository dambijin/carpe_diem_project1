<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QnA상세페이지</title>
    <link href="/carpedm/css/layout.css" rel="stylesheet">
    <script>
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
//        document.querySelector("#subject_file").innerHTML = '<a href="/path/to/your/file.pdf" download>첨부파일 다운로드</a>';

        // 게시물삭제버튼
        let QnA_delete = document.querySelector("#QnA_delete");
        // 삭제버튼 클릭시
        QnA_delete.addEventListener('click', function () {
            alert("삭제되었습니다.");
            location.href = 'QnA_board';
        });

        // 게시물삭제버튼
        let answer_delete = document.querySelector("#answer_delete");
        // 삭제버튼 클릭시
        answer_delete.addEventListener('click', function () {
            alert("삭제되었습니다.");
            location.href = 'QnA_detail';
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


    </script>
    
    
    <style>
        /* 글쓴 내용 테이블의 제목, 작성자, 등록일, 조회, 도서관 ,첨부 */
        .QnA_detail .subject {
            width: 100px;
            background-color: rgba(168, 156, 200, 0.6);
            text-align: center;
        }

        .QnA_detail {
            text-align: right;
            width: 100%;
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
            width: 100%;
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
    
    <%
	List<Map<String, String>> result_list = (List<Map<String, String>>) request.getAttribute("notice");
	 Map<String, String> map = new HashMap<String, String>();
%>
    	
        <div class="s_section">
            <div class="left_section">
				<button type="button" class="sub_but"
					onclick="location.href='notice_board';">공지사항</button>
				<br>
				<button type="button" class="sub_but"
					onclick="location.href='QnA_board';">Q&A</button>
				<br>
				<button type="button" class="sub_but"
					onclick="location.href='wishbook_add';">희망도서신청</button>
			</div>
            <div class="right_section" id="rs">
                <div class="QnA_detail">
                    <table>
                        <tr>
                            <td class="subject">제목</td>
                            <td colspan="5" class="subject_title" id="qna_title"><%=result_list.get(0).get("N_TITLE")%></td>
                        </tr>
                        <tr>
                            <td class="subject">작성자</td>
                            <td class="writer"><%=result_list.get(0).get("M_PID")%></td>
                            <td class="subject">등록일</td>
                            <td><%=result_list.get(0).get("N_DATE").substring(0,10)%></td>
                            <td class="subject">조회</td>
                            <td class="inquiry"><%=result_list.get(0).get("N_VIEWCOUNT")%></td>
                        </tr>
                        <tr>
                            <td class="subject">도서관</td>
                            <td colspan="5"><%=result_list.get(0).get("LB_ID")%></td>
                        </tr>
                        <tr>
                            <td class="subject">첨부</td>
                           <td colspan="5" id="subject_file"><%
							if (result_list.get(0).get("N_FILE") == null){
								out.print(" ");
							} else {
								out.print(result_list.get(0).get("N_FILE"));
								}%></td>
                        </tr>
                        <tr>
                            <td class="content" colspan="6"><%=result_list.get(0).get("N_CONTENT")%></td>
                        </tr>
                    </table>
                    <button type="button" class="notice_but completion">답글</button>
                    <button type="button" class="notice_but" onclick="location.href='QnA_update?N_ID=<%=result_list.get(0).get("N_ID")%>';">수정</button>
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
                    <!-- <button type="button" class="notice_but" onclick="location.href='QnA_update.jsp';">수정</button> -->
                    <button type="button" class="notice_but" id="registration">등록</button>
                    <button type="button" class="notice_but" id="answer_delete">삭제</button>
                </div>
            </div>
        </div>
    </section>
    <!-- 헤더를 덮어씌우는 자바스크립트 -->
    <script src="/carpedm/js/header.js"></script>

</body>

</html>