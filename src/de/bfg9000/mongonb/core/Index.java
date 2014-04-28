package de.bfg9000.mongonb.core;

import com.mongodb.DBObject;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * POJO that contains the data of an index.
 *
 * @author thomaswerner35
 */
@EqualsAndHashCode @NoArgsConstructor @ToString
public class Index {

    public static final String PROPERTY_KEY = "key";
    public static final String PROPERTY_KEYS = "keys";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_NAMESPACE = "nameSpace";
    public static final String PROPERTY_SPARSE = "sparse";
    public static final String PROPERTY_UNIQUE = "unique";

    @AllArgsConstructor @EqualsAndHashCode @ToString
    public class Key {

        public static final String PROPERTY_ORDER = "orderAscending";

        private final PropertyChangeSupport propSupport = new PropertyChangeSupport(this);

        @Getter private final String column;
        @Getter private boolean orderedAscending;

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            propSupport.addPropertyChangeListener(listener);
        }

        public void removePropertyChangeListener(PropertyChangeListener listener) {
            propSupport.removePropertyChangeListener(listener);
        }

        public void setOrderAscending(boolean oAscending) {
            final boolean old = this.orderedAscending;
            this.orderedAscending = oAscending;
            propSupport.firePropertyChange(PROPERTY_ORDER, old, orderedAscending);
        }

    }

    @Getter private String name;
    @Getter private String nameSpace;
    private final List<Key> keys = new ArrayList<Key>();
    @Getter private boolean sparse = true;
    @Getter private boolean unique = false;

    private final PropertyChangeSupport propSupport = new PropertyChangeSupport(this);
    private final PropertyChangeListener keyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            propSupport.firePropertyChange(PROPERTY_KEY, false, true);
        }
    };

    Index(DBObject indexInfo) {
        name = (String) indexInfo.get("name");
        nameSpace = (String) indexInfo.get("ns");
        final DBObject keyObj = (DBObject)indexInfo.get("key");
        for(String keyObjProperty: keyObj.keySet())
            keys.add(new Key(keyObjProperty, Integer.valueOf(1).equals(keyObj.get(keyObjProperty))));
        sparse = Boolean.TRUE.equals(indexInfo.get("sparse"));
    }

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

    public List<Key> getKeys() {
        return Collections.unmodifiableList(keys);
    }

    public void addKey(String column, boolean ascending) {
        final List<Key> old = Collections.unmodifiableList(new ArrayList<Key>(getKeys()));
        final Key key = new Key(column, ascending);
        key.addPropertyChangeListener(keyChangeListener);
        keys.add(key);
        propSupport.firePropertyChange(PROPERTY_KEYS, old, getKeys());
    }

    public void removeKey(Key key) {
        final List<Key> old = Collections.unmodifiableList(new ArrayList<Key>(getKeys()));
        key.removePropertyChangeListener(keyChangeListener);
        keys.remove(key);
        propSupport.firePropertyChange(PROPERTY_KEYS, old, getKeys());
    }

    public void setName(String name) {
        final String old = this.name;
        this.name = name;
        propSupport.firePropertyChange(PROPERTY_NAME, old, name);
    }

    public void setNameSpace(String nameSpace) {
        final String old = this.nameSpace;
        this.nameSpace = nameSpace;
        propSupport.firePropertyChange(PROPERTY_NAMESPACE, old, nameSpace);
    }

    public void setSparse(boolean sparse) {
        final boolean old = this.sparse;
        this.sparse = sparse;
        propSupport.firePropertyChange(PROPERTY_SPARSE, old, sparse);
    }

    public void setUnqiue(boolean unique) {
        final boolean old = this.unique;
        this.unique = unique;
        propSupport.firePropertyChange(PROPERTY_UNIQUE, old, unique);
    }

}
