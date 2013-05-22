package com.nedogeek.holdem.server.adminCommands;

import com.nedogeek.holdem.server.AdminModel;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 3:20
 */
public class StartCommandTest {

    @Test
    public void shouldModelStartWhenInvokeStartCommand() throws Exception {
        AdminModel adminModelMock = mock(AdminModel.class);

        new StartCommand(adminModelMock).invoke(null);

        verify(adminModelMock).start();
    }
}
