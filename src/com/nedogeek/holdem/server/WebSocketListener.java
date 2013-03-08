package com.nedogeek.holdem.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;

import com.nedogeek.holdem.BotGameRunner;

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
        try {
            this.server = new Server(8081);
            HoldemWebSocketHandler pingPongWebSocketHandler = new HoldemWebSocketHandler();
            pingPongWebSocketHandler.setHandler(new DefaultHandler());

            server.setHandler(pingPongWebSocketHandler);

            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Thread runner = new Thread(new Runnable() {
			
			@Override
			public void run() {
				  BotGameRunner.main(null);
				
			}
		});
        runner.start();
      
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
