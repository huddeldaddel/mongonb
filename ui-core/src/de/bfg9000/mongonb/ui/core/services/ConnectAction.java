package de.bfg9000.mongonb.ui.core.services;

import de.bfg9000.mongonb.core.Connection;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;

/**
 * Connects to a database.
 * 
 * @author wernert
 */
class ConnectAction extends AbstractAction {
    
    private static final ResourceBundle bundle = NbBundle.getBundle(ConnectAction.class);
    
    private final Connection connection;
    
    public ConnectAction(Connection connection) {
        this.connection = connection;        
        
        super.putValue(NAME, bundle.getString("ConnectAction.Name"));
    }

    @Override
    public boolean isEnabled() {
        return !connection.isConnected();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        connection.connect();
    }
    
}
