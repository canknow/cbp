package com.canknow.cbp.common.adapter.outbound.persistent.user;

import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ICbpUserJpaRepository<TUser extends CbpUser<TUser, TRole>, TRole extends CbpRole<TUser, TRole>> extends IJpaRepositoryBase<TUser> {
    TUser getByUserName(String userName);

    @Query("select count(t.id) as count,t.creationTime as creationTime from User t group by t.creationTime")
    List<?> findGroupByCreationTime();

    List<TUser> findAllByIdInAndPhoneNumberNotNull(List<String> ids);
}
