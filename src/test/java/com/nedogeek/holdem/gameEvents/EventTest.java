package com.nedogeek.holdem.gameEvents;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Konstantin Demishev
 * Date: 23.03.13
 * Time: 20:22
 */
public class EventTest {

    @Test
    public void shouldToJSONProperlyWhenMultiStringEvent() throws Exception {
        Event event = new Event("First line\nSecond line") {
        };

        assertEquals("[\"First line\", \"Second line\"]", event.toJSON());
    }
}
