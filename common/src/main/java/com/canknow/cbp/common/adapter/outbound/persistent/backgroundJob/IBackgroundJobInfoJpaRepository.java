package com.canknow.cbp.common.adapter.outbound.persistent.backgroundJob;

import com.canknow.cbp.base.backgroundJob.BackgroundJobInfo;
import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import org.springframework.stereotype.Repository;

@Repository
public interface IBackgroundJobInfoJpaRepository extends IJpaRepositoryBase<BackgroundJobInfo> {
}
