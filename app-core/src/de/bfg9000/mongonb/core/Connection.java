package de.bfg9000.mongonb.core;

import com.mongodb.MongoClient;
import java.beans.PropertyChangeSupport;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        final boolean old = isConnected();
        if(isConnected())
            disconnect();
        
        try {
            mongoClient = new MongoClient(host, port);                        
        } catch(UnknownHostException ignore) { }
        
        final boolean connected = isConnected();
        propSupport.firePropertyChange(PROPERTY_CONNECTED, old, connected);
        return connected;                 
    }
    
    public void disconnect() {        
        if(isConnected()) {
            mongoClient.close();
            mongoClient = null;
            propSupport.firePropertyChange(PROPERTY_CONNECTED, true, false);
        }
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

    @Override
    public Connection clone() {
        final Connection result = new Connection();
        result.setHost(host);
        result.setName(name);
        result.setPort(port);
        result.mongoClient = null;
        return result;
    }
    
}
