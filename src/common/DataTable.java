/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author quangthinh
 */
public class DataTable extends DefaultTableModel {

    public DataTable(Object[][] data, String[] types) {
        super(data, types);
    }
    
    public boolean isCellEditable(int row, int column){  
        return false;  
    }
}
