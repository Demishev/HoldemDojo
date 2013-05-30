package com.nedogeek.holdem.server;

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
        if (user == null && password == null) {
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
