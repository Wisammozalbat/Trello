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
    private Poolmanager pm = Poolmanager.getInstance();
    private DBConnection db;
    private PropReader prpReader;
    private JacksonMapper jackson;
    private ResultSet rs;
    private ResultSet rs2;

    
    public String createProject(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = pm.getConex();
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
//        db.closeCon();
        pm.returnConexDisponibles();
        return jackson.plainObjToJson(resp);
    }
    
    public String getProjects(HttpServletRequest request) throws SQLException, JsonProcessingException {
        ArrayList<ProjectModel> projects = new ArrayList<>();
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        System.out.println(request.getSession(false).getAttribute("user_id").toString());
        Integer userId = Integer.parseInt(request.getSession(false).getAttribute("user_id").toString());

        try {
            rs = db.execute(prpReader.getValue("getProjects"), userId);
            while (rs.next()) {
                ArrayList<ItemModel> items = new ArrayList<>();
                ProjectModel project = new ProjectModel();
                System.out.println("trajo algo");
                UserModel user = new UserModel();
                project.setData(rs);
                user.setName(rs.getString(6));
                user.setUsername(rs.getString(7));
                user.setId(rs.getInt(2));
                rs2 = db.execute(prpReader.getValue("getItems"), project.getProjectId());
                while(rs2.next()){
                    ItemModel item = new ItemModel();
                    item.setItemId(rs2.getInt(1));
                    item.setItemName(rs2.getString(2));
                    item.setItemDes(rs2.getString(3));    
                    item.setStatus(rs2.getInt(4));
                    item.setProjectId(rs2.getInt(5));
                    items.add(item);
                }
                project.setItems(items);
                project.setUser(user);
                projects.add(project);
                System.out.println("");
            }
            System.out.println("todo bien hasta aca");
            resp.setData(projects);
            resp.setMessage("Projects Returned");
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setMessage("DB Connection Error: " + e);
            resp.setStatus(500);
        }
//        db.closeCon();
        pm.returnConexDisponibles();
//        db.closeCon();
        return jackson.plainObjToJson(resp);
    }
    
    public String updateProject(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        ProjectModel project = jackson.jsonToPlainObj(request, ProjectModel.class);
        Integer projectId = Integer.parseInt(request.getParameter("projectId"));

        
        Integer userId = Integer.parseInt(request.getSession(false).getAttribute("user_id").toString());

        try {
            boolean exists = db.validate(prpReader.getValue("getProject"), projectId, userId);
            if(exists){
                boolean done = db.update(prpReader.getValue("updateProject"), project.getProjectName(), 
                project.getProjectDes(), project.getStatus(), projectId, userId);
                            System.out.println(done);
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
//        db.closeCon();
        pm.returnConexDisponibles();
        return jackson.plainObjToJson(resp);
    }
    
    public String deleteProject(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        Integer projectId = Integer.parseInt(request.getParameter("projectId"));
        
        Integer userId = Integer.parseInt(request.getSession(false).getAttribute("user_id").toString());

        try {
            boolean exists = db.validate(prpReader.getValue("getProject"), projectId, userId);
            if(exists){
                boolean done = db.update(prpReader.getValue("deleteProject"), projectId, userId);
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
//        db.closeCon();
        pm.returnConexDisponibles();
        return jackson.plainObjToJson(resp);
    }
    
}
