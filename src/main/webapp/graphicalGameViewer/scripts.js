/**
 * Date: 20.03.13
 * Time: 0:17
 */
document.write("<script src='jquery.min.js' type='text/javascript'></script>");
document.write("<script src='gameData.js' type='text/javascript'></script> ");

var sessionAction = function () {
    alert("Session action");
};

var drawGameData = function () {
    drawEvents();
    updateEvents();

    drawPlayers();
    drawMover();

    drawDeskInfo();
};


drawEvents = function () {
    $console = $(document).find(".console");

    $(gameData.events).each(function () {
        $console.append(this + "\n");
    });
};

updateEvents = function () {
    $(document).find(".console").append(gameData.lastEvent);
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
    fileName = "cardDeck/" + card.cardSuit + card.cardValue + ".png";
    cardDiv.css("background-image", "url(" + fileName + ")");
};

drawDeskInfo = function() {
    $deskCardsDiv = $(document).find(".deskCards .card");

    $deskCardsDiv.each(function(cardNumber) {
        $($deskCardsDiv[cardNumber]).css("opacity", 0);
    });

    $deskCards = $(gameData.deskCards);

    $deskCards.each(function(cardNumber) {
        updateCard($deskCards[cardNumber], $($deskCardsDiv[cardNumber]));


        $($deskCardsDiv[cardNumber]).css("opacity", 1);
    });


    $(document).find(".deskPot").text("Pot: $" + gameData.deskPot);

    $cardCombinationDiv = $(document).find(".cardCombination");
    if (gameData.cardCombination != undefined) {
        $cardCombinationDiv.text(gameData.cardCombination);
    } else {
        $cardCombinationDiv.text("");
    }

};

drawMover = function () {
    $(document).find(".player" + gameData.moverNumber).addClass("mover");
};
