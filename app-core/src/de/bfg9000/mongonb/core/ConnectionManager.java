package de.bfg9000.mongonb.core;

import de.bfg9000.mongonb.core.impl.ConnectionNameComparator;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 * Manages {@code Connection} objects.
 * 
 * @author wernert
 */
public enum ConnectionManager {
    
    INSTANCE;
    
    public static final String PROPERTY_CONNECTIONS = "connections";
    
    private static final String CONNECTIONS_COUNT = "connections.count";
    private static final String CONNECTIONS_HOST = "connections.{0}.host";
    private static final String CONNECTIONS_PORT = "connections.{0}.port";
    private static final String CONNECTIONS_NAME = "connections.{0}.name";
    
    private final PropertyChangeSupport propSupport = new PropertyChangeSupport(this);
    private final List<Connection> connections = new LinkedList<Connection>();        
    
    private ConnectionManager() {
        load();
    }
    
    public Collection<Connection> getConnections() {
        return Collections.unmodifiableCollection(connections);
    }
    
    public void add(Connection connection) {
        if(!connections.contains(connection)) {            
            final Collection<Connection> old = new ArrayList<Connection>(connections);            
            connections.add(connection);            
            Collections.sort(connections, new ConnectionNameComparator());
            propSupport.firePropertyChange(PROPERTY_CONNECTIONS, old, connections);            
            store();
        }
    }
    
    public void remove(Connection connection) {
        final Collection<Connection> old = new ArrayList<Connection>(connections);            
        connections.remove(connection);
        Collections.sort(connections, new ConnectionNameComparator());
        propSupport.firePropertyChange(PROPERTY_CONNECTIONS, old, connections);            
        store();
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
    
    private void load() {
        connections.clear();
        final Preferences prefs = NbPreferences.forModule(getClass());
        final int count = prefs.getInt(CONNECTIONS_COUNT, 0);
        for(int i=0; i<count; i++) {
            final Connection connection = new Connection();                                                
            connection.setHost(prefs.get(MessageFormat.format(CONNECTIONS_HOST, i), null));
            connection.setPort(prefs.getInt(MessageFormat.format(CONNECTIONS_PORT, i), -1));
            connection.setName(prefs.get(MessageFormat.format(CONNECTIONS_NAME, i), null));
            if((null != connection.getName()) && (-1 != connection.getPort()) && (null != connection.getName()))
                connections.add(connection);
        }
        Collections.sort(connections, new ConnectionNameComparator());
    }
    
    private void store() {
        final Preferences prefs = NbPreferences.forModule(getClass());
        prefs.putInt(CONNECTIONS_COUNT, connections.size());
        int i = 0;
        for(Connection connection: connections) {            
            prefs.put(MessageFormat.format(CONNECTIONS_HOST, i), connection.getHost());
            prefs.putInt(MessageFormat.format(CONNECTIONS_PORT, i), connection.getPort());
            prefs.put(MessageFormat.format(CONNECTIONS_NAME, i), connection.getName());
            i++;
        }
    }
    
}
