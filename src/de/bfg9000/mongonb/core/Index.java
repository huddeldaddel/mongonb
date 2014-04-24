package de.bfg9000.mongonb.core;

import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * POJO that contains the data of an index.
 *
 * @author thomaswerner35
 */
@ToString
public class Index {

    @AllArgsConstructor @ToString
    public static class Key {
        @Getter private final String column;
        @Getter private final boolean orderedAscending;
    }

    @Getter private final String name;
    @Getter private final String nameSpace;
    private final List<Key> keys = new ArrayList<Key>();
    @Getter private final boolean sparse;

    Index(DBObject indexInfo) {
        name = (String) indexInfo.get("name");
        nameSpace = (String) indexInfo.get("ns");
        final DBObject keyObj = (DBObject)indexInfo.get("key");
        for(String keyObjProperty: keyObj.keySet())
            keys.add(new Key(keyObjProperty, Integer.valueOf(1).equals(keyObj.get(keyObjProperty))));
        sparse = Boolean.TRUE.equals(indexInfo.get("sparse"));
    }

    public List<Key> getKeys() {
        return Collections.unmodifiableList(keys);
    }

}
