
window.addEventListener("load", function () {
    bind();
});


function bind() {
    // 연체추가 가져오기
    let add = document.querySelector("#over_add");

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
        book_html +='<td>aaaaaaaaaaa</td>';
        book_html +='<td>홍길동</td>';
        book_html +='<td>천안도서관</td>';
        book_html +='<td>yyyy/mm/dd</td>';
        book_html +='<td>yyyy/mm/dd</td>';
        book_html +='<td>3일</td>';
        book_html +='<td><input type="checkbox" class="check"></td>';
        // html +=	'</tr>'
    
        let tr = document.createElement("tr"); // <tr></tr>
            tr.innerHTML = book_html;
    
        todolist.append(tr);
    })

    // 체크박스 
    document.querySelector("#selected_del").addEventListener("click", function () {
        let list_checked = document.querySelectorAll(".check:checked");

        if (list_checked.length > 0) {
            for (let i = 0; i < list_checked.length; i++) {
                list_checked[i].parentNode.parentNode.remove();
            }
        } else {
            alert("연체해제할 항목을 선택해주세요");
        }
    });

    document.querySelector("#select_all").addEventListener("click", function (event) {
        let list_check = document.querySelectorAll(".check");

        for (let i = 0; i < list_check.length; i++) {
            list_check[i].checked = event.target.checked;
        }
    });



    
}



    