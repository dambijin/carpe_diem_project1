// todo 이벤트
// 책 추가버튼 가져오기

window.addEventListener("load", function () {
    bind();
});

function bind() {
    let add = document.querySelector("#book_add");

    add.addEventListener('click', function handle_book () { // 책 추가 클릭이벤트
    
        // 테이블을 todlist 에 담아둠
        let todolist = document.querySelector("#todo_booktable");
        
        if (!todolist) {
            console.error("Error: todo_booktable not found.");
            return;
        }

        let book_html = '';
    
        // html += '</tr>';
        // 추가한다
        book_html +='<td>java중급</td>';
        book_html +='<td>최민수강사님</td>';
        book_html +='<td>2023-05-01</td>';
        book_html +='<td>12215</td>';
        book_html +='<td>10</td>';
        book_html +='<td>천안도서관</td>';
        book_html +='<td>2024-02-02</td>';
        book_html +='<td>예약</td>';
        book_html +='<td>대출중</td>';
        book_html +='<td><input type="checkbox" name="check"></td>';
        // html +=	'</tr>'
    
        let tr = document.createElement("tr"); // <tr></tr>
            tr.innerHTML = book_html;
    
        todolist.append(tr);
    });
};

// 10개씩 누를 때 change 이벤트 : 변동이 있을 때 발생
function changeViewCount(count) {
	alert(count + "개씩 보기로 변경되었습니다.");
}

// 폐기버튼누를 때 이벤트
function dispose() {
    alert("폐기되었습니다")
}

// 검색기능(텍스트박스내용 알림띄우기)
        //todo_search : 검색 input창
// let search = document.querySelector("#todo_search");
//         // i
// let search2 = document.querySelector(#input_todo);

// search.addEventListener() {
//     alert("dasda");
// };



// *체크선택된 애들만 폐기되도록