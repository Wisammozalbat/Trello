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
import utils.DBConnection;
import utils.*;

/**
 *
 * @author Wisam
 */
public class ContentFacade {
    private Poolmanager pm = Poolmanager.getInstance();
    private DBConnection db;
    private PropReader prpReader;
    private JacksonMapper jackson;
    private ResultSet rs;
    
    public String createContent(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        ContentModel content = jackson.jsonToPlainObj(request, ContentModel.class);
        
        try {
            boolean done = db.update(prpReader.getValue("newContent"), content.getItemId(), content.getContentDes());
            if(done){
                resp.setStatus(201);
                resp.setMessage("Created content successfully.");
                resp.setData(true);
            }
            else{
                resp.setMessage("Failed to create the content");
                resp.setStatus(400);
                
            }
        } catch (Exception e) {
            resp.setMessage("DB Connection Error: " + e);
            resp.setStatus(500);
        }
        
        pm.returnConexDisponibles();
        return jackson.plainObjToJson(resp);
    }
    
    public String getContents(HttpServletRequest request) throws SQLException, JsonProcessingException {
        ArrayList<ContentModel> contents = new ArrayList<>();
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        Integer itemId = Integer.parseInt(request.getParameter("itemId"));
        System.out.println(itemId);
        try {
            rs = db.execute(prpReader.getValue("getContents"), itemId);
            while (rs.next()) {
                ContentModel content = new ContentModel();        
                System.out.println("trajo algo " +rs.getInt(1) );
                content.setData(rs);
                contents.add(content);
            }
            resp.setData(contents);
            resp.setMessage("Content Returned");
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
////    
    public String updateContent(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        ContentModel content = jackson.jsonToPlainObj(request, ContentModel.class);
        Integer itemId = Integer.parseInt(request.getParameter("itemId"));

        try {
            boolean exists = db.validate(prpReader.getValue("getContent"), content.getContentId(), itemId);
            System.out.println(exists);
            if(exists){
                boolean done = db.update(prpReader.getValue("updateContent"), content.getContentDes(), content.getContentId());
                System.out.println(done);
                if(done){
                    resp.setStatus(200);
                    resp.setMessage("Updated content successfully.");
                    resp.setData(true);
                }
                else{
                    resp.setMessage("Failed to update the content");
                    resp.setStatus(400);
                }
            }
            else{
                resp.setMessage("Content doesn't exist");
                resp.setStatus(400);
            }
           
        } catch (Exception e) {
            resp.setMessage("DB Connection Error: " + e);
            resp.setStatus(500);
        }
        pm.returnConexDisponibles();
        return jackson.plainObjToJson(resp);
    }
//    
    public String deleteContent(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        ContentModel content = jackson.jsonToPlainObj(request, ContentModel.class);
        Integer itemId = Integer.parseInt(request.getParameter("itemId"));
        
        try {
            boolean exists = db.validate(prpReader.getValue("getContent"), content.getContentId(), itemId);
            if(exists){
                System.out.println("asdweqq");
                boolean done = db.update(prpReader.getValue("deleteContent"), content.getContentId());
                System.out.println(done);
                if(done){
                    System.out.println("se");
                    resp.setStatus(200);
                    resp.setMessage("Deleted content successfully.");
                    resp.setData(true);
                }
                else{
                    resp.setMessage("Failed to delete the content");
                    resp.setStatus(400);
                }
            }
            else{
                resp.setMessage("content doesn't exist");
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
