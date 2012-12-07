package com.nedogeek.holdem.gamingStuff;

/**
 * User: Konstantin Demishev
 * Date: 08.10.12
 * Time: 21:47
 */
public class PlayerAction {
    public enum ActionType {
        Fold, Check, Call, Rise, AllIn
    }

    public ActionType getActionType() {
        return null;
    }

    public int getRiseAmount() {
        return 0;
    }

}
