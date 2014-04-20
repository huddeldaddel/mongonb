package de.bfg9000.mongonb.ui.core.nodes;

import com.mongodb.CommandResult;
import com.mongodb.DBObject;
import de.bfg9000.mongonb.core.Database;
import de.bfg9000.mongonb.ui.core.actions.CreateCollectionAction;
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
class DatabaseNode extends AbstractNode {

    public static final String ICON = "de/bfg9000/mongonb/ui/core/images/database.png";

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
        actions.addAll(Arrays.asList(super.getActions(context)));
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    protected Sheet createSheet() {
        final Sheet result = new Sheet();
        final Sheet.Set defaultProperties = Sheet.createPropertiesSet();
        final CommandResult stats = database.getStats();
        defaultProperties.put(new LocalizedProperty("serverUsed", stats.get("serverUsed")));
        defaultProperties.put(new LocalizedProperty("db", stats.get("db")));
        defaultProperties.put(new LocalizedProperty("collections", stats.get("collections") instanceof Integer ?
                              NumberFormat.getIntegerInstance().format((Integer) stats.get("collections")) : ""));
        defaultProperties.put(new LocalizedProperty("objects", stats.get("objects") instanceof Integer ?
                              NumberFormat.getIntegerInstance().format((Integer) stats.get("objects")) : ""));
        defaultProperties.put(new LocalizedProperty("avgObjSize", stats.get("avgObjSize") instanceof Double ?
                              NumberFormat.getNumberInstance().format((Double) stats.get("avgObjSize")) : ""));
        defaultProperties.put(new LocalizedProperty("dataSize", stats.get("dataSize") instanceof Integer ?
                              NumberFormat.getIntegerInstance().format((Integer) stats.get("dataSize")) : ""));
        defaultProperties.put(new LocalizedProperty("storageSize", stats.get("storageSize") instanceof Integer ?
                              NumberFormat.getIntegerInstance().format((Integer) stats.get("storageSize")) : ""));
        defaultProperties.put(new LocalizedProperty("numExtents", stats.get("numExtents") instanceof Integer ?
                              NumberFormat.getIntegerInstance().format((Integer) stats.get("numExtents")) : ""));
        defaultProperties.put(new LocalizedProperty("indexes", stats.get("indexes") instanceof Integer ?
                              NumberFormat.getIntegerInstance().format((Integer) stats.get("indexes")) : ""));
        defaultProperties.put(new LocalizedProperty("indexSize", stats.get("indexSize") instanceof Integer ?
                              NumberFormat.getIntegerInstance().format((Integer) stats.get("indexSize")) : ""));
        defaultProperties.put(new LocalizedProperty("fileSize", stats.get("fileSize") instanceof Integer ?
                              NumberFormat.getIntegerInstance().format((Integer) stats.get("fileSize")) : ""));
        defaultProperties.put(new LocalizedProperty("nsSizeMB", stats.get("nsSizeMB") instanceof Integer ?
                              NumberFormat.getIntegerInstance().format((Integer) stats.get("nsSizeMB")) : ""));
        defaultProperties.put(new LocalizedProperty("dataFileVersion", stats.get("dataFileVersion") instanceof DBObject?
                              ((DBObject)stats.get("dataFileVersion")).get("major") +"." +
                              ((DBObject)stats.get("dataFileVersion")).get("minor") : ""));
        defaultProperties.put(new LocalizedProperty("ok", Double.valueOf(1.0).equals(stats.get("ok")) ?
                              bundle.getString("DatabaseNode.property.value.yes") :
                              bundle.getString("DatabaseNode.property.value.no")));
        result.put(defaultProperties);
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
