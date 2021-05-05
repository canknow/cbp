package com.canknow.cbp.base.authorization;

import com.canknow.cbp.base.exceptions.ApplicationException;

public interface IUserManager {
    boolean isGranted(String userId, String permissionName) throws ApplicationException;

    void unlockUser(String userId);

    void lockUser(String userId);

    void changePassword(String userId, String oldPassword, String newPassword);

    void changePassword(String userId, String newPassword);
}
