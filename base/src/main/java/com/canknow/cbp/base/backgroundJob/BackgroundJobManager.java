package com.canknow.cbp.base.backgroundJob;

import com.canknow.cbp.base.dependency.IIocResolver;
import com.canknow.cbp.base.threading.backgroundWorkers.PeriodicBackgroundWorkerBase;
import com.canknow.cbp.base.threading.timers.CanknowTimer;
import com.canknow.cbp.utils.JsonUtils;
import com.canknow.cbp.utils.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class BackgroundJobManager extends PeriodicBackgroundWorkerBase implements IBackgroundJobManager {
    @Autowired
    protected IBackgroundJobStore _store;
    @Autowired
    protected IIocResolver _iocResolver;

    public static final int JobPollPeriod = 5000;

    public BackgroundJobManager(CanknowTimer timer) {
        super(timer);
        timer.setPeriod(JobPollPeriod);
    }

    @Override
    public <TJob extends IBackgroundJob<TArgs>, TArgs> String enqueue(Class<TJob> tJobClass, TArgs args, BackgroundJobPriority priority, Duration delay) {
        BackgroundJobInfo jobInfo = new BackgroundJobInfo();
        jobInfo.setJobType(tJobClass.getTypeName());
        jobInfo.setJobArgs(JsonUtils.objectToJson(args));
        jobInfo.setJobArgsType(args.getClass().getTypeName());
        jobInfo.setPriority(priority);
        jobInfo.setCreationTime(LocalDateTime.now());

        if (delay != null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            localDateTime.plus(delay);
            jobInfo.setNextTryTime(localDateTime);
        }
        _store.insert(jobInfo);
        return jobInfo.getId();
    }

    @Override
    public Boolean delete(String jobId) {
        BackgroundJobInfo jobInfo = _store.get(jobId);

        _store.delete(jobInfo);
        return true;
    }

    @Override
    protected void doWork() {
        List<BackgroundJobInfo> waitingJobs = _store.getWaitingJobs(1000);
        waitingJobs.forEach(this::tryProcessJob);
    }

    private void tryProcessJob(BackgroundJobInfo jobInfo) {
        jobInfo.increaseTryCount();
        jobInfo.setLastTryTime(LocalDateTime.now());

        String jobType = jobInfo.getJobType();

        try {
            Object job = _iocResolver.resolveByClassName(jobType);
            Method jobExecuteMethod = ReflectUtil.getMethod(job.getClass(), "execute");

            Type argsType = ((ParameterizedType)job.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Object argsObj = JsonUtils.jsonToObject(jobInfo.getJobArgs(), (Class)argsType);

            jobExecuteMethod.invoke(job, argsObj);

            _store.delete(jobInfo);
        }
        catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
        catch (InvocationTargetException e) {
            log.error(e.getTargetException().getMessage());

            jobInfo.setExceptionName(e.getTargetException().getClass().getName());
            jobInfo.setMessage(e.getTargetException().getMessage());

            LocalDateTime nextTryTime = jobInfo.calculateNextTryTime();

            if (nextTryTime != null) {
                jobInfo.setNextTryTime(nextTryTime);
            }
            else {
                jobInfo.setAbandoned(true);
            }
            tryUpdate(jobInfo);
        }
    }

    private void tryUpdate(BackgroundJobInfo jobInfo) {
        _store.update(jobInfo);
    }
}
