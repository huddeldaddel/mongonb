package de.bfg9000.mongonb.ui.core.nodes;

import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.core.Database;
import de.bfg9000.mongonb.ui.core.actions.DropCollectionAction;
import de.bfg9000.mongonb.ui.core.actions.OpenQueryWindowAction;
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
        return new Action[] {
            new OpenQueryWindowAction(collection),
            new DropCollectionAction(collection, database)
        };
    }

}
