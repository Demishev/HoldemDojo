package com.nedogeek.holdem.dealer;

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


import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gameEvents.ChangeGameRoundEvent;
import com.nedogeek.holdem.gameEvents.PlayerMovesNotificationEvent;
import com.nedogeek.holdem.gamingStuff.Card;
import com.nedogeek.holdem.gamingStuff.CardDeck;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:02
 */
public class Dealer implements Runnable {
    private final MoveManager moveManager;
    private final NewGameSetter newGameSetter;
    private final PlayersList playersList;
    private final EndGameManager endGameManager;
    private final EventManager eventManager;

    private GameStatus gameStatus = GameStatus.NOT_ENOUGH_PLAYERS;
    private GameRound gameRound;

    private int callValue;
    private CardDeck cardDeck;
    private List<Card> deskCards = new ArrayList<>();


    public Dealer(PlayersList playersList, EventManager eventManager) {
        this.playersList = playersList;
        this.eventManager = eventManager;

        this.eventManager.setDealer(this);

        moveManager = new MoveManager(this, playersList, eventManager);

        newGameSetter = new NewGameSetter(this, playersList, moveManager, eventManager);
        endGameManager = new EndGameManager(this, playersList, eventManager);
        gameRound = GameRound.INITIAL;
    }

    /**
     * Test constructor
     */
    Dealer(MoveManager moveManager, NewGameSetter newGameSetter,
           PlayersList playersList, EndGameManager endGameManager,
           GameStatus gameStatus, GameRound gameRound, EventManager eventManager) {
        this.moveManager = moveManager;
        this.newGameSetter = newGameSetter;
        this.playersList = playersList;
        this.endGameManager = endGameManager;
        this.gameStatus = gameStatus;
        this.gameRound = gameRound;
        this.eventManager = eventManager;
    }

    public void run() {
        gameStatus = calculateGameStatus();
        while (!(gameStatus == GameStatus.STOPPED || gameStatus == GameStatus.PAUSED)) {
            tick();
        }
    }

    GameStatus calculateGameStatus() {
        if (playersList.size() < 2) {
            return GameStatus.NOT_ENOUGH_PLAYERS;
        }

        if (gameStatus == GameStatus.STOPPED) {
            gameRound = GameRound.INITIAL;
        }

        return (gameStatus == GameStatus.PAUSED) ?
                GameStatus.STARTED : GameStatus.READY;
    }

    void tick() {
        switch (gameStatus) {
            case READY:
                setGameStarted();
                break;
            case STARTED:
                makeGameAction();
                break;
            default:
                try {
                    Thread.sleep(GameSettings.getGameDelayValue());
                } catch (InterruptedException ignored) {
                }
        }
    }

    private void makeGameAction() {
        switch (gameRound) {
            case INITIAL:
                newGameSetter.setNewGame();
                break;
            case FINAL:
                endGameManager.endGame();
                break;
            default:
                if (playersList.hasAvailableMovers()) {
                    final Player mover = playersList.getMover();
                    eventManager.addEvent(new PlayerMovesNotificationEvent(mover));
                    moveManager.makeMove(mover);
                } else {
                    if (playersList.moreThanOnePlayerWithActiveStatus())
                        setNextGameRound();
                    else
                        goToFinalRound();
                }
                break;
        }
    }

    private void goToFinalRound() {
        while (gameRound != GameRound.FINAL) {
            setNextGameRound();
        }
    }

    void setGameStarted() {
        gameStatus = GameStatus.STARTED;
        newGameSetter.setNewGame();
    }

    public int getCallValue() {
        return callValue;
    }

    void setCallValue(int callValue) {
        this.callValue = callValue;
    }

    void resetCards() {
        cardDeck = new CardDeck();
        deskCards = new ArrayList<>();
    }

    void setNextGameRound() {
        gameRound = GameRound.next(gameRound);
        switch (gameRound) {
            case THREE_CARDS:
                deskCards.add(cardDeck.getCard());
                deskCards.add(cardDeck.getCard());
                deskCards.add(cardDeck.getCard());
                break;
            case FOUR_CARDS:
            case FIVE_CARDS:
                deskCards.add(cardDeck.getCard());
        }

        eventManager.addEvent(new ChangeGameRoundEvent(gameRound));

        if (gameRound != GameRound.BLIND) {
            playersList.setPlayersNotMoved();
        }

    }

    void giveCardsToPlayer(Player player) {
        final Card[] cards = {cardDeck.getCard(), cardDeck.getCard()};
        player.setCards(cards);
    }

    void setInitialGameRound() {
        gameRound = GameRound.INITIAL;
    }

    public void stop() {
        gameStatus = GameStatus.STOPPED;
    }

    public void pause() {
        gameStatus = GameStatus.PAUSED;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public GameRound getGameRound() {
        return gameRound;
    }

    public Card[] getDeskCards() {
        Card[] cards = new Card[deskCards.size()];

        for (int i = 0; i < deskCards.size(); i++) {
            cards[i] = deskCards.get(i);
        }

        return cards;
    }
}
