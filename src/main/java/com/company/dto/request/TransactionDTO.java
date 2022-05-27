package com.company.dto.request;

import com.company.enums.TransactionalStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {
    private  String fromCard;
    private  String toCard;
    private  Long amount;
    private  String profileName;
}
