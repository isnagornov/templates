$(document).ready(function () {
    $(".searchAuthorForm").on("submit", searchAuthorByName);
    $(".foundAuthors").on("submit", loadBooks);
    $("#addBookByAuthorForm").on("submit", showAddPage);
});

function searchAuthorByName(e) {
    e.preventDefault();

    var nameToSearch = $(".searchAuthorForm #authorName").val();

    $.post('/authors/byName?nameToSearch=' + nameToSearch, null, function (data) {
        console.log(data);

        $(".searchAuthorForm").hide();
        $(".foundAuthors").html(data).show();
    });
}

function loadBooks(e) {
    e.preventDefault();

    var selectedAuthorId = $("#authorList").val();

    $.post('/books/byAuthor?selectedAuthorId=' + selectedAuthorId, null, function (data) {
        console.log(data);

        $(".foundAuthors").hide();
        $("#bookBlock").show();

        $(".bookList").html(data);
    });
}

function showAddPage(e) {
    e.preventDefault();

    var selectedAuthorId = $("#authorList").val();

    location.href = location.origin + '/books/addByAuthor?authorId=' + selectedAuthorId;
}
