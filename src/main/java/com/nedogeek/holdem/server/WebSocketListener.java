package com.nedogeek.holdem.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * User: Dmitriy Vilyuzhanin
 * Date: 08.03.13
 * Time: 17:46
 */
@WebListener
public class WebSocketListener implements ServletContextListener {
    private Server server;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
/*
        try {
            this.server = new Server(8081);
            HoldemWebSocketHandler holdemWebSocketHandler = new HoldemWebSocketHandler();
            holdemWebSocketHandler.setHandler(new DefaultHandler());
            server.setHandler(holdemWebSocketHandler);

            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
/*
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
*/
    }
}
