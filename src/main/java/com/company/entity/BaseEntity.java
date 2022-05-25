package com.company.entity;


import com.company.enums.EntityStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {


    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String uuid;

    private LocalDateTime createDate = LocalDateTime.now();




}
