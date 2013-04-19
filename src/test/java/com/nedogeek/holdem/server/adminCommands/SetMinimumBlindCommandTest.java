package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 4:14
 */
public class SetMinimumBlindCommandTest {
    private AdminModel adminModelMock;

    private SetMinimumBlindCommand minimumBlindCommand;

    @Before
    public void setUp() throws Exception {
        adminModelMock = mock(AdminModel.class);

        minimumBlindCommand = new SetMinimumBlindCommand();
    }

    @Test
    public void shouldAdminModelSetInitialCoins100WhenSetInitialCoinsCommandInvoked100() throws Exception {
        String HUNDRED_COINS_TEXT = "100";
        minimumBlindCommand.invoke(new String[]{HUNDRED_COINS_TEXT}, adminModelMock);

        int HUNDRED_COINS = 100;
        verify(adminModelMock).setMinimumBlind(HUNDRED_COINS);
    }

    @Test
    public void shouldAdminModelSetInitialCoins200WhenSetInitialCoinsCommandInvoked200() throws Exception {
        String TWO_HUNDRED_COINS_TEXT = "200";
        minimumBlindCommand.invoke(new String[]{TWO_HUNDRED_COINS_TEXT}, adminModelMock);

        int TWO_HUNDRED_COINS = 200;
        verify(adminModelMock).setMinimumBlind(TWO_HUNDRED_COINS);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenSetInitialCoinsCommandInvokeWithNullParams() throws Exception {
        minimumBlindCommand.invoke(new String[]{null}, adminModelMock);
    }
}
