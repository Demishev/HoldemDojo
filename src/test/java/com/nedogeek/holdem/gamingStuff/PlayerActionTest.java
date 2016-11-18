package com.nedogeek.holdem.gamingStuff;

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


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Konstantin Demishev
 * Date: 27.03.13
 * Time: 19:14
 */
public class PlayerActionTest {

    private void isAction(PlayerAction playerAction, PlayerAction.ActionType actionType, int riseValue) {
        assertEquals(actionType, playerAction.getActionType());
        assertEquals(riseValue, playerAction.getRiseAmount());
    }

    @Test
    public void shouldAllIn0WhenAllIn() throws Exception {
        isAction(PlayerAction.defineAction("Rise"), PlayerAction.ActionType.Rise, 0);
    }

    @Test
    public void shouldFoldWhenNull() throws Exception {
        isAction(PlayerAction.defineAction(null), PlayerAction.ActionType.Fold, 0);
    }

    @Test
    public void shouldRise500WhenRise500() throws Exception {
        isAction(PlayerAction.defineAction("Rise,500"), PlayerAction.ActionType.Rise, 500);
    }

    @Test
    public void shouldFold0WhenErrorCommand() throws Exception {
        isAction(PlayerAction.defineAction("Error"), PlayerAction.ActionType.Fold, 0);
    }

    @Test
    public void shouldRise500WhenRiseSpace500() throws Exception {
        isAction(PlayerAction.defineAction("Rise, 500"), PlayerAction.ActionType.Rise, 500);
    }
}
