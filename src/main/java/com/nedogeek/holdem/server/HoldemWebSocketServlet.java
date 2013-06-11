package com.nedogeek.holdem.server;

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
