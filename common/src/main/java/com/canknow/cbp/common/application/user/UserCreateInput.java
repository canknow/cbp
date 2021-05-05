package com.canknow.cbp.common.application.user;

import com.canknow.cbp.base.application.dto.CreateInput;
import com.canknow.cbp.base.application.dto.IdInput;
import com.canknow.cbp.common.domain.common.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserCreateInput extends CreateInput {
    private String userName;

    private String fullName;

    private String nickName;

    private String password;

    private String phoneNumber;

    private String avatar;

    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthDay;

    private boolean isActive;

    private Set<IdInput> roles;
}
