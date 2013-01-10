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

    private final ActionType actionType;
    private final int riseAmount;

    public PlayerAction(ActionType actionType) {
        this.actionType = actionType;
        riseAmount = 0;
    }

    public PlayerAction(ActionType actionType, int riseAmount) {
        this.actionType = actionType;
        this.riseAmount = riseAmount;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getRiseAmount() {
        return riseAmount;
    }

}
