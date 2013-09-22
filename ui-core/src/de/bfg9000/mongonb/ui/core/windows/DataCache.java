package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

/**
 * Caches the data of a database query. Uses lazy loading to get new results when required.
 * 
 * @author wernert
 */
class DataCache {
    
    /**
     * A {@code DataCache} instance that has no content.
     */
    public static final DataCache EMPTY = new DataCache(new EmptySource(), 20);
    
    private final List<DBObject> cache = new ArrayList<DBObject>();
    
    @Getter protected Source source;
    @Getter private int windowSize = 20;        // size of the content window
    @Getter private int windowPosition = 0;     // index of the first visible element in the content window
    
    public DataCache(DBCursor cursor, int defaultWindowSize) {
        source = new Source(cursor);
        windowSize = defaultWindowSize;
        
        final int missingObjects = windowPosition +windowSize -cache.size();
        if(missingObjects > 0)
            loadData(missingObjects);
    }        
    
    protected DataCache(Source source, int defaultWindowSize) {
        this.source = source;
        windowSize = defaultWindowSize;
        
        final int missingObjects = windowPosition +windowSize -cache.size();
        if(missingObjects > 0)
            loadData(missingObjects);
    }
    
    public boolean canMoveForward() {
        return windowPosition +windowSize < source.getCount();
    }
    
    public boolean canMoveReverse() {
        return windowPosition > 0;
    }
    
    /**
     * Moves the window position forward (windowPosition += windowSize)
     */
    public void moveForward() {
        if(!canMoveForward())
            return;
            
        windowPosition += windowSize;
        final int missingObjects = windowPosition +windowSize -cache.size();
        if(missingObjects > 0)
            loadData(missingObjects);
    }
    
    /**
     * Moves the window position reverse (windowPosition -= windowSize)
     */
    public void moveReverse() {
        windowPosition = Math.max(windowPosition -windowSize, 0);        
    }    
    
    public List<DBObject> getContent() {
        final int endIndex = Math.min(windowPosition +windowSize, cache.size());
        return Collections.unmodifiableList(cache.subList(windowPosition, endIndex));
    }
    
    public int getCount() {
        return source.getCount();
    }
    
    private void loadData(int records) {
        for(int i=0; i<records; i++)
            if(source.hasNext())
                cache.add(source.next());
    }
    
    /**
     * Encapsulates the DBCursor. We don't want to depend on a database in unit tests!
     */
    protected static class Source {
        
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
    
    /**
     * A source that returns no content.
     */
    private static final class EmptySource extends Source {
        
        public EmptySource() {
            super(null);
        }
        
        @Override
        public boolean hasNext() {
            return false;
        }
        
        @Override
        public DBObject next() {
            return null;
        }
        
        @Override
        public int getCount() {
            return 0;
        }
        
    }
    
}
