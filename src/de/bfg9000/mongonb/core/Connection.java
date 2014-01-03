package de.bfg9000.mongonb.core;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import lombok.Getter;
import lombok.ToString;

/**
 * This class contains the information required to establish a connection to a MongoDB server.
 *
 * @author thomaswerner35
 */
@ToString
public class Connection {

    private final PropertyChangeSupport propSupport = new PropertyChangeSupport(this);

    public static final String PROPERTY_HOST = "host";
    public static final String PROPERTY_PORT = "port";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_AUTH_REQUIRED = "authRequired";
    public static final String PROPERTY_AUTH_USERNAME = "authUsername";
    public static final String PROPERTY_AUTH_PASSWORD = "authPassword";
    public static final String PROPERTY_AUTH_DATABASE = "authDatabase";
    public static final String PROPERTY_CONNECTED = "connected";

    @Getter private String host = "localhost";
    @Getter private Integer port = 27017;
    @Getter private String name = "";
    @Getter private boolean authRequired = false;
    @Getter private String authUsername = "";
    @Getter private char[] authPassword = {};
    @Getter private String authDatabase = "";
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

    public void setAuthRequired(boolean authRequired) {
        final boolean old = this.authRequired;
        this.authRequired = authRequired;
        propSupport.firePropertyChange(PROPERTY_AUTH_REQUIRED, old, authRequired);
    }

    public void setAuthDatabase(String authDatabase) {
        final String old = this.authDatabase;
        this.authDatabase = authDatabase;
        propSupport.firePropertyChange(PROPERTY_AUTH_DATABASE, old, authDatabase);
    }

    public void setAuthUsername(String authUsername) {
        final String old = this.authUsername;
        this.authUsername = authUsername;
        propSupport.firePropertyChange(PROPERTY_AUTH_USERNAME, old, authUsername);
    }

    public void setAuthPassword(char[] authPassword) {
        final char[] old = this.authPassword;
        this.authPassword = authPassword;
        propSupport.firePropertyChange(PROPERTY_AUTH_PASSWORD, old, authPassword);
    }

    public boolean connect() {
        Logger.getLogger("com.mongodb").setLevel(Level.SEVERE); // turn off logging - logging would cause NB error msgs
        final boolean wasConneced = isConnected();
        if(wasConneced)
            disconnect();

        try {
            final ServerAddress sa = new ServerAddress(host, port);
            if(authRequired) {
                MongoCredential mc = MongoCredential.createMongoCRCredential(authUsername, authDatabase, authPassword);
                mongoClient = new MongoClient(sa, Collections.singletonList(mc));
            } else {
                mongoClient = new MongoClient(sa);
            }
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
            if(authRequired)
                return mongoClient.getDB(authDatabase).authenticate(authUsername, authPassword);

            mongoClient.getDatabaseNames();
            return true;
        } catch(Exception ignore) {
            return false;
        }
    }

    public java.util.Collection<Database> getDatabases() {
        if(!isConnected())
            return Collections.EMPTY_LIST;

        if(authRequired)
            return Collections.singleton(new Database(this, authDatabase));

        final List<Database> result = new LinkedList<Database>();
        for(String dbName: mongoClient.getDatabaseNames())
            result.add(new Database(this, dbName));
        Collections.sort(result, new DatabaseNameComparator());
        return result;
    }

    @Override
    public Connection clone() {
        final Connection result = new Connection();
        result.setAuthDatabase(authDatabase);
        result.setAuthPassword(authPassword);
        result.setAuthRequired(authRequired);
        result.setAuthUsername(authUsername);
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