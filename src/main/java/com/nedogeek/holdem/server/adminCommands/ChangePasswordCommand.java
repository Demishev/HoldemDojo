package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 22.04.13
 * Time: 17:36
 */
public class ChangePasswordCommand implements AdminCommand {
    @Override
    public void invoke(String[] params, AdminModel adminModel) {
        adminModel.changePassword(params[0]);
    }
}
