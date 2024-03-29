package com.company.repository;

import com.company.entity.SalesCardNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesCardNumberRepository extends JpaRepository<SalesCardNumber, String> {

    Optional<SalesCardNumber> findByCardNumber(String cardNum);

    Optional<SalesCardNumber> findTop1By();
}