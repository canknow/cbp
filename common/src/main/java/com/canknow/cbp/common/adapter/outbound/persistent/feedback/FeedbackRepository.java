package com.canknow.cbp.common.adapter.outbound.persistent.feedback;

import com.canknow.cbp.jpa.repositories.FullAuditedJpaRepositoryBase;
import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.common.domain.feedback.Feedback;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class FeedbackRepository extends FullAuditedJpaRepositoryBase<Feedback, IFeedbackJpaRepository> implements IRepository<Feedback> {
    public FeedbackRepository(IFeedbackJpaRepository jpaRepository) {
        super(jpaRepository);
    }
}
