package com.company.entity;


import com.company.enums.EntityStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Setter
@Getter
public class ClientEntity extends BaseEntity {

    private String name;

    private String surname;

    private String phone;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

}
