package de.bfg9000.mongonb.core;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import static com.mongodb.MapReduceCommand.OutputType.INLINE;
import com.mongodb.MapReduceOutput;
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

    public QueryResult mapReduce(String mapFunction, String reduceFunction, QueryExecutor executor) {
        final DBCollection collection = connection.getMongoClient().getDB(database).getCollection(name);
        final MapReduceOutput out = collection.mapReduce(mapFunction, reduceFunction, null, INLINE, new BasicDBObject());
        return new QueryResult.MapReduceResult(out, executor);
    }

    public QueryResult query(String query, QueryExecutor executor) throws IOException {
        final DBObject queryObject = new QueryObjectConverter().convertQueryObject(query);
        final DBCursor cursor = connection.getMongoClient().getDB(database).getCollection(name).find(queryObject);
        return new QueryResult.DBCursorResult(cursor, executor);
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
