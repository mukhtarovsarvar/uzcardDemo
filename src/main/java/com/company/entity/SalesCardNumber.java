package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class SalesCardNumber extends BaseEntity {


    private String cardNumber;

}
