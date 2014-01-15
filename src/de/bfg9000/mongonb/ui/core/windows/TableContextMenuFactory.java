package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBObject;
import de.bfg9000.mongonb.ui.core.actions.ActionFactory;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Builds a JPopupMenu for the result table.
 *
 * @author thomaswerner35
 */
class TableContextMenuFactory {

    public JPopupMenu buildContextMenu(DBObject selectedItem) {
        final JPopupMenu result = new JPopupMenu();
        for(Action action: new ActionFactory().getActionsForTableContextMenu(selectedItem))
            result.add(new JMenuItem(action));
        return result;
    }

}
