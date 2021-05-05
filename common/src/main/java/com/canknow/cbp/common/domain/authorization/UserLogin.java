package com.canknow.cbp.common.domain.authorization;

import com.canknow.cbp.base.domain.entities.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Max;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserLogin extends AggregateRoot {
    public static final int MaxLoginProviderLength = 256;
    public static final int MaxProviderKeyLength = 256;
    private static final long serialVersionUID = -8878701851793973104L;

    @Column(length = 64)
    private String userId;

    @Max(MaxLoginProviderLength)
    private String loginProvider;

    @Max(MaxProviderKeyLength)
    private String providerKey;
}
