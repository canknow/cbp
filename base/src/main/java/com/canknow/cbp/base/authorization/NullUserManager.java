package com.canknow.cbp.base.authorization;

import com.canknow.cbp.base.exceptions.ApplicationException;
import org.springframework.stereotype.Service;

@Service("nullUserManager")
public class NullUserManager implements IUserManager {
    @Override
    public boolean isGranted(String userId, String permissionName) throws ApplicationException {
        return false;
    }

    @Override
    public void unlockUser(String userId) {

    }

    @Override
    public void lockUser(String userId) {

    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {

    }

    @Override
    public void changePassword(String userId, String newPassword) {

    }
}
