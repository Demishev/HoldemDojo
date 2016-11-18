package com.nedogeek.holdem.server;

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
