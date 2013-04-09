package localBotRunner;

import com.nedogeek.holdem.GameImpl;
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
    private static class ConsoleAnswer implements Answer {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            System.out.println(invocation.getArguments()[0]);
            return null;
        }
    }

    public static void main(String[] args) {
        WebSocket.Connection consoleConnection = prepareConsoleConnection();

        GameImpl.getInstance().addViewer(consoleConnection);
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
