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

        // 공지사항
        function announcement() {
            // 공지사항 글쓰기 버튼을 클릭했을때

            let complet=document.querySelector('#completion')
            complet.addEventListener('click', function () {

                // 테이블안에
                let announ = document.querySelector("#announcement_table");
                console.log(announ.value);
                // db마다 공지사항 하나씩 추가해주기
                let html = '';
                html += '<td class="ann_title">';
                html += '공지사항1';
                html += '</td>';
                html += '<td class="ann_day">';
                html += '날짜예요';
                html += '</td>';

                // 만약에 5개의 공지사항이 모두 나타났을시 맨아래 tr삭제 필요

            });
        };