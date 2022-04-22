/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import btql.watchman.DlgCreate;
import dtos.User;
import dtos.Vendor;
import dtos.Watch;
import dtos.WatchView;
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
public class WatchDAO extends AbstractDAO<Watch> {

    class WatchViewDAO extends AbstractDAO<WatchView> {

        @Override
        protected WatchView resultSetToDTO(ResultSet rs) throws SQLException {
            WatchView u = new WatchView();
            u.setId(rs.getInt(1));
            u.setTitle(rs.getString(2));
            u.setUsername(rs.getString(3));
            u.setUnitPrice(rs.getInt(4));
            u.setQuantity(rs.getInt(5));
            u.setVendorTitle(rs.getString(6));
            u.setCategoryTitle(rs.getString(7));

            return u;
        }

        public List<WatchView> findAll(String query) throws SQLException {
            if (query == null || "".equals(query)) {
                return watchViewDAO.fetchAllFromSQL(
                        "SELECT w.id id, w.title title, u.username username, w.unitPrice unitPrice, w.quantity quantity, v.title vendorTitle, c.title categoryTitle "
                        + "FROM watches w "
                        + "INNER JOIN users u ON w.userId=u.id "
                        + "INNER JOIN vendors v ON w.vendorId=v.id "
                        + "INNER JOIN categories c ON w.categoryId=c.id");
            }

            return fetchAllFromSQL("SELECT w.id id, w.title title, u.username username, w.unitPrice unitPrice, w.quantity quantity, v.title vendorTitle, c.title categoryTitle "
                    + "FROM watches w "
                    + "INNER JOIN users u ON w.userId=u.id "
                    + "INNER JOIN vendors v ON w.vendorId=v.id "
                    + "INNER JOIN categories c ON w.categoryId=c.id "
                    + "WHERE w.title LIKE ? OR w.description LIKE ? OR v.title LIKE ? OR c.title LIKE ?", new Object[]{
                        "%" + query + "%",
                        "%" + query + "%",
                        "%" + query + "%",
                        "%" + query + "%",});
        }

        private List<WatchView> findAll(String query, boolean outOfStockOnly, boolean sapHetHang) throws SQLException {
            String additionCondition = "";
            
            if (outOfStockOnly && !sapHetHang) {
                additionCondition += " AND(quantity = 0)";
            }
            
            if (outOfStockOnly && sapHetHang) {
                additionCondition += " AND (quantity < 10)";
            }
            
            if (!outOfStockOnly && sapHetHang) {
                additionCondition += " AND (quantity > 0 && quantity < 10)";
            }
            
            if (query == null || "".equals(query)) {
                return watchViewDAO.fetchAllFromSQL(
                        "SELECT w.id id, w.title title, u.username username, w.unitPrice unitPrice, w.quantity quantity, v.title vendorTitle, c.title categoryTitle "
                        + "FROM watches w "
                        + "INNER JOIN users u ON w.userId=u.id "
                        + "INNER JOIN vendors v ON w.vendorId=v.id "
                        + "INNER JOIN categories c ON w.categoryId=c.id "
                        + "WHERE 1=1" + additionCondition
                        + " ORDER BY unitPrice DESC"
                );
            }

            return fetchAllFromSQL("SELECT w.id id, w.title title, u.username username, w.unitPrice unitPrice, w.quantity quantity, v.title vendorTitle, c.title categoryTitle "
                    + "FROM watches w "
                    + "INNER JOIN users u ON w.userId=u.id "
                    + "INNER JOIN vendors v ON w.vendorId=v.id "
                    + "INNER JOIN categories c ON w.categoryId=c.id "
                    + "WHERE 1=1" + additionCondition
                    + " AND (w.title LIKE ? OR w.description LIKE ? OR v.title LIKE ? OR c.title LIKE ?) ORDER BY unitPrice DESC", new Object[]{
                        "%" + query + "%",
                        "%" + query + "%",
                        "%" + query + "%",
                        "%" + query + "%",});
        }
    }

    private WatchViewDAO watchViewDAO;

    WatchDAO() {
        watchViewDAO = new WatchViewDAO();
    }

    public static WatchDAO instance = new WatchDAO();

    public List<WatchView> findAll(String query) throws SQLException {
        return watchViewDAO.findAll(query);
    }

    public List<WatchView> findAll(String query, boolean outOfStockOnly, boolean sapHetHang) throws SQLException {
        return watchViewDAO.findAll(query, outOfStockOnly, sapHetHang);
    }

    public ComboBoxModel<WatchView> buildComboBoxModel(boolean includeNoSelect) {
        try {
            List<WatchView> watches = WatchDAO.instance.findAll(null);
            if (includeNoSelect) {
                Vector<WatchView> v = new Vector<>();
                WatchView m = new WatchView();
                m.setTitle("-- chưa chọn --");
                v.add(m);
                v.addAll(watches);
                DefaultComboBoxModel model = new DefaultComboBoxModel<>(v);
                return model;
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel<>(new Vector<>(watches));
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(DlgCreate.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new DefaultComboBoxModel<>();
    }

    @Override
    protected Watch resultSetToDTO(ResultSet rs) throws SQLException {
        Watch u = new Watch();
        u.setId(rs.getInt(1));
        u.setTitle(rs.getString(2));
        u.setDescription(rs.getString(3));
        u.setUserId(rs.getInt(4));
        u.setUnitPrice(rs.getInt(5));
        u.setQuantity(rs.getInt(6));
        u.setVendorId(rs.getInt(7));
        u.setCategoryId(rs.getInt(8));

        return u;
    }

    public void create(Watch watch) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement("INSERT INTO watches (title, description, userId, unitPrice, quantity, vendorId, categoryId) VALUES (?, ?, ?, ?, ?, ?, ?)");

            st.setString(1, watch.getTitle());
            st.setString(2, watch.getDescription());
            st.setInt(3, watch.getUserId());
            st.setInt(4, watch.getUnitPrice());
            st.setInt(5, watch.getQuantity());
            st.setInt(6, watch.getVendorId());
            st.setInt(7, watch.getCategoryId());

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

    public Watch findById(int id) throws Exception {
        Watch u = fetchOneFromSQL("SELECT * FROM watches WHERE id=?", new Object[]{
            id
        });

        return u;
    }

    public void update(Watch watch) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement("UPDATE watches SET title=?, description=?, userId=?, unitPrice=?, quantity=?, vendorId=?, categoryId=? WHERE id=?");

            st.setString(1, watch.getTitle());
            st.setString(2, watch.getDescription());
            st.setInt(3, watch.getUserId());
            st.setInt(4, watch.getUnitPrice());
            st.setInt(5, watch.getQuantity());
            st.setInt(6, watch.getVendorId());
            st.setInt(7, watch.getCategoryId());
            st.setInt(8, watch.getId());

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
            st = conn.prepareStatement("DELETE FROM watches WHERE id=?");
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
