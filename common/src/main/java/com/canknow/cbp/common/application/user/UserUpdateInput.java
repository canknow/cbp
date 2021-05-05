package com.canknow.cbp.common.application.user;

import com.canknow.cbp.base.application.dto.IdInput;
import com.canknow.cbp.base.application.dto.UpdateInput;
import com.canknow.cbp.base.constants.FormatConstant;
import com.canknow.cbp.common.domain.common.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserUpdateInput extends UpdateInput {
    private static final long serialVersionUID = 7925375722997079259L;

    private String userName;

    private String password;

    private String avatar;

    private Gender gender;

    private String fullName;

    private String nickName;

    private String phoneNumber;

    @DateTimeFormat(pattern = FormatConstant.DATA_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatConstant.DATA_TIME_FORMAT)
    private LocalDateTime birthDay;

    private Set<IdInput> roles;

    private boolean isActive;
}
