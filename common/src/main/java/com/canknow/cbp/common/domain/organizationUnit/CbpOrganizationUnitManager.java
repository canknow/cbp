package com.canknow.cbp.common.domain.organizationUnit;

import com.canknow.cbp.base.domain.domainServices.DomainService;
import com.canknow.cbp.common.domain.organizationUnitUser.CbpOrganizationUnitUser;
import com.canknow.cbp.common.domain.organizationUnitUser.IOrganizationUnitUserRepository;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CbpOrganizationUnitManager<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>,
        TOrganizationUnit extends CbpOrganizationUnit<TUser, TRole, TOrganizationUnit>,
        TOrganizationUnitUser extends CbpOrganizationUnitUser<TUser, TRole, TOrganizationUnit>> extends DomainService {

    @Autowired
    private IOrganizationUnitUserRepository organizationUnitUserRepository;

    public List<String> getOrganizationUnitIdsOfUser(String userId) {
        List<TOrganizationUnitUser> organizationUnitUsers = organizationUnitUserRepository.findAllByUserId(userId);
        return organizationUnitUsers.stream().map(CbpOrganizationUnitUser::getOrganizationUnitId).collect(Collectors.toList());
    }
}
