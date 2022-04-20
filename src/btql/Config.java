/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btql;

import dtos.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Computer
 */
public class Config {

    public static Connection createConnection() throws SQLException {
        String connectionUrl = "jdbc:mysql://localhost:3306/ql_dongho";
//        String connectionUrl = "jdbc:mysql://localhost:6033/quanly";
        String username = "root";
        String password = "";
        Connection conn = DriverManager.getConnection(connectionUrl, username, password);
        return conn;
    }

    public static void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }
    
    public static String queryRaw(String sql) throws SQLException {
        Connection conn = Config.createConnection();

        ResultSet rs = null;
        PreparedStatement st = null;
        String result = "";
        
        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
            }
            
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            conn.close();
        }

        return result;
    }
    
    private static int userId;
    
    public static void setCurrentUserId(int id) {
        userId = id;
    }
    
    public static int getCurrentUserId() {
        return userId;
    }
}
