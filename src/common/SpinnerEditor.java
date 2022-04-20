/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author quangthinh
 */
public class SpinnerEditor<T extends JSpinner> extends AbstractCellEditor implements TableCellEditor {

    private final T spinner;

    public SpinnerEditor(T spinner) {
        this.spinner = spinner;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
            int row, int column) {
        spinner.setValue(value);
        return spinner;
    }

    public boolean isCellEditable(EventObject evt) {
        if (evt instanceof MouseEvent) {
            return ((MouseEvent) evt).getClickCount() >= 2;
        }
        return true;
    }

    public Object getCellEditorValue() {
        return spinner.getValue();
    }
}
