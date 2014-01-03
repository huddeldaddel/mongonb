package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Wraps a property name and a property value for the hierarchical table.
 * 
 * @author thomaswerner35
 */
@AllArgsConstructor
class PropertyObject {

    @Getter private final String property;
    @Getter private final DBObject object;

}
