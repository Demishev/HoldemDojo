package com.nedogeek.holdem.server;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

	public static final HashMap<String, String> USER_LIST = new HashMap<String, String>();

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		USER_LIST.put(arg0.getParameter("login"), arg0.getParameter("passwor")); 
	}

	
}
