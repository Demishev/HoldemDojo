package com.nedogeek.holdem.dealer;

import bot.CallBot;
import bot.FoldBot;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gamingStuff.CardDeck;
import com.nedogeek.holdem.gamingStuff.CardDeckTest;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

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

}
