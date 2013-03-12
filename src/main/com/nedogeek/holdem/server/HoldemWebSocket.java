package com.nedogeek.holdem.server;


import com.nedogeek.holdem.dealer.EventManager;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.gamingStuff.PlayerAction.ActionType;

import org.eclipse.jetty.websocket.WebSocket;


/**
 * User: Konstantin Demishev
 * Date: 06.08.12
 * Time: 16:41
 */
public class HoldemWebSocket implements WebSocket.OnTextMessage {

    private Connection connection;
	private String login;
	private Player player;

	 public HoldemWebSocket() {
			
	}
	
    public HoldemWebSocket(String login) {
		this.login = login;
		
	}

	@Override
    public void onMessage(String command) {
		PlayerAction playerAction = defineAction(command);
		
		player.setMove(playerAction);
    }

    private PlayerAction defineAction(String command) {
    	if (command == null) {
    		return new PlayerAction(ActionType.Fold);
    	}
    	
    	ActionType actionType;
    	int actionValue;
    	String actionTypePart;
    	String actionValuePart; 
    	if (command.contains(",")) {
    		actionTypePart = command.substring(0,command.indexOf(','));
    		actionValuePart = command.substring(command.indexOf(',') + 1);
    	} else {
    		actionTypePart = command;
    		actionValuePart = "0";
    	}
    	
    	try {
    	actionValue = Integer.parseInt(actionValuePart);
    	} catch (NumberFormatException e){
    		actionValue = 0;
    	}
  		actionType = ActionType.Fold;
  		
    	if ("Check".equals(actionTypePart)) {
    		actionType = ActionType.Check;
    	}
    	if ("Call".equals(actionTypePart)) {
    		actionType = ActionType.Call;
    	}
    	if ("Rise".equals(actionTypePart)) {
    		actionType = ActionType.Rise;
    	}
    	if ("AllIn".equals(actionTypePart)) {
    		actionType = ActionType.AllIn;
    	}
    		
		return new PlayerAction(actionType, actionValue);
	}

	@Override
    public void onOpen(Connection connection) {
        this.connection = connection;
       if(login == null){
        EventManager.getInstance().addViewer(connection);
       }else{
    	  player = EventManager.getInstance().addPlayer(connection, login);
       }
    }

    @Override
    public void onClose(int i, String s) {

    }


}