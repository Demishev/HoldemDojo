package com.nedogeek.holdem;

import com.nedogeek.holdem.bot.Bots;
import com.nedogeek.holdem.bot.CallBot;
import com.nedogeek.holdem.bot.RandomBot;
import com.nedogeek.holdem.dealer.ConnectionsManager;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.dealer.EventManager;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import com.nedogeek.holdem.server.GameDataBean;
import org.eclipse.jetty.websocket.WebSocket;

/**
 * User: Konstantin Demishev
 * Date: 09.04.13
 * Time: 23:20
 */
public class GameImpl implements Game {
    private static GameImpl instance;

    private final EventManager eventManager;
    private Dealer dealer;
    private final PlayersList players;
    private ConnectionsManager connectionsManager;

    private Thread dealerThread;

    private GameImpl() {
        eventManager = new EventManager();
        players = new PlayersList(eventManager);
        eventManager.setPlayersList(players);
        connectionsManager = new ConnectionsManager();
        eventManager.setConnectionsManager(connectionsManager);

        createDealer();
        start();
    }

    public static GameImpl getInstance() {
        if (instance == null) {
            instance = new GameImpl();
        }
        return instance;
    }

    GameImpl(PlayersList players, ConnectionsManager connectionsManager) {
        this.eventManager = new EventManager();
        this.players = players;
        this.connectionsManager = connectionsManager;

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
        connectionsManager.addPersonalConnection(name, connection);
        return players.getPlayerByName(name, dealer);
    }

    @Override
    public void addViewer(WebSocket.Connection connection) {
        connectionsManager.addViewer("DEFAULT", connection);
    }

    @Override
    public void addBot(Bots botType, String name) {
        players.add(Bots.createBot(botType, name, dealer));
    }

    @Override
    public GameDataBean getGameData() {
        GameDataBean gameDataBean = new GameDataBean();
        gameDataBean.setBotTypes(Bots.getBotTypes());

        gameDataBean.setCoinsAtStart(GameSettings.getCoinsAtStart());
        gameDataBean.setMinimumBind(GameSettings.getSmallBlind());

        gameDataBean.setGameDelay(GameSettings.getGameDelayValue());
        gameDataBean.setEndGameDelay(GameSettings.getEndGameDelayValue());

        gameDataBean.setGameStatus(dealer.getGameStatus());

        gameDataBean.setPlayers(players.getPlayerNames());

        return gameDataBean;
    }

    @Override
    public void setMove(String login, PlayerAction move) {
        players.setPlayerMove(login, move);
    }

    @Override
    public void removeConnection(WebSocket.Connection connection) {
        connectionsManager.removeConnection(connection);
    }

    @Override
    public void removePlayer(String playerName) {
        players.kickPlayer(playerName);
    }

}
