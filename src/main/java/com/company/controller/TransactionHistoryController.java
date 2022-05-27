package com.company.controller;

import com.company.dto.request.TransactionDTO;
import com.company.service.TransactionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ResolvedPointcutDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/transaction")
public class TransactionHistoryController {

    private final TransactionalService transactionalService;


    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody TransactionDTO dto) {
        return ResponseEntity.ok(transactionalService.create(dto));
    }

    @GetMapping("/{cardNum}")
    public ResponseEntity<?> getByCardNumber(@PathVariable("cardNum") String cardNum,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(transactionalService.getByCardNumber(cardNum, page, size));
    }


    @GetMapping("/{clientId}/client")
    public ResponseEntity<?> getByClientId(@PathVariable("clientId") String clientId,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(transactionalService.getByClientId(clientId, page, size));
    }


    @GetMapping("/{phone}/phone")
    public ResponseEntity<?> getByPhone(@PathVariable("phone") String phone,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(transactionalService.getByPhone(phone, page, size));
    }


    @GetMapping("/{profileName}/profileName")
    public ResponseEntity<?> getByProfileNme(@PathVariable("profileName") String clientId,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(transactionalService.getByProfileName(clientId, page, size));
    }


}
