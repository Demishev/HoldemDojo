/**
 * User: Konstantin Demishev
 * Date: 20.03.13
 * Time: 12:09
 */

var login = undefined;
var server = "ws://77.47.200.184:8080/ws";

gameData = {


players: [ {
    name: "First player",
    balance: 100,
    pot: 28,

    cards: [{cardValue: "K", cardSuit: "♠"}, {cardValue: "10", cardSuit: "♥"}],

    status: "Folded"
}, {
    name: "Second player",
    balance: 12004,
    pot: 148,

    cards: [{cardValue: "2", cardSuit: "♦"}, {cardValue: "10", cardSuit: "♦"}],

    status: "Bet 15"
}, {
    name: "Third player",
    balance: 23,
    pot: 0
}],


deskCards: [{cardValue: "2", cardSuit: "♦"}, {cardValue: "4", cardSuit: "♦"},
    {cardValue: "5", cardSuit: "♦"}],

deskPot: 580,
moverNumber: 0,
dealerNumber: 0,

events: ["Some first event", "Some second event", "Some third event"],

cardCombination: "Some test card combination text."

};