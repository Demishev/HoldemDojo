package com.nedogeek.holdem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Konstantin Demishev
 * Date: 03.06.13
 * Time: 19:19
 */
public class PlayerCommandsParserTest {

    private PlayerCommandsParser parser;
    private final static String LOGIN = "The login!";

    @Before
    public void setUp() throws Exception {
        parser = new PlayerCommandsParser();
    }

    @Test
    public void shouldLoginTheLoginWhenParseCommandWithTheLogin() throws Exception {

        assertEquals(LOGIN, parseCommand(LOGIN, "Fold").getLogin());
    }

    private PlayerCommand parseCommand(String login, String stringCommand) {
        return parser.parseCommand(login, stringCommand);
    }

    @Test
    public void shouldReceiverGameWhenParseFoldCommand() throws Exception {
        assertEquals(PlayerCommandReceiver.Game, parseCommand(LOGIN, "Fold").getReceiver());
    }

    @Test
    public void shouldReceiverGameWhenParseSitToDESK_ID() throws Exception {
        assertEquals(PlayerCommandReceiver.GameCenter,parseCommand(LOGIN,"SitTo,DESK_ID").getReceiver());
    }

    @Test
    public void shouldReceiverGameCenterWhenParseSitOutCommand() throws Exception {
        assertEquals(PlayerCommandReceiver.GameCenter, parseCommand(LOGIN, "SitOut").getReceiver());
    }
    @Test
    public void shouldReceiverGameCenterWhenParseSitTOCommand() throws Exception{
        assertEquals(PlayerCommandReceiver.GameCenter,parseCommand(LOGIN,"SitTo").getReceiver());
    }

    @Test
    public void shouldReceiverGameWhenParseCall() throws Exception {
        assertEquals(PlayerCommandReceiver.Game,parseCommand(LOGIN,"Call").getReceiver());
    }
}
