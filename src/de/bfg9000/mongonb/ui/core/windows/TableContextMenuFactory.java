package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBObject;
import de.bfg9000.mongonb.ui.core.actions.ActionFactory;
import java.util.Collection;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Builds a JPopupMenu for the result table.
 *
 * @author thomaswerner35
 */
class TableContextMenuFactory {

    public JPopupMenu buildContextMenu(Collection<DBObject> selectedItems) {
        final JPopupMenu result = new JPopupMenu();
        for(Action action: new ActionFactory().getActionsForTableContextMenu(selectedItems))
            result.add(new JMenuItem(action));
        return result;
    }

}
