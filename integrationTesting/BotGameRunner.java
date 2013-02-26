import bot.CallBot;
import bot.FoldBot;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.gamingStuff.PlayersList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * User: Konstantin Demishev
 * Date: 10.01.13
 * Time: 19:11
 */
public class BotGameRunner {

    public static void main(String[] args) {
        PlayersList players = new PlayersList();

        Dealer dealer = new Dealer(players);

        players.add(new CallBot(dealer));
        players.add(new FoldBot(dealer));
        players.add(new CallBot(dealer));

        dealer.setGameReady();

        doTicks(100, dealer);
    }

    private static void doTicks(int ticksCount, Dealer dealer) {
        for (int i = 0; i < ticksCount; i++) {
            try {
                Method tick = dealer.getClass().getDeclaredMethod("tick");
                tick.setAccessible(true);
                tick.invoke(dealer);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
