package com.company.dto;

import com.company.enums.TransactionalStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionHistoryDTO   {
    private  String uuid;
    private  LocalDateTime createDate;
    private  String fromCard;
    private  String toCard;
    private  Long amount;
    private  String profileName;
    private  TransactionalStatus status;
}
