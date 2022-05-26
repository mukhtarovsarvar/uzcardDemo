package com.company.controller;

import com.company.dto.request.CardRequestDTO;
import com.company.entity.SalesCardNumber;
import com.company.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CardRequestDTO dto) {
        return ResponseEntity.ok(cardService.create(dto));
    }


    @PostMapping("/generator")
    public ResponseEntity<List<SalesCardNumber>> generatorCardNumber() {
        return ResponseEntity.ok(cardService.generatorSaveCardNumber());
    }
}
