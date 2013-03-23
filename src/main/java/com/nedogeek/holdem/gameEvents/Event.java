package com.nedogeek.holdem.gameEvents;

import java.io.Serializable;
import java.util.Arrays;

/**
 * User: Demishev
 * Date: 10.02.13
 * Time: 0:43
 */
public abstract class Event implements Serializable {
    private final String event;

    public Event(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return event;
    }

    public String toJSON() {
        String[] result = (event.split("\n"));
        for (int i = 0; i < result.length; i++) {
            result[i] = "\"" + result[i] + "\"";
        }

        return Arrays.toString(result);
    }
}
