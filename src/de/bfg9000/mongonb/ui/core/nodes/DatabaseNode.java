package de.bfg9000.mongonb.ui.core.nodes;

import de.bfg9000.mongonb.core.Database;
import de.bfg9000.mongonb.ui.core.actions.CreateCollectionAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Displays a {@code Database} in the Services window.
 *
 * @author thomaswerner35
 */
class DatabaseNode extends AbstractNode {

    public static final String ICON = "de/bfg9000/mongonb/ui/core/images/database.png";

    private final Database database;

    public DatabaseNode(Database database) {
        super(Children.create(new CollectionNodeFactory(database), true));
        this.database = database;

        setIconBaseWithExtension(ICON);
        setName(database.getName());
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[] {
            new CreateCollectionAction(database)
        };
    }

}
