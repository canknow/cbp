package com.canknow.cbp.common.domain.settings;

import com.canknow.cbp.base.domain.repositories.IRepository;

import java.util.List;

public interface ISettingRepository extends IRepository<Setting> {
    Setting findByName(String name);

    Setting findByNameAndUserId(String name, String userId);

    List<Setting> getAllByUserId(String userId);
}
