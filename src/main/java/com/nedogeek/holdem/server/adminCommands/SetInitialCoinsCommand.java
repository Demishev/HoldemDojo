package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 4:07
 */
public class SetInitialCoinsCommand extends AdminCommand {

    public SetInitialCoinsCommand(AdminModel adminModel) {
        super(adminModel);
    }

    @Override
    public void invoke(String[] params) {

        int initialCoinsCount = Integer.parseInt(params[0]);

        adminModel.setInitialCoins(initialCoinsCount);
    }
}
