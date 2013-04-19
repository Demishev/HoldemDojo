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
        adminCommandsPerformer = new AdminCommandsPerformer();

        adminModelMock = mock(AdminModel.class);
    }

    @Test
    public void shouldKickFirstPlayerWhenPerformActionKickFirstPlayer() throws Exception {
        adminCommandsPerformer.performAction("kick",new String[] {"First player"}, adminModelMock);

        verify(adminModelMock).kick("First player");
    }

    @Test
    public void shouldStartGameWhenPerformStartGame() throws Exception {
        adminCommandsPerformer.performAction("start",new String[] {}, adminModelMock);

        verify(adminModelMock).start();
    }

    @Test
    public void shouldStopGameWhenPerformStopGame() throws Exception {
        adminCommandsPerformer.performAction("stop",new String[] {}, adminModelMock);

        verify(adminModelMock).stop();
    }

    @Test
    public void shouldPauseGameWhenPerformPauseGame() throws Exception {
        adminCommandsPerformer.performAction("pause",new String[] {}, adminModelMock);

        verify(adminModelMock).pause();
    }

    @Test
    public void shouldAddBotWhenPerformAddBot() throws Exception {
        adminCommandsPerformer.performAction("addBot",new String[] {"CallBot","Call bot"}, adminModelMock);

        verify(adminModelMock).addBot(Bots.CallBot, "Call bot");
    }

    @Test
    public void shouldSetInitialCoins1000WhenSetInitialCoinsCommand1000() throws Exception {
        adminCommandsPerformer.performAction("setInitialCoins",new String[] {"1000"}, adminModelMock);

        verify(adminModelMock).setInitialCoins(1000);
    }

    @Test
    public void shouldSetMinimumBlind1000WhenSetMinimumBlind1000() throws Exception {
        adminCommandsPerformer.performAction("setMinimumBlind",new String[] {"1000"}, adminModelMock);

        verify(adminModelMock).setMinimumBlind(1000);
    }

    @Test
    public void shouldSetGameDelay1000WhenSetGameDelayCommand1000() throws Exception {
        adminCommandsPerformer.performAction("setGameDelay",new String[] {"1000"}, adminModelMock);

        verify(adminModelMock).setGameDelay(1000);
    }

    @Test
    public void shouldSetEndGameDelay1000WhenSetEndGameDelay1000() throws Exception {
        adminCommandsPerformer.performAction("setEndGameDelay",new String[] {"1000"}, adminModelMock);

        verify(adminModelMock).setEndGameDelay(1000);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenPerformActionWrongCommand() throws Exception {
        adminCommandsPerformer.performAction("Wrong command", null, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenPerformActionNullCommand() throws Exception {
        adminCommandsPerformer.performAction(null,null, null);
    }
}
