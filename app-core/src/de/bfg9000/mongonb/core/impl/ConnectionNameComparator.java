package de.bfg9000.mongonb.core.impl;

import de.bfg9000.mongonb.core.Connection;
import java.util.Comparator;

/**
 * Can be used to order Connections by their name.
 * 
 * @author wernert
 */
public class ConnectionNameComparator implements Comparator<Connection> {

    @Override
    public int compare(Connection o1, Connection o2) {
        return o1.getName().compareTo(o2.getName());
    }
    
}
