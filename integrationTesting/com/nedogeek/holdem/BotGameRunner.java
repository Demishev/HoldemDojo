package com.nedogeek.holdem;

import com.nedogeek.holdem.bot.CallBot;
import com.nedogeek.holdem.bot.FoldBot;
import com.nedogeek.holdem.bot.RandomBot;
import com.nedogeek.holdem.bot.RiseBot;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.gamingStuff.PlayersList;

/**
 * User: Konstantin Demishev
 * Date: 10.01.13
 * Time: 19:11
 */
public class BotGameRunner {

    public static void main(String[] args) {
        PlayersList players = new PlayersList();

        Dealer dealer = new Dealer(players);

        players.add(new RiseBot(dealer));
        players.add(new RandomBot(dealer));
        players.add(new CallBot(dealer));
        players.add(new FoldBot(dealer));

        dealer.run();
    }
}
