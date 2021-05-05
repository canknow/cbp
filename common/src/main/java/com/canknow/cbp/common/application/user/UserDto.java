package com.canknow.cbp.common.application.user;

import com.canknow.cbp.base.application.dto.FullAuditedDto;
import com.canknow.cbp.base.autoMapper.AutoMap;
import com.canknow.cbp.common.domain.common.Gender;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserDto extends FullAuditedDto {
    private static final long serialVersionUID = -888175911865317154L;
    private String userName;

    private String number;

    private String avatar;

    private String phoneNumber;

    private boolean isPhoneNumberConfirmed;

    private String emailAddress;

    private boolean isEmailConfirmed;

    private Gender gender;

    private LocalDateTime birthDay;

    private String fullName;

    private String nickName;

    private boolean isActive;

    private boolean isDisabled;

    private boolean isStatic;

    private boolean isLocked;

    private int accessFailedCount;

    private String roleId;

    @AutoMap(type = RoleDto.class)
    private Set<RoleDto> roles;
}
