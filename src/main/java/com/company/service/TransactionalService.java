package com.company.service;

import com.company.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionalService {

    private final TransactionHistoryRepository transactionRepository;



}
