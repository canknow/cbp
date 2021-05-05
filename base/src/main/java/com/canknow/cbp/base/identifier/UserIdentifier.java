package com.canknow.cbp.base.identifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentifier implements IUserIdentifier {
    private String userId;

    public static UserIdentifier parse(String userId) {
        return new UserIdentifier(userId);
    }
}
