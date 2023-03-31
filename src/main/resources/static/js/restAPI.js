const makeReplyHeader = function (writer, createAt) {
    const header =
        `<div class="article-header">
            <div class="article-header-thumb">
                <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
            </div>
            <div class="article-header-text">
                <a href="/user/profile/${writer}" class="article-author-name" >${writer}</a>
                <a href="#answer-1434" class="article-header-time" title="퍼머링크">${createAt}</a>
            </div>
        </div>`
    return header;
}

const makeReplyBody = function (content) {
    const body = `
        <div class="article-doc comment-doc">
            <p >${content}</p>
        </div>
        `
    return body;
}

const makeReplyFooter = function (replyId) {
    const footer =
        `<div class="article-util">
                    <ul class="article-util-list">
                        <li>
                            <a class="link-modify-article"
                               href="/questions/413/answers/1405/form">수정</a>
                        </li>
                        <li>
                            <form name="${replyId}" class="delete-answer-form" method="POST">
                                <input type="hidden" name="_method" value="DELETE">
                                <button type="submit" class="delete-answer-button">삭제</button>
                            </form>
                        </li>
                    </ul>
        </div>`
    return footer;
}

const makeReply = function (replyInformation) {
    const replyId = replyInformation["replyId"];
    const writer = replyInformation["writer"];
    const content = replyInformation["content"];
    const createAt = replyInformation["createAt"];
    return `${makeReplyHeader(writer, createAt)}\n${makeReplyBody(content)}\n${makeReplyFooter(replyId)}`
}

const requestReply = function(articleId){
    const form = document.getElementById("question");
    const formData = new FormData(form);
    formData.append("articleId", articleId);
    fetch(`/reply` , {
        method:"POST",
        headers:{
            'Content-Type' : 'application/json'
        },
        body:JSON.stringify(Object.fromEntries(formData))
    })
        .then(response => response.json())
        .then(replies => {
            let contexts = '';
            for (let i = 0; i < replies.length; i++) {
                contexts += makeReply(replies[i]);
            }
            document.querySelector("#qna-comment-box").innerHTML = contexts;
        })
        .then(() => deleteFunction());
};

