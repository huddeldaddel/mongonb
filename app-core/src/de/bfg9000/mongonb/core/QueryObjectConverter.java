package de.bfg9000.mongonb.core;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Can be used to convert JSON literals into DBObjects.
 * 
 * @author wernert
 */
class QueryObjectConverter {    
    
    public DBObject convertQueryObject(String query) throws IOException, JsonParseException {                                       
        final QueryObjectConverter converter = new QueryObjectConverter();
        final JsonParser parser = new JsonFactory().createParser(new StringReader(query));
        try {
            if(!JsonToken.START_OBJECT.equals(parser.nextToken()))
                throw new IOException("Invalid JSON document");
            
            return converter.getObject(parser);
        } finally {
            parser.close();    
        }
    }
        
    DBObject getObject(JsonParser parser) throws IOException {
        if(parser.getCurrentToken() != JsonToken.START_OBJECT)
            throw new IOException("Not a valid object start");
        
        final List<Object> array = new LinkedList<Object>();
        final BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        
        String fieldName = "";
        JsonToken token;
        boolean inArray = false;
        
        while((token = parser.nextToken()) != JsonToken.END_OBJECT) {
            switch(token) {                                    
                case END_ARRAY:
                    builder.add(fieldName, array.toArray());
                    inArray = false;
                    break;
                case FIELD_NAME:
                    fieldName = parser.getCurrentName();
                    break;
                case START_ARRAY:                    
                    array.clear();
                    inArray = true;
                    break;
                case START_OBJECT:
                    builder.add(fieldName, getObject(parser));
                    break;
                case VALUE_EMBEDDED_OBJECT:
                    break;
                case VALUE_FALSE:
                    if(inArray) 
                        array.add(parser.getValueAsBoolean());
                    else
                        builder.add(fieldName, parser.getValueAsBoolean());                    
                    break;
                case VALUE_NULL:
                    if(inArray) 
                        array.add(null);
                    else
                        builder.add(fieldName, null);
                    break;
                case VALUE_NUMBER_FLOAT:
                    if(inArray) 
                        array.add(parser.getValueAsDouble());
                    else
                        builder.add(fieldName, parser.getValueAsDouble());
                    break;
                case VALUE_NUMBER_INT:
                    if(inArray) 
                        array.add(parser.getValueAsLong());
                    else
                        builder.add(fieldName, parser.getValueAsLong());
                    break;
                case VALUE_STRING:
                    if(inArray) 
                        array.add(parser.getValueAsString());
                    else
                        builder.add(fieldName, parser.getValueAsString());
                    break;
                case VALUE_TRUE:
                    if(inArray) 
                        array.add(parser.getValueAsBoolean());
                    else
                        builder.add(fieldName, parser.getValueAsBoolean());
                    break;                
                default:
                    break;
            }
        }                
        return builder.get();
    }
    
}
