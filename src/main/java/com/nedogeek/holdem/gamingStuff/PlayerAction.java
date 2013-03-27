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

    private static final ActionType DEFAULT_ACTION_TYPE = ActionType.Fold;

    private static final PlayerAction DEFAULT_ACTION = new PlayerAction(DEFAULT_ACTION_TYPE, 0);

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
            return DEFAULT_ACTION;
        }
        String[] parts = prepareCommandStrings(command);

        ActionType actionType = defineActionType(parts[0]);
        int actionValue = (parts.length > 1) ? defineActionValue(parts[1]) : 0;

        return new PlayerAction(actionType, actionValue);
    }

    private static String[] prepareCommandStrings(String command) {
        command = command.replace(" ", "");
        return command.split(",");
    }

    private static int defineActionValue(String valuePart) {
        try {
            return Integer.parseInt(valuePart);
        } catch (NumberFormatException ignored) {

        }
        return 0;
    }

    private static ActionType defineActionType(String actionPart) {
        for (PlayerAction.ActionType actionType : PlayerAction.ActionType.values()) {
            if (actionType.toString().equals(actionPart)) {
                return actionType;
            }
        }
        return DEFAULT_ACTION_TYPE;
    }
}
