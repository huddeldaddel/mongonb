package de.bfg9000.mongonb.ui.core.windows;

import com.jidesoft.FitScrollPane;
import com.jidesoft.grid.HierarchicalTable;
import com.jidesoft.grid.HierarchicalTableComponentFactory;
import com.jidesoft.grid.TreeLikeHierarchicalPanel;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Returns the objects that will be used on the hierarchical table.
 *
 * @see http://www.jidesoft.com/products/JIDE_Grids_Developer_Guide.pdf
 * @author thomaswerner35
 */
class ComponentFactory implements HierarchicalTableComponentFactory {

    private final TableContextMenuFactory menuFactory = new TableContextMenuFactory();

    @Override
    public Component createChildComponent(HierarchicalTable ht, Object o, int i) {
        if(o instanceof PropertyObjectList) {
            final PropertyObjectList value = (PropertyObjectList) o;
            final HierarchicalTable table = new HierarchicalTable(new PropertyObjectListTableModel(value));
            table.setComponentFactory(this);
            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting())
                        table.setComponentPopupMenu(menuFactory.buildContextMenu(getSelectedItem(table)));
                }
                private DBObject getSelectedItem(HierarchicalTable table) {
                    if(-1 == table.getSelectedRow())
                        return null;
                    final PropertyObject pObject = value.get(table.getSelectedRow());
                    return new BasicDBObject(pObject.getProperty(), pObject.getObject());
                }
            });
            final TreeLikeHierarchicalPanel result = new TreeLikeHierarchicalPanel(new FitScrollPane(table));
            result.setBackground(table.getMarginBackground());
            return result;
        }

        if(o instanceof DBObject) {
            final DBObject value = (DBObject) o;
            final HierarchicalTable table = new HierarchicalTable(new DBObjectTableModel(value));
            table.setComponentFactory(this);
            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting())
                        table.setComponentPopupMenu(menuFactory.buildContextMenu(getSelectedItem(table)));
                }
                private DBObject getSelectedItem(HierarchicalTable table) {
                    if(-1 == table.getSelectedRow())
                        return null;
                    final List<String> keys = new ArrayList<String>(value.keySet());
                    Collections.sort(keys);
                    final String property = keys.get(table.getSelectedRow());
                    return new BasicDBObject(property, value.get(property));
                }
            });
            return new TreeLikeHierarchicalPanel(new FitScrollPane(table));
        }

        return null;
    }

    @Override
    public void destroyChildComponent(HierarchicalTable ht, Component cmpnt, int i) { }

}
