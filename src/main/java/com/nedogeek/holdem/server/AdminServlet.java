package com.nedogeek.holdem.server;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.bot.CallBot;
import com.nedogeek.holdem.bot.RandomBot;
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

    private Dealer dealer;
    private Thread dealerThread;
    private PlayersList players;


    @Override
    public void init() throws ServletException {
        createDealer();
        startDealerThread();
    }

    private void startDealerThread() {
        dealerThread = new Thread(dealer);
        dealerThread.start();
    }

    private void createDealer() {
        players = new PlayersList();

        dealer = new Dealer(players);

        players.add(new RandomBot(dealer));
        players.add(new CallBot(dealer));
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
        request.setAttribute("GameStatus", gameStatus);

        List<String> playerNames = (players != null) ? players.getPlayerNames() : new ArrayList<String>();
        request.setAttribute("Players", playerNames);

        request.setAttribute("Bots", serverBots);

        request.setAttribute("CoinsAtStart", GameSettings.getCoinsAtStart());
        request.setAttribute("SmallBlind", GameSettings.getSmallBlind());

        request.setAttribute("GameDelay", GameSettings.getGameDelayValue());
        request.setAttribute("EndGameDelay", GameSettings.getEndGameDelayValue());
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
            dealer.pause();
        }

        if (kickPlayerCommand != null && players != null) {
            players.kickPlayer(kickPlayerCommand);
        }

        if (coinsAtStartCommand != null) {
            try {
                GameSettings.setCoinsAtStart(Integer.parseInt(coinsAtStartCommand));
            } catch (NumberFormatException ignored) {
            }
        }
        if (smallBlindCommand != null) {
            try {
                GameSettings.setSmallBlind(Integer.parseInt(smallBlindCommand));
            } catch (NumberFormatException ignored) {
            }
        }
        if (gameDelayCommand != null) {
            try {
                GameSettings.setGameDelayValue(Integer.parseInt(gameDelayCommand));
            } catch (NumberFormatException ignored) {
            }
        }
        if (endGameDelayCommand != null) {
            try {
                GameSettings.setEndGameDelayValue(Integer.parseInt(endGameDelayCommand));
            } catch (NumberFormatException ignored) {
            }
        }
        if (addBotCommand != null && botName != null && !botName.equals("")) {
            addBot(addBotCommand, botName);
        }

        if (changePasswordCommand != null) {
            adminPassword = changePasswordCommand;
        }
    }

    private void addBot(String addBotCommand, String botName) {
        try {
            players.add((Player) Class.forName("com.nedogeek.holdem.bot." + addBotCommand).getConstructor(String.class, Dealer.class).newInstance(botName, dealer));
        } catch (InstantiationException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
