package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 4:24
 */
public class SetEndGameDelayValueCommand implements AdminCommand{
    @Override
    public void invoke(String[] params, AdminModel adminModel) {
        int EndGameDelayValue = Integer.parseInt(params[0]);

        adminModel.setEndGameDelay(EndGameDelayValue);
    }
}
