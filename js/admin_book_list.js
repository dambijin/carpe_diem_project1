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
    })


    // 10개씩 누를 때 change 이벤트 : 변동이 있을 때 발생
    function changeViewCount(count) {
        alert(count + "개씩 보기로 변경되었습니다.");
    }

    // 폐기버튼누를 때 이벤트
    function dispose() {
        alert("폐기되었습니다")
    }



// *체크선택된 애들만 폐기되도록    미완성

    document.querySelector("#selected_del").addEventListener("click", function () {
        let list_checked = document.querySelectorAll(".check:checked")
            for(let i=0; i<list_checked.length; i++){
                list_checked[i].parentNode.parentNode.remove();
            }
        })

    document.querySelector("#select_all").addEventListener("click", function (event) {
        // 클릭된 요소가
        // check 상태라면
        let list_check = document.querySelectorAll(".check")

        if(event.target.checked){
            for(let i=0; i<list_check.length; i++){
                list_check[i].checked = true;
            }
        } else {
            for(let i=0; i<list_check.length; i++){
                list_check[i].checked = false;
            }
        }
        // check 상태가 아니라면
    });

};
// 검색기능
function seach() {
    var textbox = document.getElementById("input_todo");
    if (textbox != null){
        alert(textbox.value + " ");
    }
}

