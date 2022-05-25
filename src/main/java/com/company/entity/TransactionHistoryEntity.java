package com.company.entity;

import com.company.enums.TransactionalStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
public class TransactionHistoryEntity extends BaseEntity {

    private String fromCard;

    private String toCard;

    private Long amount;

    private String profileName;

    @Enumerated(EnumType.STRING)
    private TransactionalStatus status;

}
