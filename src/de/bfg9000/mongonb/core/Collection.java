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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Wrapps a MongoDB collection.
 *
 * @author thomaswerner35
 */
@AllArgsConstructor
public class Collection {

    private static final Logger logger = Logger.getLogger(Collection.class.getName());

    @Getter private final Connection connection;
    @Getter private final String database;
    @Getter private final String name;

    /**
     * Stores the given document into this collection
     * @param document the document to be stored, as JSON
     * @return the DBObject that has been written
     * @throws IOException in case of error
     */
    public DBObject add(String document) throws IOException {
        final DBObject dbObject = new QueryObjectConverter().convertQueryObject(document);
        final WriteResult result = connection.getMongoClient().getDB(database).getCollection(name).insert(dbObject);
        final CommandResult lastError = result.getLastError();
        if(!lastError.ok())
            throw lastError.getException();
        return dbObject;
    }

    /**
     * Performs a map/reduce operation on this collection.
     * @param mapFunction the map function to be applied on the entries of the collection
     * @param reduceFunction the reduce function to be applied on the result of the map function
     * @param executor the executor to be used
     * @return the QueryResult
     */
    public QueryResult mapReduce(String mapFunction, String reduceFunction, QueryExecutor executor) {
        final DBCollection collection = connection.getMongoClient().getDB(database).getCollection(name);
        final MapReduceOutput out= collection.mapReduce(mapFunction, reduceFunction, null, INLINE, new BasicDBObject());
        return new QueryResult.MapReduceResult(out, executor);
    }

    /**
     * Performs a query operation on this collection.
     * @param query the template object
     * @param executor the executor to be used
     * @return the QueryResult
     * @throws IOException in case of error
     */
    public QueryResult query(String query, QueryExecutor executor) throws IOException {
        final DBObject queryObject = new QueryObjectConverter().convertQueryObject(query);
        final DBCursor cursor = connection.getMongoClient().getDB(database).getCollection(name).find(queryObject);
        return new QueryResult.DBCursorResult(cursor, executor);
    }

    /**
     * Performs a remove operation on this collection.
     * @param query the template object
     * @return the documents that have been removed
     * @throws IOException in case of error
     */
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

    /**
     * Drops the collection. All data will be lost.
     */
    public void drop() {
        connection.getMongoClient().getDB(database).getCollection(name).drop();
    }

    /**
     * Gets the fields that are used by the documents in this collection. This is a rather lengthy operation because all
     * elements have to be analyzed (by a map/reduce query).
     *
     * @return a map of (field name)-&gt;(# of usages)
     */
    public Map<String, Integer> getFields() {
        final String mapFunction = "" +
                "function() {                         \n" +
                "   for(var prop in this)             \n" +
                "       if(this.hasOwnProperty(prop)) \n" +
                "           emit(prop, 1);            \n" +
                "}";
        final String reduceFunction = "" +
                "function(key, values) {    \n" +
                "    return values.length;  \n" +
                "}";
        final DBCollection collection = connection.getMongoClient().getDB(database).getCollection(name);
        final MapReduceOutput out= collection.mapReduce(mapFunction, reduceFunction, null, INLINE, new BasicDBObject());

        final Map<String, Integer> result = new HashMap<String, Integer>();
        for(DBObject object: out.results())
            result.put((String)object.get("_id"), ((Double)object.get("value")).intValue());
        return result;
    }

    /**
     * @return the results of the collStats mongo server command
     */
    public CommandResult getStats() {
        try {
            final CommandResult result = connection.getMongoClient().getDB(name).getCollection(name).getStats();
            return result.ok() ? result : null;
        } catch(Exception iae) {
            return null;
        }
    }

}
