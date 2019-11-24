/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import utils.*;

/**
 *
 * @author Wisam
 */
public class ProjectFacade {
    private Poolmanager pm = Poolmanager.getSingletonPM();
    private DBConnection db;
    private PropReader prpReader;
    private JacksonMapper jackson;
    private ResultSet rs;
    
    public String createProject(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = new DBConnection();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        ProjectModel project = jackson.jsonToPlainObj(request, ProjectModel.class);

        
        Integer userId = Integer.parseInt(request.getSession(false).getAttribute("user_id").toString());

        try {
            db.update(prpReader.getValue("newProject"), userId, project.getProjectName(), project.getProjectDes(), 1);
            resp.setStatus(200);
            resp.setMessage("Added project successfully.");
            resp.setData(true);
            db.closeCon();
        } catch (Exception e) {
            resp.setMessage("DB Connection Error: " + e);
            resp.setStatus(500);
            db.closeCon();
        }
        return jackson.plainObjToJson(resp);
    }
    
    public String getProjects(HttpServletRequest request) throws SQLException, JsonProcessingException {
        ArrayList<ProjectModel> projects = new ArrayList<>();
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        ProjectModel project = new ProjectModel();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        Integer userId = Integer.parseInt(request.getSession(false).getAttribute("user_id").toString());

        try {
            rs = db.execute(prpReader.getValue("getListProject"), userId);
            while (rs.next()) {
                UserModel user = new UserModel();
                project.setData(rs);
                user.setUsername(rs.getString(6));
                user.setName(rs.getString(7));
                project.setUser(user);
                projects.add(project);
            }
            resp.setData(projects);
            resp.setMessage("Projects Returned");
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setMessage("DB Connection Error: " + e);
            resp.setStatus(500);
            db.closeCon();
        }
        return jackson.plainObjToJson(resp);
    }
    
}
