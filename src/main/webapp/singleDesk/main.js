var targetUrl = "http://localhost:8080/test/tables.html";

function showTablesCallback(callback) {
    alert(document.targetUrl);
    $.ajax({
        url: targetUrl,
        success: callback,
        error: function (xhr, ajaxOptions, thrownError) {

        }
    });
}

function showTables() {

    callback = function (value) {
        $fixture = $("#qunit-fixture");
        $fixture.append(value);

    };

    showTablesCallback(callback);

}