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
		playerList.add(new FoldBot(dealer));
		playerList.add(new CallBot(dealer));



	//	new GameCycleManager(dealer, playerList).prepareNewGameCycle();

		//newGameSetter.setNewGame();
		
		/*for(int i = 0; i < playerList.size(); i++){
			moveManager.makeMove(playerList.getMover());
			
		}
		dealer.setNextGameRound();

		new EndGameManager(dealer, playerList).endGame();*/
		//dealer.tick();
		
		dealer.setGameReady();
		for(int i = 0; i<300;i++){
			dealer.tick();
		}
	}

}
