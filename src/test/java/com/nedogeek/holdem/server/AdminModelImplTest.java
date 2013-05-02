package com.nedogeek.holdem.server;

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
