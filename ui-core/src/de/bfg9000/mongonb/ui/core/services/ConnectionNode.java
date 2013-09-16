package de.bfg9000.mongonb.ui.core.services;

import de.bfg9000.mongonb.core.Connection;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Displays a {@code Connection} in the Services window.
 * 
 * @author wernert
 */
class ConnectionNode extends AbstractNode {
    
    public static final String ICON_CONNECTED = "de/bfg9000/mongonb/ui/core/images/connected.png";   
    public static final String ICON_DISCONNECTED = "de/bfg9000/mongonb/ui/core/images/disconnected.png";   
    
    private final Connection connection;
    
    public ConnectionNode(Connection connection) {
        super(Children.LEAF);
        
        this.connection = connection;
        
        setName(connection.getName());
        setIconBaseWithExtension(connection.isConnected() ? ICON_CONNECTED : ICON_DISCONNECTED);        
    }
    
    @Override
    public Action[] getActions(boolean context) {
        return new Action[] { 
            new DeleteConnectionAction(connection),
            new EditConnectionAction(connection)
        };        
    }
    
}
