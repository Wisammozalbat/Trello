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
public class ContentModel {
    private int contentId, itemId;
    private String contentDes;
    
    public void setData(ResultSet rs) throws SQLException{
	this.setContentId(rs.getInt(1));
        this.setItemId(rs.getInt(2));
        this.setContentDes(rs.getString(3));
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getContentDes() {
        return contentDes;
    }

    public void setContentDes(String contentDes) {
        this.contentDes = contentDes;
    }
    
}
