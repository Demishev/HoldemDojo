package localBotRunner;

/*-
 * #%L
 * Holdem dojo project is a server-side java application for playing holdem pocker in DOJO style.
 * %%
 * Copyright (C) 2016 Holdemdojo
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


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

    public static void main(String[] args) { //TODO does not work!
        WebSocket.Connection consoleConnection = prepareConsoleConnection();

//        GameImpl.getInstance().addViewer(consoleConnection);
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
