package com.admin.login;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/com/admin/login")
public class AdminLogin extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = reader.readLine()) != null) {
            sb.append(temp);
        }
        System.out.println(sb);
        AdminLoginInfo info = new Gson().fromJson(sb.toString(), AdminLoginInfo.class);

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain;charset=utf8");
        if (
                Objects.equals(info.getUsername(), "diana") &&
                  Objects.equals(info.getPwd(), "12345")
        ) {
            resp.getWriter().write("success");
        }else{
            resp.getWriter().write("fail");
        }
    }
}
