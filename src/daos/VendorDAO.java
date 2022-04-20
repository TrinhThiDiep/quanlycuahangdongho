/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import btql.watchman.DlgCreate;
import dtos.User;
import dtos.Vendor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Computer
 */
public class VendorDAO extends AbstractDAO<Vendor> {

    VendorDAO() {

    }

    public static VendorDAO instance = new VendorDAO();

    public List<Vendor> findAll(String query) throws SQLException {
        
        if (query == null || "".equals(query)) {
            return fetchAllFromSQL("SELECT * FROM vendors");
        }
        
        return fetchAllFromSQL("SELECT * FROM vendors WHERE title LIKE ?", new Object[] {
            "%" + query + "%"
        });
    }

    
    public ComboBoxModel<Vendor> buildComboBoxModel() {
         try {
            // load vendor / category
            List<Vendor> vendors = VendorDAO.instance.findAll(null);
            DefaultComboBoxModel model = new DefaultComboBoxModel<>(new Vector(vendors));
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(DlgCreate.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return new DefaultComboBoxModel<>();
    }
    
    @Override
    protected Vendor resultSetToDTO(ResultSet rs) throws SQLException {
        Vendor u = new Vendor();
        u.setId(rs.getInt(1));
        u.setTitle(rs.getString(2));

        return u;
    }

    public void create(Vendor vendor) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement("INSERT INTO vendors (title) VALUES (?)");
            
            st.setString(1, vendor.getTitle());

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

    public Vendor findById(int id) throws Exception {
        Vendor u = fetchOneFromSQL("SELECT * FROM vendors WHERE id=?", new Object[]{
            id
        });
        
        return u;
    }

    public void update(Vendor vendor) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement("UPDATE vendors SET title=? WHERE id=?");
            
            st.setString(1, vendor.getTitle());
            st.setInt(2, vendor.getId());

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
            st = conn.prepareStatement("DELETE FROM vendors WHERE id=?");
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
