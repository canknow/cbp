package com.canknow.cbp.common.adapter.outbound.persistent.settings;

import com.canknow.cbp.common.domain.settings.Setting;
import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ISettingJpaRepository extends IJpaRepositoryBase<Setting> {
    Setting findFirstByName(String name);

    Setting findFirstByNameAndUserId(String name, String userId);

    List<Setting> findAllByUserId(String userId);
}
