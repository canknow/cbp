package com.canknow.cbp.common.domain.gis;

import lombok.Data;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Data
@Embeddable
@Access(AccessType.FIELD)
public class Location {
    private Double latitude;

    private Double longitude;

    private String address;
}
