package com.nedogeek.holdem;

import com.nedogeek.holdem.bot.CallBot;
import com.nedogeek.holdem.bot.FoldBot;
import com.nedogeek.holdem.bot.RandomBot;
import com.nedogeek.holdem.bot.RiseBot;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.dealer.EventManager;
import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gamingStuff.PlayersList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        dealer.setGameReady();

        doTicks(1000, dealer);
    }

    private static void doTicks(int ticksCount, Dealer dealer) {
        for (int i = 0; i < ticksCount; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {

                e1.printStackTrace();
            }

            try {
                Method tick = dealer.getClass().getDeclaredMethod("tick");
                tick.setAccessible(true);
                tick.invoke(dealer);
                EventManager.getInstance().addEvent(new Event("Tick!") {
                });
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
