package com.company.controller;

import com.company.dto.CardDTO;
import com.company.dto.ClientDTO;
import com.company.dto.request.AssignPhoneDTO;
import com.company.dto.request.CardRequestDTO;
import com.company.entity.SalesCardNumber;
import com.company.enums.EntityStatus;
import com.company.service.CardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/card")
public class CardController {

    private final CardService cardService;


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CardRequestDTO dto) {
        return ResponseEntity.ok(cardService.create(dto));
    }


    @PutMapping("/{cardNum}")
    public ResponseEntity<?> changeStatus(@PathVariable("cardNum") String cardNum,
                                          @RequestParam("status") EntityStatus status) {
        return ResponseEntity.ok(cardService.changeStatus(cardNum, status));
    }

    @PutMapping("/assign")
    public ResponseEntity<?> assignPhone(@RequestBody AssignPhoneDTO dto) {
        return ResponseEntity.ok(cardService.assignPhone(dto));
    }

    @GetMapping("/{phoneNum}")
    public ResponseEntity<?> getByPhone(@PathVariable("phoneNum") String phoneNumber) {
        return ResponseEntity.ok(cardService.getByPhone(phoneNumber));
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<?> getByClientId(@PathVariable("id") String id) {
        return ResponseEntity.ok(cardService.getByClientId(id));
    }

    @GetMapping("/card/{cardNumber}")
    public ResponseEntity<CardDTO> getByCardNumber(@PathVariable("cardNumber") String number){
        return ResponseEntity.ok(cardService.getByCardNum(number));
    }

    @GetMapping("/card/{cardNumber}/balance")
    public ResponseEntity<String> getBalanceByCardNumber(@PathVariable("cardNumber") String number){
        return ResponseEntity.ok(cardService.getBalanceByCardNumber(number));
    }

    @PostMapping("/generator")
    public ResponseEntity<List<SalesCardNumber>> generatorCardNumber() {
        return ResponseEntity.ok(cardService.generatorSaveCardNumber());
    }
}
