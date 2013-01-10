import bot.CallBot;
import bot.FoldBot;
import bot.RandomBot;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.gamingStuff.PlayersList;

/**
 * User: Konstantin Demishev
 * Date: 10.01.13
 * Time: 19:11
 */
public class BotGameRunner {

    public static void main(String[] args) {
        PlayersList players = new PlayersList();

        Dealer dealer = new Dealer(players);

        players.add(new FoldBot(dealer));
        players.add(new CallBot(dealer));
        players.add(new RandomBot(dealer));

        dealer.run();
    }
}
