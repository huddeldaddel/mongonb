package de.bfg9000.mongonb.ui.core.services;

import de.bfg9000.mongonb.core.Collection;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Displays a {@code Collection} in the Services window.
 * 
 * @author wernert
 */
class CollectionNode extends AbstractNode {
    
    public static final String ICON = "de/bfg9000/mongonb/ui/core/images/table.png";       
        
    private final Collection collection;
    
    public CollectionNode(Collection collection) {
        super(Children.LEAF);        
        this.collection = collection;
        
        setIconBaseWithExtension(ICON);        
        setName(collection.getName());        
    }
    
    @Override
    public Action[] getActions(boolean context) {
        return new Action[] { 
            
        };        
    }
    
}
