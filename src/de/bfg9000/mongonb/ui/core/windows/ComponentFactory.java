package de.bfg9000.mongonb.ui.core.windows;

import com.jidesoft.FitScrollPane;
import com.jidesoft.grid.HierarchicalTable;
import com.jidesoft.grid.HierarchicalTableComponentFactory;
import com.jidesoft.grid.TreeLikeHierarchicalPanel;
import com.mongodb.DBObject;
import java.awt.Component;

/**
 * Returns the objects that will be used on the hierarchical table.
 * 
 * @see http://www.jidesoft.com/products/JIDE_Grids_Developer_Guide.pdf
 * @author wernert
 */
class ComponentFactory implements HierarchicalTableComponentFactory {

    @Override
    public Component createChildComponent(HierarchicalTable ht, Object o, int i) {
        if(o instanceof PropertyObjectList) {
            final PropertyObjectList value = (PropertyObjectList) o;            
            final HierarchicalTable table = new HierarchicalTable(new PropertyObjectListTableModel(value));            
            table.setComponentFactory(this);                                    
            final TreeLikeHierarchicalPanel result = new TreeLikeHierarchicalPanel(new FitScrollPane(table));            
            result.setBackground(table.getMarginBackground());
            return result;
        }
        
        if(o instanceof DBObject) {
            final DBObject value = (DBObject) o;            
            final HierarchicalTable table = new HierarchicalTable(new DBObjectTableModel(value));
            table.setComponentFactory(this);                                    
            return new TreeLikeHierarchicalPanel(new FitScrollPane(table));            
        }
        
        return null;
    }

    @Override
    public void destroyChildComponent(HierarchicalTable ht, Component cmpnt, int i) { }
    
}
