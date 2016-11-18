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


import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Konstantin Demishev
 * Date: 13.08.12
 * Time: 14:25
 */
public class HoldemWebSocketServlet extends WebSocketServlet {

    private final HoldemWebSocketConnector holdemWebSocketConnector = new HoldemWebSocketConnector();

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
        String login = request.getParameter("user");
        String password = request.getParameter("password");
        String gameID = request.getParameter("gameID");

        return holdemWebSocketConnector.connect(login, password, gameID);
    }
}
