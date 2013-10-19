package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBObject;
import java.util.Collection;
import java.util.Iterator;

/**
 * A {@code Source} that returns a fixed collection of {@code DBObject}s.
 * 
 * @author wernert
 */
class CollectionSource extends Source {
        
    private final int count;
    private final Iterator<? extends DBObject> iterator;
    
    public CollectionSource(Collection<? extends DBObject> data) {
        super(null);
        
        count = data.size();
        iterator = data.iterator();
    }
    
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public DBObject next() {
        return iterator.next();
    }

    @Override
    public int getCount() {
        return count;
    }
    
}
