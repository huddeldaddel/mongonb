package de.bfg9000.mongonb.core;

import com.mongodb.*;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * POJO that contains information about a MongoDB collection.
 * 
 * @author wernert
 */
@AllArgsConstructor
public class Collection {
    
    @Getter private final Connection connection;
    @Getter private final String database;
    @Getter private final String name;           
    
    public DBCursor executeQuery(String query) throws IOException {
        final DBObject queryObject = new QueryObjectConverter().convertQueryObject(query);
        return connection.getMongoClient().getDB(database).getCollection(name).find(queryObject);
    }        
    
}
