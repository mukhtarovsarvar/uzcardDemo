package com.company.controller;

import com.company.service.TransactionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/transaction")

public class TransactionHistoryController {

    private final TransactionalService transactionalService;


}
