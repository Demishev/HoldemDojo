package com.nedogeek.holdem.bot;

import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;

/**
 * User: Konstantin Demishev
 * Date: 10.01.13
 * Time: 19:28
 */
public class FoldBot extends Player {
    public FoldBot(String name, Dealer dealer) {
        super(name, dealer);
    }

    @Override
    public PlayerAction getMove() {
        return new PlayerAction(PlayerAction.ActionType.Fold);
    }
}
