package de.bfg9000.mongonb.core;

import java.text.MessageFormat;
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
    
    private static final String CONNECTIONS_COUNT = "connections.count";
    private static final String CONNECTIONS_HOST = "connections.{0}.host";
    private static final String CONNECTIONS_PORT = "connections.{0}.port";
    private static final String CONNECTIONS_NAME = "connections.{0}.name";
    
    private final List<Connection> connections = new LinkedList<Connection>();        
    
    private ConnectionManager() {
        load();
    }
    
    public Collection<Connection> getConnections() {
        return Collections.unmodifiableCollection(connections);
    }
    
    public void add(Connection connection) {
        if(!connections.contains(connection)) {
            connections.add(connection);
            store();
        }
    }
    
    public void remove(Connection connection) {
        connections.remove(connection);
        store();
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
