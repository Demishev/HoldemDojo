package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 3:24
 */
public class StopCommand extends AdminCommand {
    public StopCommand(AdminModel adminModel) {
        super(adminModel);
    }

    @Override
    public void invoke(String[] params) {
        adminModel.stop();
    }
}
