package de.bfg9000.mongonb.ui.core.dialogs;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * {@code TableCellRenderer} that displays a label.
 *
 * @author thomaswerner35
 */
class IndexManagerStringRenderer extends JLabel implements TableCellRenderer {

    private final Color tableBackground = UIManager.getDefaults().getColor("Table.background");
    private final Color tableDisabledBg = UIManager.getDefaults().getColor("ComboBox.disabledBackground");
    private final Color tableSelectionBg = UIManager.getDefaults().getColor("Table.selectionBackground");

    {
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                     int row, int column) {
        setBackground(isSelected ?
                      tableSelectionBg : table.getModel().isCellEditable(row, 1) ? tableBackground : tableDisabledBg);
        setText(null == value ? "*" : value.toString());
        return this;
    }

}
