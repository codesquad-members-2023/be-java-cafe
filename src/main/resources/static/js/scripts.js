String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

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
        var template = answerTemplate.format(data.userId, data.creationTime, data.contents, data.articleId, data.replyId);
                    $(".qna-comment-slipp-articles").append(template);
                    $("textarea[name=replyContents]").val("");
        var numberOfArticles = $(".qna-comment-slipp-articles > article").length;
        $(".qna-comment-count strong").text(numberOfArticles);
        $('.delete-answer-button').last().on("click", deleteAnswer);
    }
    $.ajax({
            type : 'post',
            url : url,
            data : queryString,
            dataType : 'json',
            error : onError,
            success : onSuccess
        });

}


$('.delete-answer-button').click(deleteAnswer);

function deleteAnswer(e) {
    var deleteBtn = (e.target);
    var url = e.target.dataset['url'];

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json'
        }).done((data) => {
                deleteBtn.closest("article").remove();
                var numberOfArticles = $(".qna-comment-slipp-articles > article").length;
                var qnaCommentCounter = $(".qna-comment-count strong").text(numberOfArticles);
        }).fail((data, status) => {
            alert(data.responseJSON.errorMessage);
        });
}

