package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 3:01
 */
public class KickCommandTest {

    private AdminModel adminModelMock;

    private KickCommand kickCommand;

    @Before
    public void setUp() throws Exception {
        kickCommand = new KickCommand();

        adminModelMock = mock(AdminModel.class);
    }

    @Test
    public void shouldKickFirstPlayerWhenKickFirstPlayer() throws Exception {
        String FIRST_PLAYER_NAME = "First player";
        kickCommand.invoke(new String[]{FIRST_PLAYER_NAME}, adminModelMock);

        verify(adminModelMock).kick(FIRST_PLAYER_NAME);
    }

    @Test
    public void shouldKickSecondPlayerWhenKickSecondPlayer() throws Exception {
        String SECOND_PLAYER_NAME = "Second player";
        kickCommand.invoke(new String[]{SECOND_PLAYER_NAME}, adminModelMock);

        verify(adminModelMock).kick(SECOND_PLAYER_NAME);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenKickNullPlayer() throws Exception {
        kickCommand.invoke(null, adminModelMock);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenKickEmptyParams() throws Exception {
        kickCommand.invoke(new String[] {}, adminModelMock);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenKickPlayerNameIsNull() throws Exception {
        kickCommand.invoke(new String[] {null}, adminModelMock);
    }
}
