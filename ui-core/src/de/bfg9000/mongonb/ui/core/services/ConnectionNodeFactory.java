package de.bfg9000.mongonb.ui.core.services;

import de.bfg9000.mongonb.core.Connection;
import de.bfg9000.mongonb.core.ConnectionManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates {@code Node}s for each {@code Connection} registered at the {@code ConnectionManager}.
 * 
 * @author wernert
 */
class ConnectionNodeFactory extends ChildFactory<Connection> implements PropertyChangeListener {

    public ConnectionNodeFactory() {
        ConnectionManager.INSTANCE.addPropertyChangeListener(this);
    }
    
    @Override
    protected boolean createKeys(List<Connection> toPopulate) {
        toPopulate.addAll(ConnectionManager.INSTANCE.getConnections());
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Connection key) {
        return new ConnectionNode(key);        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refresh(true);
    }
    
}
