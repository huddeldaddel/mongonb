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

    public Collection<Action> getActionsForTableContextMenu(DBObject selectedItem) {
        final Collection<Action> result = new LinkedList<Action>();
        if(null != selectedItem)
            result.add(new CopyEntryAction(selectedItem));
        return result;
    }

}
