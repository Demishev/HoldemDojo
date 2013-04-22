package com.nedogeek.holdem.server;

import com.nedogeek.holdem.server.adminCommands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Konstantin Demishev
 * Date: 17.04.13
 * Time: 14:39
 */
public class AdminCommandsPerformer {
    private Map<String, AdminCommand> commands;

    public AdminCommandsPerformer() {
        commands = new HashMap<>();

        commands.put("kick", new KickCommand());

        commands.put("start", new StartCommand());
        commands.put("stop", new StopCommand());
        commands.put("pause", new PauseCommand());

        commands.put("addBot", new AddBotCommand());

        commands.put("setInitialCoins", new SetInitialCoinsCommand());
        commands.put("setMinimumBlind", new SetMinimumBlindCommand());

        commands.put("setGameDelay", new SetGameDelayCommand());
        commands.put("setEndGameDelay", new SetEndGameDelayValueCommand());
        commands.put("refreshPage", new DoNothingCommand());
    }

    public void performAction(String actionName, String[] params, AdminModel adminModel) {
        if (!commands.containsKey(actionName)) {
            throw new IllegalArgumentException("Wrong command name");
        }
        commands.get(actionName).invoke(params, adminModel);
    }
}
