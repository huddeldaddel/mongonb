package de.bfg9000.mongonb.ui.core.services;

import de.bfg9000.mongonb.core.Connection;
import de.bfg9000.mongonb.core.ConnectionManager;
import de.bfg9000.mongonb.ui.core.services.dialogs.ConnectionEditorDialog;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;

/**
 * Edits a {@code Connection}.
 * 
 * @author wernert
 */
class EditConnectionAction extends AbstractAction {
    
    private static final ResourceBundle bundle = NbBundle.getBundle(EditConnectionAction.class);
    
    private final Connection connection;
    
    public EditConnectionAction(Connection connection) {
        super.putValue(NAME, bundle.getString("EditConnectionAction.Name"));
        this.connection = connection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final ConnectionEditorDialog editor = new ConnectionEditorDialog();
        if(editor.execute(connection)) {
            ConnectionManager.INSTANCE.remove(connection);
            ConnectionManager.INSTANCE.add(editor.getConnection());            
        }
        editor.dispose();
    }
    
}
