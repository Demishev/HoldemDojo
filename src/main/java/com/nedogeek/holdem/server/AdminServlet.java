package com.nedogeek.holdem.server;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Konstantin Demishev
 * Date: 17.04.13
 * Time: 14:18
 */
@WebServlet(urlPatterns = "/admin", loadOnStartup = 1)
public class AdminServlet extends HttpServlet {
    private AdminModel model;

    @Override
    public void init() throws ServletException {
        model = new AdminModelImpl();
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        redirectToLoginPage(httpServletRequest, httpServletResponse);
    }

    private void redirectToLoginPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        RequestDispatcher view = httpServletRequest.getRequestDispatcher("/admin/index.html");
        view.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        super.doPost(httpServletRequest, httpServletResponse);

        if (!model.passwordCorrect(httpServletRequest.getParameter("password"))) {
            redirectToLoginPage(httpServletRequest, httpServletResponse);
        }


    }
}
