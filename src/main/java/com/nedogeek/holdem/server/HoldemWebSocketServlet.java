package com.nedogeek.holdem.server;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Konstantin Demishev
 * Date: 13.08.12
 * Time: 14:25
 */
public class HoldemWebSocketServlet extends WebSocketServlet {
    private final Map<String, String> userData = new HashMap<>();

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
        String login = request.getParameter("user");
        String password = request.getParameter("password");

        if (login == null && password == null) {
            return new HoldemWebSocket();
        } else {
            if (login != null && password != null) {
                if (userData.containsKey(login)) {
                    if (userData.get(login).equals(password)) {
                        return new HoldemWebSocket(login);
                    }
                } else {
                    userData.put(login, password);
                    return new HoldemWebSocket(login);
                }
            }
        }
        return null;
    }

}
