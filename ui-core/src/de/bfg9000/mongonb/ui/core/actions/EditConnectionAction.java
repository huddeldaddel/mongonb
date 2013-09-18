package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.core.Connection;
import de.bfg9000.mongonb.core.ConnectionManager;
import de.bfg9000.mongonb.ui.core.dialogs.ConnectionEditorDialog;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;

/**
 * Edits a {@code Connection}.
 * 
 * @author wernert
 */
public class EditConnectionAction extends AbstractAction {
    
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
            connection.disconnect();
            ConnectionManager.INSTANCE.remove(connection);
            ConnectionManager.INSTANCE.add(editor.getConnection());            
        }
        editor.dispose();
    }
    
}
