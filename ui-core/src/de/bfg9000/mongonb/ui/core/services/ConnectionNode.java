package de.bfg9000.mongonb.ui.core.services;

import de.bfg9000.mongonb.core.Connection;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
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
    
}
