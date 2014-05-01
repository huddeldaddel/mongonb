package de.bfg9000.mongonb.core;

import com.mongodb.MongoException;
import java.io.IOException;

/**
 * Unwrapps a MongoException message.
 *
 * @author thomaswerner35
 */
public class MongoExceptionUnwrapper {

    private final String message;

    public MongoExceptionUnwrapper(Exception ex) {
        if(ex instanceof MongoException) {
            String msgValue = ex.getMessage();
            try {
                msgValue = (String) new QueryObjectConverter().convertQueryObject(ex.getMessage()).get("err");
            } catch(IOException ignored) { }
            message = msgValue;
        } else
            message = ex == null ? null : ex.getMessage();
    }

    @Override
    public String toString() {
        return message;
    }

}
