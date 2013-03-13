package com.nedogeek.holdem.server;

import com.nedogeek.holdem.dealer.EventManager;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * User: Konstantin Demishev
 * Date: 13.08.12
 * Time: 14:25
 */
public class HoldemWebSocketServlet extends WebSocketServlet {
    private Map<String,String> userList = EventManager.getInstance().getUserData();

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
    	String login = request.getParameter("user");
    	String password = request.getParameter("password");
    	if(login == null && password == null){
    		return new HoldemWebSocket();
    	}else{
    		if(userList.containsKey(login) &&
                    userList.get(login).equals(password)){
    			return new HoldemWebSocket(login);
    		}
    	}
    	
    	return null;
    }
}
