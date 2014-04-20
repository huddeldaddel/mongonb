package de.bfg9000.mongonb.ui.core.nodes;

import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.core.Database;
import de.bfg9000.mongonb.ui.core.actions.DropCollectionAction;
import de.bfg9000.mongonb.ui.core.actions.OpenMapReduceWindowAction;
import de.bfg9000.mongonb.ui.core.actions.OpenQueryWindowAction;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Displays a {@code Collection} in the Services window.
 *
 * @author thomaswerner35
 */
class CollectionNode extends AbstractNode {

    public static final String ICON = "de/bfg9000/mongonb/ui/core/images/table.png";

    private final Collection collection;
    private final Database database;

    public CollectionNode(Collection collection, Database database) {
        super(Children.LEAF);
        this.collection = collection;
        this.database = database;

        setIconBaseWithExtension(ICON);
        setName(collection.getName());
    }

    @Override
    public Action[] getActions(boolean context) {
        final List<Action> actions = new LinkedList<Action>();
        actions.add(new OpenQueryWindowAction(collection));
        actions.add(new OpenMapReduceWindowAction(collection));
        actions.add(new DropCollectionAction(collection, database));
        actions.addAll(Arrays.asList(super.getActions(context)));
        return actions.toArray(new Action[actions.size()]);
    }

}
