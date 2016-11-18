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
import com.nedogeek.holdem.gamingStuff.PlayerAction;

import java.util.Random;

/**
 * User: Konstantin Demishev
 * Date: 10.01.13
 * Time: 19:37
 */
public class RandomBot extends Bot {
    private final Random random;

    public RandomBot(Dealer dealer) {
        super("Random bot", dealer);

        random = new Random();
    }

    public RandomBot(String name, Dealer dealer) {
        super(name, dealer);

        random = new Random();
    }

    @Override
    public PlayerAction getMove() {
        int riseAmount = 0;
        if (getBalance() != 0) {
            riseAmount = random.nextInt(getBalance());
        }

        int actionTypeNumber = random.nextInt(PlayerAction.ActionType.values().length);
        PlayerAction.ActionType actionType = PlayerAction.ActionType.values()[actionTypeNumber];

        return new PlayerAction(actionType, riseAmount);
    }
}
