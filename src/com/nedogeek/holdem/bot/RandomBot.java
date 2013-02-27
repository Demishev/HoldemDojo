package com.nedogeek.holdem.bot;

import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;

import java.util.Random;

/**
 * User: Konstantin Demishev
 * Date: 10.01.13
 * Time: 19:37
 */
public class RandomBot extends Player {
    private Random random;

    public RandomBot(Dealer dealer) {
        super("Random com.nedogeek.holdem.bot", dealer);

        random = new Random();
    }

    @Override
    public PlayerAction getMove() {
        int riseAmount = random.nextInt(getBalance());

        int actionTypeNumber = random.nextInt(PlayerAction.ActionType.values().length);
        PlayerAction.ActionType actionType = PlayerAction.ActionType.values()[actionTypeNumber];

        return new PlayerAction(actionType, riseAmount);
    }
}
