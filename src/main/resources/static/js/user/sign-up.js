let checkNicknameId = null;
// input keyup 이벤트 리스너
window.addEventListener("keyup", () => {

    let nickname = $('#nickname').val();

    window.clearTimeout(checkNicknameId); // 1초 안에 유저가 다시 타이핑하면 예정된 닉네임 체크 취소
    $("#nickname-noti").text("잠시만 기다려주세요");
    $("#nickname-noti").css("color", "#000000");
    checkNicknameId = window.setTimeout(checkNickname, 1000, nickname); // 1초 뒤 닉네임 체크 예약

});

// 유저 닉네임 중복, 유효성 체크
function checkNickname(data) {
    $.ajax({
        type: "post",
        url: `/api/user/duplicate`,
        data: {nickname : data},
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res => {
        let responseCode = res.code;

        if(responseCode == 1) {
            $("#nickname-noti").text("사용 가능한 닉네임입니다");
            $("#nickname-noti").css("color", "#0067a3");
            return;
        } else {
            $("#nickname-noti").text("사용중인 닉네임입니다");
            $("#nickname-noti").css("color", "#dc143c");
        }

    }).fail(error  => {
        let responseCode = error.responseJSON.code;
        console.log(error);
        if(responseCode == -101) {
            $("#nickname-noti").text("영어 대/소문자와 숫자 4~12자리까지 가능합니다");
            $("#nickname-noti").css("color", "#dc143c");
        } else {
            $("#nickname-noti").text("문제가 있습니다. 관리자에게 문의해주세요.");
            $("#nickname-noti").css("color", "#dc143c");
        }
    });
}

// 유저 등록
function signUp() {
    let notiText = $("#nickname-noti").text();

    if(notiText !== "사용 가능한 닉네임입니다") {
        return;
    }

    $('#sign-up').submit();
}