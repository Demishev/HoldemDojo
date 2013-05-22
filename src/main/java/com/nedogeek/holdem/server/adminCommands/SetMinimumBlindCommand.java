package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 4:15
 */
public class SetMinimumBlindCommand extends AdminCommand {


    public SetMinimumBlindCommand(AdminModel adminModel) {
        super(adminModel);
    }

    @Override
    public void invoke(String[] params) {
        int MinimumBlind = Integer.parseInt(params[0]);

        adminModel.setMinimumBlind(MinimumBlind);
    }
}
