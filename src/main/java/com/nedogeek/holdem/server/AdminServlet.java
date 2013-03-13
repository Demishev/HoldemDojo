package com.nedogeek.holdem.server;

import com.nedogeek.holdem.bot.CallBot;
import com.nedogeek.holdem.bot.FoldBot;
import com.nedogeek.holdem.bot.RandomBot;
import com.nedogeek.holdem.bot.RiseBot;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.gamingStuff.PlayersList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Konstantin Demishev
 * Date: 09.03.13
 * Time: 20:08
 */
@WebServlet(urlPatterns="/admin", loadOnStartup=1)
public class AdminServlet extends HttpServlet {
    Dealer dealer;


    @Override
    public void init() throws ServletException {
        PlayersList players = new PlayersList();

        dealer = new Dealer(players);

        players.add(new RiseBot(dealer));
        players.add(new RandomBot(dealer));
        players.add(new CallBot(dealer));
        players.add(new FoldBot(dealer));
    }

    @Override
    public void destroy() {
        super.destroy();
        dealer.stop();
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        switch (httpServletRequest.getParameter("Command")) {
            case "Start":
                new Thread(dealer).start();
                break;
            case "Stop":
                dealer.stop();
                break;
            case "Pause":
                dealer.pause();
        }
    }
}
