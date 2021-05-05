package com.canknow.cbp.base.threading.backgroundWorkers;

import com.canknow.cbp.base.threading.IRunnable;

public interface IBackgroundWorkerManager extends IRunnable {
    void add(IBackgroundWorker worker);
}
