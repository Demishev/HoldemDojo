package com.nedogeek.holdem.gamingStuff;

/*-
 * #%L
 * Holdem dojo project is a server-side java application for playing holdem pocker in DOJO style.
 * %%
 * Copyright (C) 2016 Holdemdojo
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


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
