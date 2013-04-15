package com.nedogeek.holdem.server;

import com.nedogeek.holdem.Game;
import com.nedogeek.holdem.GameImpl;
import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.bot.Bots;

/**
 * User: Konstantin Demishev
 * Date: 12.04.13
 * Time: 2:52
 */
public class AdminModelImpl implements AdminModel {
    private static final String DEFAULT_PASSWORD = "1234";

    private String adminPassword = DEFAULT_PASSWORD;

    private Game game = GameImpl.getInstance();

    @Override
    public boolean login(String password) {
        return adminPassword.equals(DEFAULT_PASSWORD);
    }

    @Override
    public void addBot(Bots botType, String botName, String password) {
        if (adminPassword.equals(password)) {
            game.addBot(botType, botName);
        }
    }

    @Override
    public boolean kick(String playerName, String password) {
        if (adminPassword.equals(password)) {
            game.removePlayer(playerName);
            return true;
        }
        return false;
    }

    @Override
    public boolean setInitialCoins(int coinsCount, String password) {
        if (adminPassword.equals(password)) {
            GameSettings.setCoinsAtStart(coinsCount);
            return true;
        }
        return false;
    }

    @Override
    public boolean setMinimumBlind(int minimumBlind, String password) {
        if (adminPassword.equals(password)) {
            GameSettings.setSmallBlind(minimumBlind);
            return true;
        }
        return false;
    }

    @Override
    public boolean setGameDelay(int gameDelay, String password) {
        if (adminPassword.equals(password)) {
            GameSettings.setGameDelayValue(gameDelay);
            return true;
        }
        return false;
    }

    @Override
    public boolean setEndGameDelay(int endGameDelay, String password) {
        if (adminPassword.equals(password)) {
            GameSettings.setEndGameDelayValue(endGameDelay);
            return true;
        }
        return false;
    }

    @Override
    public boolean start(String password) {
        if (adminPassword.equals(password)) {
            game.start();
            return true;
        }
        return false;
    }

    @Override
    public boolean stop(String password) {
        if (adminPassword.equals(password)) {
            game.stop();
            return true;
        }
        return false;
    }

    @Override
    public boolean pause(String password) {
        if (adminPassword.equals(password)) {
            game.pause();
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword) {
        if (adminPassword.equals(oldPassword)) {
            adminPassword = newPassword;
            return true;
        }
        return false;
    }

    @Override
    public GameDataBean getGameData(String password) {
        GameDataBean gameDataBean = new GameDataBean();
        if (adminPassword.equals(password)) {

            gameDataBean.setBotTypes(Bots.getBotTypes());

            gameDataBean.setCoinsAtStart(GameSettings.getCoinsAtStart());
            gameDataBean.setMinimumBind(GameSettings.getSmallBlind());

            gameDataBean.setGameDelay(GameSettings.getGameDelayValue());
            gameDataBean.setEndGameDelay(GameSettings.getEndGameDelayValue());

            gameDataBean.setGameStatus(game.getGameStatus());

            gameDataBean.setPlayers(game.getPlayers());
        }
        return gameDataBean;
    }
}
