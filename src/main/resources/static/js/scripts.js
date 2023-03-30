String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

$('#btn-save').on('click', (e) => {
    save();
});

function save() {
    var queryString = $(".submit-write textarea[name='contents']").serialize();
    var url = window.location.pathname + '/replies';
    var articleIndex = window.location.pathname.split('articles/')[1];


    $.ajax({
        type: 'POST',
        url: url,
        data: queryString,
        dataType: 'json',
    }).done((data) => {
        var answerTemplate = $("#answerTemplate").html();
        var template = answerTemplate.format(data.userName, data.createDate,
            data.contents, articleIndex, data.id);
        $(".qna-comment-slipp-articles").prepend(template);
        $("textarea[name=contents]").val("");

    }).fail((err) => {
        alert(JSON.stringify(err));
    });
}





