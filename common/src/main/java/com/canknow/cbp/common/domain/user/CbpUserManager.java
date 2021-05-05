package com.canknow.cbp.common.domain.user;

import com.canknow.cbp.base.authorization.IUserManager;
import com.canknow.cbp.base.authorization.Permission;
import com.canknow.cbp.base.domain.domainServices.DomainService;
import com.canknow.cbp.base.exceptions.ApplicationException;
import com.canknow.cbp.base.exceptions.BusinessException;
import com.canknow.cbp.base.identifier.UserIdentifier;
import com.canknow.cbp.base.utils.PasswordUtil;
import com.canknow.cbp.common.domain.authorization.UserLogin;
import com.canknow.cbp.common.domain.permission.CbpPermissionManager;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.role.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public abstract class CbpUserManager<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>> extends DomainService implements IUserManager {
    @Autowired
    protected IUserRepository<TUser, TRole> userRepository;
    @Autowired
    protected IRoleRepository<TUser, TRole> roleRepository;
    @Autowired
    protected CbpPermissionManager permissionManager;
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private IUserNumberProvider userNumberProvider;

    @Override
    public boolean isGranted(String userId, String permissionName) throws ApplicationException {
        return isGranted(userId, permissionManager.getPermission(permissionName));
    }

    @Override
    public void unlockUser(String userId) {
        TUser user = userRepository.get(userId);
        user.unlock();
        userRepository.update(user);
    }

    @Override
    public void lockUser(String userId) {
        TUser user = userRepository.get(userId);
        user.lock();
        userRepository.update(user);
    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {
        TUser user = userRepository.get(userId);
        String encryptedOldPassword =  passwordUtil.encrypt(user.getUserName(), oldPassword);

        if (!encryptedOldPassword.equals(user.getPassword())) {
            throw new BusinessException(getLocalizationText("yourCurrentPasswordIsWrong"));
        }
        String encryptedPassword = passwordUtil.encrypt(user.getUserName(), newPassword);
        user.setPassword(encryptedPassword);
        userRepository.update(user);
    }

    @Override
    public void changePassword(String userId, String newPassword) {
        TUser user = userRepository.get(userId);
        String encryptedPassword = passwordUtil.encrypt(user.getUserName(), newPassword);
        user.setPassword(encryptedPassword);
        userRepository.update(user);
    }

    public boolean isGranted(TUser user, Permission permission) {
        return isGranted(user.getId(), permission);
    }

    public boolean isGranted(String userId, Permission permission) {
        TUser user = userRepository.get(userId);

        if (user.isAdmin()) {
            return true;
        }
        Set<String> permissions = permissionManager.getUserPermissions(userId);
        return permissions.contains(permission.getName());
    }

    public TUser register (UserLogin userLogin, String userName, String nickName, String avatar) throws IllegalAccessException, InstantiationException {
        Class<TUser> tUserClass = getTClass(0);
        TUser user = tUserClass.newInstance();
        TRole role = roleRepository.findDefault();

        user.setUserName(userName);
        user.setPassword(passwordUtil.encrypt(user.getUserName(), CbpUser.DEFAULT_PASSWORD));
        user.setNickName(nickName);
        user.setAvatar(avatar);
        user.setNumber(userNumberProvider.generateNumber(user, userLogin));

        if (role!=null) {
            user.getRoles().add(role);
        }

        userRepository.insert(user);
        return user;
    }

    public TUser getUser(UserIdentifier userIdentifier) {
        TUser user = getUserOrNull(userIdentifier);

        if (user == null) {
            throw new BusinessException("There is no user: " + userIdentifier);
        }
        return user;
    }

    public TUser getUserOrNull(UserIdentifier userIdentifier) {
        return userRepository.get(userIdentifier.getUserId());
    }
}
