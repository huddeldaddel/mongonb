package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.ui.core.dialogs.IndexManagerDialog;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;

/**
 * Opens the IndexManagerDialog for a given {@code Collection}.
 *
 * @author thomaswerner35
 */
public class ManageIndexesAction extends AbstractAction {

    private static final ResourceBundle bundle = NbBundle.getBundle(ManageIndexesAction.class);

    private final Collection collection;

    public ManageIndexesAction(Collection collection) {
        this.collection = collection;

        super.putValue(NAME, bundle.getString("ManageIndexesAction.Name"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final IndexManagerDialog dialog = new IndexManagerDialog(collection);
        dialog.execute();
        dialog.dispose();
    }

}
