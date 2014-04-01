package de.bfg9000.mongonb.ui.core.actions;

import com.mongodb.DBObject;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.Action;

/**
 * Returns actions that can be used in the context menu of a result table.
 *
 * @author thomaswerner35
 */
public class ActionFactory {

    public Collection<Action> getActionsForTableContextMenu(Collection<DBObject> selectedItems) {
        final Collection<Action> result = new LinkedList<Action>();
        if((null == selectedItems) || selectedItems.isEmpty())
            return result;

        // Copy
        result.add(1 == selectedItems.size() ?
                   new CopyEntryAction(selectedItems.iterator().next()) : new CopyEntriesAction(selectedItems));

        return result;
    }

}
