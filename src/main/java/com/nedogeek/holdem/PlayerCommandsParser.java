package com.nedogeek.holdem;

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
