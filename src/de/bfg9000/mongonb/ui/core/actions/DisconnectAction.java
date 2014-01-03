package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.core.Connection;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;

/**
 * Disconnects from a database.
 * 
 * @author thomaswerner35
 */
public class DisconnectAction extends AbstractAction {
    
    private static final ResourceBundle bundle = NbBundle.getBundle(DisconnectAction.class);
    
    private final Connection connection;
    
    public DisconnectAction(Connection connection) {
        this.connection = connection;
                
        super.putValue(NAME, bundle.getString("DisconnectAction.Name"));
    }

    @Override
    public boolean isEnabled() {
        return connection.isConnected();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        connection.disconnect();
    }
    
}
