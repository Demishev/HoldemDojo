/**
 * Date: 20.03.13
 * Time: 0:17
 */
document.write("<script src='jquery.min.js' type='text/javascript'></script>");
document.write("<script src='gameData.js' type='text/javascript'></script> ");
document.write("<script src='gameSocket.js' type='text/javascript'></script> ");

var path;

var sessionAction = function () {
    login = $(document).find('#login').val();
    password = $(document).find('#password').val();

    path = server + '?user=' + login + '&password=' + password;

    if (socket != undefined) {
        socket.close();
    }

    connect(path);
};

var drawGameData = function () {
    drawEvents();

    drawPlayers();
    drawMover();

    drawDeskInfo();
};


var drawEvents = function () {
    $console = $(document).find(".console");

    event = gameData.event;
    for (i = 0; i < event.length; i++) {
        $console.append(event[i] + "\n");
    }

    $console.scrollTop($console[0].scrollHeight - $console.height());
};

drawPlayers = function () {
    for (i = 0; i < 12; i++) {
        var player = $(document).find(".player" + i);
        player.css("opacity", 0);

        if (player.hasClass("mover")) {
            player.removeClass("mover");
        }
    }

    players = gameData.players;

    for (i = 0; i < players.length; i++) {
        if (players[i] != undefined) {
            player = players[i];
            playerDiv = $(document).find(".player" + i);
            playerDiv.css("opacity", 1);

            $(playerDiv).find(".name").text(player.name);
            $(playerDiv).find(".balance").text("Balance: $" + player.balance);
            $(playerDiv).find(".pot").text("Pot: $" + player.pot);

            if (player.status == undefined) {
                $(playerDiv).find(".status").css("opacity", 0);
            } else {
                $(playerDiv).find(".status").text(player.status);
                $(playerDiv).find(".status").css("opacity", 1);
            }

            for (cardNumber = 0; cardNumber < 2; cardNumber++) {
                $(playerDiv).find(".card" + cardNumber).css("background-image", "url(cardDeck/blank.png)");
            }

            if (player.cards != undefined) {
                for (cardNumber = 0; cardNumber < player.cards.length; cardNumber++) {
                    card = player.cards[cardNumber];
                    cardDiv = $(playerDiv).find(".card" + cardNumber);
                    updateCard(card, cardDiv);
                }
            }
        }
    }
}
;

updateCard = function (card, cardDiv) {
    fileName = "cardDeck/" + card.cardValue + card.cardSuit + ".png";
    cardDiv.css("background-image", "url(" + fileName + ")");
};

drawDeskInfo = function () {
    $deskCardsDiv = $(document).find(".deskCards .card");

    $deskCardsDiv.each(function (cardNumber) {
        $($deskCardsDiv[cardNumber]).css("opacity", 0);
    });

    $deskCards = $(gameData.deskCards);

    $deskCards.each(function (cardNumber) {
        updateCard($deskCards[cardNumber], $($deskCardsDiv[cardNumber]));


        $($deskCardsDiv[cardNumber]).css("opacity", 1);
    });


    $(document).find(".deskPot").text("Pot: $" + gameData.deskPot);

    $cardCombinationDiv = $(document).find(".cardCombination");
    if (gameData.combination != undefined) {
        $cardCombinationDiv.text(gameData.combination);
    } else {
        $cardCombinationDiv.text("");
    }

};

drawMover = function () {
    if (gameData.mover != "")
        $(document).find('.name:contains(' + gameData.mover + ')').parent().addClass("mover");
};
