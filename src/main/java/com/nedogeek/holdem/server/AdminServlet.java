package com.nedogeek.holdem.server;

/*-
 * #%L
 * Holdem dojo project is a server-side java application for playing holdem pocker in DOJO style.
 * %%
 * Copyright (C) 2016 Holdemdojo
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Konstantin Demishev
 * Date: 17.04.13
 * Time: 14:18
 */
@WebServlet(urlPatterns = "/admin", loadOnStartup = 1)
public class AdminServlet extends HttpServlet {
    private AdminModel model;
    private AdminCommandsPerformer commandsPerformer;
    private AdminLogic adminLogic = new AdminLogic();

    @Override
    public void init() throws ServletException {
        model = new AdminModelImpl();
        commandsPerformer = new AdminCommandsPerformer(model);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        redirectToLoginPage(httpServletRequest, httpServletResponse);
    }

    private void redirectToLoginPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String redirectPath = "/admin/index.html";
        redirectTo(httpServletRequest, httpServletResponse, redirectPath);
    }

    private void redirectTo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String redirectPath) throws ServletException, IOException {
        RequestDispatcher view = httpServletRequest.getRequestDispatcher(redirectPath);
        view.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        checkLoginInfo(httpServletRequest, httpServletResponse);

        doCommand(httpServletRequest);

        httpServletRequest.setAttribute("gameData", model.getGameData());

        redirectTo(httpServletRequest, httpServletResponse, adminLogic.successPage);
    }

    private void checkLoginInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String PASSWORD = "password";
        final String passwordValue = (String) httpServletRequest.getSession().getAttribute(PASSWORD);
        if (!model.passwordCorrect(passwordValue)) {
            String password = httpServletRequest.getParameter(PASSWORD);
            if (model.passwordCorrect(password)) {
                httpServletRequest.getSession().setAttribute(PASSWORD, password);
            } else {
                redirectToLoginPage(httpServletRequest, httpServletResponse);
            }
        }
    }

    private void doCommand(HttpServletRequest httpServletRequest) {
        String command = httpServletRequest.getParameter("command");
        String paramsString = httpServletRequest.getParameter("params");

        System.out.println("Performing action: " + command + "; params: " + paramsString);

        if (command != null && paramsString != null) {
            String[] params = paramsString.split("\n");
            for (int i = 0; i < params.length; i++) {
                params[i] = params[i].replace("\n", "").replace("\r", "");
            }
            commandsPerformer.performCommand(command, params);
        }
    }
}
