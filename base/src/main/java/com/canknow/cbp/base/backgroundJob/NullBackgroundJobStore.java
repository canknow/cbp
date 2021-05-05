package com.canknow.cbp.base.backgroundJob;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("nullBackgroundJobStore")
public class NullBackgroundJobStore implements IBackgroundJobStore {
    @Override
    public BackgroundJobInfo get(String jobId) {
        return null;
    }

    @Override
    public void insert(BackgroundJobInfo jobInfo) {

    }

    @Override
    public List<BackgroundJobInfo> getWaitingJobs(int maxResultCount) {
        return null;
    }

    @Override
    public void delete(BackgroundJobInfo jobInfo) {

    }

    @Override
    public void update(BackgroundJobInfo jobInfo) {

    }
}
