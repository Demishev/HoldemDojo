package com.nedogeek.holdem.server;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.bot.CallBot;
import com.nedogeek.holdem.bot.FoldBot;
import com.nedogeek.holdem.bot.RandomBot;
import com.nedogeek.holdem.bot.RiseBot;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 09.03.13
 * Time: 20:08
 */
@WebServlet(urlPatterns = "/admin", loadOnStartup = 1)
public class AdminServlet extends HttpServlet {
    private final String[] serverBots = new String[]{"CallBot", "FoldBot", "RandomBot", "RiseBot"};

    private final String DEFAULT_ADMIN_PASSWORD = "1234";
    private String adminPassword = DEFAULT_ADMIN_PASSWORD;

    Dealer dealer;
    Thread dealerThread;
    PlayersList players;


    @Override
    public void init() throws ServletException {
        startDealerThread();
    }

    private void startDealerThread() {
        players = new PlayersList();

        dealer = new Dealer(players);

        players.add(new RiseBot(dealer));
        players.add(new RandomBot(dealer));
        players.add(new CallBot(dealer));
        players.add(new FoldBot(dealer));

        players.add(new CallBot("Vasili Call", dealer));
        players.add(new RandomBot("Fredia Rand", dealer));
        players.add(new RandomBot("Nina Rand", dealer));
        players.add(new RiseBot("Leha Rise!", dealer));

        dealerThread = new Thread(dealer);
        dealerThread.start();
    }

    @Override
    public void destroy() {
        dealer.stop();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        RequestDispatcher view = httpServletRequest.getRequestDispatcher("/admin/index.html");
        view.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        final String enteredPassword = httpServletRequest.getParameter("password");
        RequestDispatcher view;
        if (adminPassword.equals(enteredPassword)) {
            processCommands(httpServletRequest);
            addGameData(httpServletRequest);
            view = httpServletRequest.getRequestDispatcher("/admin/adminPage.jsp");
        } else {
            view = httpServletRequest.getRequestDispatcher("/admin/index.html");
        }
        view.forward(httpServletRequest, httpServletResponse);
    }

    private void addGameData(HttpServletRequest request) {
        String gameStatus = (dealer != null) ? dealer.getGameStatus().toString() : "Stopped";
        request.setAttribute("gameStatus", gameStatus);

        List<String> playerNames = (players != null) ? players.getPlayerNames() : new ArrayList<String>();
        request.setAttribute("players", playerNames);

        request.setAttribute("bots", serverBots);

        request.setAttribute("coinsAtStart", GameSettings.COINS_AT_START);
        request.setAttribute("smallBlind", GameSettings.SMALL_BLIND);

        request.setAttribute("gameDelay", GameSettings.GAME_DELAY_VALUE);
        request.setAttribute("endGameDelay", GameSettings.END_GAME_DELAY_VALUE);
    }

    private void processCommands(HttpServletRequest request) {
        final String stopServerCommand = request.getParameter("Stop");
        final String startServerCommand = request.getParameter("Start");
        final String pauseServerCommand = request.getParameter("Pause");

        final String kickPlayerCommand = request.getParameter("Kick");

        final String coinsAtStartCommand = request.getParameter("CoinsAtStart");
        final String smallBlindCommand = request.getParameter("SmallBlind");
        final String gameDelayCommand = request.getParameter("GameDelay");
        final String endGameDelayCommand = request.getParameter("EndGameDelay");

        final String addBotCommand = request.getParameter("AddBot");
        final String botName = request.getParameter("BotName");

        final String changePasswordCommand = request.getParameter("ChangePassword");

        if ("Stop".equals(stopServerCommand)) {
            dealer.stop();
        }
        if ("Start".equals(startServerCommand)) {
            if (dealerThread != null && !dealerThread.isAlive()) {
                startDealerThread();
            }
        }
        if ("Pause".equals(pauseServerCommand)) {
            dealer.pause();//TODO test it
        }

        if (kickPlayerCommand != null && players != null) {
            players.kickPlayer(kickPlayerCommand);
        }

        if (coinsAtStartCommand != null) {
            try {
                GameSettings.COINS_AT_START = Integer.parseInt(coinsAtStartCommand);
            } catch (NumberFormatException ignored) {
            }
        }
        if (smallBlindCommand != null) {
            try {
                GameSettings.SMALL_BLIND = Integer.parseInt(smallBlindCommand);
            } catch (NumberFormatException ignored) {
            }
        }
        if (gameDelayCommand != null) {
            try {
                GameSettings.GAME_DELAY_VALUE = Integer.parseInt(gameDelayCommand);
            } catch (NumberFormatException ignored) {
            }
        }
        if (endGameDelayCommand != null) {
            try {
                GameSettings.END_GAME_DELAY_VALUE = Integer.parseInt(endGameDelayCommand);
            } catch (NumberFormatException ignored) {
            }
        }
        if (addBotCommand != null && botName != null) {
            addBot(addBotCommand, botName);
        }

        if (changePasswordCommand != null) {
            adminPassword = changePasswordCommand;
        }
    }

    private void addBot(String addBotCommand, String botName) {
        try {
            players.add((Player) Class.forName(addBotCommand).getConstructor(String.class).newInstance(botName));
        } catch (InstantiationException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException ignored) {
        }
    }
}
