package com.redirect;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Cus extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String isLogged = (String)session.getAttribute("is-logged");
        System.out.println(session.getId());
        // ServletContext application = req.getServletContext();
        // String isLogged = (String)application.getAttribute("is-logged");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain;charset=utf-8");
        if(!(isLogged == null)) {
            resp.getWriter().write(isLogged);
        }
    }
}

