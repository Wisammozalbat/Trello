/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Wisam
 */
@WebFilter(urlPatterns = {"/login", "/register"}, filterName = "Session Filter")
public class SessionFilter implements Filter {
    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String json;
        PrintWriter out = resp.getWriter();
        ObjectMapper objM = new ObjectMapper();
        HttpSession session = req.getSession(false);
        if (session == null) {
            System.out.println("loged in");
            chain.doFilter(req, resp);
        } else {
            ResponseModel<UserModel> msgToUser = new ResponseModel();
            UserModel usr = new UserModel();
            usr.setId((int)session.getAttribute("user_id"));
            usr.setUsername(session.getAttribute("user").toString());
            msgToUser.setData(usr);
            msgToUser.setMessage("Already logged in");
            msgToUser.setStatus(403);
            json = objM.writeValueAsString(msgToUser);
            out.print(json);
        }
    }
}