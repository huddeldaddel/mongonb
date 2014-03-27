package de.bfg9000.mongonb.core;

import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Wrapps a MongoDB collection.
 *
 * @author thomaswerner35
 */
@AllArgsConstructor
public class Collection {

    @Getter private final Connection connection;
    @Getter private final String database;
    @Getter private final String name;

    public DBObject add(String document) throws IOException {
        final DBObject dbObject = new QueryObjectConverter().convertQueryObject(document);
        final WriteResult result = connection.getMongoClient().getDB(database).getCollection(name).insert(dbObject);
        final CommandResult lastError = result.getLastError();
        if(!lastError.ok())
            throw lastError.getException();
        return dbObject;
    }

    public DBCursor query(String query) throws IOException {
        final DBObject queryObject = new QueryObjectConverter().convertQueryObject(query);
        return connection.getMongoClient().getDB(database).getCollection(name).find(queryObject);
    }

    public List<DBObject> remove(String query) throws IOException {
        final List<DBObject> result = new LinkedList<DBObject>();
        final DBObject queryObject = new QueryObjectConverter().convertQueryObject(query);
        final DBCollection collection = connection.getMongoClient().getDB(database).getCollection(name);
        final DBCursor cursor = collection.find(queryObject);
        while(cursor.hasNext()) {
            final DBObject object = cursor.next();
            collection.remove(object);
            result.add(object);
        }
        return result;
    }

    public void drop() {
        connection.getMongoClient().getDB(database).getCollection(name).drop();
    }

}
