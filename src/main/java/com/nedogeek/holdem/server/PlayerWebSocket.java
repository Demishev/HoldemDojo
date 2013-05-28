package com.nedogeek.holdem.server;


import com.nedogeek.holdem.GameCenter;
import com.nedogeek.holdem.PlayerCommand;
import com.nedogeek.holdem.PlayerCommandsParser;
import org.eclipse.jetty.websocket.WebSocket;


/**
 * User: Konstantin Demishev
 * Date: 06.08.12
 * Time: 16:41
 */
class PlayerWebSocket implements WebSocket.OnTextMessage {
    private final PlayerCommandsParser playerCommandParser;
    private final GameCenter gameCenter;

    private String login;

    PlayerWebSocket(PlayerCommandsParser playerCommandParser, GameCenter gameCenter, String login) {
        this.playerCommandParser = playerCommandParser;
        this.gameCenter = gameCenter;
        this.login = login;
    }

    public PlayerWebSocket(String login) {
        this.login = login;

        playerCommandParser = new PlayerCommandsParser();
        gameCenter = null; //TODO fixMe!!!!!
    }

    @Override
    public void onMessage(String message) {
        PlayerCommand playerCommand = playerCommandParser.parseCommand(message);
        gameCenter.performPlayerCommand(playerCommand);
    }


    @Override
    public void onOpen(Connection connection) {
        gameCenter.connectPlayer(login, connection);
    }

    @Override
    public void onClose(int i, String s) {
    }
}