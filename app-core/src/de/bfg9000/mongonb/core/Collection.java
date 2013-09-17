package de.bfg9000.mongonb.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author wernert
 */
@AllArgsConstructor
public class Collection {

    @Getter private final Connection connection;
    @Getter private final String database;
    @Getter private final String name;
    
}
