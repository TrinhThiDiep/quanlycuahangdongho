/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import javax.swing.JFrame;

/**
 *
 * @author Computer
 */
public abstract class DataGridForm extends JFrame {
    
    /**
     * Cập nhật lại bảng dữ liệu
     */
    public void refresh() {
        refresh(true);
    }
    
    public void refresh(boolean forceReset) {}

}
