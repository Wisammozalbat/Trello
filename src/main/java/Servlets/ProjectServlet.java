/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Facade.ProjectFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wisam
 */
@WebServlet(name = "ProjectServlet", urlPatterns = {"/projects"})
public class ProjectServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JsonProcessingException {
        ProjectFacade project = new ProjectFacade();
        PrintWriter out = response.getWriter();
        try {
            String json = project.createProject(request);
            out.write(json);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
