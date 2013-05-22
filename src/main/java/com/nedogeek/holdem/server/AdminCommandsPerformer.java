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

    public AdminCommandsPerformer(AdminModel adminModel) {
        commands = new HashMap<>();

        commands.put("kick", new KickCommand(adminModel));

        commands.put("start", new StartCommand(adminModel));
        commands.put("stop", new StopCommand(adminModel));
        commands.put("pause", new PauseCommand(adminModel));

        commands.put("addBot", new AddBotCommand(adminModel));

        commands.put("setInitialCoins", new SetInitialCoinsCommand(adminModel));
        commands.put("setMinimumBlind", new SetMinimumBlindCommand(adminModel));

        commands.put("setGameDelay", new SetGameDelayCommand(adminModel));
        commands.put("setEndGameDelay", new SetEndGameDelayValueCommand(adminModel));
        commands.put("refreshPage", new DoNothingCommand());

        commands.put("changePassword", new ChangePasswordCommand(adminModel));
    }

    public void performCommand(String actionName, String[] params) {
        if (!commands.containsKey(actionName)) {
            throw new IllegalArgumentException("Wrong command name");
        }
        commands.get(actionName).invoke(params);
    }
}
