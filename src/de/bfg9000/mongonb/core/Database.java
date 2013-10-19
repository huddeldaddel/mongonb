package de.bfg9000.mongonb.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * POJO that holds the data of a database managed by a MongoDB server.
 * 
 * @author wernert
 */
@AllArgsConstructor
public class Database {

    @Getter private final Connection connection;
    @Getter private final String name;
    
    public java.util.Collection<Collection> getCollections() {
        final List<Collection> result = new LinkedList<Collection>();
        for(String collection: connection.getMongoClient().getDB(name).getCollectionNames()) 
            result.add(new Collection(connection, name, collection));        
        Collections.sort(result, new CollectionNameComparator());
        return result;
    }
    
    /**
     * Used to sort {@code Connection}s by their names.
     */
    private static final class CollectionNameComparator implements Comparator<Collection> {

        @Override
        public int compare(Collection o1, Collection o2) {
            return o1.getName().compareTo(o2.getName());
        }

    }
    
}
