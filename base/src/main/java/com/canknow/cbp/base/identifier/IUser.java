package com.canknow.cbp.base.identifier;

import com.canknow.cbp.base.domain.entities.IEntity;

public interface IUser extends IEntity {
    String getUserName();

    String getPassword();
}
