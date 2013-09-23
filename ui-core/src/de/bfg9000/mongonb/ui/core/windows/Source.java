package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 *
 * Encapsulates the DBCursor. We don't want to depend on a database in unit tests!
 * 
 * @author wernert
 */
class Source {
    
    private final DBCursor cursor;    
    private Integer count = null;

    public Source(DBCursor cursor) {
        this.cursor = cursor;            
    }

    public boolean hasNext() {
        return cursor.hasNext();
    }

    public DBObject next() {
        return cursor.next();
    }

    public int getCount() {
        return null == count ? count = cursor.count() : count;            
    }
    
}
