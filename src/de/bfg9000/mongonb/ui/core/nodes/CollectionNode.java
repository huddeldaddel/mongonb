package de.bfg9000.mongonb.ui.core.nodes;

import com.mongodb.CommandResult;
import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.core.Database;
import de.bfg9000.mongonb.ui.core.actions.DropCollectionAction;
import de.bfg9000.mongonb.ui.core.actions.OpenMapReduceWindowAction;
import de.bfg9000.mongonb.ui.core.actions.OpenQueryWindowAction;
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
 * Displays a {@code Collection} in the Services window.
 *
 * @author thomaswerner35
 */
class CollectionNode extends AbstractNode {

    public static final String ICON = "de/bfg9000/mongonb/ui/core/images/table.png";

    private static final ResourceBundle bundle = NbBundle.getBundle(CollectionNode.class);

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

    @Override
    protected Sheet createSheet() {
        final NumberFormat nfi = NumberFormat.getIntegerInstance();
        final NumberFormat nfn = NumberFormat.getNumberInstance();
        final Sheet result = new Sheet();
        final Sheet.Set defaultProps = Sheet.createPropertiesSet();
        final CommandResult stats = collection.getStats();
        if(null != stats) {
            defaultProps.put(new LocalizedProperty("serverUsed", stats.get("serverUsed")));
            defaultProps.put(new LocalizedProperty("ns", stats.get("ns")));
            defaultProps.put(new LocalizedProperty("count", stats.get("count") instanceof Number ?
                             nfi.format(((Number) stats.get("count")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("size", stats.get("size") instanceof Number ?
                             nfi.format(((Number) stats.get("size")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("storageSize", stats.get("storageSize") instanceof Number ?
                             nfi.format(((Number) stats.get("storageSize")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("numExtents", stats.get("numExtents") instanceof Number ?
                             nfi.format(((Number) stats.get("numExtents")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("nindexes", stats.get("nindexes") instanceof Number ?
                             nfi.format(((Number) stats.get("nindexes")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("lastExtentSize", stats.get("lastExtentSize") instanceof Number ?
                             nfi.format(((Number) stats.get("lastExtentSize")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("paddingFactor", stats.get("paddingFactor") instanceof Number ?
                             nfn.format(((Number) stats.get("paddingFactor")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("systemFlags", stats.get("systemFlags") instanceof Number ?
                             nfi.format(((Number) stats.get("systemFlags")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("userFlags", stats.get("userFlags") instanceof Number ?
                             nfi.format(((Number) stats.get("userFlags")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("totalIndexSize", stats.get("totalIndexSize") instanceof Number ?
                             nfi.format(((Number) stats.get("totalIndexSize")).doubleValue()) : ""));
            defaultProps.put(new LocalizedProperty("ok", Double.valueOf(1.0).equals(stats.get("ok")) ?
                             bundle.getString("CollectionNode.property.value.yes") :
                             bundle.getString("CollectionNode.property.value.no")));
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
            super(bundle.getString("CollectionNode.property." +propertyName +".name"), String.class,
                  bundle.getString("CollectionNode.property." +propertyName +".displayname"),
                  bundle.getString("CollectionNode.property." +propertyName +".shortdesc"));
            this.data = data;
        }

        @Override
        public String getValue() throws IllegalAccessException, InvocationTargetException {
            return null == data ? "" : data.toString();
        }
    }

}
