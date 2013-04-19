package com.nedogeek.holdem.bot;

import com.nedogeek.holdem.dealer.Dealer;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 12.04.13
 * Time: 2:37
 */
public enum Bots {
    CallBot, FoldBot, RandomBot, RiseBot;

    public static Bot createBot(Bots botType, String name, Dealer dealer) {
        if (botType == CallBot) {
            return new CallBot(name, dealer);
        }
        if (botType == FoldBot) {
            return new CallBot(name, dealer);
        }
        if (botType == RandomBot) {
            return new RandomBot(name, dealer);
        }
        if (botType == RiseBot) {
            return new RiseBot(name, dealer);
        }

        return new FoldBot(name, dealer);
    }

    public static List<String> getBotTypes() {
        List<String> botTypes = new ArrayList<>();

        for (Bots bot : Bots.values()) {
            botTypes.add(bot.name());
        }
        return botTypes;
    }

    public static Bots getBotTypeByName(String botTypeName) {
        if (botTypeName == null) {
            throw new IllegalArgumentException("Bot type name is null");
        }

        switch (botTypeName) {
            case "CallBot":
                return CallBot;
            case "RiseBot":
                return RiseBot;
            case "RandomBot":
                return RandomBot;
            case "FoldBot":
                return FoldBot;
        }

        throw new IllegalArgumentException("Wrong bot name");
    }
}
