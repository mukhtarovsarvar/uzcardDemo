package com.company.repository;

import com.company.entity.TransactionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity, String> {
}