package com.nedogeek.holdem.gameEvents;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Demishev
 * Date: 10.02.13
 * Time: 0:43
 */
public abstract class Event {
    private final String event;

    public Event(String event) {
        this.event = new SimpleDateFormat("hh:mm").format(new Date()) + ": " +   event;
    }

    @Override
    public String toString() {
        return event;
    }
}
