package com.canknow.cbp.base.notification;

import com.canknow.cbp.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserNotificationState  implements BaseEnum {
    Unread(0, "unread", "unread"),
    Read(1, "read", "read");

    private final Integer code;

    private final String name;

    private final String description;
}
