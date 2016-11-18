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
 * Time: 4:23
 */
public class SetEndGameDelayValueCommandTest {
    private AdminModel adminModelMock;

    private SetEndGameDelayValueCommand gameDelayCommand;

    @Before
    public void setUp() throws Exception {
        adminModelMock = mock(AdminModel.class);

        gameDelayCommand = new SetEndGameDelayValueCommand(adminModelMock);
    }

    @Test
    public void shouldAdminModelSetEndDelayValue100WhenSetEndDelayValueCommandInvoked100() throws Exception {
        String HUNDRED_TEXT = "100";
        gameDelayCommand.invoke(new String[]{HUNDRED_TEXT});

        int HUNDRED = 100;
        verify(adminModelMock).setEndGameDelay(HUNDRED);
    }

    @Test
    public void shouldAdminModelSetEndDelayValue200WhenSetEndDelayValueCommandInvoked200() throws Exception {
        String TWO_HUNDRED_TEXT = "200";
        gameDelayCommand.invoke(new String[]{TWO_HUNDRED_TEXT});

        int TWO_HUNDRED = 200;
        verify(adminModelMock).setEndGameDelay(TWO_HUNDRED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExceptionWhenSetDelayValueCommandInvokeWithNullParams() throws Exception {
        gameDelayCommand.invoke(new String[]{null});
    }
}
