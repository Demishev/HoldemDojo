package com.nedogeek.holdem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Konstantin Demishev
 * Date: 26.02.13
 * Time: 0:40
 */
public class GameRoundTest {

    @Test
    public void shouldBlindWhenInitialNext() throws Exception {
        assertEquals(GameRound.BLIND, GameRound.next(GameRound.INITIAL));
    }

    @Test
    public void shouldThreeCardsWhenBlindNext() throws Exception {
        assertEquals(GameRound.THREE_CARDS, GameRound.next(GameRound.BLIND));
    }
}
