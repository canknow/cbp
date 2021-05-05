package com.canknow.cbp.base.backgroundJob;

import com.canknow.cbp.base.domain.entities.AggregateRoot;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Entity
@Accessors(chain = true)
public class BackgroundJobInfo extends AggregateRoot {
    public static final int MaxJobTypeLength = 512;
    public static final int MaxJobArgsLength = 1024 * 1024;

    static {
        defaultFirstWaitDuration = 60;
        defaultTimeout = 172800;
        defaultWaitFactor = 2.0;
    }

    private LocalDateTime creationTime;

    private static int defaultFirstWaitDuration;

    private static int defaultTimeout;

    private static double defaultWaitFactor;

    private String jobType;

    private String jobArgs;

    private String jobArgsType;

    private short tryCount;

    @Lob
    @Basic(fetch= FetchType.LAZY)
    private String message;

    private String exceptionName;

    private LocalDateTime nextTryTime;

    private LocalDateTime lastTryTime;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean abandoned;

    private BackgroundJobPriority priority;

    public void increaseTryCount () {
        tryCount++;
    }

    public LocalDateTime calculateNextTryTime() {
        double nextWaitDuration = defaultFirstWaitDuration * (Math.pow(defaultWaitFactor, tryCount - 1));
        LocalDateTime nextTryDate = lastTryTime != null
                ? lastTryTime.plusSeconds((long)nextWaitDuration)
                : LocalDateTime.now().plusSeconds((long)nextWaitDuration);

        if (Duration.between(nextTryDate, getCreationTime()).getSeconds() > defaultTimeout) {
            return null;
        }

        return nextTryDate;
    }
}
