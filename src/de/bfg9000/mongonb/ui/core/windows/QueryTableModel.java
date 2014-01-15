package de.bfg9000.mongonb.ui.core.windows;

import com.jidesoft.grid.HierarchicalTableModel;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
import lombok.Getter;

/**
 * The {@code TableModel} of the result table.
 *
 * @see http://www.jidesoft.com/products/JIDE_Grids_Developer_Guide.pdf
 * @author thomaswerner35
 */
class QueryTableModel extends AbstractTableModel implements HierarchicalTableModel {

    private final List<DBObject> data;
    private final List<String> columns;
    private final int offset;
    @Getter private final int rowCount;
    @Getter private final int columnCount;

    public QueryTableModel(DataCache dataCache) {
        data = dataCache.getContent();
        columns = prepareColumns();
        offset = dataCache.getWindowPosition();

        rowCount = data.size();
        columnCount = columns.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(0 == columnIndex)
            return offset +1 +rowIndex;

        final DBObject document = data.get(rowIndex);
        final Object result = document.get(columns.get(columnIndex));

        if(result instanceof Object[]) {
            final BasicDBList temp = new BasicDBList();
            temp.addAll(Arrays.asList((Object[]) result));
            return temp;
        }
        return result;
    }

    @Override
    public boolean hasChild(int i) {
        final DBObject document = data.get(i);
        for(String key: columns)
            if((document.get(key) instanceof DBObject) || (document.get(key) instanceof Object[]))
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
        final DBObject document = data.get(i);
        for(String key: columns) {
            final Object value = document.get(key);
            if(value instanceof DBObject)
                result.add(new PropertyObject(key, (DBObject) value));
            if(value instanceof Object[]) {
                final BasicDBList temp = new BasicDBList();
                temp.addAll(Arrays.asList((Object[]) value));
                result.add(new PropertyObject(key, temp));
            }
        }
        return result;
    }

    @Override
    public boolean isExpandable(int i) {
        return hasChild(i);
    }

    private List<String> prepareColumns() {
        final Set<String> set = new LinkedHashSet<String>();
        for(DBObject dbo: data)
            set.addAll(dbo.keySet());
        final List<String> result = new ArrayList<String>(set);
        result.add(0, "#");
        return result;
    }

}
