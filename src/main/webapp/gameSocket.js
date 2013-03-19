/**
 * Date: 16.03.13
 * Time: 12:24
 */
document.write("<script src='jquery.min.js' type='text/javascript'></script>");

var WS_ADDRESS = 'ws://localhost:8080/ws';
var SAMPLE_MESSAGE =
    '{"gameRound":"","dealerNumber":-1,"moverNumber":-1,"events":[],"players":[],"gameStatus":"","deskCards":[]}';

var gameStatus = 'Disconnected';
var gameRound = 'Undefined';

var pot = 0;
var deskCards = '';

var movingPlayer = 0;
var dealerPlayer = 0;

var history = '';

var players = [];

var socket

var connect = function (adderess) {

    socket = new WebSocket(adderess);

    socket.onopen = function () {

    };

    socket.onclose = function () {

        document.getElementById("gameStatus").innerHTML = 'Disconnected';
    };

    socket.onmessage = function (message) {

        updateGameData(message.data)
    }
};

var updateGameData = function (stringData) {
    function updateJSON() {
        var gameData = JSON.parse(stringData);
        gameStatus = gameData.gameStatus;
        gameRound = gameData.gameRound;
        if (gameData.dealerNumber != -1) dealerPlayer = gameData.players[gameData.dealerNumber].name;
        if (gameData.moverNumber != -1) movingPlayer = gameData.players[gameData.moverNumber].name;
        var cardsArray = gameData.deskCards;
        deskCards = '';
        for (var i = 0; i < cardsArray.length; i++) {
            deskCards += cardsArray[i].cardSuit + cardsArray[i].cardValue;
            if (i + 1 < cardsArray.length) {
                deskCards += ',';
            }
        }
        players = gameData.players;
        parseHistory(gameData.events);
        calculatePot();
    }

    if (stringData.indexOf("Your cards") != -1) {
        $('#playersCards').text(stringData);
    } else {
        updateJSON();
    }
    refreshPage();
};

var parseHistory = function (events) {
    history = '';
    for (var i = 0; i < events.length; i++) {
        history += events[i] + '\n';
    }
};

var calculatePot = function () {
    pot = 0;
    for (var i = 0; i < players.length; i++) {
        pot += parseInt(players[i].bet);
    }
};

var refreshPage = function () {
    document.getElementById("gameStatus").innerHTML = 'Game status: ' + gameStatus;

    document.getElementById("gameRound").innerHTML = 'Round: ' + gameRound;
    document.getElementById("dealer").innerHTML = 'Dealer is: ' + dealerPlayer;
    document.getElementById("mover").innerHTML = 'Mover is: ' + movingPlayer;
    document.getElementById("pot").innerHTML = 'Pot: ' + pot;
    document.getElementById("deskCards").innerHTML = 'Desk cards: ' + deskCards;

    document.getElementById("console").innerHTML = history;


    document.getElementById("console").scrollTop = document.getElementById("console").scrollHeight;
    refreshPlayers();

};

var refreshPlayers = function () {
    playersTableInnerHTML = document.getElementById("playersTable");
    playersTableInnerHTML.innerHTML = '<tr> \
            <th>Name</th> \
            <th>balance</th> \
            <th>bet</th>    \
            <th>status</th> \
        </tr>';

    for (var i = 0; i < players.length; i++) {
        player = players[i];
        playersTableInnerHTML.innerHTML += '  <tr>\
            <td>' + player.name + '</td>  \
            <td>' + player.balance + '</td> \
            <td>' + player.bet + '</td> \
            <td>' + player.status + '</td>';
        playersTableInnerHTML.innerHTML += ' </tr> ';
    }
};

var makeMove = function () {

    socket.send($('input:checked').val())


};