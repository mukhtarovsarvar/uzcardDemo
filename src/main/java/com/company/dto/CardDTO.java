package com.company.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class CardDTO  {
    private  String uuid;
    private  LocalDateTime createDate;
    private  String number;
    private  String expDate;
    private  Long balance;
    private  String clientId;
    private  String phoneNUmber;
}
