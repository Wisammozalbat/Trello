/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Models.ResponseModel;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.JacksonMapper;

/**
 *
 * @author Wisam
 */
public class Logout extends HttpServlet {
      @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        JacksonMapper objM = new JacksonMapper();
        response.setContentType("application/json");
        request.getSession().invalidate();
        ResponseModel msgToUser = new ResponseModel();
        msgToUser.setStatus(200);
        msgToUser.setMessage("Session finished");        
        String json = objM.plainObjToJson(msgToUser);
        response.getWriter().print(json);
    } 
}
