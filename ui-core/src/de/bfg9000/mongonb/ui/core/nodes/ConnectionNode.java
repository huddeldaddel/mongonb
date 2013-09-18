package de.bfg9000.mongonb.ui.core.nodes;

import de.bfg9000.mongonb.ui.core.actions.EditConnectionAction;
import de.bfg9000.mongonb.ui.core.actions.ConnectAction;
import de.bfg9000.mongonb.ui.core.actions.DeleteConnectionAction;
import de.bfg9000.mongonb.ui.core.actions.DisconnectAction;
import de.bfg9000.mongonb.core.Connection;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Displays a {@code Connection} in the Services window.
 * 
 * @author wernert
 */
class ConnectionNode extends AbstractNode implements PropertyChangeListener {
    
    public static final String ICON_CONNECTED = "de/bfg9000/mongonb/ui/core/images/connected.png";   
    public static final String ICON_DISCONNECTED = "de/bfg9000/mongonb/ui/core/images/disconnected.png";   
    
    private final Connection connection;
    
    public ConnectionNode(Connection connection) {
        super(Children.create(new DatabaseNodeFactory(connection), true));        
        this.connection = connection;
        this.connection.addPropertyChangeListener(this);
        
        setName(connection.getName());
        setIconBaseWithExtension(connection.isConnected() ? ICON_CONNECTED : ICON_DISCONNECTED);        
    }
    
    @Override
    public Action getPreferredAction() {
        return connection.isConnected() ? null : new ConnectAction(connection);
    }
    
    @Override
    public Action[] getActions(boolean context) {
        return new Action[] { 
            new ConnectAction(connection),
            new DisconnectAction(connection),
            new DeleteConnectionAction(connection),
            new EditConnectionAction(connection)
        };        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(Connection.PROPERTY_CONNECTED)) {
            setIconBaseWithExtension(Boolean.TRUE.equals(evt.getNewValue()) ? ICON_CONNECTED : ICON_DISCONNECTED);
            fireIconChange();
        }
    }
    
}
