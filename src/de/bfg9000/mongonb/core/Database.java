package de.bfg9000.mongonb.core;

import com.mongodb.BasicDBObject;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * POJO that holds the data of a database managed by a MongoDB server.
 *
 * @author thomaswerner35
 */
@AllArgsConstructor
public class Database {

    public static final String PROPERTY_COLLECTIONS = "collections";

    private final PropertyChangeSupport propSupport = new PropertyChangeSupport(this);

    @Getter private final Connection connection;
    @Getter private final String name;

    /**
     * Add a PropertyChangeListener to the listener list.
     * The listener is registered for all properties. The same listener object may be added more than once, and will be
     * called as many times as it is added. If <code>listener</code> is null, no exception is thrown and no action is
     * taken.
     *
     * @param listener  The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove a PropertyChangeListener from the listener list.
     * This removes a PropertyChangeListener that was registered for all properties. If <code>listener</code> was added
     * more than once to the same event source, it will be notified one less time after being removed. If
     * <code>listener</code> is null, or was never added, no exception is thrown and no action is taken.
     *
     * @param listener  The PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propSupport.removePropertyChangeListener(listener);
    }

    public java.util.Collection<Collection> getCollections() {
        final List<Collection> result = new LinkedList<Collection>();
        for(String collection: connection.getMongoClient().getDB(name).getCollectionNames())
            result.add(new Collection(connection, name, collection));
        Collections.sort(result, new CollectionNameComparator());
        return result;
    }

    public Collection createCollection(String collectionName) {
        final BasicDBObject options = new BasicDBObject();
        options.put("capped", Boolean.FALSE);

        final java.util.Collection<Collection> old = getCollections();
        connection.getMongoClient().getDB(name).createCollection(collectionName, options);
        propSupport.firePropertyChange(PROPERTY_COLLECTIONS, old, getCollections());
        return new Collection(connection, name, collectionName);
    }

    public void removeCollection(Collection collection) {
        final java.util.Collection<Collection> old = getCollections();
        collection.drop();
        propSupport.firePropertyChange(PROPERTY_COLLECTIONS, old, getCollections());
    }

    /**
     * Used to sort {@code Connection}s by their names.
     */
    private static final class CollectionNameComparator implements Comparator<Collection> {

        @Override
        public int compare(Collection o1, Collection o2) {
            return o1.getName().compareTo(o2.getName());
        }

    }

}
