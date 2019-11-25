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
import utils.JacksonMapper;
import utils.Poolmanager;
import utils.PropReader;

/**
 *
 * @author Wisam
 */
public class ItemFacade {
    private Poolmanager pm = Poolmanager.getInstance();
    private DBConnection db;
    private PropReader prpReader;
    private JacksonMapper jackson;
    private ResultSet rs;
    
    public String createItem(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        ItemModel item = jackson.jsonToPlainObj(request, ItemModel.class);
        
        try {
            boolean done = db.update(prpReader.getValue("newItem"), item.getItemName(), item.getItemDes(),
                    item.getStatus(), item.getProjectId());
            if(done){
                resp.setStatus(201);
                resp.setMessage("Created item successfully.");
                resp.setData(true);
            }
            else{
                resp.setMessage("Failed to create the item");
                resp.setStatus(400);
                
            }
        } catch (Exception e) {
            resp.setMessage("DB Connection Error: " + e);
            resp.setStatus(500);
        }
        
        pm.returnConexDisponibles();
        return jackson.plainObjToJson(resp);
    }
    
    public String getItems(HttpServletRequest request) throws SQLException, JsonProcessingException {
        ArrayList<ItemModel> items = new ArrayList<>();
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        Integer projectId = Integer.parseInt(request.getParameter("projectId"));
        System.out.println(projectId);
        try {
            rs = db.execute(prpReader.getValue("getItems"), projectId);
            while (rs.next()) {
                ItemModel item = new ItemModel();        
                System.out.println("trajo algo " +rs.getInt(1) );
                item.setData(rs);
                items.add(item);
            }
            resp.setData(items);
            resp.setMessage("Items Returned");
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
//    
    public String updateItem(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        ItemModel item = jackson.jsonToPlainObj(request, ItemModel.class);
        Integer projectId = Integer.parseInt(request.getParameter("projectId"));
        System.out.println(item.getItemId());

        try {
            boolean exists = db.validate(prpReader.getValue("getItem"), item.getItemId(), projectId);
            if(exists){
                boolean done = db.update(prpReader.getValue("updateItem"), item.getItemName(), item.getItemDes(), item.getStatus(), item.getItemId());
                            System.out.println(done);
                if(done){
                    resp.setStatus(200);
                    resp.setMessage("Updated item successfully.");
                    resp.setData(true);
                }
                else{
                    resp.setMessage("Failed to update the item");
                    resp.setStatus(400);
                }
            }
            else{
                resp.setMessage("Item doesn't exist");
                resp.setStatus(400);
            }
           
        } catch (Exception e) {
            resp.setMessage("DB Connection Error: " + e);
            resp.setStatus(500);
        }
        pm.returnConexDisponibles();
        return jackson.plainObjToJson(resp);
    }
    
    public String deleteItem(HttpServletRequest request) throws SQLException, JsonProcessingException, IOException {
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        ItemModel item = jackson.jsonToPlainObj(request, ItemModel.class);
        Integer projectId = Integer.parseInt(request.getParameter("projectId"));
        
        try {
            boolean exists = db.validate(prpReader.getValue("getItem"), item.getItemId(), projectId);
            if(exists){
                System.out.println("asdweqq");
                boolean done = db.update(prpReader.getValue("deleteItem"), item.getItemId(), projectId);
                System.out.println(done);
                if(done){
                    System.out.println("se");
                    resp.setStatus(200);
                    resp.setMessage("Deleted item successfully.");
                    resp.setData(true);
                }
                else{
                    resp.setMessage("Failed to item the project");
                    resp.setStatus(400);
                }
            }
            else{
                resp.setMessage("item doesn't exist");
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
