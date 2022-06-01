package com.company.repository;

import com.company.entity.ClientEntity;
import com.company.enums.EntityStatus;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, String> {

    Optional<ClientEntity> findByPhone(String phone);

    @Modifying
    @Transactional
    @Query("update clients set status = :status where uuid = :id")
    void updateStatus(@Param("id") String id, @Param("status")EntityStatus status);



    @Modifying
    @Transactional
    @Query("update clients set phone = :newPhone where phone = :phone")
    void updatePhone(@Param("phone") String phone, @Param("newPhone")String newPhone);


    Optional<ClientEntity> findByPhoneAndProfileNameAndStatus(String phone,String name,EntityStatus status);

    Optional<ClientEntity> findByUuidAndProfileNameAndStatus(String uuid,String name,EntityStatus status);


    List<ClientEntity> findByProfileNameAndStatus(String name, EntityStatus status);

}