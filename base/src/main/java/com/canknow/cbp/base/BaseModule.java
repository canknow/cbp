package com.canknow.cbp.base;

import com.canknow.cbp.base.authorization.PermissionManager;
import com.canknow.cbp.base.backgroundJob.BackgroundWorkerProperties;
import com.canknow.cbp.base.backgroundJob.IBackgroundJobManager;
import com.canknow.cbp.base.settings.SettingDefinitionManager;
import com.canknow.cbp.base.threading.backgroundWorkers.IBackgroundWorkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseModule extends Module {
    @Autowired
    private PermissionManager permissionManager;
    @Autowired
    private IBackgroundWorkerManager backgroundWorkerManager;
    @Autowired
    private IBackgroundJobManager backgroundJobManager;
    @Autowired
    private SettingDefinitionManager settingDefinitionManager;
    @Autowired
    private BackgroundWorkerProperties backgroundWorkerProperties;

    @Override
    protected void initialize() {
        settingDefinitionManager.initialize();
        permissionManager.initialize();

        initializeBackgroundWorker();
    }

    private void initializeBackgroundWorker() {
        if (!backgroundWorkerProperties.getDisabled()) {
            backgroundWorkerManager.start();
            backgroundWorkerManager.add(backgroundJobManager);
        }
    }
}
