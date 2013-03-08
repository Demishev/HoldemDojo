package com.nedogeek.holdem.gameEvents;

/**
 * Created with IntelliJ IDEA.
 * User: Demishev
 * Date: 10.02.13
 * Time: 0:43
 */
public abstract class Event {
    private final String event;

    public Event(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return event;
    }
}
