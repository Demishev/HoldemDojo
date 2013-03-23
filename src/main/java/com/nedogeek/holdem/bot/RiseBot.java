package com.nedogeek.holdem.bot;

import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;

/**
 * User: Konstantin Demishev
 * Date: 26.02.13
 * Time: 21:39
 */
public class RiseBot extends Player {
    public RiseBot(Dealer dealer) {
        super("Rise bot", dealer);
    }

    public RiseBot(String name, Dealer dealer) {
        super(name, dealer);
    }

    @Override
    public PlayerAction getMove() {
        return new PlayerAction(PlayerAction.ActionType.Rise);
    }
}
