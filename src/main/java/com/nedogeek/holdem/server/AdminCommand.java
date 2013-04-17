package com.nedogeek.holdem.server;

/**
 * User: Konstantin Demishev
 * Date: 17.04.13
 * Time: 14:41
 */
public interface AdminCommand {
    void invoke(String[] params, AdminModel adminModel);
}
