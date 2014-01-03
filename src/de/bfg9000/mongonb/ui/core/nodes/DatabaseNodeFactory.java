package de.bfg9000.mongonb.ui.core.nodes;

import de.bfg9000.mongonb.core.Connection;
import de.bfg9000.mongonb.core.Database;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates {@code Node}s for each {@code Database} registered at the {@code Connection}.
 * 
 * @author thomaswerner35
 */
class DatabaseNodeFactory extends ChildFactory<Database> implements PropertyChangeListener {

    private final Connection connection;
    
    public DatabaseNodeFactory(Connection connection) {
        this.connection = connection;
        connection.addPropertyChangeListener(this);
    }
    
    @Override
    protected boolean createKeys(List<Database> toPopulate) {
        toPopulate.addAll(connection.getDatabases());
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Database key) {
        return new DatabaseNode(key);        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(Connection.PROPERTY_CONNECTED))
            refresh(false);
    }
    
}
