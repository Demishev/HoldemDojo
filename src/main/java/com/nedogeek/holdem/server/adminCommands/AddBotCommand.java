package com.nedogeek.holdem.server.adminCommands;

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
