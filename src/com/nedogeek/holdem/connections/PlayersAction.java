package com.nedogeek.holdem.connections;

/**
 * User: Konstantin Demishev
 * Date: 08.10.12
 * Time: 21:47
 */
public class PlayersAction {
    enum ActionType {
        Fold, Check, Call, Bet, AllIn
    }

    public ActionType getActionType() {
        return null;
    }

    public int getBetQuantity() {
        return 0;
    }

}
