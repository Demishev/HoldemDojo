package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.bot.Bots;
import com.nedogeek.holdem.server.AdminModel;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 17.04.13
 * Time: 15:01
 */
public class AddBotCommandTest {
    final String ANOTHER_BOT_NAME = "Another bot name";

    final String CALL_BOT_TYPE_NAME = Bots.CallBot.name();
    final Bots CALL_BOT_TYPE = Bots.CallBot;
    final String CALL_BOT_NAME = "Call bot";

    final String RISE_BOT_TYPE_NAME = Bots.RiseBot.name();
    final Bots RISE_BOT_TYPE = Bots.RiseBot;
    final String RISE_BOT_NAME = "Rise bot";

    AddBotCommand addBotCommand;
    AdminModel adminModel;

    @Before
    public void setUp() throws Exception {
        addBotCommand = new AddBotCommand();

        adminModel = mock(AdminModel.class);
    }

    @Test
    public void shouldAddCallBotWhenAddBotCommandInvokeCallBotAdding() throws Exception {
        String[] params = new String[] {CALL_BOT_TYPE_NAME, CALL_BOT_NAME};

        addBotCommand.invoke(params, adminModel);

        verify(adminModel).addBot(CALL_BOT_TYPE, CALL_BOT_NAME);
    }

    @Test
    public void shouldAddCallBotWithAnotherNameWhenAddBotCommandInvokeCallBotWithAnotherNameAdding() throws Exception {
        String[] params = new String[] {CALL_BOT_TYPE_NAME, ANOTHER_BOT_NAME};

        addBotCommand.invoke(params, adminModel);

        verify(adminModel).addBot(CALL_BOT_TYPE, ANOTHER_BOT_NAME);
    }

    @Test
    public void shouldAddRiseBotWhenAddBotCommandInvokeRiseBotAdding() throws Exception {
        String[] params = new String[] {RISE_BOT_TYPE_NAME, RISE_BOT_NAME};

        addBotCommand.invoke(params, adminModel);

        verify(adminModel).addBot(RISE_BOT_TYPE, RISE_BOT_NAME);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldIllegalArgumentExceptionWhenAddBotCommandInvokesWithNullParams() throws Exception {
        addBotCommand.invoke(null, adminModel);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldIllegalArgumentExceptionWhenAddBotCommandWithNullName() throws Exception {
        String[] params = new String[] {RISE_BOT_TYPE_NAME, null};

        addBotCommand.invoke(params, adminModel);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldIllegalArgumentExceptionWhenAddBotCommandWithNullBotType() throws Exception {
        String[] params = new String[] {null, RISE_BOT_NAME};

        addBotCommand.invoke(params, adminModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldIllegalArgumentExceptionWhenAddBotCommandWithOnlyBotType() throws Exception {
        String[] params = new String[] {RISE_BOT_TYPE_NAME};

        addBotCommand.invoke(params, adminModel);
    }
}
