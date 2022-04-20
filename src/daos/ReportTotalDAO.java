/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import btql.watchman.DlgCreate;
import dtos.User;
import dtos.Category;
import dtos.ReportTotal;
import dtos.Vendor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class ReportTotalDAO extends AbstractDAO<ReportTotal> {

    ReportTotalDAO() {

    }

    public static ReportTotalDAO instance = new ReportTotalDAO();

    public List<ReportTotal> findAll(String query) throws SQLException {

        String condition = " WHERE 1=1";
        List<Object> x = new ArrayList();

        if (query != null) {
            if (! query.equals("")) {
                condition += " AND (p.title LIKE ? OR c.title LIKE ? OR v.title LIKE ?)";
                x.add("%" + query + "%");
                x.add("%" + query + "%");
                x.add("%" + query + "%");
            }
        }

        String sql = "SELECT "
                + "DISTINCT p.id, p.title productTitle, c.title productCategory, v.title productVendor, p.unitPrice, p.quantity,"
                + "SUM(od.quantity) soldQuantity,"
                + "SUM(od.quantity * od.unitPrice) subTotal"
                + " FROM watches p "
                + " INNER JOIN categories c ON c.id = p.categoryId"
                + " INNER JOIN vendors v ON v.id = p.vendorId"
                + " LEFT JOIN orderdetails od ON od.watchId = p.id"
                + condition
                + " GROUP BY p.id ORDER BY p.title ASC";

        return fetchAllFromSQL(sql, x.toArray());
    }

    @Override
    protected ReportTotal resultSetToDTO(ResultSet rs) throws SQLException {
        ReportTotal u = new ReportTotal();
        u.setId(rs.getInt("id"));
        u.setProductTitle(rs.getString("productTitle"));
        u.setProductCategory(rs.getString("productCategory"));
        u.setProductVendor(rs.getString("productVendor"));
        u.setUnitPrice(rs.getInt("unitPrice"));
        u.setQuantity(rs.getInt("quantity"));
        u.setSoldQuantity(rs.getInt("soldQuantity"));
        u.setSubTotal(rs.getInt("subTotal"));

        return u;
    }
}
