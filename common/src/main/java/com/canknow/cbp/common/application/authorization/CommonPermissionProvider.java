package com.canknow.cbp.common.application.authorization;

import com.canknow.cbp.base.authorization.IPermissionDefinitionContext;
import com.canknow.cbp.base.authorization.Permission;
import com.canknow.cbp.base.authorization.PermissionProvider;
import com.canknow.cbp.base.exceptions.ApplicationException;
import org.springframework.stereotype.Component;

@Component("commonPermissionProvider")
public class CommonPermissionProvider extends PermissionProvider {

    @Override
    public void registerPermissions(IPermissionDefinitionContext context) throws ApplicationException {
        Permission userPermission = context.createPermission(CommonPermissions.User.VALUE);
        userPermission.createChildPermission(CommonPermissions.User.CREATE);
        userPermission.createChildPermission(CommonPermissions.User.UPDATE);
        userPermission.createChildPermission(CommonPermissions.User.GET);
        userPermission.createChildPermission(CommonPermissions.User.GET_ALL);
        userPermission.createChildPermission(CommonPermissions.User.DELETE);
        userPermission.createChildPermission(CommonPermissions.User.LOCK);
        userPermission.createChildPermission(CommonPermissions.User.UNLOCK);
        userPermission.createChildPermission(CommonPermissions.User.CHANGE_PASSWORD);

        Permission rolePermission = context.createPermission(CommonPermissions.Role.VALUE);
        rolePermission.createChildPermission(CommonPermissions.Role.CREATE);
        rolePermission.createChildPermission(CommonPermissions.Role.UPDATE);
        rolePermission.createChildPermission(CommonPermissions.Role.GET);
        rolePermission.createChildPermission(CommonPermissions.Role.GET_ALL);
        rolePermission.createChildPermission(CommonPermissions.Role.DELETE);

        Permission organizationPermission = context.createPermission(CommonPermissions.OrganizationUnit.VALUE);
        organizationPermission.createChildPermission(CommonPermissions.OrganizationUnit.CREATE);
        organizationPermission.createChildPermission(CommonPermissions.OrganizationUnit.UPDATE);
        organizationPermission.createChildPermission(CommonPermissions.OrganizationUnit.GET);
        organizationPermission.createChildPermission(CommonPermissions.OrganizationUnit.GET_ALL);
        organizationPermission.createChildPermission(CommonPermissions.OrganizationUnit.DELETE);

        Permission auditLogPermission = context.createPermission(CommonPermissions.AuditLog.VALUE);
        auditLogPermission.createChildPermission(CommonPermissions.AuditLog.DELETE);
        auditLogPermission.createChildPermission(CommonPermissions.AuditLog.GET_ALL);

        Permission backgroundJobPermission = context.createPermission(CommonPermissions.BackgroundJobInfo.VALUE);
        backgroundJobPermission.createChildPermission(CommonPermissions.BackgroundJobInfo.DELETE);
        backgroundJobPermission.createChildPermission(CommonPermissions.BackgroundJobInfo.GET_ALL);

        Permission loginAttemptPermission = context.createPermission(CommonPermissions.LoginAttempt.VALUE);
        loginAttemptPermission.createChildPermission(CommonPermissions.LoginAttempt.DELETE);
        loginAttemptPermission.createChildPermission(CommonPermissions.LoginAttempt.GET_ALL);

        Permission feedbackPermission = context.createPermission(CommonPermissions.Feedback.VALUE);
        feedbackPermission.createChildPermission(CommonPermissions.Feedback.DELETE);
        feedbackPermission.createChildPermission(CommonPermissions.Feedback.GET_ALL);

        Permission filePermission = context.createPermission(CommonPermissions.File.VALUE);
        filePermission.createChildPermission(CommonPermissions.File.GET_ALL);

        Permission regionPermission = context.createPermission(CommonPermissions.Region.VALUE);
        regionPermission.createChildPermission(CommonPermissions.Region.GET_ALL);
    }
}
