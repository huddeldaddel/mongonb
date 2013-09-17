package de.bfg9000.mongonb.ui.core.services;

import de.bfg9000.mongonb.core.Database;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Displays a {@code Database} in the Services window.
 * 
 * @author wernert
 */
class DatabaseNode extends AbstractNode {
    
    public static final String ICON = "de/bfg9000/mongonb/ui/core/images/database.png";       
        
    private final Database database;
    
    public DatabaseNode(Database database) {
        super(Children.LEAF);        
        this.database = database;
        
        setIconBaseWithExtension(ICON);        
        setName(database.getName());        
    }
    
    @Override
    public Action[] getActions(boolean context) {
        return new Action[] { 
            
        };        
    }
    
}
