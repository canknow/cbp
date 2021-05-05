package com.canknow.cbp.base.application.applicationService;

import com.canknow.cbp.base.ServiceBase;
import com.canknow.cbp.base.authorization.IPermissionChecker;
import com.canknow.cbp.base.authorization.PermissionManager;
import com.canknow.cbp.base.runtime.session.IApplicationSession;
import com.canknow.cbp.base.settings.ISettingManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ApplicationService extends ServiceBase implements IApplicationService {
    @Autowired(required = false)
    public IApplicationSession applicationSession;

    @Autowired
    public PermissionManager permissionManager;

    @Autowired
    public IPermissionChecker permissionChecker;

    @Autowired
    protected ISettingManager _settingManager;

    protected void checkPermission(String permissionName) {
        if (StringUtils.isEmpty(permissionName)) {
            return;
        }
        if (!permissionChecker.isGranted(permissionName)) {
            throw new UnauthorizedException();
        }
    }
}
