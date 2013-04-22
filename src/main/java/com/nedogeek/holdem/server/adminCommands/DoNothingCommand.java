package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;

/**
 * User: Konstantin Demishev
 * Date: 22.04.13
 * Time: 12:53
 */
public class DoNothingCommand implements AdminCommand {
    @Override
    public void invoke(String[] params, AdminModel adminModel) {
        //Doing nothing :)
    }
}
