package com.canknow.cbp.common.adapter.outbound.persistent.settings;

import com.canknow.cbp.common.domain.settings.ISettingRepository;
import com.canknow.cbp.common.domain.settings.Setting;
import com.canknow.cbp.jpa.repositories.FullAuditedJpaRepositoryBase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SettingRepository extends FullAuditedJpaRepositoryBase<Setting, ISettingJpaRepository> implements ISettingRepository {
    public SettingRepository(ISettingJpaRepository jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public Setting findByName(String name) {
        return jpaRepository.findFirstByName(name);
    }

    @Override
    public Setting findByNameAndUserId(String name, String userId) {
        return jpaRepository.findFirstByNameAndUserId(name, userId);
    }

    @Override
    public List<Setting> getAllByUserId(String userId) {
        return jpaRepository.findAllByUserId(userId);
    }
}
