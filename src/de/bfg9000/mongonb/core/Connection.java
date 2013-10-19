package de.bfg9000.mongonb.core;

import com.mongodb.MongoClient;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import lombok.Getter;
import lombok.ToString;

/**
 * POJO that contains the information required to establish a connection to a MongoDB server.
 * 
 * @author wernert
 */
@ToString
public class Connection {
    
    private final PropertyChangeSupport propSupport = new PropertyChangeSupport(this);
    
    public static final String PROPERTY_HOST = "host";
    public static final String PROPERTY_PORT = "port";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_CONNECTED = "connected";
    
    @Getter private String host = "localhost";
    @Getter private Integer port = 27017;
    @Getter private String name = "";
    @Getter private MongoClient mongoClient;    
    
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
    
    public void setHost(String host) {
        final String old = this.host;
        this.host = host;
        propSupport.firePropertyChange(PROPERTY_HOST, old, host);
    }
    
    public void setName(String name) {
        final String old = this.name;
        this.name = name;
        propSupport.firePropertyChange(PROPERTY_NAME, old, name);
    }
    
    public void setPort(Integer port) {
        final Integer old = this.port;
        this.port = port;
        propSupport.firePropertyChange(PROPERTY_PORT, old, port);
    }
    
    public boolean connect() {
        Logger.getLogger("com.mongodb").setLevel(Level.SEVERE); // turn off logging - logging would cause NB error msgs
        final boolean wasConneced = isConnected();
        if(wasConneced)
            disconnect();
        
        try {
            mongoClient = new MongoClient(host, port);                        
        } catch(UnknownHostException ignore) { }
        
        final boolean connected = isConnected();
        if(!connected) {
            mongoClient.close();
            mongoClient = null;
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                propSupport.firePropertyChange(PROPERTY_CONNECTED, wasConneced, connected);
            }
        });
        
        return connected;                 
    }
    
    public void disconnect() {        
        if(null == mongoClient)
            return;
        
        mongoClient.close();
        mongoClient = null;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                propSupport.firePropertyChange(PROPERTY_CONNECTED, true, false);        
            }
        });        
    }
    
    public boolean isConnected() {
        if(null == mongoClient)
            return false;
        
        try {            
            mongoClient.getDatabaseNames();
            return true;
        } catch(Exception ignore) {
            return false;
        }        
    }

    public java.util.Collection<Database> getDatabases() {
        if(!isConnected())
            return Collections.EMPTY_LIST;
        
        final List<Database> result = new LinkedList<Database>();
        for(String dbName: mongoClient.getDatabaseNames())
            result.add(new Database(this, dbName));
        Collections.sort(result, new DatabaseNameComparator());
        return result;
    }
    
    @Override
    public Connection clone() {
        final Connection result = new Connection();
        result.setHost(host);
        result.setName(name);
        result.setPort(port);
        result.mongoClient = null;
        return result;
    }
    
    /**
     * Used to sort {@code Connection}s by their names.
     */
    private static final class DatabaseNameComparator implements Comparator<Database> {

        @Override
        public int compare(Database o1, Database o2) {
            return o1.getName().compareTo(o2.getName());
        }

    }
    
}
