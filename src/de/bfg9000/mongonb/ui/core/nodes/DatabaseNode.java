package de.bfg9000.mongonb.ui.core.nodes;

import de.bfg9000.mongonb.core.Database;
import de.bfg9000.mongonb.core.DatabaseStats;
import de.bfg9000.mongonb.ui.core.actions.CreateCollectionAction;
import de.bfg9000.mongonb.ui.core.actions.DropDatabaseAction;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.Action;
import lombok.Getter;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle;

/**
 * Displays a {@code Database} in the Services window.
 *
 * @author thomaswerner35
 */
public class DatabaseNode extends AbstractNode {

    public static final String ICON = "de/bfg9000/mongonb/ui/core/images/database.png";
    public static final String PROPERTY_REMOVED = "removed";

    private static final ResourceBundle bundle = NbBundle.getBundle(DatabaseNode.class);

    private final Database database;

    public DatabaseNode(Database database) {
        super(Children.create(new CollectionNodeFactory(database), true));
        this.database = database;

        setIconBaseWithExtension(ICON);
        setName(database.getName());
    }

    @Override
    public Action[] getActions(boolean context) {
        final List<Action> actions = new LinkedList<Action>();
        actions.add(new CreateCollectionAction(database));
        actions.add(new DropDatabaseAction(this));
        actions.addAll(Arrays.asList(super.getActions(context)));
        return actions.toArray(new Action[actions.size()]);
    }

    public void drop() {
        database.drop();
        firePropertyChange(PROPERTY_REMOVED, false, true);
    }

    @Override
    protected Sheet createSheet() {
        final Sheet result = new Sheet();
        final Sheet.Set defaultProps = Sheet.createPropertiesSet();
        final DatabaseStats stats = database.getStats();
        defaultProps.put(new LocalizedProperty("serverUsed", stats.getServerUsed()));
        defaultProps.put(new LocalizedProperty("db", stats.getDb()));
        defaultProps.put(new LocalizedProperty("collections", stats.getCollections()));
        defaultProps.put(new LocalizedProperty("objects", stats.getObjects()));
        defaultProps.put(new LocalizedProperty("avgObjSize", stats.getAvgObjSize()));
        defaultProps.put(new LocalizedProperty("dataSize", stats.getDataSize()));
        defaultProps.put(new LocalizedProperty("storageSize", stats.getStorageSize()));
        defaultProps.put(new LocalizedProperty("numExtents", stats.getNumExtents()));
        defaultProps.put(new LocalizedProperty("indexes", stats.getIndexes()));
        defaultProps.put(new LocalizedProperty("indexSize", stats.getIndexSize()));
        defaultProps.put(new LocalizedProperty("fileSize", stats.getFileSize()));
        defaultProps.put(new LocalizedProperty("nsSizeMB", stats.getNsSizeMB()));
        defaultProps.put(new LocalizedProperty("dataFileVersion", stats.getDataFileVersion()));
        defaultProps.put(new LocalizedProperty("ok", stats.getOk()));
        result.put(defaultProps);
        return result;
    }

    /**
     * Read-Only property that uses localized strings from the bundle file.
     */
    private final static class LocalizedProperty extends PropertySupport.ReadOnly<String> {

        @Getter private final String value;

        public LocalizedProperty(String propertyName, String value) {
            super(bundle.getString("DatabaseNode.property." +propertyName +".name"), String.class,
                  bundle.getString("DatabaseNode.property." +propertyName +".displayname"),
                  bundle.getString("DatabaseNode.property." +propertyName +".shortdesc"));
            this.value = value;
        }

    }

}
