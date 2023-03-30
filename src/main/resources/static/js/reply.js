$(".submit-write button").click(addAnswer);

function addAnswer(e) {
    e.preventDefault(); //submit 이 자동으로 동작하는 것을 막는다.

    var queryString = $(".submit-write").serialize(); //form data들을 자동으로 묶어준다.
    var url = $(".submit-write").attr("action");
    var onError = function () {
        alert("error");
    }
    var onSuccess = function (data, status) {
        console.log(data);
        var answerTemplate = $("#answerTemplate").html();
        var template = answerTemplate.format(data.userId, data.creationTime, data.contents);
                    $(".qna-comment-slipp-articles").append(template);
                    $("textarea[name=replyContents]").val("");
        var numberOfArticles = $(".qna-comment-slipp-articles > article").length;
        $(".qna-comment-count strong").text(numberOfArticles);
    }
    console.log("url : " + url);
    console.log("query : "+ queryString);
    $.ajax({
            type : 'post',
            url : url,
            data : queryString,
            dataType : 'json',
            error : onError,
            success : onSuccess
        });

}

