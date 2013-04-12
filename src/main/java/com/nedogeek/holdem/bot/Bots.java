package com.nedogeek.holdem.bot;

import com.nedogeek.holdem.gamingStuff.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 12.04.13
 * Time: 2:37
 */
public enum Bots {
    CallBot, FoldBot, RandomBot, RiseBot;

    public static Player createBot(Bots botType, String name) {
        //TODO it is a stub
        return null;
    }

    public static List<String> getBotTypes() {
        List<String> botTypes = new ArrayList<>();

        for (Bots bot : Bots.values()) {
            botTypes.add(bot.name());
        }
        return botTypes;
    }
}
