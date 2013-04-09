package com.nedogeek.holdem;

import com.nedogeek.holdem.bot.CallBot;
import com.nedogeek.holdem.bot.RandomBot;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.dealer.EventManager;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.eclipse.jetty.websocket.WebSocket;

/**
 * User: Konstantin Demishev
 * Date: 09.04.13
 * Time: 23:20
 */
public class GameImpl implements Game {
    private static GameImpl instance;

    private EventManager eventManager;
    private Dealer dealer;
    private PlayersList players;

    private Thread dealerThread;

    private GameImpl() {
        eventManager = new EventManager();
        players = new PlayersList(eventManager);
        eventManager.setPlayersList(players);

        createDealer();
        start();
    }

    public static GameImpl getInstance() {
        if (instance == null) {
            instance = new GameImpl();
        }
        return instance;
    }

    GameImpl(EventManager eventManager, PlayersList players) {
        this.eventManager = eventManager;
        this.players = players;

        createDealer();
        start();
    }

    private void createDealer() {
        dealer = new Dealer(players, eventManager);

        players.add(new RandomBot(dealer));
        players.add(new CallBot(dealer));
    }

    @Override
    public void start() {
        if (dealerThread == null || !dealerThread.isAlive()) {
            startDealerThread();
        }
    }

    private void startDealerThread() {
        dealerThread = new Thread(dealer);
        dealerThread.start();
    }

    @Override
    public void pause() {
        dealer.pause();
    }

    @Override
    public void stop() {
        dealer.stop();
    }

    @Override
    public Player addPlayer(String name, WebSocket.Connection connection) {
        return eventManager.addPlayer(connection, name);
    }

    @Override
    public void addViewer(WebSocket.Connection connection) {
        eventManager.addViewer(connection);
    }

    @Override
    public void removeConnection(WebSocket.Connection connection) {
        eventManager.closeConnection(connection);
    }

    @Override
    public GameStatus getGameStatus() {
        return GameStatus.NOT_ENOUGH_PLAYERS;
    }
}
