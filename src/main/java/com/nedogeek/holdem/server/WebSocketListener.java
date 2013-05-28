package com.nedogeek.holdem.server;

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
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
