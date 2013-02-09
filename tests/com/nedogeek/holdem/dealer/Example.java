package com.nedogeek.holdem.dealer;

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

        Assert.assertEquals("CallBot,CallBot",EventManager.getInstance().getPlayersNames());
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

        Assert.assertEquals("Call bot,Call bot",EventManager.getInstance().getPlayersNames());

    }
}
