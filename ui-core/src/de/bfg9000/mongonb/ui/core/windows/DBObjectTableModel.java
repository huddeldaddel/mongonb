package de.bfg9000.mongonb.ui.core.windows;

import com.jidesoft.grid.HierarchicalTableModel;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;
import org.openide.util.NbBundle;

/**
 * This TableModel is used in the hierarchical table that displays the query results. It shows all properties of a 
 * DBObject.
 * 
 * @see http://www.jidesoft.com/products/JIDE_Grids_Developer_Guide.pdf
 * @author wernert
 */
class DBObjectTableModel extends AbstractTableModel implements HierarchicalTableModel {

    private static final ResourceBundle bundle = NbBundle.getBundle(PropertyObjectListTableModel.class);
    
    private final DBObject value;
    private final List<String> keys;
    
    public DBObjectTableModel(DBObject value) {
        this.value = value;
        keys = new ArrayList<String>(value.keySet());
        Collections.sort(keys);
    }
    
    @Override
    public int getRowCount() {
        return keys.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return bundle.getString("DBObjectTableModel.col" +Integer.toString(columnIndex));
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final String property = keys.get(rowIndex);        
        return 0 == columnIndex ? property : value.get(property);
    }

    @Override
    public boolean hasChild(int i) {
        for(String key: keys)
            if(value.get(key) instanceof DBObject)
                return true;
        return false;
    }

    @Override
    public boolean isHierarchical(int i) {
        return true;
    }

    @Override
    public Object getChildValueAt(int i) {
        final PropertyObjectList result = new PropertyObjectList();        
        for(String key: keys) {
            final Object o = value.get(key);
            if(o instanceof DBObject)
                result.add(new PropertyObject(key, (DBObject) o));
        }
        return result;
    }

    @Override
    public boolean isExpandable(int i) {
        return hasChild(i);
    }
    
}
