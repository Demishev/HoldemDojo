package com.nedogeek.holdem.server.adminCommands;

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

        initialCoinsCommand = new SetInitialCoinsCommand();
    }

    @Test
    public void shouldAdminModelSetInitialCoins100WhenSetInitialCoinsCommandInvoked100() throws Exception {
        String HUNDRED_COINS_TEXT = "100";
        initialCoinsCommand.invoke(new String[] {HUNDRED_COINS_TEXT}, adminModelMock);

        int HUNDRED_COINS = 100;
        verify(adminModelMock).setInitialCoins(HUNDRED_COINS);
    }

    @Test
    public void shouldAdminModelSetInitialCoins200WhenSetInitialCoinsCommandInvoked200() throws Exception {
        String TWO_HUNDRED_COINS_TEXT = "200";
        initialCoinsCommand.invoke(new String[] {TWO_HUNDRED_COINS_TEXT}, adminModelMock);

        int TWO_HUNDRED_COINS = 200;
        verify(adminModelMock).setInitialCoins(TWO_HUNDRED_COINS);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenSetInitialCoinsCommandInvokeWithNullParams() throws Exception {
        initialCoinsCommand.invoke(new String[] {null}, adminModelMock);
    }
}
