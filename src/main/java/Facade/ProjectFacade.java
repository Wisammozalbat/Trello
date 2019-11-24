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
            boolean done = db.update(prpReader.getValue("newProject"), userId, project.getProjectName(), project.getProjectDes(), 1);
            if(done){
                resp.setStatus(201);
                resp.setMessage("Created project successfully.");
                resp.setData(true);
            }
            else{
                resp.setMessage("Failed to create the project");
                resp.setStatus(400);
                
            }
        } catch (Exception e) {
            resp.setMessage("DB Connection Error: " + e);
            resp.setStatus(500);
        }
        db.closeCon();
        return jackson.plainObjToJson(resp);
    }
    
    public String getProjects(HttpServletRequest request) throws SQLException, JsonProcessingException {
        ArrayList<ProjectModel> projects = new ArrayList<>();
        prpReader = PropReader.getInstance();
        db = new DBConnection();
        ProjectModel project = new ProjectModel();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        System.out.println("asdasd");
        System.out.println(request.getSession(false).getAttribute("user_id").toString());
        Integer userId = Integer.parseInt(request.getSession(false).getAttribute("user_id").toString());

        try {
            rs = db.execute(prpReader.getValue("getProjects"), userId);
            while (rs.next()) {
                System.out.println("trajo algo");
                UserModel user = new UserModel();
                project.setData(rs);
                user.setUsername(rs.getString(6));
                user.setName(rs.getString(7));
                user.setTypeId(rs.getInt(8));
                user.setId(rs.getInt(2));
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
    
    public String updateProject(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = new DBConnection();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        ProjectModel project = jackson.jsonToPlainObj(request, ProjectModel.class);

        
        Integer userId = Integer.parseInt(request.getSession(false).getAttribute("user_id").toString());

        try {
            boolean exists = db.validate(prpReader.getValue("getProject"), project.getProjectId(), userId);
            if(exists){
                boolean done = db.update(prpReader.getValue("updateProject"), project.getProjectName(), 
                project.getProjectDes(), project.getStatus(), project.getProjectId(), userId);
                if(done){
                    resp.setStatus(200);
                    resp.setMessage("Updated project successfully.");
                    resp.setData(true);
                }
                else{
                    resp.setMessage("Failed to update the project");
                    resp.setStatus(400);
                }
            }
            else{
                resp.setMessage("Project doesn't exist");
                resp.setStatus(400);
            }
           
        } catch (Exception e) {
            resp.setMessage("DB Connection Error: " + e);
            resp.setStatus(500);
        }
        db.closeCon();
        return jackson.plainObjToJson(resp);
    }
    
    public String deleteProject(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = new DBConnection();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        ProjectModel project = jackson.jsonToPlainObj(request, ProjectModel.class);
        
        Integer userId = Integer.parseInt(request.getSession(false).getAttribute("user_id").toString());

        try {
            boolean exists = db.validate(prpReader.getValue("getProject"), project.getProjectId(), userId);
            if(exists){
                boolean done = db.update(prpReader.getValue("deleteProject"), project.getProjectId(), userId);
                if(done){
                    resp.setStatus(200);
                    resp.setMessage("Deleted project successfully.");
                    resp.setData(true);
                }
                else{
                    resp.setMessage("Failed to delete the project");
                    resp.setStatus(400);
                }
            }
            else{
                resp.setMessage("Project doesn't exist");
                resp.setStatus(400);
            }
           
        } catch (Exception e) {
            resp.setMessage("DB Connection Error: " + e);
            resp.setStatus(500);
        }
        db.closeCon();
        return jackson.plainObjToJson(resp);
    }
    
}
