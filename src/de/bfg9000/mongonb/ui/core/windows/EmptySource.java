package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBObject;

/**
 * A {@code Source} that returns no content.
 * 
 * @author thomaswerner35
 */
class EmptySource extends Source {
        
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
