package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 22.04.13
 * Time: 17:36
 */
public class ChangePasswordCommand extends AdminCommand {

    public ChangePasswordCommand(AdminModel adminModel) {
        super(adminModel);
    }

    @Override
    public void invoke(String[] params) {
        adminModel.changePassword(params[0]);
    }
}
