package com.company.controller;

import com.company.dto.CardDTO;
import com.company.dto.request.AssignPhoneDTO;
import com.company.dto.request.CardFilterDTO;
import com.company.dto.request.CardRequestDTO;
import com.company.entity.SalesCardNumber;
import com.company.enums.EntityStatus;
import com.company.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/card")
@Api(tags = "Card")
public class CardController {

    private final CardService cardService;


    @PostMapping("/create")
    @ApiOperation(value = "Create card", notes = "Method used for create card")
    public ResponseEntity<?> create(@RequestBody CardRequestDTO dto) {
        log.info("create card: {}",dto);
        return ResponseEntity.ok(cardService.create(dto));
    }


    @PutMapping("/{cardNum}")
    @ApiOperation(value = "Change status", notes = "Method used for change status card")
    public ResponseEntity<?> changeStatus(@PathVariable("cardNum") String cardNum,
                                          @RequestParam("status") EntityStatus status) {
        log.info("change status card: {} status  {}",cardNum,status);
        return ResponseEntity.ok(cardService.changeStatus(cardNum, status));
    }

    @PutMapping("/assign")
    @ApiOperation(value = "assign phone ", notes = "Method used for assign phone (card set phone)")
    public ResponseEntity<?> assignPhone(@RequestBody AssignPhoneDTO dto) {
        log.info("assign phone: {}",dto);
        return ResponseEntity.ok(cardService.assignPhone(dto));
    }

    @GetMapping("/{phoneNum}")
    @ApiOperation(value = "Get cards by phone", notes = "Method used for Get cards by phone")
    public ResponseEntity<?> getByPhone(@PathVariable("phoneNum") String phoneNumber) {
        log.info("get card by phone: {}",phoneNumber);
        return ResponseEntity.ok(cardService.getByPhone(phoneNumber));
    }

    @GetMapping("/client/{id}")
    @ApiOperation(value = "Get cards by client id", notes = "Method used for Get cards by client id")
    public ResponseEntity<?> getByClientId(@PathVariable("id") String id) {
        log.info("get card by clientId: {}",id);
        return ResponseEntity.ok(cardService.getByClientId(id));
    }

    @GetMapping("/card/{cardNumber}")
    @ApiOperation(value = "Get card by card number", notes = "Method used for Get card by card number")
    public ResponseEntity<CardDTO> getByCardNumber(@PathVariable("cardNumber") String number) {
        log.info("get card by cardNumber {}",number);
        return ResponseEntity.ok(cardService.getByCardNum(number));
    }


    @GetMapping("/card/{cardNumber}/balance")
    @ApiOperation(value = "get balance by card number", notes = "Method used for get balance by card number")
    public ResponseEntity<String> getBalanceByCardNumber(@PathVariable("cardNumber") String number) {
        log.info("get card balance by cardNumber: {}",number);
        return ResponseEntity.ok(cardService.getBalanceByCardNumber(number));
    }


    @PostMapping("/generator")
    @ApiOperation(value = "generator sales card ", notes = "Method used for generator sales card")
    public ResponseEntity<List<SalesCardNumber>> generatorCardNumber() {
        log.info("generator sales card!");
        return ResponseEntity.ok(cardService.generatorSaveCardNumber());
    }

    @PostMapping("/filter")
    @ApiOperation(value = "filter search", notes = "Method used for filter search")
    public ResponseEntity<?> filter(@RequestBody CardFilterDTO dto) {
        log.info("get filter: {}",dto);
        return ResponseEntity.ok(cardService.filter(dto));
    }

}
