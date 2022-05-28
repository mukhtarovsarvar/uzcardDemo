package com.company.dto.request;


import com.company.enums.TransactionalStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionFilterDTO {
    private String client_id;
    private String cardNumber;
    private String cardId;
    private Long fromAmount;
    private Long toAmount;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String profile_name;
    private TransactionalStatus status;
}
