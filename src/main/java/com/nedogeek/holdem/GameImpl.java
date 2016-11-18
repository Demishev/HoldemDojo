package com.nedogeek.holdem;

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


import com.nedogeek.holdem.bot.Bots;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.dealer.EventManager;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import com.nedogeek.holdem.server.GameDataBean;

import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 09.04.13
 * Time: 23:20
 */
public class GameImpl implements Game {
    private final EventManager eventManager;
    private Dealer dealer;
    private final PlayersList players;

    private Thread dealerThread;

    GameImpl(String gameID, GameCenter gameCenter) {
        eventManager = new EventManager(gameID, gameCenter);
        players = new PlayersList(eventManager);
        eventManager.setPlayersList(players);

        createDealer();
        start();
    }

    /**
     * Test constructor!!
     *
     * @param players injecting players for test reasons.
     */
    GameImpl(PlayersList players) {
        this.eventManager = new EventManager(null, null);
        this.players = players;

        createDealer();
        start();
    }

    private void createDealer() {
        dealer = new Dealer(players, eventManager);
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
    public Player addPlayer(String name) {
        return players.getPlayerByName(name, dealer);
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
    public List<String> getPlayerNames() {
        return players.getPlayerNames();
    }


    @Override
    public void removePlayer(String playerName) {
        players.kickPlayer(playerName);
    }

}
