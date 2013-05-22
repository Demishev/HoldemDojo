package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 17.04.13
 * Time: 14:41
 */
public abstract class AdminCommand {
    protected final AdminModel adminModel;

    protected AdminCommand(AdminModel adminModel) {
        this.adminModel = adminModel;
    }

    public abstract void invoke(String[] params);
}
