package com.nedogeek.holdem;

/*-
 * #%L
 * Holdem dojo project is a server-side java application for playing holdem pocker in DOJO style.
 * %%
 * Copyright (C) 2016 Holdemdojo
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


/**
 * User: Konstantin Demishev
 * Date: 28.05.13
 * Time: 20:12
 */
public class PlayerCommandsParser {

    class SomePlayerCommand implements PlayerCommand {
        private final PlayerCommandReceiver receiver;
        private final String login;

        SomePlayerCommand(String login, PlayerCommandReceiver receiver) {
            this.login = login;
            this.receiver = receiver;
        }

        @Override
        public String getLogin() {
            return login;
        }

        @Override
        public PlayerCommandReceiver getReceiver() {
            return receiver;
        }
    }


    public PlayerCommand parseCommand(String login, String message) {
        if (isGameCommand(message)) {
            return new SomePlayerCommand(login, PlayerCommandReceiver.Game);
        } else
            return new SomePlayerCommand(login, PlayerCommandReceiver.GameCenter);
    }

    private boolean isGameCommand(String message) {
        return message.equals("Fold") || message.equals("Call");
    }
}
