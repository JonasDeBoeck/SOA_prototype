$(function () {
    $text = $("#container").text();
    $("#container").text("");
    $json = jQuery.parseJSON($text);
    $("#container").jsonViewer($json);
});