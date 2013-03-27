package com.nedogeek.holdem.gamingStuff;

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
