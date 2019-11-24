/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Wisam
 */
public class ItemModel {
    private int itemId, projectId, status;
    private String itemName, itemDes;
    
    public void setData(ResultSet rs) throws SQLException{
	this.setItemId(rs.getInt(1));
        this.setItemName(rs.getString(2));
        this.setItemDes(rs.getString(3));    
        this.setStatus(rs.getInt(4));
        this.setProjectId(rs.getInt(5));
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    

    
}
