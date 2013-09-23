package com.jidesoft;

import static com.jidesoft.utils.Lm.*;
import org.openide.modules.ModuleInstall;

/**
 * Initializes the JIDE components with the Open Source license.
 * 
 * @author wernert
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        verifyLicense("Thomas Werner", "MongoNb", "7okEKvBubaMAOOroAD0ROAUoGaWMu0L1");
    }
    
}
