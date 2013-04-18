package com.nedogeek.holdem.bot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 2:12
 */
public class BotsTest {

    @Test
    public void shouldCallBotWhenBotsGetBotTypeByNameCallBot() throws Exception {
        assertEquals(Bots.CallBot, Bots.getBotTypeByName("CallBot"));
    }

    @Test
    public void shouldRiseBotWhenBotsGetBotTypeByNameRiseBot() throws Exception {
        assertEquals(Bots.RiseBot, Bots.getBotTypeByName("RiseBot"));
    }

    @Test
    public void shouldFoldBotWhenBotsGetBotTypeByNameFoldBot() throws Exception {
        assertEquals(Bots.FoldBot, Bots.getBotTypeByName("FoldBot"));
    }

    @Test
    public void shouldRandomBotWhenBotsGetBotTypeByNameRandomBot() throws Exception {
        assertEquals(Bots.RandomBot, Bots.getBotTypeByName("RandomBot"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenBotsGetBotTypeByNameWrongName() throws Exception {
        Bots.getBotTypeByName("Wrong name");
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenBotsGetBotTypeByNameNullName() throws Exception {
        Bots.getBotTypeByName(null);
    }
}
