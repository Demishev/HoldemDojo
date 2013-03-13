package com.nedogeek.holdem.server;

import com.nedogeek.holdem.dealer.EventManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

	private Map<String, String> userData = EventManager.getInstance().getUserData();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        if (login != null && password != null) {
            if (userData.containsKey(login)) {
                out.write("User already present.");
            } else {
                userData.put(login, password);
                out.write("Registered.");
            }
        } else {
            out.write("Could not define username or password.");
        }
	}

}
