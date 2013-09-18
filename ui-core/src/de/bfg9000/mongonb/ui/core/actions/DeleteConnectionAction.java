package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.core.Connection;
import de.bfg9000.mongonb.core.ConnectionManager;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import org.openide.util.NbBundle;

/**
 * Deletes a given {@code Connection}.
 * 
 * @author wernert
 */
public class DeleteConnectionAction extends AbstractAction {
    
    private static final ResourceBundle bundle = NbBundle.getBundle(CreateConnectionAction.class);
    
    private final Connection connection;
    
    public DeleteConnectionAction(Connection connection) {
        super.putValue(NAME, bundle.getString("DeleteConnectionAction.Name"));
        this.connection = connection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {        
        ConnectionManager.INSTANCE.remove(connection);        
    }
    
}
