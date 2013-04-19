package com.nedogeek.holdem.bot;

import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 13:39
 */
public abstract class Bot extends Player {
    public Bot(String name, Dealer dealer) {
        super(name, dealer);
    }

    @Override
    public abstract PlayerAction getMove();
}
