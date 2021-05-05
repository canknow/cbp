package com.canknow.cbp.common.adapter.outbound.persistent.backgroundJob;

import com.canknow.cbp.base.backgroundJob.BackgroundJobInfo;
import com.canknow.cbp.base.backgroundJob.IBackgroundJobStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component("backgroundJobStore")
@Primary
public class BackgroundJobStore implements IBackgroundJobStore {
    @Autowired
    private IBackgroundJobInfoRepository backgroundJobInfoRepository;

    @Override
    public BackgroundJobInfo get(String jobId) {
        return backgroundJobInfoRepository.get(jobId);
    }

    @Override
    public void insert(BackgroundJobInfo jobInfo) {
        backgroundJobInfoRepository.insert(jobInfo);
    }

    @Override
    public List<BackgroundJobInfo> getWaitingJobs(int maxResultCount) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"priority"));
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(0, maxResultCount, sort);
        return backgroundJobInfoRepository.getAllByPage(pageable, (Specification<BackgroundJobInfo>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("abandoned"), false),
                criteriaBuilder.lessThanOrEqualTo(root.get("nextTryTime"), LocalDateTime.now()))).getContent();
    }

    @Override
    public void delete(BackgroundJobInfo jobInfo) {
        backgroundJobInfoRepository.delete(jobInfo);
    }

    @Override
    public void update(BackgroundJobInfo jobInfo) {
        backgroundJobInfoRepository.update(jobInfo);
    }
}
