package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import java.util.List;
import lombok.Getter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thomaswerner35
 */
public class DataCacheTest {
    
    public DataCacheTest() {
    }

    @Test
    public void testDataLessThanWindowSize() {
        final TestSource source = new TestSource(5);
        final DataCache cut = new DataCache(source, 10);                
                       
        assertMatches(source, 0, 5, cut.getContent());
        assertEquals(false, cut.canMoveForward());
        assertEquals(false, cut.canMoveReverse());
    }
    
    @Test
    public void testDataMatchesWindowSize() {
        final TestSource source = new TestSource(10);
        final DataCache cut = new DataCache(source, 10);        
        cut.source = source;        
        
        assertMatches(source, 0, 10, cut.getContent());
        assertEquals(false, cut.canMoveForward());
        assertEquals(false, cut.canMoveReverse());
    }
    
    @Test
    public void testDataThreeTimesWindowSize() {
        final TestSource source = new TestSource(15);
        final DataCache cut = new DataCache(source, 5);        
        cut.source = source;        
        
        assertMatches(source, 0, 5, cut.getContent());
        assertEquals(true, cut.canMoveForward());
        assertEquals(false, cut.canMoveReverse());
        
        cut.moveForward();        
        
        assertMatches(source, 5, 10, cut.getContent());
        assertEquals(true, cut.canMoveForward());
        assertEquals(true, cut.canMoveReverse());
        
        cut.moveForward();        
        
        assertMatches(source, 10, 15, cut.getContent());
        assertEquals(false, cut.canMoveForward());
        assertEquals(true, cut.canMoveReverse());
    }
    
    @Test
    public void testDataThreeTimesWindowSizeWithRest() {
        final TestSource source = new TestSource(17);
        final DataCache cut = new DataCache(source, 5);        
        cut.source = source;        
        
        assertMatches(source, 0, 5, cut.getContent());
        assertEquals(true, cut.canMoveForward());
        assertEquals(false, cut.canMoveReverse());
        
        cut.moveForward();        
        
        assertMatches(source, 5, 10, cut.getContent());
        assertEquals(true, cut.canMoveForward());
        assertEquals(true, cut.canMoveReverse());
        
        cut.moveForward();        
        
        assertMatches(source, 10, 15, cut.getContent());
        assertEquals(true, cut.canMoveForward());
        assertEquals(true, cut.canMoveReverse());
        
        cut.moveForward();        
        
        assertMatches(source, 15, 17, cut.getContent());
        assertEquals(false, cut.canMoveForward());
        assertEquals(true, cut.canMoveReverse());
    }
    
    @Test
    public void testBackAndForth() {
        final TestSource source = new TestSource(15);
        final DataCache cut = new DataCache(source, 5);        
        cut.source = source;        
        
        assertMatches(source, 0, 5, cut.getContent());
        assertEquals(true, cut.canMoveForward());
        assertEquals(false, cut.canMoveReverse());
        
        cut.moveForward();        
        
        assertMatches(source, 5, 10, cut.getContent());
        assertEquals(true, cut.canMoveForward());
        assertEquals(true, cut.canMoveReverse());
        
        cut.moveReverse();
        
        assertMatches(source, 0, 5, cut.getContent());
        assertEquals(true, cut.canMoveForward());
        assertEquals(false, cut.canMoveReverse());
        
        cut.moveForward();        
        
        assertMatches(source, 5, 10, cut.getContent());
        assertEquals(true, cut.canMoveForward());
        assertEquals(true, cut.canMoveReverse());
        
        cut.moveForward();        
        
        assertMatches(source, 10, 15, cut.getContent());
        assertEquals(false, cut.canMoveForward());
        assertEquals(true, cut.canMoveReverse());
    }
    
    /**
     * Compares the result list with the expected outcome
     * @param source the data source
     * @param begin begin index, inclusive
     * @param end end index, exclusive
     * @param result the result to be compared
     */
    private void assertMatches(TestSource source, int begin, int end, List<DBObject> result) {
        for(int i=0; i<end -begin; i++)
            if(source.getData()[begin +i] != result.get(i))
                fail();
    }
    
    private static final class TestSource extends Source {
        
        @Getter private final DBObject[] data;
        private int pos = 0;        
        
        public TestSource(int size) {
            super(null);
            data = new DBObject[size];
            for(int i=0; i<size; i++)
                data[i] = BasicDBObjectBuilder.start().add("x", i).get();            
        }
        
        @Override
        public boolean hasNext() {
            return pos < data.length;
        }
        
        @Override
        public DBObject next() {
            return data[pos++];
        }        
        
        @Override
        public int getCount() {
            return data.length;
        }
        
    }
    
}