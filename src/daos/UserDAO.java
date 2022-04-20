/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Computer
 */
public class UserDAO extends AbstractDAO<User> {

    UserDAO() {

    }

    public static UserDAO instance = new UserDAO();

    public List<User> findAll(String query) throws SQLException {
        
        if (query == null || "".equals(query)) {
            return fetchAllFromSQL("SELECT * FROM users");
        }
        
        return fetchAllFromSQL("SELECT * FROM users WHERE username LIKE ?", new Object[] {
            "%" + query + "%"
        });
    }

    @Override
    protected User resultSetToDTO(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setIsOwner(rs.getBoolean("is_owner"));
        u.setFullname(rs.getString("fullname"));

        return u;
    }

    public User findByUsername(String username) throws SQLException {
        User u = fetchOneFromSQL("SELECT * FROM users WHERE username=?", new Object[]{
            username
        });
        
        return u;
    }

    public boolean isUsernameExists(String username) throws SQLException {
        User u = fetchOneFromSQL("SELECT * FROM users WHERE username=?", new Object[]{
            username
        });
        
        return u != null;
    }
    
    public boolean isUsernameExists(String username, int id) throws SQLException {
        User u = fetchOneFromSQL("SELECT * FROM users WHERE username=?", new Object[]{
            username
        });
        
        if (u == null) return false;
        
        // ignore id
        return id != u.getId();
    }

    public void create(User user) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement("INSERT INTO users (username, password, is_owner, fullname) VALUES (?, ?, ?, ?)");
            
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setBoolean(3, user.isIsOwner());
            st.setString(4, user.getFullname());

            st.execute();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            conn.close();
        }
    }

    public User findById(int id) throws Exception {
        User u = fetchOneFromSQL("SELECT * FROM users WHERE id=?", new Object[]{
            id
        });
        
        return u;
    }

    public void update(User user) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement("UPDATE users SET username=?,password=?,is_owner=?,fullname=? WHERE id=?");
            
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setBoolean(3, user.isIsOwner());
            st.setString(4, user.getFullname());
            st.setInt(5, user.getId());

            st.execute();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            conn.close();
        }
    }

    public void remove(int currentSelected) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement("DELETE FROM users WHERE id=?");
            st.setInt(1, currentSelected);

            st.execute();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            conn.close();
        }
    }
}
