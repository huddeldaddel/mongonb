package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.core.ConnectionManager;
import de.bfg9000.mongonb.ui.core.dialogs.ConnectionEditorDialog;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;

/**
 * Creates a new {@code Connection}.
 * 
 * @author wernert
 */
public class CreateConnectionAction extends AbstractAction {
    
    private static final ResourceBundle bundle = NbBundle.getBundle(CreateConnectionAction.class);
    
    public CreateConnectionAction() {
        super.putValue(NAME, bundle.getString("CreateConnectionAction.Name"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final ConnectionEditorDialog editor = new ConnectionEditorDialog();
        if(editor.execute())
            ConnectionManager.INSTANCE.add(editor.getConnection());
        editor.dispose();
    }
    
}
