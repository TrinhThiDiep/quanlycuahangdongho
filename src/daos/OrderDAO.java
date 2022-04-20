/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import btql.Config;
import dtos.Category;
import dtos.Order;
import dtos.OrderDetail;
import dtos.OrderView;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Computer
 */
public class OrderDAO extends AbstractDAO<Order> {

    class OrderViewDAO extends AbstractDAO<OrderView> {

        @Override
        protected OrderView resultSetToDTO(ResultSet rs) throws SQLException {
            OrderView u = new OrderView();
            u.setId(rs.getInt(1));
            u.setCustomerName(rs.getString(2));
            u.setCustomerAddress(rs.getString(3));
            u.setUsername(rs.getString(4));
            u.setCreatedAt(rs.getDate(5));
            u.setTotal(rs.getInt(6));

            return u;
        }

        public List<OrderView> findAll(String query) throws SQLException {
            if (query == null || "".equals(query)) {
                return orderViewDAO.fetchAllFromSQL(
                        "SELECT id, customerName, customerAddress, (SELECT username FROM users WHERE id=orders.userId) username, createdAt, (SELECT SUM(quantity * unitPrice) FROM orderdetails WHERE orderId=orders.id) total FROM orders");
            }

            return fetchAllFromSQL("SELECT id, customerName, customerAddress, (SELECT username FROM users WHERE id=orders.userId) username, createdAt, (SELECT SUM(quantity * unitPrice) FROM orderdetails WHERE orderId=orders.id) total "
                    + "FROM orders"
                    + " WHERE customerName LIKE ? OR customerAddress LIKE ?", new Object[]{
                        "%" + query + "%",
                        "%" + query + "%",});
        }
    }

    class OrderDetailDAO extends AbstractDAO<OrderDetail> {

        @Override
        protected OrderDetail resultSetToDTO(ResultSet rs) throws SQLException {
            OrderDetail d = new OrderDetail();
            d.setOrderId(rs.getInt(1));
            d.setWatchTitle(rs.getString(2));
            d.setWatchId(rs.getInt(3));
            d.setQuantity(rs.getInt(4));
            d.setUnitPrice(rs.getInt(5));
            return d;
        }

        public List<OrderDetail> findAllByOrderId(int orderId) throws SQLException {
            return fetchAllFromSQL("SELECT d.orderId, w.title watchTitle, d.watchId, d.quantity, d.unitPrice FROM orderdetails d INNER JOIN watches w ON d.watchId=w.id WHERE orderId=?", 
                    new Object[] {
                orderId
            });
        }
    }

    private OrderViewDAO orderViewDAO;
    private OrderDetailDAO orderDetailDAO;

    OrderDAO() {
        orderViewDAO = new OrderViewDAO();
        orderDetailDAO = new OrderDetailDAO();
    }

    public static OrderDAO instance = new OrderDAO();

    public List<OrderView> findAll(String query) throws SQLException {
        return orderViewDAO.findAll(query);
    }
    
    public List<OrderDetail> findAllByOrderId(int orderId) throws SQLException {
        return orderDetailDAO.findAllByOrderId(orderId);
    }

    @Override
    protected Order resultSetToDTO(ResultSet rs) throws SQLException {
        Order u = new Order();
        u.setId(rs.getInt("id"));
        u.setCustomerName(rs.getString("customerName"));
        u.setCustomerAddress(rs.getString("customerAddress"));
        u.setCreatedAt(rs.getDate("createdAt"));
        u.setUserId(rs.getInt("userId"));

        return u;
    }

    public void create(Order order) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            st = conn.prepareStatement("INSERT INTO orders (customerName, customerAddress, createdAt, userId) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, order.getCustomerName());
            st.setString(2, order.getCustomerAddress());
            st.setDate(3, Date.valueOf(LocalDate.now()));
            st.setInt(4, Config.getCurrentUserId());

            st.executeUpdate();

            ResultSet resultSet = st.getGeneratedKeys();
            if (resultSet.next()) {
                int lastInsertedId = resultSet.getInt(1);

                List<OrderDetail> details = order.getOrderDetails();
                if (details != null) {
                    PreparedStatement pst = conn.prepareStatement("INSERT INTO orderdetails (orderId,watchId,unitPrice,quantity) VALUES (?, ?, ?, ?)");
                    for (OrderDetail detail : details) {
                        // insert an element ...
                        pst.setInt(1, lastInsertedId);
                        pst.setInt(2, detail.getWatchId());
                        pst.setInt(3, detail.getUnitPrice());
                        pst.setInt(4, detail.getQuantity());

                        pst.execute();

                        PreparedStatement pst2 = conn.prepareStatement("UPDATE watches SET quantity = quantity - ? WHERE id=?");
                        pst2.setInt(1, detail.getQuantity());
                        pst2.setInt(2, detail.getWatchId());
                        pst2.execute();
                    }
                }

            }

            conn.commit();
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

    public Order findById(int id) throws Exception {
        Order u = fetchOneFromSQL("SELECT * FROM orders WHERE id=?", new Object[]{
            id
        });

        return u;
    }

    /**
     * Update thông tin hoá đơn
     *
     * @param order
     * @throws SQLException
     */
    public void update(Order order) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement("UPDATE orders SET customerName=?, customerAddress, userId=? WHERE id=?");

            st.setString(1, order.getCustomerName());
            st.setString(2, order.getCustomerAddress());
            st.setInt(3, Config.getCurrentUserId());
            st.setInt(4, order.getId());

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
            st = conn.prepareStatement("DELETE FROM orders WHERE id=?");
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
