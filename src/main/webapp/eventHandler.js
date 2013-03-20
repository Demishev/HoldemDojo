/**
 * User: Konstantin Demishev
 * Date: 20.03.13
 * Time: 21:13
 */

eventTypes = {
    PLAYER_ADDED: "PLAYER_ADDED",
    CARDS_GIVEN: "CARDS_GIVEN",
    ROUND_CHANGED: "ROUND_CHANGED",
    MOVE_EVENT: "MOVE_EVENT",
    NEW_GAME_STARTED: "NEW_GAME_STARTED",
    PLAYER_CONNECTED: "PLAYER_CONNECTED",
    MOVER_IS: "MOVER_IS",
    PLAYER_WIN: "PLAYER_WIN",
    PLAYER_DISCONNECTED: "PLAYER_DISCONNECTED"
};



var parseEvent = function(event) {
    eventType = event.EventType;

    if (eventType == eventType.PLAYER_ADDED) {
        drawGameData();
    } else if (eventType == eventTypes.CARDS_GIVEN) {

    } else if (eventType == eventTypes.ROUND_CHANGED) {

    } else if (eventType == eventTypes.MOVE_EVENT) {

    } else if (eventType == eventTypes.NEW_GAME_STARTED) {

    } else if (eventType == eventTypes.PLAYER_CONNECTED) {

    } else if (eventType == eventTypes.MOVER_IS) {

    } else if (eventType == eventTypes.PLAYER_WIN) {

    } else if (eventType == eventTypes.PLAYER_DISCONNECTED) {
        drawGameData();
    }
};


