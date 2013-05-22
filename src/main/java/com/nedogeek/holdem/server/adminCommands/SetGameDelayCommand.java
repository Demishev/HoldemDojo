package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 4:19
 */
public class SetGameDelayCommand extends AdminCommand {

    public SetGameDelayCommand(AdminModel adminModel) {
        super(adminModel);
    }

    @Override
    public void invoke(String[] params) {
        int delayValue = Integer.parseInt(params[0]);

        adminModel.setGameDelay(delayValue);
    }
}
