package de.bfg9000.mongonb.ui.core.nodes;

import de.bfg9000.mongonb.core.Collection;
import de.bfg9000.mongonb.core.Database;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates {@code Node}s for each {@code Collection} registered at a {@code Database}.
 * 
 * @author wernert
 */
class CollectionNodeFactory extends ChildFactory<Collection> {

    private final Database database;
    
    public CollectionNodeFactory(Database database) {
        this.database = database;
    }
    
    @Override
    protected boolean createKeys(List<Collection> toPopulate) {
        toPopulate.addAll(database.getCollections());
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Collection key) {
        return new CollectionNode(key);        
    }
    
}
