package com.company.dto.request;

import com.company.dto.BaseDTO;
import com.company.dto.CardDTO;
import com.company.enums.TransactionalStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class TransactionDTO extends BaseDTO {

    @NotBlank(message = "FromCardNumber required")
    private String fromCardNumber;
    private CardDTO fromCard;

    @NotBlank(message = "ToCardNumber required")
    private String toCardNumber;
    private CardDTO toCard;

    @Positive(message = "Amount must be positive number")
    @NotNull(message = "Amount cannot be null")
    private Long amount;

    private String cash;

    private TransactionalStatus status;

    private String profileName;

}
