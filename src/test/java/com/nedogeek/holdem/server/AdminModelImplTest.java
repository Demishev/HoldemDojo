package com.nedogeek.holdem.server;

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


import com.nedogeek.holdem.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * User: Konstantin Demishev
 * Date: 17.04.13
 * Time: 14:44
 */
public class AdminModelImplTest {
    private static String SOME_PASSWORD = "Some password";

    private AdminModelImpl adminModel;

    @Before
    public void setUp() throws Exception {
        adminModel = new AdminModelImpl(mock(Game.class));
    }

    @Test
    public void shouldTrueWhenPasswordCorrectDefaultPassword() throws Exception {
        assertTrue(adminModel.passwordCorrect(AdminModelImpl.DEFAULT_PASSWORD));
    }

    @Test
    public void shouldFalseWhenPasswordCorrectSomePassword() throws Exception {
        assertFalse(adminModel.passwordCorrect(SOME_PASSWORD));
    }

    @Test
    public void shouldTrueWhenChangePasswordToSomePasswordAndPasswordCorrectSomePassword() throws Exception {
        adminModel.changePassword(SOME_PASSWORD);

        assertTrue(adminModel.passwordCorrect(SOME_PASSWORD));
    }
}
