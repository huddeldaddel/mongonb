package de.bfg9000.mongonb;

import com.jidesoft.grid.CellRendererManager;
import static com.jidesoft.utils.Lm.verifyLicense;
import de.bfg9000.mongonb.core.Connection;
import de.bfg9000.mongonb.core.ConnectionManager;
import org.openide.modules.ModuleInstall;

/**
 * Initializes the JIDE components with the Open Source license.
 *
 * @author thomaswerner35
 */
public class Installer extends ModuleInstall {

    @Override
    public boolean closing() {
        for(Connection con: ConnectionManager.INSTANCE.getConnections())
            con.disconnect();
        return super.closing();
    }

    @Override
    public void restored() {
        verifyLicense("Thomas Werner", "MongoNb", "7okEKvBubaMAOOroAD0ROAUoGaWMu0L1");
        CellRendererManager.initDefaultRenderer();
    }

}
