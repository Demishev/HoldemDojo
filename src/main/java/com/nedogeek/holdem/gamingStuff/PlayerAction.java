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


    public static PlayerAction defineAction(String command) {
        if (command == null) {
            return new PlayerAction(ActionType.Fold);
        }

        ActionType actionType;
        int actionValue;
        String actionTypePart;
        String actionValuePart;
        if (command.contains(",")) {
            actionTypePart = command.substring(0, command.indexOf(','));
            actionValuePart = command.substring(command.indexOf(',') + 1);
        } else {
            actionTypePart = command;
            actionValuePart = "0";
        }

        try {
            actionValue = Integer.parseInt(actionValuePart);
        } catch (NumberFormatException e) {
            actionValue = 0;
        }
        actionType = ActionType.Fold;

        if ("Check".equals(actionTypePart)) {
            actionType = ActionType.Check;
        }
        if ("Call".equals(actionTypePart)) {
            actionType = ActionType.Call;
        }
        if ("Rise".equals(actionTypePart)) {
            actionType = ActionType.Rise;
        }
        if ("AllIn".equals(actionTypePart)) {
            actionType = ActionType.AllIn;
        }

        return new PlayerAction(actionType, actionValue);
    }
}
