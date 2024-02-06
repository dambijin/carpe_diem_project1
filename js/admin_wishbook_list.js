// 검색기능     왜 undefind가
function seach() {
    let textbox = document.getElementById("input_todo");
    if (textbox != null){
        alert(textbox.value + " ");
    }
}

function complete() {
    alert("완료되었습니다.")
}


function companion() {
    alert("반려되었습니다.")
}