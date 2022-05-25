package com.company.controller;

import com.company.dto.ClientDTO;
import com.company.dto.reponse.ClientResponseDTO;
import com.company.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/client")

public class ClientController {

    private final ClientService clientService;


    @PostMapping("")
    public ResponseEntity<ClientDTO> create(@RequestBody ClientResponseDTO dto){
        return ResponseEntity.ok(clientService.create(dto));
    }


}
