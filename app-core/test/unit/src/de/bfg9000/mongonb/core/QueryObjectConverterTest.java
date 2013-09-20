package de.bfg9000.mongonb.core;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;
import java.io.StringReader;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 *
 * @author wernert
 */
public class QueryObjectConverterTest {

    @Test
    @Ignore
    public void testGetObject_Nested() throws IOException {        
        final String input = "{ \"name\" : \"MongoDB\" , \"type\" : \"database\" , \"count\" : 1 , \"info\" : { \"x\" : 203 , \"y\" : 102}}";
        final JsonParser parser = new JsonFactory().createParser(new StringReader(input));
        final QueryObjectConverter converter = new QueryObjectConverter();
        parser.nextToken();
        assertEquals(input, converter.getObject(parser).toString());
        parser.close();
    }
    
    @Test
    public void testGetObject_Array() throws IOException {
        final String input = "{ \"Vorname\" : \"Max\" , \"männlich\" : true , \"Hobbys\" : [ \"Reiten\" , \"Golfen\" , \"Lesen\"] , \"Kinder\" : [ ] , \"Partner\" :  null }";
        final JsonParser parser = new JsonFactory().createParser(new StringReader(input));
        final QueryObjectConverter converter = new QueryObjectConverter();
        parser.nextToken();
        assertEquals(input, converter.getObject(parser).toString());
        parser.close();
    }
    
}