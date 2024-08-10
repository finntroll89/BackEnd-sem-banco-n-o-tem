/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceManager {  

    public static int getValue(String sequenceName) throws SQLException {
        String sql = "SELECT nextval(?)";
        PreparedStatement stmt = ConectorBD.getPrepared(sql);
        stmt.setString(1, sequenceName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        throw new SQLException("Unable to retrieve sequence value.");
    }
}

