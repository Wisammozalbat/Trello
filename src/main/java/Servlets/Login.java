/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Facade.LoginFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wisam
 */
public class Login extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    LoginFacade loginUser = new LoginFacade();
    PrintWriter out = response.getWriter();
        try{
            response.setContentType("application/json");
            String json = loginUser.loginUser(request);
            out.write(json);
        } catch (SQLException ex) {
      Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

}
