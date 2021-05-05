package com.canknow.cbp.base.threading.backgroundWorkers;

import com.canknow.cbp.base.backgroundJob.BackgroundWorkerBase;
import com.canknow.cbp.base.threading.timers.CanknowTimer;

import java.util.TimerTask;

public abstract class PeriodicBackgroundWorkerBase extends BackgroundWorkerBase {
    private final CanknowTimer timer;

    public PeriodicBackgroundWorkerBase(CanknowTimer timer) {
        this.timer = timer;
        this.timer.setElapsed(new TimerTask() {
            @Override
            public void run() {
                timer_Elapsed();
            }
        });
    }

    @Override
    public void start() {
        super.start();
        timer.start();
    }

    @Override
    public void stop() {
        timer.stop();
        super.stop();
    }

    @Override
    public void waitToStop() {
        timer.waitToStop();
        super.waitToStop();
    }

    private void timer_Elapsed() {
        doWork();
    }

    protected abstract void doWork();
}
