package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.bot.CallBot;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.gamingStuff.PlayersList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.ElementType;

@WebServlet("/events")

public class EventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PlayersList playerList = new PlayersList();
        Dealer dealer = new Dealer(playerList);
        playerList.add(new CallBot(dealer));
        playerList.add(new CallBot(dealer));

        dealer.setGameReady();

        response.getWriter().print(playerList.toJSON());

    }


}
