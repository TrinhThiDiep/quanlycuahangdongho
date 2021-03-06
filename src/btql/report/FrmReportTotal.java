/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btql.report;

import btql.Config;
import btql.categoryman.*;
import btql.categoryman.*;
import common.DataGridForm;
import common.DataTable;
import daos.CategoryDAO;
import daos.ReportTotalDAO;
import dtos.Category;
import dtos.ReportTotal;
import dtos.Vendor;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Computer
 */
public class FrmReportTotal extends DataGridForm {

    /**
     * Creates new form FrmCategoryManage
     */
    public FrmReportTotal() {
        initComponents();
        setLocationRelativeTo(null);

        txtSearchQuery.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refresh();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refresh();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refresh();
            }
        });

        tblDataGrid.setModel(new DataTable(
                new Object[][]{
                    {null, null, null}
                },
                new String[]{
                    "ID", 
                    "Tên đồng hồ",
                    "Danh mục",
                    "Nhà sản xuất",
                    "Đơn giá",
                    "Tồn",
                    "Đã bán",
                    "Doanh thu"
                }
        ));
        
        try {
            lbDoanhThu.setText(Config.queryRaw("SELECT SUM(unitPrice * quantity) FROM orderdetails") + " VND");
        } catch (SQLException ex) {
            Logger.getLogger(FrmReportTotal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataGrid = new javax.swing.JTable();
        txtSearchQuery = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbDoanhThu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Báo cáo doanh thu");

        tblDataGrid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "ID", "Username", "Owner"
            }
        ));
        tblDataGrid.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDataGrid.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                tblDataGridCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        jScrollPane1.setViewportView(tblDataGrid);

        txtSearchQuery.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                txtSearchQueryCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        jLabel1.setText("Tìm kiếm");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setText("Doanh thu");

        lbDoanhThu.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        lbDoanhThu.setForeground(new java.awt.Color(255, 0, 0));
        lbDoanhThu.setText("Doanh thu");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lbDoanhThu)
                .addGap(409, 409, 409)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchQuery, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchQuery, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(lbDoanhThu))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    int getCurrentSelected() {
        // row selected
        int selectId = tblDataGrid.getSelectedRow();
        if (selectId == -1) {
            return -1;
        }

        int id = Integer.valueOf(tblDataGrid.getModel().getValueAt(selectId, 0).toString());

        return id;
    }

    private void tblDataGridCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tblDataGridCaretPositionChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDataGridCaretPositionChanged

    private void txtSearchQueryCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtSearchQueryCaretPositionChanged

    }//GEN-LAST:event_txtSearchQueryCaretPositionChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmReportTotal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmReportTotal().setVisible(true);
            }
        });
    }

    public void load() throws Exception {
        refresh();
        setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbDoanhThu;
    private javax.swing.JTable tblDataGrid;
    private javax.swing.JTextField txtSearchQuery;
    // End of variables declaration//GEN-END:variables

    @Override
    public void refresh(boolean reset) {
        try {
            List<ReportTotal> items = ReportTotalDAO.instance.findAll(txtSearchQuery.getText());

            DefaultTableModel model = (DefaultTableModel) tblDataGrid.getModel();
            model.setRowCount(0);

            for (ReportTotal n : items) {
                Object dataRow[] = new Object[8];
                dataRow[0] = n.getId();
                dataRow[1] = n.getProductTitle();
                dataRow[2] = n.getProductCategory();
                dataRow[3] = n.getProductVendor();
                dataRow[4] = n.getUnitPrice();
                dataRow[5] = n.getQuantity();
                dataRow[6] = n.getSoldQuantity();
                dataRow[7] = n.getSubTotal();
                model.addRow(dataRow);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgCreate.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
