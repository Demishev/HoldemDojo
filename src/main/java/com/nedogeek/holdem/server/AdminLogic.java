package com.nedogeek.holdem.server;

/**
 * User: Konstantin Demishev
 * Date: 07.06.13
 * Time: 23:48
 */
public class AdminLogic {
    public String successPage = "/admin/adminPage.jsp";
    public String result;


    public void checkLoginInfo(String sessionPassword) {
        if("correctSessionPassword".equals(sessionPassword))
            return;
        if("wrongRequestPassword".equals(sessionPassword))   {
            result = "redirectToLoginPage";
            return;
        }
        result = "changeSessionPassword";
    }
}
