package de.bfg9000.mongonb.ui.core.services;

import java.util.ResourceBundle;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle;

/**
 * This Node is the base node of the plugin. It is registered as a root entry of the Services tab.
 * 
 * @author wernert
 */
@ServicesTabNodeRegistration(
        displayName=MongoServiceNode.NAME, 
        iconResource=MongoServiceNode.ICON_BASE, 
        name=MongoServiceNode.NAME, 
        position = 5000
)
public class MongoServiceNode extends AbstractNode {
    
    public static final String ICON_BASE = "de/bfg9000/mongonb/images/MongoDB-logo-16.png";    
    public static final String NAME = "MongoDB";    

    private static final ResourceBundle bundle = NbBundle.getBundle(MongoServiceNode.class);
    
    public MongoServiceNode() {
        super(Children.LEAF);
        
        setName(bundle.getString("MongoServiceNode.Name"));
        setIconBaseWithExtension(ICON_BASE);
    }
    
}
