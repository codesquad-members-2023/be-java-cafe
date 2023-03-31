String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

$(".qna-comment").each(requestReplyAll)

function requestReplyAll() {
    const url = `/article/${$(".article-id").val()}/replyAll`;
    const queryString = $("form[name=replySubmit]").serialize();
    const type = 'GET'

    $.ajax({
        type: type,
        data: queryString,
        url: url,
        dataType: 'json',
        error: function () {
            console.log("ajax list error")
        },
        success: function (data) {
            var answerTemplate = $("#answerTemplate").html();
            for (var i = 0; i < data.length; i++) {
                var template = answerTemplate.format(data[i].userId, data[i].time, data[i].contents, data[i].replyId, data[i].articleId);
                $("#userIdNew").val(data[i].userId);
                $(".qna-comment-slipp-articles").prepend(template);
            }
            $("textarea[name=contents]").val("");
            var commentCount = data.length;
            var string = '개의 의견';
            $(".qna-comment-st1").text(commentCount);
            $(".qna-comment-st2").text(string);
        }
    });
}

$(".submit-write button[type=submit]").click(sendNewReply);

function sendNewReply(e) {
    e.preventDefault();
    const url = $(".submit-write").attr("action");
    const queryString = $("form[name=replySubmit]").serialize();
    const type = 'POST'

    $.ajax({
        type: type,
        data: queryString,
        url: url,
        dataType: 'json',
        error: function () {
            console.log("ajax new error")
        },
        success: function (data) {
            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.userId, data.time, data.contents, data.replyId, data.articleId);
            $(".qna-comment-slipp-articles").prepend(template);
            $("textarea[name=contents]").val("");
            var string = '개의 의견';
            $(".qna-comment-st1").text(parseInt($(".qna-comment-st1").text()) + 1);
            $(".qna-comment-st2").text(string);
        }
    });
}

$(document).on("click", ".delete-answer-form button[type=submit]", sendDeleteReply);
function sendDeleteReply(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var url = $(".delete-answer-form").attr("action");

    $.ajax({
        type : 'DELETE',
        url : url,
        dataType : 'json',
        error : function (xhr, status) {
            console.log("error");
        },
        success : function (data, status) {
            if (data.valid) {
                deleteBtn.closest("article").remove();
            } else {
                alert(data.message);
            }
        }
    });
}




