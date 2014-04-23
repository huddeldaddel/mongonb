package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.core.Database;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

/**
 * Drops (removes) a given {@code Collection}.
 *
 * @author thomaswerner35
 */
public class DropCollectionAction extends AbstractAction {

    private static final ResourceBundle bundle = NbBundle.getBundle(DropCollectionAction.class);

    private final Collection collection;
    private final Database database;

    public DropCollectionAction(Collection collection, Database database) {
        this.collection = collection;
        this.database = database;
        super.putValue(NAME, bundle.getString("DropCollectionAction.Name"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String template = bundle.getString("DropCollectionAction.Message");
        final String message = MessageFormat.format(template, collection.getName());
        final NotifyDescriptor c = new NotifyDescriptor.Confirmation(message, NotifyDescriptor.YES_NO_OPTION);
        if(NotifyDescriptor.YES_OPTION == DialogDisplayer.getDefault().notify(c))
            new Thread(new Runnable() {
                @Override
                public void run() {
                    database.removeCollection(collection);
                }
            }).start();
    }

}
