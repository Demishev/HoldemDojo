/**
 * Date: 16.03.13
 * Time: 12:24
 */
var WS_ADDRESS = 'ws://localhost:8080/ws';
var SAMPLE_MESSAGE =
    '{"gameRound":"FOUR_CARDS","dealerNumber":1,"moverNumber":1,"events":[],"players":[{"balance":940,"status":"Rise","name":"Rise\
    bot","bet":60},{"balance":990,"status":"Fold","name":"Random \
    bot","bet":10},{"balance":940,"status":"Call","name":"Call\
    bot","bet":60},{"balance":1000,"status":"Fold","name":"Folding\
    bot","bet":0}],"gameStatus":"STARTED","deskCards":[{"cardValue":"5","cardValueName":"5","cardSuit":"♠"},{"cardValue":"Q","cardValueName":"Queen","cardSuit":"♣"},{"cardValue":"10","cardValueName":"10","cardSuit":"♠"},{"cardValue":"3","cardValueName":"3","cardSuit":"♦"}]}';

var gameStatus = 'Disconnected';
var gameRound = 'Undefined';

var pot = 0;
var deskCards = '';

var movingPlayer = 0;
var dealerPlayer = 0;

var history = '';

var players = [];

var connect = function (adderess) {

    var socket = new WebSocket(adderess);

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
    var gameData = JSON.parse(stringData);

    gameStatus = gameData.gameStatus;
    gameRound = gameData.gameRound;
    dealerPlayer = gameData.players[gameData.dealerNumber].name;
    movingPlayer = gameData.players[gameData.moverNumber].name;


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