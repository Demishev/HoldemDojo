package com.nedogeek.holdem.server;

import com.nedogeek.holdem.bot.Bots;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 4:36
 */
public class AdminCommandsPerformerTest {
    private AdminCommandsPerformer adminCommandsPerformer;


    private AdminModel adminModelMock;

    @Before
    public void setUp() throws Exception {
        adminModelMock = mock(AdminModel.class);

        adminCommandsPerformer = new AdminCommandsPerformer(adminModelMock);

    }

    @Test
    public void shouldKickFirstPlayerWhenPerformActionKickFirstPlayer() throws Exception {
        adminCommandsPerformer.performCommand("kick", new String[]{"First player"});

        verify(adminModelMock).kick("First player");
    }

    @Test
    public void shouldStartGameWhenPerformStartGame() throws Exception {
        adminCommandsPerformer.performCommand("start", new String[]{});

        verify(adminModelMock).start();
    }

    @Test
    public void shouldStopGameWhenPerformStopGame() throws Exception {
        adminCommandsPerformer.performCommand("stop", new String[]{});

        verify(adminModelMock).stop();
    }

    @Test
    public void shouldPauseGameWhenPerformPauseGame() throws Exception {
        adminCommandsPerformer.performCommand("pause", new String[]{});

        verify(adminModelMock).pause();
    }

    @Test
    public void shouldAddBotWhenPerformAddBot() throws Exception {
        adminCommandsPerformer.performCommand("addBot", new String[]{"CallBot", "Call bot"});

        verify(adminModelMock).addBot(Bots.CallBot, "Call bot");
    }

    @Test
    public void shouldSetInitialCoins1000WhenSetInitialCoinsCommand1000() throws Exception {
        adminCommandsPerformer.performCommand("setInitialCoins", new String[]{"1000"});

        verify(adminModelMock).setInitialCoins(1000);
    }

    @Test
    public void shouldSetMinimumBlind1000WhenSetMinimumBlind1000() throws Exception {
        adminCommandsPerformer.performCommand("setMinimumBlind", new String[]{"1000"});

        verify(adminModelMock).setMinimumBlind(1000);
    }

    @Test
    public void shouldSetGameDelay1000WhenSetGameDelayCommand1000() throws Exception {
        adminCommandsPerformer.performCommand("setGameDelay", new String[]{"1000"});

        verify(adminModelMock).setGameDelay(1000);
    }

    @Test
    public void shouldSetEndGameDelay1000WhenSetEndGameDelay1000() throws Exception {
        adminCommandsPerformer.performCommand("setEndGameDelay", new String[]{"1000"});

        verify(adminModelMock).setEndGameDelay(1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExceptionWhenPerformActionWrongCommand() throws Exception {
        adminCommandsPerformer.performCommand("Wrong command", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExceptionWhenPerformActionNullCommand() throws Exception {
        adminCommandsPerformer.performCommand(null, null);
    }
}
