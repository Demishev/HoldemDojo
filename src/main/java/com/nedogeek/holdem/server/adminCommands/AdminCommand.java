package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 17.04.13
 * Time: 14:41
 */
public interface AdminCommand {
    void invoke(String[] params, AdminModel adminModel);
}
