package com.company.repository;

import com.company.entity.TransactionHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity, String> {


    Page<TransactionHistoryEntity> findByToCard(String cardNum, Pageable pageable);

    Page<TransactionHistoryEntity> findByFromCard(String cardNum, Pageable pageable);

    Page<TransactionHistoryEntity> findByProfileName(String profileName,Pageable pageable);
}