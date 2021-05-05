package com.canknow.cbp.common.adapter.outbound.persistent.role;

import com.canknow.cbp.common.domain.role.IRoleRepository;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.jpa.repositories.FullAuditedJpaRepositoryBase;

import java.util.List;

public abstract class CbpRoleRepository<TUser extends CbpUser<TUser, TRole>, TRole extends CbpRole<TUser, TRole>> extends FullAuditedJpaRepositoryBase<TRole, ICbpRoleJpaRepository<TUser, TRole>> implements IRoleRepository<TUser, TRole> {
    public CbpRoleRepository(ICbpRoleJpaRepository<TUser, TRole> jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public List<TRole> findAllOfDefault() {
        return jpaRepository.findAllByIsDefault(true);
    }

    @Override
    public TRole findDefault() {
        return jpaRepository.getByIsDefault(true);
    }

    @Override
    public TRole findByName(String name) {
        return jpaRepository.getByName(name);
    }
}
