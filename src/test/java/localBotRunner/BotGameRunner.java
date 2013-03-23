package localBotRunner;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.bot.CallBot;
import com.nedogeek.holdem.bot.FoldBot;
import com.nedogeek.holdem.bot.RandomBot;
import com.nedogeek.holdem.bot.RiseBot;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.dealer.EventManager;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.eclipse.jetty.websocket.WebSocket;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Konstantin Demishev
 * Date: 10.01.13
 * Time: 19:11
 */
public class BotGameRunner {
    static class ConsoleAnswer implements Answer {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            System.out.println(invocation.getArguments()[0]);
            return null;
        }
    }

    public static void main(String[] args) {
        PlayersList players = new PlayersList();

        Dealer dealer = new Dealer(players);

        players.add(new RiseBot(dealer));
        players.add(new RandomBot(dealer));
        players.add(new CallBot(dealer));
        players.add(new FoldBot(dealer));

        GameSettings.END_GAME_DELAY_VALUE = 100;
        GameSettings.GAME_DELAY_VALUE = 100;

        WebSocket.Connection consoleConnection = prepareConsoleConnection();

        EventManager.getInstance().addViewer(consoleConnection);

        dealer.run();
    }

    private static WebSocket.Connection prepareConsoleConnection() {
        WebSocket.Connection consoleConnection = mock(WebSocket.Connection.class);


        try {
            Mockito.doAnswer(new ConsoleAnswer()).when(consoleConnection).sendMessage(anyString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        when(consoleConnection.isOpen()).thenReturn(true);
        return consoleConnection;
    }
}
