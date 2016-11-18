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
 * Time: 4:03
 */
public class SetInitialCoinsCommandTest {
    private AdminModel adminModelMock;

    private SetInitialCoinsCommand initialCoinsCommand;

    @Before
    public void setUp() throws Exception {
        adminModelMock = mock(AdminModel.class);

        initialCoinsCommand = new SetInitialCoinsCommand(adminModelMock);
    }

    @Test
    public void shouldAdminModelSetInitialCoins100WhenSetInitialCoinsCommandInvoked100() throws Exception {
        String HUNDRED_COINS_TEXT = "100";
        initialCoinsCommand.invoke(new String[]{HUNDRED_COINS_TEXT});

        int HUNDRED_COINS = 100;
        verify(adminModelMock).setInitialCoins(HUNDRED_COINS);
    }

    @Test
    public void shouldAdminModelSetInitialCoins200WhenSetInitialCoinsCommandInvoked200() throws Exception {
        String TWO_HUNDRED_COINS_TEXT = "200";
        initialCoinsCommand.invoke(new String[]{TWO_HUNDRED_COINS_TEXT});

        int TWO_HUNDRED_COINS = 200;
        verify(adminModelMock).setInitialCoins(TWO_HUNDRED_COINS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExceptionWhenSetInitialCoinsCommandInvokeWithNullParams() throws Exception {
        initialCoinsCommand.invoke(new String[]{null});
    }
}
