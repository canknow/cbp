package com.canknow.cbp.base.backgroundJob;

import com.canknow.cbp.base.threading.IRunnable;
import com.canknow.cbp.base.threading.RunnableBase;

public abstract class BackgroundWorkerBase extends RunnableBase implements IRunnable {
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
