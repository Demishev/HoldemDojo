package com.nedogeek.holdem.server;

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
