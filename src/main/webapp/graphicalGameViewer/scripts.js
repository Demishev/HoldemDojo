/**
 * Date: 20.03.13
 * Time: 0:17
 */
document.write("<script src='jquery.min.js' type='text/javascript'></script>");
document.write("<script src='gameData.js' type='text/javascript'></script> ");

var sessionAction = function() {
    alert("Session action");
};

var drawGameData = function() {
    drawEvents();
    updateEvents();

    drawPlayers();
    drawMover();
};


drawEvents = function() {
    console = $(document).find(".console");

    $(gameData.events).each(function() {
        console.append(this + "\n");
    });
};

updateEvents = function() {
    $(document).find(".console").append(gameData.lastEvent);
};

drawPlayers = function() {
    for (i = 0; i < 12; i++) {
        var player = $(document).find(".player" + i);
        player.css("opacity",0);

        if (player.hasClass("mover")) {
            player.removeClass("mover");
        }
    }

    players = gameData.players;

    for (i = 0; i < players.length; i++) {
        if (players[i] != undefined) {
            player = players[i];
            playerDiv = $(document).find(".player" + i);
            playerDiv.css("opacity",1);

            $(playerDiv).find(".name").text(player.name);
            $(playerDiv).find(".balance").text("Balance: $" + player.balance);
            $(playerDiv).find(".pot").text("Pot: $" + player.pot);
            $(playerDiv).find(".status").text(player.status);
            for (cardNumber = 0; cardNumber < player.cards.length; cardNumber++) {
                card = player.cards[cardNumber];
                cardDiv = $(playerDiv).find(".card" + cardNumber);
                updateCard(card, cardDiv);
            }
        }
    }
};

updateCard = function(card, cardDiv) {
    fileName = "cardDeck/" + card.cardSuit + card.cardValue + ".png";

    cardDiv.css("background-image", "url(" + fileName + ")");
};

drawMover = function() {
    $(document).find(".player" + gameData.moverNumber).addClass("mover");
};
