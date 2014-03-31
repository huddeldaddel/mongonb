package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBObject;
import de.bfg9000.mongonb.core.QueryResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

/**
 * Caches the data of a database query. Uses lazy loading to get new results when required.
 *
 * @author thomaswerner35
 */
class DataCache {

    /**
     * A {@code DataCache} instance that has no content.
     */
    public static final DataCache EMPTY = new DataCache(QueryResult.EMPTY, 20);

    private final List<DBObject> cache = new ArrayList<DBObject>();

    @Getter protected QueryResult queryResult;
    @Getter private final String query;
    @Getter private int windowSize = 20;        // size of the content window
    @Getter private int windowPosition = 0;     // index of the first visible element in the content window

    public DataCache(QueryResult queryResult, int defaultWindowSize, String query) {
        this.queryResult = queryResult;
        windowSize = defaultWindowSize;
        this.query = query;
        loadMissingObjects();
    }

    public DataCache(QueryResult queryResult, int defaultWindowSize) {
        query = "";
        this.queryResult = queryResult;
        windowSize = defaultWindowSize;
        loadMissingObjects();
    }

    public boolean canMoveForward() {
        return windowPosition +windowSize < queryResult.getCount();
    }

    public boolean canMoveReverse() {
        return windowPosition > 0;
    }

    public void moveFirst() {
        windowPosition = 0;
    }

    public void moveLast() {
        int pos = getCount() / windowSize;
        if((getCount() % windowSize) == 0)
            pos--;
        windowPosition = windowSize *pos;
        loadMissingObjects();
    }

    /**
     * Moves the window position forward (windowPosition += windowSize)
     */
    public void moveForward() {
        if(!canMoveForward())
            return;

        windowPosition += windowSize;
        loadMissingObjects();
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
        return queryResult.getCount();
    }

    private void loadData(int records) {
        for(int i=0; i<records; i++)
            if(queryResult.hasNext())
                cache.add(queryResult.next());
    }

    private void loadMissingObjects() {
        final int missingObjects = windowPosition +windowSize -cache.size();
        if(missingObjects > 0)
            loadData(missingObjects);
    }

}
