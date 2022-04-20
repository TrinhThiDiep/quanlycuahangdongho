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
public class DataTableEditable extends DefaultTableModel {

    public DataTableEditable(Object[][] data, String[] types) {
        super(data, types);
    }
}
