package com.canknow.cbp.base.threading.backgroundWorkers;

import com.canknow.cbp.base.threading.IRunnable;
import com.canknow.cbp.base.threading.RunnableBase;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BackgroundWorkerManager extends RunnableBase implements IBackgroundWorkerManager {
    private final List<IBackgroundWorker> _backgroundJobs = new ArrayList<>();

    @Override
    public void add(IBackgroundWorker worker) {
        _backgroundJobs.add(worker);

        if (getIsRunning()) {
            worker.start();
        }
    }

    @Override
    public void start() {
        _backgroundJobs.forEach(IRunnable::start);
        super.start();
    }

    @Override
    public void stop() {
        _backgroundJobs.forEach(IRunnable::start);
        super.start();
    }

    @Override
    public void waitToStop() {
        _backgroundJobs.forEach(IRunnable::waitToStop);
        super.waitToStop();
    }
}
