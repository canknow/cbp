package com.canknow.cbp.common.adapter.outbound.persistent.backgroundJob;

import com.canknow.cbp.base.backgroundJob.BackgroundJobInfo;
import com.canknow.cbp.jpa.repositories.JpaRepositoryBase;
import org.springframework.stereotype.Component;

@Component
public class BackgroundJobInfoRepository extends JpaRepositoryBase<BackgroundJobInfo, IBackgroundJobInfoJpaRepository> implements IBackgroundJobInfoRepository {
    public BackgroundJobInfoRepository(IBackgroundJobInfoJpaRepository jpaRepository) {
        super(jpaRepository);
    }
}
