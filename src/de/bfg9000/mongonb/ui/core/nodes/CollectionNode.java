package de.bfg9000.mongonb.ui.core.nodes;

import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.core.CollectionStats;
import de.bfg9000.mongonb.core.Database;
import de.bfg9000.mongonb.ui.core.actions.DropCollectionAction;
import de.bfg9000.mongonb.ui.core.actions.ManageIndexesAction;
import de.bfg9000.mongonb.ui.core.actions.OpenMapReduceWindowAction;
import de.bfg9000.mongonb.ui.core.actions.OpenQueryWindowAction;
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
        actions.add(new ManageIndexesAction(collection));
        actions.add(new DropCollectionAction(collection, database));
        actions.addAll(Arrays.asList(super.getActions(context)));
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    protected Sheet createSheet() {
        final Sheet result = new Sheet();
        final Sheet.Set defaultProps = Sheet.createPropertiesSet();
        final CollectionStats stats = collection.getStats();
        defaultProps.put(new LocalizedProperty("serverUsed", stats.getServerUsed()));
        defaultProps.put(new LocalizedProperty("ns", stats.getNs()));
        defaultProps.put(new LocalizedProperty("capped", stats.getCapped()));
        defaultProps.put(new LocalizedProperty("count", stats.getCount()));
        defaultProps.put(new LocalizedProperty("size", stats.getSize()));
        defaultProps.put(new LocalizedProperty("storageSize", stats.getStorageSize()));
        defaultProps.put(new LocalizedProperty("numExtents", stats.getNumExtents()));
        defaultProps.put(new LocalizedProperty("nindexes", stats.getNindexes()));
        defaultProps.put(new LocalizedProperty("lastExtentSize", stats.getLastExtentSize()));
        defaultProps.put(new LocalizedProperty("paddingFactor", stats.getPaddingFactor()));
        defaultProps.put(new LocalizedProperty("systemFlags", stats.getSystemFlags()));
        defaultProps.put(new LocalizedProperty("userFlags", stats.getUserFlags()));
        defaultProps.put(new LocalizedProperty("totalIndexSize", stats.getTotalIndexSize()));
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
            super(bundle.getString("CollectionNode.property." +propertyName +".name"), String.class,
                  bundle.getString("CollectionNode.property." +propertyName +".displayname"),
                  bundle.getString("CollectionNode.property." +propertyName +".shortdesc"));
            this.value = value;
        }

    }

}
