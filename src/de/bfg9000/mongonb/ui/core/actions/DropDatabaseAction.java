package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.ui.core.nodes.DatabaseNode;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

/**
 * Drops (removes) a given {@code Database}.
 *
 * @author thomaswerner35
 */
public class DropDatabaseAction extends AbstractAction {

    private static final ResourceBundle bundle = NbBundle.getBundle(DropDatabaseAction.class);

    private final DatabaseNode databaseNode;

    public DropDatabaseAction(DatabaseNode databaseNode) {
        this.databaseNode = databaseNode;
        super.putValue(NAME, bundle.getString("DropDatabaseAction.Name"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String template = bundle.getString("DropDatabaseAction.Message");
        final String message = MessageFormat.format(template, databaseNode.getName());
        final NotifyDescriptor c = new NotifyDescriptor.Confirmation(message, NotifyDescriptor.YES_NO_OPTION);
        if(NotifyDescriptor.YES_OPTION == DialogDisplayer.getDefault().notify(c))
            new Thread(new Runnable() {
                @Override
                public void run() {
                    databaseNode.drop();
                }
            }).start();
    }

}
