package com.canknow.cbp.base.backgroundJob;

import java.util.List;

public interface IBackgroundJobStore {
    BackgroundJobInfo get(String jobId);

    void insert(BackgroundJobInfo jobInfo);

    List<BackgroundJobInfo> getWaitingJobs(int maxResultCount);

    void delete(BackgroundJobInfo jobInfo);

    void update(BackgroundJobInfo jobInfo);
}
