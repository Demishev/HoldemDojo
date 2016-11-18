package com.nedogeek.holdem.bot;

/*-
 * #%L
 * Holdem dojo project is a server-side java application for playing holdem pocker in DOJO style.
 * %%
 * Copyright (C) 2016 Holdemdojo
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


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

        throw new IllegalArgumentException("Wrong bot name: \"" + botTypeName + "\"");
    }
}
