package com.nedogeek.holdem.server;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Konstantin Demishev
 * Date: 13.08.12
 * Time: 14:25
 */
public class HoldemWebSocketHandler extends WebSocketHandler {
    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
        return new HoldemWebSocket();
    }
}
