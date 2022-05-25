package com.company.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "create_date")
    protected LocalDateTime createDate = LocalDateTime.now();
    @Column(name = "update_date")
    protected LocalDateTime updateDate;

}
