/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import utils.*;

/**
 *
 * @author Wisam
 */
public class RegisterFacade {
    private Poolmanager pm = Poolmanager.getSingletonPM();
    private DBConnection db;
    private PropReader prpReader;
    private JacksonMapper jackson;

    public String insertUser(HttpServletRequest request) throws SQLException, JsonProcessingException {
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();
        try {
            UserModel user = jackson.jsonToPlainObj(request, UserModel.class);
            boolean isValid = !db.validate(prpReader.getValue("getUser"), user.getUsername());
            if (isValid) {
                boolean done = db.update(prpReader.getValue("newUser"), user.getUsername(),
                    Encrypter.getMD5(user.getPassword()), user.getName(), user.getTypeId());
                if(done){
                    resp.setStatus(200);
                    resp.setMessage("Succesfully registrated");
                }
                else{
                    resp.setStatus(400);
                    resp.setMessage("Failed to registrate");
                }
            } else {
                resp.setStatus(401);
                resp.setMessage("Usuario ya registrado");
            }
            db.closeCon();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setMessage("DB Connection Error");
            resp.setStatus(500);
            db.closeCon();
        }
        return jackson.plainObjToJson(resp);
    }
}
