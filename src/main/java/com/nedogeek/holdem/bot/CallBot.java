package com.nedogeek.holdem.bot;

import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;

/**
 * User: Konstantin Demishev
 * Date: 10.01.13
 * Time: 19:35
 */
public class CallBot extends Player {

    public CallBot(Dealer dealer) {
        super("Call bot", dealer);
    }

    public CallBot(String name, Dealer dealer) {
        super(name, dealer);
    }

    @Override
    public PlayerAction getMove() {
        return new PlayerAction(PlayerAction.ActionType.Call);
    }
}
