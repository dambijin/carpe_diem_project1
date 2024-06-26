<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>도서 신청</title>
    <link href="/carpedm/resources/css/layout.css" rel="stylesheet">
    <style>
        /* table 왼쪽 */
        .right_section .subject {
            background-color: rgba(168, 156, 200, 0.6);
            text-align: center;
            font-size: 18px;
            width: 200px;
        }

        /* 각행마다 높이설정을 주기위해 만듬 */
        .right_section .hope_subscribe tr {
            height: 50px;
            border-bottom: 1px solid rgba(59, 57, 63, 0.445);

        }

        /* table input요소들*/
        .right_section .textbox {
            font-size: 18px;
            font-family: "Wanted Sans Variable";
            margin: 10px;

        }

        /* 테이블 */
        .right_section .hope_subscribe {
            border-collapse: collapse;
            width: 100%;
            margin-top: 10px;
        }

        /* 신청버튼 */
        .right_section .request {
            font-family: "Wanted Sans Variable";
            width: 100px;
            background-color: rgba(155, 178, 225, 0.6);
            font-size: 18px;
            border: 0;
            border-radius: 5px;

        }

        /* 신청버튼 마우스 오버 */
        .right_section .request:hover {
            background-color: rgba(205, 155, 225, 0.6);

        }

        /* 신청버튼 tr */
        .right_section .request_but {
            border: 0px;
        }

        /* 신청버튼 td */
        .right_section .request_but td {
            text-align: center;
        }

        /* 희망도서신청 제목 */
        .s_section .right_section .wish_book {
            font-size: 25px;
            font-weight: bold;
            margin-bottom: 10px;
            text-align: center;
        }

        /* 신청사유 textbox */
        .right_section #reason {
            width: 400px;
            height: 200px;
            vertical-align: top;
            text-align: start;
            /* 텍스트를 상자의 위쪽으로 정렬? */
        }

        /* 신청안내 펼치기/접기 */
        .right_section .details_summary details {
            width: 400px;
            border: 1px solid #aaa;
            border-radius: 4px;
            padding: .5em .5em 0;
            background: #f9f9f9;
        }

        .right_section .details_summary summary {
            font-weight: bold;
            margin: -.5em -.5em 0;
            padding: .5em;
            text-align: center;
        }

        .right_section .details_summary pre {
            white-space: pre-wrap;
            /* CSS3 */
            white-space: -moz-pre-wrap;
            /* Firefox */
            white-space: -pre-wrap;
            /* Opera <7 */
            white-space: -o-pre-wrap;
            /* Opera 7 */
            word-wrap: break-word;
            /* Internet Explorer */
            padding: 5px;
        }

        .right_section .details_summary details[open] {
            padding: .5em;
        }

        .right_section .details_summary details[open] summary {
            border-bottom: 1px solid #aaa;
            margin-bottom: .5em;
        }
        #handphone::-webkit-inner-spin-button,
        #handphone::-webkit-outer-spin-button {
        -webkit-appearance: none;
        margin: 0;
        }
        
        /* 신청안내 */
		.application_guide{
		    font-family: "Wanted Sans Variable";
		    text-align : left;
		}
        
    </style>
    <script>
        window.addEventListener("load", function () {
            document.getElementById('request_but').addEventListener('click', function () {
                // 모든 입력 필드의 id를 배열로 저장
                let inputIds = ['library', 'material', 'writer', 'year', 'reason', 'publisher', 'handphone'];

                for (let i = 0; i < inputIds.length; i++) {
                    var input = document.getElementById(inputIds[i]);

                    if (input.tagName === 'SELECT') {
                        // select 요소의 경우, selectedIndex가 0보다 크거나 같아야 합니다.
                        if (input.selectedIndex < 0) {
                        	event.preventDefault();
                            alert('필수작성(*) 칸(을 채워주세요.');
                            input.focus();
                            return;
                        }
                    } else {
                        // input 요소의 경우, value가 비어 있지 않아야 합니다.
                        if (!input.value.trim()) {
                        	event.preventDefault();
                            alert('필수작성(*) 칸을 채워주세요.');
                            input.focus();
                            return;
                        }
                    }
                }

                // 개인정보 수집 동의 체크박스가 체크되어 있는지 확인
                let checkbox = document.querySelector('.hope_subscribe input[type="checkbox"]');
                if (!checkbox.checked) {
                	event.preventDefault();
                    alert('개인정보 수집에 동의해주세요.');
                    checkbox.focus();
                    return;
                }

                request();
            });
        });

//         function request() {

//             // 신청 버튼
//             let reqbut = document.querySelector("#request_but");
//             // 자료명 input
//             let mrial = document.querySelector("#material");
//             // 저자
//             let writer = document.querySelector("#writer");
//             // 발행연도
//             let year = document.querySelector("#year");
//             // 사유
//             let reason = document.querySelector("#reason");
//             // 출판사
//             let publisher = document.querySelector("#publisher");
//             // 전화번호
//             let handphone = document.querySelector("#handphone");

//             reqbut.addEventListener('click', function () {
//             	if (mrial == "") {
//             		event.preventDefault();
//                     alert("자료명을 입력해주세요.");
//                     mrial.focus();
//                 } else if (writer == "") {
//                 	event.preventDefault();
//                 	alert("저자를 입력해주세요.");
//                 	writer.focus();
//                 } else if (year == "") {
//                 	event.preventDefault();
//                 	alert("발행연도를 입력해주세요.");
//                 	year.focus();
//                 } else if (reason  == "") {
//                 	event.preventDefault();
//                 	alert("내용을 입력해주세요.");
//                 	reason.focus();
//                 } else if (publisher  == "") {
//                 	event.preventDefault();
//                 	alert("출판사를 입력해주세요.");
//                 	publisher.focus();
//                 } else if (handphone == "") {
//                 	event.preventDefault();
//                 	alert("핸드폰번호를 입력해주세요.");
//                 	handphone.focus();
//                 } else{
//                 	alert("신청이 완료되었습니다.");
//                 }
//             });
//         };
    </script>
</head>


<body>
    <header></header>
    <section>
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
            <div class="right_section">
                <div class="wish_book">희망도서신청</div>
                <details class="details_summary">
                    <summary>신청안내(클릭)!!</summary>
                    <pre class="application_guide">
<b>1. 신청 가능 자료 수</b> : 1인 월 5책 이내(예산범위 내)
※ 외국도서 신청자료 수 : 상한 기준 연 20책 이내(2020년 5월부터 시행)
* 표시는 필수 기재 사항입니다.<br>
<b>2. 처리 기간</b>: 신청접수 후 우선 처리(납본 지체, 외국자료 입수처리 등으로 2개월 이상 소요될 수 있음)<br>
<b>3. 신청 제한 자료</b>
- 국립중앙도서관에 소장 되어있는 자료
- 각종 수험서·문제집, 초중고 학습서, 강의·교재용 자료
- 무협·판타지·애정 소설, 게임·만화 등 각종 오락용 자료
- 50쪽 미만 또는 내용이 거의 없는 자료, 낱장제본 도서 등 일반적인 자료의 형태(구성요소)를 갖추고 있지 않은 자료
품절 또는 절판 자료, 출판 예정 도서(또는 출판 미확인 자료)
- ISBN 정보 또는 출판정보가 부정확한 자료
- 신청일 기준 출판한 지 5년경과 된 외국 도서 또는 외국에서 발행된 비도서
- 「국립중앙도서관 장서개발지침」에 따라 도서관 자료로 적합하지 않은 자료
※ 신청 자료 중, 유사한 자료의 통상적인 거래가격과 상당한 차이가 나는 자료는 도서관자료 심의위원회 심의를 통하여 신청이 제한될 수 있음 (근거: 도서관법 시행령 제13조의4 ③항)<br>
 - 교양, 학습 및 연구 등의 목적으로 이용을 원하는 자료 신청
 - 신청자료 수 : 상한 기준, 연 20책 이내(2020년 5월부터 시행)
 
◆신청 제한대상
- 이용증 미발급자(홈페이지 회원가입만 한 이용자)
- 최근 신청한 비치희망 자료의 대출 이력이 없는 이용자(최초 신청자는 예외)
- 교양, 학습 및 연구 목적이 아닌 상업적 목적으로 신청하는 자

- 고가의 자료 등은 도서관 내부 심의 후 구입 결정
- 매년 10월 말 신청 마감: 마감일 이후 신청 자료는 다음 해에 구입 예정

<b>5. 신청방법</b> : 로그인 → 신청·참여 → 비치희망도서 → 자료검색하기(신청도서 소장여부 검색)→ ‘비치 희망도서 신청‘ 클릭

<b>6. 신청 및 처리결과 보기</b> : 내서재 → 자료 신청내역 → 비치희망도서
                 </pre>
                </details>
                <h3>* 표시는 필수항목입니다.</h3>
                <hr> 
                <div>   
					<form method="post" action="wishbook_add">
					    <table class="hope_subscribe">
					        <tr>
					            <td class="subject">* 희망소장처</td>
					            <td>
					                <select class="textbox" id="library" name="lb_id">
					                    <c:forEach var="library" items="${library}">
					                        <option value="${library.lb_id}">${library.lb_name}</option>
					                    </c:forEach>
					                </select>
					            </td>
					        </tr>
					        <tr>
					            <td class="subject"> * 자료명 </td>
					            <td><input type="text" name="w_title" class="textbox" placeholder="자료명" id="material" /></td>
					        </tr>
					        <tr>
					            <td class="subject"> * 저자 </td>
					            <td><input type="text" name="w_author" class="textbox" placeholder="저자" id="writer" /></td>
					        </tr>
					        <tr>
					            <td class="subject"> * 발행연도 </td>
					            <td><input type="text" name="w_pubyear" class="textbox" placeholder="발행연도" id="year" /></td>
					        </tr>
<!-- 					        <tr> -->
<!-- 					            <td class="subject">ISBN번호<br> ISSN번호</td> -->
<!-- 					            <td><input type="text" name="w_isbn" class="textbox" placeholder="ISBN/ISSN 번호" id="is" /></td> -->
<!-- 					        </tr> -->
					        <tr>
					            <td class="subject">* 신청사유</td>
					            <td>
					                <textarea name="w_content" class="textbox reason" placeholder="신청사유를 입력해주세요." id="reason"></textarea>
					            </td>
					        </tr>
					        <tr>
					            <td class="subject">* 출판사</td>
					            <td><input type="text" name="w_publisher" class="textbox" placeholder="출판사" id="publisher" /></td>
					        </tr>
					        <tr>
					            <td class="subject">신청자 이름</td>
					            <td><input type="text" value="${mem[0].m_name}" name="w_name" class="textbox" placeholder="이름" id="user_name" />
					            	<input type="hidden" value="${mem[0].m_pid}" name="m_pid">
					            </td>
					        </tr>
					        <tr>
					            <td class="subject">* 휴대폰 번호</td>
					            <td><input type="number" value="${fn:replace(mem[0].m_tel, '-', '')}" name="w_tel" class="textbox" placeholder="-없이 입력해주세요" id="handphone"></td>
					        </tr>
					        <tr>
					            <td class="subject">* 개인정보 수집 동의</td>
					            <td>
					                <input type="checkbox" /> 희망도서 신청을 위한 '개인정보 수집 및 이용'에 동의합니다.
					            </td>
					        </tr>
					        <tr class="request_but">
					            <td colspan="2">
					                <button type="submit" class="request" id="request_but">신청</button>
					            </td>
					        </tr>
					    </table>
					</form>
                </div>
            </div>
        </div>
    </section>
</body>

</html>