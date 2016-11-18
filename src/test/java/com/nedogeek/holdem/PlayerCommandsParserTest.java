package com.nedogeek.holdem;

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
