package com.nedogeek.holdem.server;


import com.nedogeek.holdem.dealer.EventManager;
import org.eclipse.jetty.websocket.WebSocket;

/**
 * User: Konstantin Demishev
 * Date: 06.08.12
 * Time: 16:41
 */
public class HoldemWebSocket implements WebSocket.OnTextMessage {
    Connection connection;

    @Override
    public void onMessage(String command) {


    }

    @Override
    public void onOpen(Connection connection) {
        this.connection = connection;
        System.out.println("Connection opened");
        EventManager.getInstance().addViewer(connection);
    }

    @Override
    public void onClose(int i, String s) {
        EventManager.getInstance().closeConnection(connection);
    }


}
