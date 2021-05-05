package com.canknow.cbp.base.threading.backgroundWorkers;

import com.canknow.cbp.base.threading.RunnableBase;

public abstract class BackgroundWorkerBase extends RunnableBase implements IBackgroundWorker {
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void waitToStop() {
        super.waitToStop();
    }
}
