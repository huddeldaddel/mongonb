package de.bfg9000.mongonb.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * POJO that holds the data of a database managed by a MongoDB server.
 * 
 * @author wernert
 */
@AllArgsConstructor
public class Database {

    @Getter private final Connection connection;
    @Getter private final String name;
    
}
