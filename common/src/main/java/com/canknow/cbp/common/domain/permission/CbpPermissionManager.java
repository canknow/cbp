package com.canknow.cbp.common.domain.permission;

import com.canknow.cbp.base.authorization.PermissionManager;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.common.domain.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public abstract class CbpPermissionManager<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>>  extends PermissionManager {
    @Autowired
    protected IUserRepository<TUser, TRole> userRepository;

    public Set<String> getCurrentUserPermissions() {
        return getUserPermissions(applicationSession.getUserId());
    }

    public Set<String> getUserPermissions(String userId) {
        TUser user = userRepository.get(userId);
        Set<TRole> roles = user.getRoles();
        Set<String> permissions = new HashSet<>();

        roles.forEach(role -> {
            permissions.addAll(role.getPermissions());
        });
        return permissions;
    }
}
