/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Wisam
 */
public class UserModel {
    private String name, password, username;
    private int typeId, id;

    public void setData(ResultSet rs) throws SQLException{
	this.setId(rs.getInt(1));
        this.setUsername(rs.getString(2));
        this.setPassword(null);    
        this.setName(rs.getString(4));
        this.setTypeId(rs.getInt(5));
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
