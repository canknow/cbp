package com.canknow.cbp.common.application.authorization;

public class CommonPermissions {
    public static final class User {
        public final static String VALUE = "common.user";

        public final static String CREATE = "common.user.create";

        public final static String DELETE = "common.user.delete";

        public final static String UPDATE = "common.user.update";

        public final static String GET_ALL = "common.user.getAll";

        public static final String GET = "common.user.get";

        public static final String LOCK = "common.user.lock";

        public static final String UNLOCK = "common.user.unlock";

        public static final String CHANGE_PASSWORD = "common.user.changePassword";
    }

    public static final class Role {
        public final static String VALUE = "common.role";

        public final static String CREATE = "common.role.create";

        public final static String DELETE = "common.role.delete";

        public final static String UPDATE = "common.role.update";

        public final static String GET = "common.role.get";

        public final static String GET_ALL = "common.role.getAll";
    }

    public static final class OrganizationUnit {
        public final static String VALUE = "common.organizationUnit";

        public final static String CREATE = "common.organizationUnit.create";

        public final static String DELETE = "common.organizationUnit.delete";

        public final static String UPDATE = "common.organizationUnit.update";

        public final static String GET = "common.organizationUnit.get";

        public final static String GET_ALL = "common.organizationUnit.getAll";
    }

    public static final class AuditLog {
        public final static String VALUE = "common.auditLog";

        public final static String DELETE = "common.auditLog.delete";

        public final static String GET_ALL = "common.auditLog.getAll";
    }

    public static final class LoginAttempt {
        public final static String VALUE = "common.loginAttempt";

        public final static String DELETE = "common.loginAttempt.delete";

        public final static String GET_ALL = "common.loginAttempt.getAll";
    }

    public static final class Feedback {
        public final static String VALUE = "common.feedback";

        public final static String DELETE = "common.feedback.delete";

        public final static String GET_ALL = "common.feedback.getAll";
    }

    public static final class File {
        public final static String VALUE = "common.file";

        public final static String GET_ALL = "common.file.getAll";
    }

    public static final class Region {
        public final static String VALUE = "common.region";

        public final static String GET_ALL = "common.region.getAll";
    }

    public class BackgroundJobInfo {
        public final static String VALUE = "common.backgroundJob";

        public final static String DELETE = "common.backgroundJob.delete";

        public final static String GET_ALL = "common.backgroundJob.getAll";
    }
}
