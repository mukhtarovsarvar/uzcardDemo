package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CardEntity extends BaseEntity {


    private String number;

    private String expDate;

    private Long balance;

    @Column(name = "client_id")
    private String clientId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id",updatable = false,insertable = false)
    private ClientEntity client;

    private String phoneNUmber;

}
