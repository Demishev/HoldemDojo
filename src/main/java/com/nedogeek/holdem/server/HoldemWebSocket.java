package com.nedogeek.holdem.server;


import com.nedogeek.holdem.GameImpl;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import org.eclipse.jetty.websocket.WebSocket;


/**
 * User: Konstantin Demishev
 * Date: 06.08.12
 * Time: 16:41
 */
class HoldemWebSocket implements WebSocket.OnTextMessage {

    private Connection connection;
    private String login;
    private Player player;

    public HoldemWebSocket() {

    }

    public HoldemWebSocket(String login) {
        this.login = login;

    }

    @Override
    public void onMessage(String command) {
        if (player != null) {
            PlayerAction playerAction = PlayerAction.defineAction(command);

            player.setMove(playerAction);
        }
    }


    @Override
    public void onOpen(Connection connection) {
        this.connection = connection;
        if (login == null) {
            GameImpl.getInstance().addViewer(connection);
        } else {
            player = GameImpl.getInstance().addPlayer(login, connection);
        }
    }

    @Override
    public void onClose(int i, String s) {
        GameImpl.getInstance().removeConnection(connection);
    }
}