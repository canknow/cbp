package com.canknow.cbp.common.adapter.outbound.persistent.feedback;

import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import com.canknow.cbp.common.domain.feedback.Feedback;
import org.springframework.stereotype.Repository;

@Repository
public interface IFeedbackJpaRepository extends IJpaRepositoryBase<Feedback> {
}
