package de.bfg9000.mongonb.ui.core.windows;

import com.jidesoft.grid.HierarchicalTableModel;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;
import lombok.AllArgsConstructor;
import org.openide.util.NbBundle;

/**
 * This TableModel is used in the hierarchical table that displays the query results. It shows all properties of a 
 * DBObject that have other DBObjects as value.
 * 
 * @see http://www.jidesoft.com/products/JIDE_Grids_Developer_Guide.pdf
 * @author wernert
 */
@AllArgsConstructor
class PropertyObjectListTableModel extends AbstractTableModel implements HierarchicalTableModel {

    private static final ResourceBundle bundle = NbBundle.getBundle(PropertyObjectListTableModel.class);
    
    private final PropertyObjectList propertyObjectList;
    
    @Override
    public int getRowCount() {
        return propertyObjectList.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return bundle.getString("PropertyObjectListTableModel.col" +Integer.toString(columnIndex));
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final PropertyObject propertyObject = propertyObjectList.get(rowIndex);
        return 0 == columnIndex ? propertyObject.getProperty() : propertyObject.getObject();
    }

    @Override
    public boolean hasChild(int i) {
        return true;
    }

    @Override
    public boolean isHierarchical(int i) {
        return true;
    }

    @Override
    public Object getChildValueAt(int i) {
        return propertyObjectList.get(i).getObject();
    }

    @Override
    public boolean isExpandable(int i) {
        return true;
    }
    
}
