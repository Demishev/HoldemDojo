package com.nedogeek.holdem.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

	public static final HashMap<String, String> USER_LIST = new HashMap<>();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        if (login != null && password != null) {
            if (USER_LIST.containsKey(login)) {
                out.write("User already present.");
            } else {
                USER_LIST.put(login, password);
                out.write("Registered.");
            }
        } else {
            out.write("Could not define username or password.");
        }
	}

}
