package de.bfg9000.mongonb.ui.core.actions;

import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.ui.core.windows.QueryTopComponent;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;

/**
 * Opens a QueryTopComponent.
 * 
 * @author thomaswerner35
 */
public class OpenQueryWindowAction extends AbstractAction {
    
    private static final ResourceBundle bundle = NbBundle.getBundle(OpenQueryWindowAction.class);
    
    private final Collection collection;
    
    public OpenQueryWindowAction(Collection collection) {
        this.collection = collection;        
        
        super.putValue(NAME, bundle.getString("OpenQueryWindowAction.Name"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final QueryTopComponent tc  = new QueryTopComponent();
        tc.setCollection(collection);
        tc.open();
        tc.requestActive();
    }    
    
}
