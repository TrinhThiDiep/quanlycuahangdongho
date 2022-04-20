/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import btql.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Computer
 */
public abstract class AbstractDAO<T> {

    AbstractDAO() {

    }

    protected Connection getConnection() throws SQLException {
        return Config.createConnection();
    }

    protected void closeConnection(Connection connection) throws SQLException {
        Config.closeConnection(connection);
    }

    abstract protected T resultSetToDTO(ResultSet rs) throws SQLException;

    public T fetchOneFromSQL(String sql, Object[] params) throws SQLException {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement st = null;
        T n = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; ++i) {
                int paramIndex = i + 1;
                if (params[i] instanceof Integer) {
                    st.setInt(paramIndex, (int) params[i]);
                } else if (params[i] instanceof Double) {
                    st.setDouble(paramIndex, (double) params[i]);
                } else if (params[i] instanceof Boolean) {
                    st.setBoolean(paramIndex, (boolean) params[i]);
                } else {
                    st.setString(paramIndex, (String) params[i]);
                }
            }

            rs = st.executeQuery();

            if (rs.next()) {
                n = resultSetToDTO(rs);
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

        return n;
    }

    public T fetchOneFromSQL(String sql) throws SQLException {
        return fetchOneFromSQL(sql, new Object[]{});
    }

    public List<T> fetchAllFromSQL(String sql, Object[] params) throws SQLException {
        Connection conn = getConnection();
        List<T> list = new ArrayList<>();

        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; ++i) {
                int paramIndex = i + 1;
                if (params[i] instanceof Integer) {
                    st.setInt(paramIndex, (int) params[i]);
                } else if (params[i] instanceof Double) {
                    st.setDouble(paramIndex, (double) params[i]);
                } else if (params[i] instanceof Boolean) {
                    st.setBoolean(paramIndex, (boolean) params[i]);
                } else {
                    st.setString(paramIndex, (String) params[i]);
                }
            }

            rs = st.executeQuery();

            while (rs.next()) {
                T n = resultSetToDTO(rs);
                list.add(n);
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
        return list;
    }

    public List<T> fetchAllFromSQL(String sql) throws SQLException {
        return fetchAllFromSQL(sql, new Object[]{});
    }
}
