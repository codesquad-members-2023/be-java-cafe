function addAnswer(e) {
    e.preventDefault();

    $(".submit-write button[type=submit]").click(addAnswer);

    var queryString = $("form[name=answer]").serialize(); // 폼의 name 속성을 answer로 변경

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);
    console.log("queryString" + queryString)

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',

        error: function (error) {
            console.log(error)
            alert("error 발생");
        },
        success : function (data, status) {
            console.log(data);
            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.userId, data.timestamp, data.contents);
            $("textarea[name=contents]").val("");

            // 새로운 댓글을 마지막 댓글로 추가
            var articles = $(".qna-comment-slipp-articles > article");
            var lastArticle = articles.last();
            var newArticle = $(template);

            // 새로운 댓글을 마지막 댓글로 추가
            lastArticle.after(newArticle);

            // 댓글 수 업데이트
            var numReplies = $(".qna-comment-slipp-articles > article").length;
            $(".qna-comment-count strong").val(numReplies);
        }
    });
}
