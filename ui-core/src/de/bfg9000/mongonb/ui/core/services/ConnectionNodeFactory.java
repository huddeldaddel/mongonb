package de.bfg9000.mongonb.ui.core.services;

import de.bfg9000.mongonb.core.Connection;
import de.bfg9000.mongonb.core.ConnectionManager;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author wernert
 */
class ConnectionNodeFactory extends ChildFactory<Connection> {

    @Override
    protected boolean createKeys(List<Connection> toPopulate) {
        toPopulate.addAll(ConnectionManager.INSTANCE.getConnections());
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Connection key) {
        return new ConnectionNode(key);
    }
    
}
