package com.nedogeek.holdem.gameEvents;

import net.sf.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Demishev
 * Date: 10.02.13
 * Time: 0:43
 */
public abstract class Event implements Serializable {
    private final String event;
    private final EventType eventType;
    private final String params;

    public Event(String event, EventType eventType, String params) {
        this.params = params;
        this.event = new SimpleDateFormat("hh:mm:ss").format(new Date()) + ": " + event;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return event;
    }

    public String toJSON() {
        Map<String, Serializable> data = new HashMap<>();
        data.put("event", event);
        data.put("eventType", eventType);
        data.put("params", params);

        return JSONObject.fromMap(data).toString();
    }
}
