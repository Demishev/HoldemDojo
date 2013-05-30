package com.nedogeek.holdem.server;

import com.nedogeek.holdem.GameCenter;
import com.nedogeek.holdem.GameCenterImpl;
import com.nedogeek.holdem.PlayerCommandsParser;

/**
 * User: Konstantin Demishev
 * Date: 30.05.13
 * Time: 1:54
 */
public class HoldemWebSocketFactory {
    private final PlayerCommandsParser playerCommandsParser = new PlayerCommandsParser();
    private final GameCenter gameCenter = GameCenterImpl.getInstance();

    public PlayerWebSocket getUserSocket(String player) {
        return new PlayerWebSocket(playerCommandsParser, gameCenter, player);
    }

    public ViewerWebSocket getViewerSocket(String gameID) {
        return new ViewerWebSocket(gameID, gameCenter);
    }
}
