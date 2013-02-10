package com.nedogeek.holdem.dealer;

import static org.junit.Assert.assertEquals;
import bot.CallBot;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.junit.Assert;
import org.junit.Test;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PlayersList playerList = new PlayersList();		
		Dealer dealer = new Dealer(playerList);
		playerList.add(new CallBot(dealer));
		playerList.add(new CallBot(dealer));

		
		dealer.setGameReady();
		for(int i = 0; i<300;i++){
			dealer.tick();
		}        
	}

    @Test
    public void shouldNameWhen() throws Exception {

        PlayersList playerList = new PlayersList();
        Dealer dealer = new Dealer(playerList);
        playerList.add(new CallBot(dealer));
        playerList.add(new CallBot(dealer));


        dealer.setGameReady();
        for(int i = 0; i<300;i++){
            dealer.tick();
        }

        assertEquals("Call bot,Call bot",EventManager.getInstance().getPlayersNames());
        assertEquals("[\"[\\\"name\\\":\\\"Call bot\\\", \\\"status\\\":\\\"Call\\\",\\\"bet\\\":20, \\\"balance\\\":980]\",\"[\\\"name\\\":\\\"Call bot\\\", \\\"status\\\":\\\"Call\\\",\\\"bet\\\":20, \\\"balance\\\":980]\"]",playerList.toJSON());
    }
}
