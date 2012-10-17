package com.nedogeek.holdem.connections;

/**
 * User: Konstantin Demishev
 * Date: 08.10.12
 * Time: 21:47
 */
public class PlayersAction {
    public enum ActionType {
        Fold, Check, Call, Rise, AllIn
    }

    public ActionType getActionType() {
        return null;
    }

    public int getBetQuantity() {
        return 0;
    }

}
