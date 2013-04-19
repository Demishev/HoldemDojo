package com.nedogeek.holdem.server.adminCommands;

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

        gameDelayCommand = new SetEndGameDelayValueCommand();
    }

    @Test
    public void shouldAdminModelSetEndDelayValue100WhenSetEndDelayValueCommandInvoked100() throws Exception {
        String HUNDRED_TEXT = "100";
        gameDelayCommand.invoke(new String[]{HUNDRED_TEXT}, adminModelMock);

        int HUNDRED = 100;
        verify(adminModelMock).setEndGameDelay(HUNDRED);
    }

    @Test
    public void shouldAdminModelSetEndDelayValue200WhenSetEndDelayValueCommandInvoked200() throws Exception {
        String TWO_HUNDRED_TEXT = "200";
        gameDelayCommand.invoke(new String[]{TWO_HUNDRED_TEXT}, adminModelMock);

        int TWO_HUNDRED = 200;
        verify(adminModelMock).setEndGameDelay(TWO_HUNDRED);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenSetDelayValueCommandInvokeWithNullParams() throws Exception {
        gameDelayCommand.invoke(new String[]{null}, adminModelMock);
    }
}
