package com.canknow.cbp.base.domain.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class EntityBase implements IEntity {
    private static final long serialVersionUID = 1175326487032066665L;
    @Id
    @Column(length = 64)
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成64位UUID
    @GeneratedValue(generator="idGenerator")
    protected String id;

    public Boolean getIsTransient() {
        return false;
    }
}
