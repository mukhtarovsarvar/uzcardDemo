package com.company.repository;

import com.company.entity.SalesCardNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesCardNumberRepository extends JpaRepository<SalesCardNumber, String> {
}