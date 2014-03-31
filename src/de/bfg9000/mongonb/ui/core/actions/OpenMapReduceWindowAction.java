package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.ui.core.windows.MapReduceTopComponent;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;

/**
 * Opens a {@code MapReduceTopComponent}.
 *
 * @author thomaswerner35
 */
public class OpenMapReduceWindowAction extends AbstractAction {

    private static final ResourceBundle bundle = NbBundle.getBundle(OpenMapReduceWindowAction.class);

    private final Collection collection;

    public OpenMapReduceWindowAction(Collection collection) {
        this.collection = collection;

        super.putValue(NAME, bundle.getString("OpenMapReduceWindowAction.Name"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final MapReduceTopComponent tc  = new MapReduceTopComponent();
        tc.setCollection(collection);
        tc.open();
        tc.requestActive();
    }

}
