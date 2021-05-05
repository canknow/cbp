package com.canknow.cbp.common.domain.user;

import com.canknow.cbp.base.domain.entities.IPassivable;
import com.canknow.cbp.base.domain.entities.audit.FullAuditedAggregateRoot;
import com.canknow.cbp.base.identifier.IUser;
import com.canknow.cbp.base.identifier.UserIdentifier;
import com.canknow.cbp.common.domain.authorization.UserLogin;
import com.canknow.cbp.common.domain.common.Gender;
import com.canknow.cbp.common.domain.role.CbpRole;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@MappedSuperclass
public abstract class CbpUser<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>> extends FullAuditedAggregateRoot implements Principal, IPassivable, IUser {

    public final static String ADMIN_USER_NAME = "admin";

    public static final String DEFAULT_PASSWORD = "123456";
    private static final long serialVersionUID = 7861065679724034012L;

    public boolean isAdmin() {
        return userName.equals(ADMIN_USER_NAME);
    }

    public UserIdentifier toUserIdentifier() {
        return new UserIdentifier(this.id);
    }

    @Column(nullable = false, length = 64, unique = true)
    private String userName;

    private String avatar;

    private String number;

    public String getShownUserName () {
        return StringUtils.isNoneBlank(fullName) ? fullName :
                StringUtils.isNoneBlank(nickName) ? nickName :
                        userName;
    }

    @Column(length = 64)
    private String invitorUserId;

    private LocalDateTime invitationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitorUserId", insertable = false, updatable = false)
    private TUser invitorUser;

    @Column(length = 20, unique = true)
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean isPhoneNumberConfirmed;

    @Column(length = 20, unique = true)
    private String emailAddress;

    private Gender gender;

    private LocalDateTime birthDay;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean isEmailConfirmed;

    private String cardId;

    private String fullName;

    private String nickName;

    private String password;

    private String remark;

    @Column(nullable = false, columnDefinition = "bit(1) default 1")
    private boolean isActive = true;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean isStatic;

    @Column(nullable = false, columnDefinition = "int(11) default 0")
    private int accessFailedCount = 0;

    // 是否已被锁定。被锁定的用户无法登录，直至解锁
    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean isLocked;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TRole> roles = new HashSet<>();

    @Column(length = 64)
    private String currentOrganizationUnitId;

    @OneToMany(cascade = { CascadeType.MERGE }, fetch= FetchType.LAZY)
    private Set<UserLogin> userLogins = new HashSet<>();

    @Override
    public String toString() {
        return String.format("user {%s} {%s}", id, userName);
    }

    @Override
    public String getName() {
        return this.userName;
    }

    public void lock() {
        this.isLocked = true;
    }

    public void unlock() {
        this.isLocked = false;
    }

    public boolean hasRole(String roleId) {
        return roles.stream().anyMatch(role -> role.getId().equals(roleId));
    }
}
