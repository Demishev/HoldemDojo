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


import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: Konstantin Demishev
 * Date: 07.06.13
 * Time: 22:56
 */
public class ChangePasswordTest {
    private HttpServletRequest requestMock;
    private HttpServletResponse responseMock;
    private final AdminLogic adminLogic = new AdminLogic();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldDoNothingdWhenCorrectSessionPassword() throws Exception {
        final AdminLogic adminLogic = new AdminLogic();
        adminLogic.checkLoginInfo("correctSessionPassword");
        assertNull(adminLogic.result);

    }

    @Test
    public void shouldUpdatePasswordWhenSessionPasswordWrong() throws Exception {
        adminLogic.checkLoginInfo("wrongSessionPassword");
        assertEquals("changeSessionPassword", adminLogic.result);
    }


    @Test
    public void shouldRedirectWhenRequestPasswordIsWrong() throws Exception {
        adminLogic.checkLoginInfo("wrongRequestPassword");
        assertEquals("redirectToLoginPage",adminLogic.result);
    }

    private void shouldRedirectTo(String path) {
//        verify(requestMock.getRequestDispatcher(path));
    }
}
