/**
 * Date: 16.03.13
 * Time: 12:24
 */
document.write("<script src='jquery.min.js' type='text/javascript'></script>");
document.write("<script src='gameData.js' type='text/javascript'></script> ");
var socket;


var connect = function (path) {
    socket = new WebSocket(path);

    socket.onopen = function () {

    };

    socket.onclose = function () {

    };

    socket.onmessage = function (message) {
        gameData = JSON.parse(message.data);
        drawGameData();
    }
};
