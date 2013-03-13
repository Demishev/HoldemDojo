package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.GameRound;

/**
 * User: Demishev
 * Date: 10.02.13
 * Time: 1:09
 */
public class ChangeGameRoundEvent extends Event {
    public ChangeGameRoundEvent(GameRound gameRound) {
        super(gameRound + " game round started.");
    }
}
