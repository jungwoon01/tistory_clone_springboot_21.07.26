// summernote 적용 코드
$('#summernote').summernote({
    placeholder: '',
    tabsize: 2,
    height: 500,
    toolbar: [
        ['style', ['style']],
        ['font', ['bold', 'underline', 'clear']],
        ['color', ['color']],
        ['para', ['ul', 'ol', 'paragraph']],
        ['table', ['table']],
        ['insert', ['link', 'picture', 'video']],
        ['view', ['fullscreen', 'codeview', 'help']]
    ]
});

// 모달 공개 비공개 스위치
$(function () {
    $("#securityCheckBox").on('click', () => {
        if($("#securityCheckBox").prop('checked')) {
            $('#securityText').text("공개");
        } else {
            $('#securityText').text("비공개");
        }
    });
});

// 저장 버튼 클릭
function postWrite() {

    let title = $('#title').val();
    let content = $('#summernote').val();
    let category = $('#category').val();
    let security = $('#securityText').text();

    $.ajax({
        type:"post",
        url:`/`+blogUrl+`/manage/newpost`,
        data: JSON.stringify({"title":title, "content":content, "category":category, "security":security}),
        contentType:"application/json; charset=utf-8",
        dataType:"json"
    }).done(res=>{
        location.href = `/`+blogUrl+`/manage/post`;
    }).fail(error=>{
        alert(JSON.stringify(error.responseJSON.data));
    });

}

// 완료 버튼 클릭
function btnFinish() {
    let title = $('#title').val();
    let content = $('#summernote').val();

    if(title == "") {
        alert("제목을 입력하세요.");
        return;
    } else if (title.charAt(title.length - 1) == " ") {
        alert("제목의 가장 앞에는 공백이 들어갈 수 없습니다.");
    }

    if(content == "") {
        alert("내용을 입력하세요.");
        return;
    }

    $('#modalTitle').html(title);


    $('#newPostModal').modal('toggle');
}