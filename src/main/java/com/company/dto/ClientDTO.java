package com.company.dto;

import com.company.enums.EntityStatus;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ClientDTO  {
    private  String uuid;
    private  LocalDateTime createDate;
    private  EntityStatus status;
    private  String name;
    private  String surname;
    private  String phone;

    public ClientDTO(String clf_id, String clf_name, String clf_surname, String clf_phone) {

    }
}
