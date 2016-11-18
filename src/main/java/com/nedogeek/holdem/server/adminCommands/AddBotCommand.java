package com.nedogeek.holdem.server.adminCommands;

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


import com.nedogeek.holdem.bot.Bots;
import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 17.04.13
 * Time: 15:00
 */
public class AddBotCommand extends AdminCommand {

    public AddBotCommand(AdminModel adminModel) {
        super(adminModel);
    }

    @Override
    public void invoke(String[] params) {
        if (params == null || params.length != 2) {
            throw new IllegalArgumentException("Add bot command with wrong args.");
        }

        String botTypeName = params[0];
        String botName = params[1];

        if (botName == null || botTypeName == null) {
            throw new IllegalArgumentException("Add bot command with wrong args.");
        }

        Bots botType = Bots.getBotTypeByName(botTypeName);

        adminModel.addBot(botType, botName);
    }
}
