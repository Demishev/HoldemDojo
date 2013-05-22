package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 4:18
 */
public class SetGameDelayCommandTest {
    private AdminModel adminModelMock;

    private SetGameDelayCommand gameDelayCommand;

    @Before
    public void setUp() throws Exception {
        adminModelMock = mock(AdminModel.class);

        gameDelayCommand = new SetGameDelayCommand(adminModelMock);
    }

    @Test
    public void shouldAdminModelSetDelayValue100WhenSetDelayValueCommandInvoked100() throws Exception {
        String HUNDRED_TEXT = "100";
        gameDelayCommand.invoke(new String[]{HUNDRED_TEXT});

        int HUNDRED = 100;
        verify(adminModelMock).setGameDelay(HUNDRED);
    }

    @Test
    public void shouldAdminModelSetDelayValue200WhenSetDelayValueCommandInvoked200() throws Exception {
        String TWO_HUNDRED_TEXT = "200";
        gameDelayCommand.invoke(new String[]{TWO_HUNDRED_TEXT});

        int TWO_HUNDRED = 200;
        verify(adminModelMock).setGameDelay(TWO_HUNDRED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExceptionWhenSetDelayValueCommandInvokeWithNullParams() throws Exception {
        gameDelayCommand.invoke(new String[]{null});
    }

}
