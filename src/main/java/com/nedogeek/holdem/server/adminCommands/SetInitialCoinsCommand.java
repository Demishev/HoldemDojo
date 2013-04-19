package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 4:07
 */
public class SetInitialCoinsCommand implements AdminCommand{
    @Override
    public void invoke(String[] params, AdminModel adminModel) {

        int initialCoinsCount = Integer.parseInt(params[0]);

        adminModel.setInitialCoins(initialCoinsCount);
    }
}
