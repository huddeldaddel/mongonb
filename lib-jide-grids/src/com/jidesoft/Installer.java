package com.jidesoft;

import static com.jidesoft.utils.Lm.*;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        verifyLicense("Thomas Werner", "MongoNb", "7okEKvBubaMAOOroAD0ROAUoGaWMu0L1");
    }
    
}
