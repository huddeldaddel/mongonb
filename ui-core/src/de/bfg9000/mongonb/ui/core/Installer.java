package de.bfg9000.mongonb.ui.core;

import de.bfg9000.mongonb.core.Connection;
import de.bfg9000.mongonb.core.ConnectionManager;
import org.openide.modules.ModuleInstall;

/**
 * Closes all active {@code Connection}s when the IDE is closing.
 * 
 * @author wernert
 */
public class Installer extends ModuleInstall {
    
    @Override
    public boolean closing() {
        for(Connection con: ConnectionManager.INSTANCE.getConnections())
            con.disconnect();
        return super.closing();        
    }
    
}
