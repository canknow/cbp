package com.canknow.cbp.common.domain.region;

import com.canknow.cbp.base.domain.repositories.IRepository;

import java.util.List;

public interface ICbpRegionRepository<TRegion extends CbpRegion<TRegion>> extends IRepository<TRegion> {
    List<TRegion> getAllByLevel(Integer level);
}
