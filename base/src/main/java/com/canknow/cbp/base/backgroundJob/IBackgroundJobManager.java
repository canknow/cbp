package com.canknow.cbp.base.backgroundJob;

import com.canknow.cbp.base.threading.backgroundWorkers.IBackgroundWorker;

import java.time.Duration;

public interface IBackgroundJobManager extends IBackgroundWorker {
    <TJob extends IBackgroundJob<TArgs>, TArgs> String enqueue(Class<TJob> tJobClass, TArgs args, BackgroundJobPriority priority, Duration delay);

    Boolean delete(String jobId);
}
