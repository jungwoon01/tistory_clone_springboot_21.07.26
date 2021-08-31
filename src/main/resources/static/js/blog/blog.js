let commentTemp;
let updatingId;
let isUpdating = false;

const nowUrl = new URL(window.location.href); // 현재 페이지의 url
const nowUrlParams = nowUrl.searchParams; // url 의 파라미터

let nowPage = 0;

// 현재 페이지 가져오기
if(nowUrlParams.get('page')) {
    nowPage = Number(nowUrlParams.get('page'));
}

/**
 * 페이지 초기화를 위한 것들
 * */

// 글 목록 초기화
$(function () {
    $.ajax({
        type: "get",
        url: '/api/posts/'+blogUrl+'/'+category+`?page=`+page,
        dataType: "json"
    }).done(res => {
        let data = res.data;

        data.forEach(post => {
            $('#post-title-list').append(postTitleItem(post.id, post.title, post.createdDate));
            $('#post-list').append(postItem(post.id, post.title, post.content, post.createdDate, sympathyAndModifyItem(post.isLikes, post.likesCount, isHost, post.id), post.comments));
        })

        if(data.length == 0) {
            $('#post-title-item').append(emptyPostTitleItem());
        }

        moveOffset();
    }).fail(error => {
        alert(error.message);
    });
});

/**
 * 이벤트 추가
 * */

// 모달 비밀번호 입력,수정 이벤트
$(function () {
    $('#modal-comment-password').on("propertychange change keyup paste input", (key) => {
        $('#modal-delete-comment-description').text("댓글을 삭제하려면 비밀번호를 입력하세요.");
        $('#modal-delete-comment-description').css("color", '#000000');
    });
});

/**
 * 버튼 클릭 이벤트 함수
 * */

// 댓글 작성 버튼 클릭
function btnCommentWrite(postId) {
    let author = $('#comment-name-' + postId).val();
    let password = $('#comment-password-' + postId).val();
    let content = $('#comment-content-' + postId).val();

    $.ajax({
        type: "post",
        url: '/api/comment/write',
        data: JSON.stringify({
            author: author,
            password: password,
            content: content,
            postId: postId
        }),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    }).done(res => {
        let resPostId = res.data.postId;

        $('#comment-list-' + resPostId).append(commentItem(res.data.author, res.data.content, res.data.createdDate, res.data.id));

        $('#comment-name-' + resPostId).val("");
        $('#comment-password-' + resPostId).val("");
        $('#comment-content-' + resPostId).val("");
    }).fail(error => {
        alert("서버 오류로 댓글 작성을 실패하였습니다.");
    });

}

// 댓글 수정 버튼 ( 수정을 위한 템플릿으로 변환 )
function btnCommentUpdate(id) {
    if (isUpdating) {
        $('#comment-item-' + updatingId).html(commentTemp); // 임시 저장 불러오기
    }

    let strArr = id.split('-');
    let commentId = strArr[strArr.length - 1]; // 댓글 id


    let author = $('#comment-author-' + commentId).text();
    let createdDate = $('#comment-createdDate-' + commentId).text();
    let content = $('#comment-content-' + commentId).text();

    commentTemp = $('#comment-item-' + commentId).html(); // 임시 저장
    $('#comment-item-' + commentId).html(commentUpdateTemplate(author, content, createdDate, commentId));

    commentUpdatePWInputEvent(); // input 입력 이벤트 추가

    updatingId = commentId;
    isUpdating = true;
}

// 댓글 수정 취소
function btnCommentUpdateCancel(id) {
    let strArr = id.split('-');
    let commentId = strArr[strArr.length - 1]; // 댓글 id

    $('#comment-item-' + commentId).html(commentTemp); // 임시 저장 불러오기

    isUpdating = false;
}

// 댓글 수정 버튼 (서버전송)
function btnCommentUpdateSend(id) {
    let strArr = id.split('-');
    let commentId = strArr[strArr.length - 1]; // 댓글 id

    let content = $('#textarea-update-comment').val();
    let password = $('#update-comment-password').val();

    $.ajax({
        type: "put",
        url: '/api/comment/update',
        data: JSON.stringify({"commentId": commentId, "password": password, "content": content}),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    }).done(res => {
        // 비밀번호가 일치하면 {code : 1} 을 응답 받음
        // 비밀번호가 일치하지 않으면 {code : -1} 을 응답 받음
        if (res.code == -1) {
            $('#update-comment-description').text("비밀번호가 틀렸습니다.");
            $('#update-comment-description').css("color", '#b02a37');
        } else if (res.code == 1) {
            $('#comment-item-' + commentId).html(commentTemp); // 임시 저장 불러오기
            $('#comment-content-' + res.data.id).text(res.data.content);
            isUpdating = false;
        }
    }).fail(error => {
        alert("서버오류로 댓글 삭제를 실패했습니다.");
    });
}

// 댓글 삭제 모달
function btnCommentDeleteModal(id) {
    $('#modal-delete-comment-description').text("댓글을 삭제하려면 비밀번호를 입력하세요.");
    $('#modal-comment-password').val("");
    $('#delete-comment-id').val(id);

    $('#modal-delete-comment').modal('toggle');
}

// 댓글 삭제 버튼
function btnCommentDelete() {
    let password = $('#modal-comment-password').val();

    let id = $('#delete-comment-id').val();
    let strArr = id.split('-');
    let commentId = strArr[strArr.length - 1]; // 댓글 id

    $.ajax({
        type: "post",
        url: '/api/comment/check_password',
        data: {commentId: commentId, password: password},
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res => {
        // 비밀번호가 일치하면 {code : 1} 을 응답 받음
        if (res.code != 1) {
            $('#modal-delete-comment-description').text("비밀번호가 틀렸습니다.");
            $('#modal-delete-comment-description').css("color", '#b02a37');
        } else {
            deleteComment(commentId);
        }
    }).fail(error => {
        alert("서버오류로 댓글 삭제를 실패했습니다.");
        $('#modal-delete-comment').modal('hide');
    });
}

// 좋아요 하기
function clkLikes(id) {
    let strArr = id.split('-');
    let postId = strArr[strArr.length - 1]; // 글 id

    $.ajax({
        type: "post",
        url: '/api/likes/like',
        data: {postId: postId},
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res => {
        $('#likes-heart-' + postId).html(fullHeart(postId));
        let count = Number($('#likes-count-' + postId).text());
        $('#likes-count-' + postId).text(count + 1);
    }).fail(error => {
        alert("로그인 후 이용하세요");
    });
}

// 좋아요 취소 하기
function clkUnLikes(id) {
    let strArr = id.split('-');
    let postId = strArr[strArr.length - 1]; // 글 id

    $.ajax({
        type: "delete",
        url: '/api/likes/unlike',
        data: {postId: postId},
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res => {
        $('#likes-heart-' + postId).html(emptyHeart(postId));
        let count = Number($('#likes-count-' + postId).text());
        $('#likes-count-' + postId).text(count - 1);
    }).fail(error => {
        alert("로그인 후 이용하세요");
    });
}

/**
 * 템플릿 리턴 함수
 * */

// 댓글 item 템플릿
function commentItem(author, content, createdDate, commentId) {
    return `
                    <div id="comment-item-` + commentId + `" class="comment-container">
                        <div class="row">
                            <div class="col-12">
                                <p class="comment-author">
                                    <span id="comment-author-` + commentId + `">` + author + `</span>
                                    <span id="comment-createdDate-` + commentId + `" class="float-end comment-date">` + createdDate + `</span>
                                </p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <p id="comment-content-` + commentId + `" class="comment-content">` + content + `</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <button id="btn-comment-reply-` + commentId + `" class="float-end btn btn-sm btn-comment" onclick="btnCommentUpdate(this.id)">Update</button>
                                <button id="btn-comment-delete-` + commentId + `" class="float-end btn btn-sm btn-comment" onclick="btnCommentDeleteModal(this.id)">Delete</button>
                            </div>
                        </div>
                    </div>
        `
}

// 댓글 수정 템플릿 리턴
function commentUpdateTemplate(author, content, createdDate, commentId) {
    return `
                    <div class="row">
                        <div class="col-12">
                            <p class="comment-author">` + author + `<span class="float-end comment-date">` + createdDate + `</span></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <textarea id="textarea-update-comment" class="form-control" maxlength="100">` + content + `</textarea>
                        </div>
                    </div>
                    <div class="main-blank-10"></div>
                    <div class="row">
                        <div class="col-2">
                            <label for="update-comment-password">비밀번호</label>
                        </div>
                        <div class="col-4">
                            <input id="update-comment-password" type="password" class="form-control" maxlength="12">
                        </div>
                        <div class="col-6">
                            <p id="update-comment-description"></p>
                        </div>
                    </div>
                    <div class="main-blank-10"></div>
                    <div class="row">
                        <div class="col-12">
                            <button id="btn-comment-update-` + commentId + `" class="float-end btn btn-sm btn-comment" onclick="btnCommentUpdateSend(this.id)">Update</button>
                            <button id="btn-comment-cancel-` + commentId + `" class="float-end btn btn-sm btn-comment" onclick="btnCommentUpdateCancel(this.id)">Cancel</button>
                        </div>
                    </div>
        `
}

// 글 item 템플릿 리턴
function postItem(id, title, content, createdDate, sympathyAndModifyItem, comments) {
    let item = `
            <!--post 제목-->
            <div class="row" id="post-item-top-`+id+`"><div class="col-xxl-9 align-self-center main-blank-110 align-self-center"></div></div>
            <div class="row">
                <div class="col-xxl-9 align-self-center "><h5 class="text-center"><span class="blog-main-title">` + title + `</span></h5></div>
            </div>
            <div class="row"><div class="col-xxl-9 align-self-center main-blank-40"></div></div>

            <!--post 작성일-->
            <div class="row">
                <div class="col-xxl-9 align-self-center" style="padding-bottom: 10px;">
                    <span class="float-end post-item-date">` + createdDate + `</span>
                </div>
            </div>

            <!--post 내용-->
            <div class="row">
                <div class="col-xxl-9 align-self-center">
                    <div class="post-item-container">
                        <!--post 내용-->
                        <div id="board_content">
                        ` + content + `
                        </div>
                        <div class="main-blank-100"></div>
                        <!--공감/수정 버튼-->
                        <div class="text-center">
                            <span class="likes-box">` + sympathyAndModifyItem + `</span>
                        </div>
                        <div class="main-blank-40"></div>

                        <!--댓글 작성 부분-->
                        <div class="row">
                            <div class="col-2">
                                <label for="comment-name" class="form-label">NAME</label>
                            </div>

                            <div class="col-4">
                                <input type="text" class="form-control" id="comment-name-` + id + `" maxlength="12">
                            </div>
                        </div>
                        <div class="main-blank-10"></div>
                        <div class="row">
                            <div class="col-2">
                                <label for="comment-password" class="form-label">PASSWORD</label>
                            </div>
                            <div class="col-4">
                                <input type="password" class="form-control" id="comment-password-` + id + `" maxlength="12">
                            </div>
                        </div>
                        <div class="main-blank-10"></div>
                        <div class="row">
                            <div class="col-12">
                                <textarea id="comment-content-` + id + `" class="form-control" rows="3" maxlength="100"></textarea>
                            </div>
                        </div>
                        <div class="main-blank-10"></div>
                        <div class="row">
                            <div class="col-12">
                                <button type="button" class="btn float-end" style="border-bottom: 1px solid #DDDDDD;" onclick="btnCommentWrite(` + id + `)">W R I T E</button>
                            </div>
                        </div>
                        <!--댓글 작성 끝-->

                        <div class="main-blank-10"></div>
                        <!--작성된 댓글 부분-->
                        <div id="comment-list-` + id + `">`;
    comments.forEach(comment => {
        item += commentItem(comment.author, comment.content, comment.createdId, comment.id)
    });
    item += `</div>
                        <!--작성된 댓글 부분-->
                    </div>
                </div>
            </div>
            `;

    return item;
}

// 공감 수정 버튼 템플릿 리턴
function sympathyAndModifyItem(isLikes, likesCount, isHost, postId) {
    let item;

    if (isLikes) {
        item = `
                <span id="likes-heart-` + postId + `"><svg onclick="clkUnLikes(this.id)" id="` + postId + `" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart-fill likes-box-fill" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
                    </svg></span>`;
    } else {
        item = `
                <span id="likes-heart-` + postId + `"><svg onclick="clkLikes(this.id)" id="` + postId + `" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16">
                        <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
                    </svg></span>`;
    }


    item += `&nbsp;&nbsp;<span id="likes-count-` + postId + `">` + likesCount + `</span>`;


    if (isHost) {
        item += `&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="post-modify-` + postId + `" onclick="location.href='/`+blogUrl+`/manage/post/modify/`+postId+`'">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
                    <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                </svg>
                &nbsp;수정
            </span>
        `;
    }

    return item;
}

// 빈 하트
function emptyHeart(postId) {
    return `
                <svg onclick="clkLikes(this.id)" id="` + postId + `" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16">
                    <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
                </svg>`;
}

// 가득 찬 하트
function fullHeart(postId) {
    return `
                <svg onclick="clkUnLikes(this.id)" id="` + postId + `" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart-fill likes-box-fill" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
                </svg>`;
}

// 글 제목 item 리턴
function postTitleItem(id, title, createdDate) {
    return `
            <div class="row">
                <div class="col-xxl-12 align-self-center">
                    <div class="post-item">
                        <a href="#post-item-top-`+id+`" class="post-item-title">`+title+`</a><span class="float-end post-item-date">`+createdDate+`</span>
                    </div>
                </div>
            </div>`;
}

// 등록된 글 없을 때
function emptyPostTitleItem() {
    return `
            <div class="row">
                <div class="col-xxl-12">
                    <div class="text-center post-item no-post">
                        <h5>등록된 글이 없습니다.</h5>
                    </div>
                </div>
            </div>`;
}

/**
 * 그 외 함수들
 * */

function deleteComment(commentId) {

    $.ajax({
        type: "delete",
        url: '/api/comment/delete',
        data: {commentId: commentId},
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res => {
        $('#modal-delete-comment').modal('hide');
        $('#comment-item-' + commentId).remove();
    }).fail(error => {
        $('#modal-delete-comment').modal('hide');
        alert("서버오류로 댓글 삭제를 실패했습니다.");
    });
}

function commentUpdatePWInputEvent() {
    $('#update-comment-password').on("propertychange change keyup paste input", (key) => {
        $('#update-comment-description').text("");
    });
}

// 글 content 로 이동
function moveOffset() {
    if(!nowUrl.href.includes('#')) return;

    let urlSplit = nowUrl.href.split('#');
    let contentId = urlSplit[urlSplit.length - 1];

    var offset = $("#"+contentId).offset();
    $('html, body').animate({scrollTop : offset.top}, 300);
}



