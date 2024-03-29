package com.company.repository;

import com.company.entity.CardEntity;
import com.company.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, String> {

    Optional<CardEntity> findByNumber(String number);


    @Modifying
    @Transactional
    @Query("update cards set status = :status where  number = :cardNum")
    void changeStatus(@Param("cardNum") String cardNum, @Param("status") EntityStatus status);

    @Modifying
    @Transactional
    @Query("update cards set phoneNUmber = :phoneNumber where  number =:cardNum")
    void updatePhone(@Param("phoneNumber") String phoneNumber,@Param("cardNum") String cardNum);

    List<CardEntity> findByPhoneNUmberAndStatus(String phone,EntityStatus status);

    List<CardEntity> findByClientId(String id);


    @Modifying
    @Transactional
    @Query("update cards set balance = :balance where  number = :cardNum")
    void updateBalance(@Param("balance") Long balance, @Param("cardNum") String cardNum);


}