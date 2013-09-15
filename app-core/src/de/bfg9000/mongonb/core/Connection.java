package de.bfg9000.mongonb.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO that contains the information required to establish a connection to a MongoDB server.
 * 
 * @author wernert
 */
@ToString
public class Connection {
    
    @Getter @Setter private String host = "localhost";
    @Getter @Setter private int port = 27017;
    @Getter @Setter private String name = "";
    
}
