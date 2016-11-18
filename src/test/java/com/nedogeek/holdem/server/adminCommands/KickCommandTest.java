package com.nedogeek.holdem.server.adminCommands;

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


import com.nedogeek.holdem.server.AdminModel;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 3:01
 */
public class KickCommandTest {

    private AdminModel adminModelMock;

    private KickCommand kickCommand;

    @Before
    public void setUp() throws Exception {
        adminModelMock = mock(AdminModel.class);


        kickCommand = new KickCommand(adminModelMock);
    }

    @Test
    public void shouldKickFirstPlayerWhenKickFirstPlayer() throws Exception {
        String FIRST_PLAYER_NAME = "First player";
        kickCommand.invoke(new String[]{FIRST_PLAYER_NAME});

        verify(adminModelMock).kick(FIRST_PLAYER_NAME);
    }

    @Test
    public void shouldKickSecondPlayerWhenKickSecondPlayer() throws Exception {
        String SECOND_PLAYER_NAME = "Second player";
        kickCommand.invoke(new String[]{SECOND_PLAYER_NAME});

        verify(adminModelMock).kick(SECOND_PLAYER_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExceptionWhenKickNullPlayer() throws Exception {
        kickCommand.invoke(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExceptionWhenKickEmptyParams() throws Exception {
        kickCommand.invoke(new String[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExceptionWhenKickPlayerNameIsNull() throws Exception {
        kickCommand.invoke(new String[]{null});
    }
}
