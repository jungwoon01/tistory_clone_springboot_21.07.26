// 스크롤 이벤트 nav-bar 배경, 테두리 변화
$(window).scroll(() => {
    // console.log("윈도우 스크롤 탑", $(window).scrollTop());
    // console.log("문서 높이", $(document).height());
    // console.log("윈도우 높이", $(window).height());

    let scrollTop = $(window).scrollTop();

    if(scrollTop >= 400) {
        $('#ctm-nav-bar').css('background-color', "white");
        $('#ctm-nav-bar').css('border-bottom', "1px solid #CCCCCC");
    } else {
        $('#ctm-nav-bar').css('background', "none");
        $('#ctm-nav-bar').css('border-bottom', "none");
    }
});