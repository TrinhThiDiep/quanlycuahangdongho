/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import btql.watchman.DlgCreate;
import dtos.User;
import dtos.Category;
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
public class CategoryDAO extends AbstractDAO<Category> {

    CategoryDAO() {

    }

    public static CategoryDAO instance = new CategoryDAO();

    public ComboBoxModel<Category> buildComboBoxModel() {
        try {
            // load vendor / category
            List<Category> categories = CategoryDAO.instance.findAll(null);
            DefaultComboBoxModel model = new DefaultComboBoxModel<>(new Vector(categories));
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(DlgCreate.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new DefaultComboBoxModel<>();
    }

    public List<Category> findAll(String query) throws SQLException {

        if (query == null || "".equals(query)) {
            return fetchAllFromSQL("SELECT * FROM categories");
        }

        return fetchAllFromSQL("SELECT * FROM categories WHERE title LIKE ?", new Object[]{
            "%" + query + "%"
        });
    }

    @Override
    protected Category resultSetToDTO(ResultSet rs) throws SQLException {
        Category u = new Category();
        u.setId(rs.getInt(1));
        u.setTitle(rs.getString(2));

        return u;
    }

    public void create(Category Category) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement("INSERT INTO categories (title) VALUES (?)");

            st.setString(1, Category.getTitle());

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

    public Category findById(int id) throws Exception {
        Category u = fetchOneFromSQL("SELECT * FROM categories WHERE id=?", new Object[]{
            id
        });

        return u;
    }

    public void update(Category category) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement("UPDATE categories SET title=? WHERE id=?");

            st.setString(1, category.getTitle());
            st.setInt(2, category.getId());

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
            st = conn.prepareStatement("DELETE FROM categories WHERE id=?");
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
