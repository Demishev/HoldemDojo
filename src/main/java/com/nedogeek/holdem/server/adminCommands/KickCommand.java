package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 3:01
 */
public class KickCommand implements AdminCommand {
    @Override
    public void invoke(String[] params, AdminModel adminModel) {
        if (params == null || params.length != 1 || params[0] == null) {
            throw new IllegalArgumentException("Wrong params to kick command");
        }

        String playerName = params[0];

        adminModel.kick(playerName);
    }
}
