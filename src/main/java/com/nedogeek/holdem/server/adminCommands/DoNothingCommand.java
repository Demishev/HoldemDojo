package com.nedogeek.holdem.server.adminCommands;

/**
 * User: Konstantin Demishev
 * Date: 22.04.13
 * Time: 12:53
 */
public class DoNothingCommand extends AdminCommand {

    public DoNothingCommand() {
        super(null);
    }

    @Override
    public void invoke(String[] params) {
        //Doing nothing :)
    }
}
