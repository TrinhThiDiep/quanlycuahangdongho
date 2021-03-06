/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btql.orderman;

import btql.*;
import common.DataGridForm;
import common.DataTableEditable;
import common.SpinnerEditor;
import daos.OrderDAO;
import daos.WatchDAO;
import dtos.Order;
import dtos.OrderDetail;
import dtos.WatchView;
import java.awt.event.ItemEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Computer
 */
public class DlgCreate extends javax.swing.JDialog {

    /**
     * Creates new form DlgCreate
     */
    public DlgCreate(DataGridForm parent, boolean modal) {
        super(parent, modal);
        initComponents();

        setLocationRelativeTo(null);

        tblDataGrid.setModel(new DataTableEditable(
                new Object[][]{},
                new String[]{
                    "ProductID",
                    "Tên sản phẩm",
                    "Số lượng",
                    "Đơn giá",
                    "Tạm tính"
                }
        ));

        JComboBox cbWatch = new JComboBox<WatchView>();
        ComboBoxModel<WatchView> watchComboBoxModel = WatchDAO.instance.buildComboBoxModel(true);
        cbWatch.setModel(watchComboBoxModel);
        cbWatch.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                WatchView wv = (WatchView) watchComboBoxModel.getSelectedItem();
                int selectedIndex = tblDataGrid.getSelectedRow();
                tblDataGrid.getModel().setValueAt(wv.getId(), selectedIndex, 0);

                Object quantity = tblDataGrid.getModel().getValueAt(selectedIndex, 2);
                if (quantity == null) {
                    tblDataGrid.getModel().setValueAt(1, selectedIndex, 2);
                    tblDataGrid.getModel().setValueAt(wv.getUnitPrice(), selectedIndex, 3);
                    tblDataGrid.getModel().setValueAt(wv.getUnitPrice(), selectedIndex, 4);
                } else {
                    tblDataGrid.getModel().setValueAt(wv.getUnitPrice(), selectedIndex, 3);
                    tblDataGrid.getModel().setValueAt(wv.getUnitPrice() * (int) tblDataGrid.getModel().getValueAt(selectedIndex, 2), selectedIndex, 4);
                }
            }
        });

        tblDataGrid.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cbWatch));
        JSpinner spQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        spQuantity.addChangeListener((ChangeEvent e) -> {
            WatchView wv = (WatchView) watchComboBoxModel.getSelectedItem();
            int selectedIndex = tblDataGrid.getSelectedRow();
            tblDataGrid.getModel().setValueAt(wv.getId(), selectedIndex, 0);

            Object quantity = ((JSpinner) e.getSource()).getValue();
            
            if (quantity == null) {
                tblDataGrid.getModel().setValueAt(1, selectedIndex, 2);
                tblDataGrid.getModel().setValueAt(wv.getUnitPrice(), selectedIndex, 3);
                tblDataGrid.getModel().setValueAt(wv.getUnitPrice(), selectedIndex, 4);
            } else {
                int newQuantity = (int) quantity;
                if (wv.getQuantity() < newQuantity) {
                    newQuantity = wv.getQuantity();
                    ((JSpinner) e.getSource()).setValue(newQuantity);
                }
                
                tblDataGrid.getModel().setValueAt(wv.getUnitPrice(), selectedIndex, 3);
                tblDataGrid.getModel().setValueAt(wv.getUnitPrice() * (int) newQuantity, selectedIndex, 4);
            }
        });
        tblDataGrid.getColumnModel().getColumn(2).setCellEditor(new SpinnerEditor<JSpinner>(spQuantity));

        tblDataGrid.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.UPDATE || e.getType() == TableModelEvent.DELETE) {
                    DlgCreate.this.updateTotalPrice();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtCustomerAddress = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataGrid = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        lbSubTotal = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lập hoá đơn");
        setAlwaysOnTop(true);
        setResizable(false);

        jLabel1.setText("Tên khách hàng");

        btnReset.setText("Xóa");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnSubmit.setText("Thêm");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jLabel2.setText("Địa chỉ");

        tblDataGrid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDataGrid.setRowHeight(30);
        tblDataGrid.setShowGrid(true);
        jScrollPane1.setViewportView(tblDataGrid);

        jLabel3.setText("Chi tiết đơn hàng");

        lbSubTotal.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        lbSubTotal.setForeground(new java.awt.Color(255, 0, 0));
        lbSubTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbSubTotal.setText("12345678");

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Tạm tính");

        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setText("Xoá");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCustomerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdd)
                        .addGap(2, 2, 2)
                        .addComponent(btnDelete))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnReset)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnSubmit))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(lbSubTotal))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 786, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCustomerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbSubTotal)
                    .addComponent(jLabel5))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnSubmit))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        ((DefaultTableModel) tblDataGrid.getModel()).setRowCount(0);
    }//GEN-LAST:event_btnResetActionPerformed

    private List<String> getFormErrors() throws SQLException {
        List<String> errors = new ArrayList();
        if (txtCustomerName.getText().equals("")) {
            errors.add("Tên khách hàng không được bỏ trống");
        }

        if (txtCustomerAddress.getText().equals("")) {
            errors.add("Địa chỉ không được bỏ trống");
        }

        if (tblDataGrid.getModel().getRowCount() == 0) {
            errors.add("Chưa nhập sản phẩm");
        }

        return errors;
    }

    /**
     * Cập nhật vào CSDL & đóng form
     *
     * @param evt
     */
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        try {
            List<String> errors = getFormErrors();
            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(this, String.join("\n", errors), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Order order = new Order();
            order.setCustomerName(txtCustomerName.getText());
            order.setCustomerAddress(txtCustomerAddress.getText());
            order.setUserId(Config.getCurrentUserId());
            order.setCreatedAt(Date.valueOf(LocalDate.now()));

            // create detail ...
            HashMap<Integer, OrderDetail> map = new HashMap<>();

            for (int i = 0; i < tblDataGrid.getModel().getRowCount(); ++i) {
                WatchView wv = (WatchView) tblDataGrid.getModel().getValueAt(i, 1);
                int quantity = (int) tblDataGrid.getModel().getValueAt(i, 2);

                if (!map.containsKey(wv.getId())) {
                    map.put(wv.getId(), new OrderDetail());
                }

                OrderDetail d = map.get(wv.getId());
                d.setQuantity(d.getQuantity() + quantity);
                d.setUnitPrice(wv.getUnitPrice());
                d.setWatchId(wv.getId());
            }

            // validate detail
            order.setOrderDetails(new ArrayList(map.values()));

            OrderDAO.instance.create(order);
            ((DataGridForm) getOwner()).refresh(true);

            // create
            // update DB
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(DlgCreate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSubmitActionPerformed


    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        DefaultTableModel table = (DefaultTableModel) tblDataGrid.getModel();
        table.addRow(new Object[]{
            "",
            "",
            1,
            0,
            0
        });
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int selectId = tblDataGrid.getSelectedRow();
        if (selectId == -1) {
            return;
        }

        DefaultTableModel table = (DefaultTableModel) tblDataGrid.getModel();
        table.removeRow(selectId);
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbSubTotal;
    private javax.swing.JTable tblDataGrid;
    private javax.swing.JTextField txtCustomerAddress;
    private javax.swing.JTextField txtCustomerName;
    // End of variables declaration//GEN-END:variables

    private void updateTotalPrice() {
        DefaultTableModel model = (DefaultTableModel) tblDataGrid.getModel();

        int total = 0;
        for (int i = 0; i < model.getRowCount(); ++i) {
            int subTotal = (int) model.getValueAt(i, 3) * (int) model.getValueAt(i, 2);
            total += subTotal;
        }

        lbSubTotal.setText(Integer.toString(total) + " VND");
    }
}
