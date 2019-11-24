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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import utils.*;

public class LoginFacade {
    private Poolmanager pm = Poolmanager.getSingletonPM();
    private DBConnection db;
    private PropReader prpReader;
    private JacksonMapper jackson;
    private ResultSet rs;

    public String loginUser(HttpServletRequest request) throws SQLException, JsonProcessingException {
        prpReader = PropReader.getInstance();
        db = pm.getConex();
        jackson = new JacksonMapper();
        ResponseModel resp = new ResponseModel();

        try {
            UserModel user = jackson.jsonToPlainObj(request, UserModel.class);
            rs = db.execute(prpReader.getValue("getUser"), user.getUsername(), Encrypter.getMD5(user.getPassword()));
            if (rs.next()) {
                user.setData(rs);
                HttpSession session = request.getSession();
                session.setAttribute("user_id", user.getId());
                session.setAttribute("user", user.getUsername());
                System.out.println(session.getAttribute("user_id").toString());
                System.out.println(session.getAttribute("user").toString());
                resp.setStatus(200);
                resp.setMessage("login Successful");
                resp.setData(user);
            } else {
                resp.setStatus(401);
                resp.setMessage("User or password incorrect");
            }
        } catch (IOException | SQLException e) {
            resp.setMessage("DB Connection Error: " + e);
            resp.setStatus(500);
        }
        db.closeCon();
        return jackson.plainObjToJson(resp);
    }
}
