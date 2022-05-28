package com.company.dto.request;

import com.company.enums.EntityStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardFilterDTO {


    private String clientId;

    private String cardNumber;

    private String cardId;

    private Long amountFrom;

    private Long toAmount;

    private String profileName;

    private EntityStatus status;


}
