package de.bfg9000.mongonb.ui.core.dialogs;

import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.core.Index;
import de.bfg9000.mongonb.core.Index.Key;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javax.swing.table.DefaultTableModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.openide.util.NbBundle;

/**
 * The {@code TableModel} used by the {@code JTable} of the {@code IndexManagerDialog}.
 *
 * @author thomaswerner35
 */
class IndexManagerTableModel extends DefaultTableModel {

    static enum KeySelection { None, Ascending, Descending };

    private static final ResourceBundle bundle = NbBundle.getBundle(IndexManagerTableModel.class);

    private final int NAME_COLUMN = 0;              // first column, just before the attributes
    private final int FIRST_ATTRIBUTE_COLUMN = 1;   // the first attribute column. other attributes follow.
    private final int SPARSE_COLUMN;                // after the attribute columns
    private final int UNIQUE_COLUMN;                // after the sparse column
    private final IndexManagerModel model;
    private final List<AttributeColumn> attributeColumns = new ArrayList<AttributeColumn>();

    IndexManagerTableModel(Collection collection, IndexManagerModel model) {
        for(Entry<String, Long> entry: collection.getFields().entrySet())
            attributeColumns.add(new AttributeColumn(entry.getKey(), entry.getValue()));
        Collections.sort(attributeColumns);
        SPARSE_COLUMN = attributeColumns.size() +1;
        UNIQUE_COLUMN = SPARSE_COLUMN +1;
        this.model = model;
        this.model.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                fireTableDataChanged();
            }
        });
    }

    /**
     * Returns the number of columns in this data table.
     * @return the number of columns in the model
     */
    @Override
    public int getColumnCount() {
        return UNIQUE_COLUMN +1;
    }

    /**
     * Returns the column name.
     * @return a name for this column.
     */
    @Override
    public String getColumnName(int column) {
        if(NAME_COLUMN == column)
            return bundle.getString("IndexManagerTableModel.column.name.name");
        if(SPARSE_COLUMN == column)
            return bundle.getString("IndexManagerTableModel.column.sparse.name");
        if(UNIQUE_COLUMN == column)
            return bundle.getString("IndexManagerTableModel.column.unqiue.name");
        return attributeColumns.get(column -FIRST_ATTRIBUTE_COLUMN).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(NAME_COLUMN == columnIndex)
            return String.class;
        if(SPARSE_COLUMN == columnIndex)
            return Boolean.class;
        if(UNIQUE_COLUMN == columnIndex)
            return Boolean.class;
        return KeySelection.class;
    }

    /**
     * Returns the number of rows in this data table.
     * @return the number of rows in the model
     */
    @Override
    public int getRowCount() {
        return null == model ? 0 : model.getIndexCount();
    }

    /**
     * Returns {@code true} if the cell can be edited.
     *
     * @param   row             the row whose value is to be queried
     * @param   column          the column whose value is to be queried
     * @return                  {@code true} if the cell can be edited.
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return model.isNew(row) && column != NAME_COLUMN;
    }

    /**
     * Returns an attribute value for the cell at <code>row</code> and <code>column</code>.
     *
     * @param   row             the row whose value is to be queried
     * @param   column          the column whose value is to be queried
     * @return                  the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int row, int column) {
        final Index index = model.getIndex(row);
        if(NAME_COLUMN == column)
            return index.getName();
        if(SPARSE_COLUMN == column)
            return index.isSparse();
        if(UNIQUE_COLUMN == column)
            return index.isUnique();
        final String attributeName = attributeColumns.get(column -1).getName();
        for(Key key: index.getKeys())
            if(key.getColumn().equals(attributeName))
                return key.isOrderedAscending() ? KeySelection.Ascending : KeySelection.Descending;
        return KeySelection.None;
    }

    /**
     * Sets the object value for the cell at <code>column</code> and <code>row</code>. <code>aValue</code> is the new
     * value. This method will generate a <code>tableChanged</code> notification.
     *
     * @param   aValue          the new value; this can be null
     * @param   row             the row whose value is to be changed
     * @param   column          the column whose value is to be changed
     * @exception  ArrayIndexOutOfBoundsException  if an invalid row or column was given
     */
    @Override
    public void setValueAt(Object aValue, int row, int column) {
        final Index index = model.getIndex(row);
        if(NAME_COLUMN == column)
            return;
        if(SPARSE_COLUMN == column) {
            index.setSparse(Boolean.TRUE.equals(aValue));
            return;
        }
        if(UNIQUE_COLUMN == column) {
            index.setUnqiue(Boolean.TRUE.equals(aValue));
            return;
        }

        final String attributeName = attributeColumns.get(column -1).getName();
        boolean handled = false;
        for(Key key: index.getKeys())
            if(key.getColumn().equals(attributeName)) {
                if(KeySelection.None.equals(aValue)) {
                    index.removeKey(key);
                    handled = true;
                    break;
                } else if(KeySelection.Ascending.equals(aValue)) {
                    key.setOrderAscending(true);
                    handled = true;
                    break;
                } else if(KeySelection.Descending.equals(aValue)) {
                    key.setOrderAscending(false);
                    handled = true;
                    break;
                }
            }
        if(!handled)
            index.addKey(attributeName, KeySelection.Ascending.equals(aValue));
        fireTableCellUpdated(row, column);
    }

    @AllArgsConstructor @ToString
    private static final class AttributeColumn implements Comparable<AttributeColumn> {

        @Getter private final String name;
        @Getter private final Long usages;

        /**
         * Used to sort {@code AttributeColumn}s. The "_id" column is the first column. All others will be sorted
         * alphabetically.
         *
         * @param o the {@code AttributeColumn} to be compared with this one.
         * @return the result as defined by the {@code Comparable} interface
         */
        @Override
        public int compareTo(AttributeColumn o) {
            if("_id".equals(this.name))
                return "_id".equals(o.name) ? 0 : -1;
            return name.compareTo(o.name);
        }

    }

}
