const url = new URL(window.location.href); // 현재 페이지의 url
const urlParams = url.searchParams; // url 의 파라미터

let pagingBtnPage = 0;
let page = 0;

// 현재 페이지 가져오기
if(urlParams.get('page')) {
    page = Number(urlParams.get('page'));
    pagingBtnPage = Math.floor(page / 5);
}

pagingBtnLoad();

// 페이징 버튼 초기화
function pagingBtnLoad() {
    let remainPostCount = postCount - (pagingBtnPage * 50);

    if(pagingBtnPage > 0) {
        $('#paging-btn').append(pagePrevBtnTemplate());
    }

    for(let i = 1; i < 6; i++) {
        if(remainPostCount > 0) {
            $('#paging-btn').append(pageBtnTemplate( i + (5 * pagingBtnPage) ));
        } else {
            break;
        }

        remainPostCount -= 10;
    }

    if(remainPostCount > 0) {
        $('#paging-btn').append(pageAfterBtnTemplate());
    }
}

// 페이징 이전 버튼 클릭
function pagingBtnPrev() {
    pagingBtnPage -= 1;
    $('#paging-btn').empty();
    pagingBtnLoad();
}

// 페이징 다음 버튼 클릭
function pagingBtnAfter() {
    pagingBtnPage += 1;
    $('#paging-btn').empty();
    pagingBtnLoad();
}

// 페이징 이전 버튼 템플릿
function pagePrevBtnTemplate() {
    return `
        <li class="page-item">
            <a class="page-link text-black-50" href="javascript:void(0)" onclick="pagingBtnPrev()" aria-label="Previous">
                <span aria-hidden="true">&lt;</span>
            </a>
        </li>
        `;
}

// 페이징 다음 버튼 템플릿
function pageAfterBtnTemplate() {
    return `
        <li class="page-item">
            <a class="page-link text-black-50" href="javascript:void(0)" onclick="pagingBtnAfter()" aria-label="Next">
                <span aria-hidden="true">&gt;</span>
            </a>
        </li>
        `;
}

// 페이징 버튼 템플릿
function pageBtnTemplate(page) {
    return `<li class="page-item"><a class="page-link text-black-50" href="/`+blogUrl+pagingUrl+(page-1)+`">`+page+`</a></li>`;
}