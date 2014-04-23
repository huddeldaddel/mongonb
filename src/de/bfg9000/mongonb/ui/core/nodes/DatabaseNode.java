package de.bfg9000.mongonb.ui.core.nodes;

import com.mongodb.CommandResult;
import com.mongodb.DBObject;
import de.bfg9000.mongonb.core.Database;
import de.bfg9000.mongonb.ui.core.actions.CreateCollectionAction;
import de.bfg9000.mongonb.ui.core.actions.DropDatabaseAction;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.Action;
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
        final NumberFormat nfi = NumberFormat.getIntegerInstance();
        final NumberFormat nfn = NumberFormat.getNumberInstance();
        final Sheet result = new Sheet();
        final Sheet.Set defaultProps = Sheet.createPropertiesSet();
        final CommandResult stats = database.getStats();
        if(null != stats) {
            defaultProps.put(new LocalizedProperty("serverUsed", stats.get("serverUsed")));
            defaultProps.put(new LocalizedProperty("db", stats.get("db")));
            defaultProps.put(new LocalizedProperty("collections", stats.get("collections") instanceof Number ?
                             nfi.format(((Number) stats.get("collections")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("objects", stats.get("objects") instanceof Number ?
                             nfi.format(((Number) stats.get("objects")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("avgObjSize", stats.get("avgObjSize") instanceof Number ?
                             nfn.format(((Number) stats.get("avgObjSize")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("dataSize", stats.get("dataSize") instanceof Number ?
                             nfi.format(((Number) stats.get("dataSize")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("storageSize", stats.get("storageSize") instanceof Number ?
                             nfi.format(((Number) stats.get("storageSize")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("numExtents", stats.get("numExtents") instanceof Number ?
                             nfi.format(((Number) stats.get("numExtents")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("indexes", stats.get("indexes") instanceof Number ?
                             nfi.format(((Number) stats.get("indexes")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("indexSize", stats.get("indexSize") instanceof Number ?
                             nfi.format(((Number) stats.get("indexSize")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("fileSize", stats.get("fileSize") instanceof Number ?
                             nfi.format(((Number) stats.get("fileSize")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("nsSizeMB", stats.get("nsSizeMB") instanceof Number ?
                             nfi.format(((Number) stats.get("nsSizeMB")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("dataFileVersion", stats.get("dataFileVersion") instanceof DBObject?
                             ((DBObject)stats.get("dataFileVersion")).get("major") +"." +
                             ((DBObject)stats.get("dataFileVersion")).get("minor") : ""));
            defaultProps.put(new LocalizedProperty("ok", Double.valueOf(1.0).equals(stats.get("ok")) ?
                             bundle.getString("DatabaseNode.property.value.yes") :
                             bundle.getString("DatabaseNode.property.value.no")));
        }
        result.put(defaultProps);
        return result;
    }

    /**
     * Read-Only property that uses localized strings from the bundle file.
     */
    private final static class LocalizedProperty extends PropertySupport.ReadOnly<String> {

        private final Object data;

        public LocalizedProperty(String propertyName, Object data) {
            super(bundle.getString("DatabaseNode.property." +propertyName +".name"), String.class,
                  bundle.getString("DatabaseNode.property." +propertyName +".displayname"),
                  bundle.getString("DatabaseNode.property." +propertyName +".shortdesc"));
            this.data = data;
        }

        @Override
        public String getValue() throws IllegalAccessException, InvocationTargetException {
            return null == data ? "" : data.toString();
        }
    }

}
