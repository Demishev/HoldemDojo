package com.nedogeek.holdem.server;

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


import org.eclipse.jetty.websocket.WebSocket;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Konstantin Demishev
 * Date: 30.05.13
 * Time: 2:11
 */
public class HoldemWebSocketConnector {
    private Map<String, String> userData = new HashMap<>();
    private HoldemWebSocketFactory factory = new HoldemWebSocketFactory();

    public WebSocket connect(String user, String password, String gameID) {
        if (user == null && password == null && gameID != null) {
            return factory.getViewerSocket(gameID);
        }
        if (user != null && password != null) {
            if (userData.containsKey(user)) {
                if (userData.get(user).equals(password)) {
                    return factory.getUserSocket(user);
                }
            } else {
                userData.put(user, password);
                return factory.getUserSocket(user);
            }
        }
        return null;
    }

    /**
     * For test usage only!
     *
     * @param factory  inject factory for testing
     * @param userData inject userData for testing
     */
    void injectTestDependencies(HoldemWebSocketFactory factory, Map<String, String> userData) {
        this.factory = factory;
        this.userData = userData;
    }
}
