package de.bfg9000.mongonb.core;

import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO that contains the information required to establish a connection to a MongoDB server.
 * 
 * @author wernert
 */
@ToString
public class Connection {
    
    @Getter @Setter private String host = "localhost";
    @Getter @Setter private Integer port = 27017;
    @Getter @Setter private String name = "";
    @Getter private MongoClient mongoClient;
    
    static {
        Logger.getLogger("com.mongodb").setLevel(Level.SEVERE); // turn off logging - logging would cause NB error msgs
    }
    
    public boolean connect() {
        if(isConnected())
            disconnect();
        
        try {
            mongoClient = new MongoClient(host, port);                        
        } catch(UnknownHostException ignore) { }
        return isConnected();                 
    }
    
    public void disconnect() {
        if(isConnected()) {
            mongoClient.close();
            mongoClient = null;
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
